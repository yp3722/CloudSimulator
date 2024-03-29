import utils.configs.{DataCenterConfig, HostConfig, PaaSParamsConfig, SaaSParamsConfig}
import utils.{CloudletUtils, CreateLogger, DataCenterUtils, HostUtils, PowerUtils, VMUtils}
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.cloudlets.CloudletSimple
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.DatacenterSimple
import org.cloudbus.cloudsim.hosts.HostSimple
import org.cloudbus.cloudsim.resources.PeSimple
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic
import org.cloudbus.cloudsim.vms.VmSimple
import org.cloudsimplus.builders.tables.CloudletsTableBuilder
import utils.CostUtils.printTotalVmsCost
import utils.HostUtils.*
import utils.VMUtils.*

import java.util.List

object PaaSImplementation {


  //vm parameters from user from cloud provided options as this is PaaS model
  val vmtype = PaaSParamsConfig.getVMType
  val vmcount = PaaSParamsConfig.getVMCount

  //application parameters form user as this is PaaS model
  val minimumUtilizationPaaS = PaaSParamsConfig.getApplicationMinUtil
  val maximumUtilizationPaaS = PaaSParamsConfig.getApplicationMaxUtil
  val lengthPaaS = PaaSParamsConfig.getCloudletLen
  val peCountPaaS = PaaSParamsConfig.getPECount
  val applicatonCount = PaaSParamsConfig.getInstanceCount
  val enableDelay = PaaSParamsConfig.getSubmissionDelay

  //Datacenter cost params
  val ramCost = HostConfig.getRamCost
  val timeCost = HostConfig.getTimeCost
  val storageCost = HostConfig.getStorageCost
  val BWCost = HostConfig.getBWCost

  @main
  def paaSImplementation(): Unit = {

    //logger instantiation
    val logger = CreateLogger(classOf[PaaSImplementation.type])

    //Creates a CloudSim object to initialize the simulation.
    val PaaS_simulation = new CloudSim

    //Creates a Broker that will act on behalf of a cloud user (customer).
    val broker = new DatacenterBrokerSimple(PaaS_simulation)

    //Creates a Datacenter with a list of Hosts and add cost simulation data
    val dc0 = DataCenterUtils.getSimpleDataCenter(PaaS_simulation)
    dc0.getCharacteristics()
      .setCostPerSecond(timeCost)
      .setCostPerMem(ramCost)
      .setCostPerStorage(storageCost)
      .setCostPerBw(BWCost);

    //create VMs and submit to broker
    broker.submitVmList(utils.VMUtils.getVMSimple(vmtype,vmcount))

    //Create cloudletts and submit to broker as per user specification
    broker.submitCloudletList(CloudletUtils.getUserApplicationCloudlet(minimumUtilizationPaaS,maximumUtilizationPaaS,lengthPaaS,applicatonCount,peCountPaaS,enableDelay))

    /*Starts the simulation and waits all cloudlets to be executed, automatically
    stopping when there is no more events to process.*/
    PaaS_simulation.start

    /*Prints the results when the simulation is over*/
    logger.info("starting PaaS simulation")
    new CloudletsTableBuilder(broker.getCloudletFinishedList).build()
    logger.info("End of PaaS simulation")

    //Print Cost data
    printTotalVmsCost(broker)

    //Print Powerconsumption data
    PowerUtils.getPowerConsumptionStats(dc0.getHostList)
  }

}

import utils.configs.{HostConfig, IaaSParamsConfig}
import utils.{CreateLogger, HostUtils, VMUtils}
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.cloudlets.CloudletSimple
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.DatacenterSimple
import org.cloudbus.cloudsim.hosts.HostSimple
import org.cloudbus.cloudsim.resources.PeSimple
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic
import org.cloudbus.cloudsim.vms.VmSimple
import org.cloudsimplus.builders.tables.CloudletsTableBuilder
import utils.CloudletUtils
import utils.HostUtils.*
import utils.VMUtils.getCustomVM
import utils.configs.SaaSParamsConfig

import java.util.List

object IaaSImplementation {



  //vm parameters from user
  val Vram = IaaSParamsConfig.getVRam
  val Vstorage = IaaSParamsConfig.getStorage
  val Vbw = IaaSParamsConfig.getBW
  val Vpe_cap = IaaSParamsConfig.getVPECapacity
  val Vpe_count = IaaSParamsConfig.getVPECount
  val VMcount = IaaSParamsConfig.getVmCount 
  val VCloudletScheduler = IaaSParamsConfig.getScheduler

  //application parameters form user
  val minimumUtilizationIaaS = IaaSParamsConfig.getApplicationMinUtil
  val maximumUtilizationIaaS = IaaSParamsConfig.getApplicationMaxUtil
  val lengthIaaS = IaaSParamsConfig.getCloudletLen
  val peCountIaaS = IaaSParamsConfig.getPECount
  val applicatonCount = IaaSParamsConfig.getInstanceCount

  @main
  def iaaSImplementation(): Unit = {

    //logger instantiation
    val logger = CreateLogger(classOf[IaaSImplementation.type])

    //Creates a CloudSim object to initialize the simulation.
    val IaaS_simulation = new CloudSim

    //Creates a Broker that will act on behalf of a cloud user (customer).
    val broker = new DatacenterBrokerSimple(IaaS_simulation)

    //Creates a Datacenter with a list of Hosts.
    val dc0 = new DatacenterSimple(IaaS_simulation, utils.HostUtils.getSimpleHosts(30))

    //create VMs and submit to broker
    //Uses a CloudletSchedulerTimeShared by default to schedule Cloudlets
    broker.submitVmList(utils.VMUtils.getCustomVM(Vram,Vstorage,Vbw,Vpe_cap,Vpe_count,VMcount,VCloudletScheduler))

    //Create cloudletts and submit to broker
    broker.submitCloudletList(CloudletUtils.getUserApplicationCloudlet(minimumUtilizationIaaS,maximumUtilizationIaaS,lengthIaaS,applicatonCount,peCountIaaS))

    /*Starts the simulation and waits all cloudlets to be executed, automatically
    stopping when there is no more events to process.*/
    IaaS_simulation.start

    /*Prints the results when the simulation is over*/
    logger.info("starting IaaS simulation")
    new CloudletsTableBuilder(broker.getCloudletFinishedList).build()
    logger.info("End of IaaS simulation")
  }

}

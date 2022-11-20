import utils.configs.{HostConfig, PaaSParamsConfig}
import utils.{CreateLogger, HostUtils, VMUtils}

object PaaSImplementation {

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
  import utils.VMUtils.*
  import utils.configs.SaaSParamsConfig

  import java.util.List

  //vm parameters from user
  val vmtype = PaaSParamsConfig.getVMType
  val vmcount = PaaSParamsConfig.getVMCount

  //application parameters form user
  val minimumUtilizationPaaS = PaaSParamsConfig.getApplicationMinUtil
  val maximumUtilizationPaaS = PaaSParamsConfig.getApplicationMaxUtil
  val lengthPaaS = PaaSParamsConfig.getCloudletLen
  val peCountPaaS = PaaSParamsConfig.getPECount
  val applicatonCount = PaaSParamsConfig.getInstanceCount

  @main
  def paaSImplementation(): Unit = {

    //logger instantiation
    val logger = CreateLogger(classOf[PaaSImplementation.type])

    //Creates a CloudSim object to initialize the simulation.
    val PaaS_simulation = new CloudSim

    //Creates a Broker that will act on behalf of a cloud user (customer).
    val broker = new DatacenterBrokerSimple(PaaS_simulation)

    //Creates a Datacenter with a list of Hosts.
    val dc0 = new DatacenterSimple(PaaS_simulation, utils.HostUtils.getSimpleHosts(20))

    //create VMs and submit to broker
    //Uses a CloudletSchedulerTimeShared by default to schedule Cloudlets
    broker.submitVmList(utils.VMUtils.getVMSimple(vmtype,vmcount))

    //Create cloudletts and submit to broker
    broker.submitCloudletList(CloudletUtils.getUserApplicationCloudlet(minimumUtilizationPaaS,maximumUtilizationPaaS,lengthPaaS,applicatonCount,peCountPaaS))

    /*Starts the simulation and waits all cloudlets to be executed, automatically
    stopping when there is no more events to process.*/
    PaaS_simulation.start

    /*Prints the results when the simulation is over*/
    logger.info("starting PaaS simulation")
    new CloudletsTableBuilder(broker.getCloudletFinishedList).build()
    logger.info("End of PaaS simulation")
  }

}

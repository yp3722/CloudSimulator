import utils.configs.HostConfig
import utils.{CreateLogger, HostUtils, VMUtils}

object SaaSImplementation {

  import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
  import org.cloudbus.cloudsim.cloudlets.CloudletSimple
  import org.cloudbus.cloudsim.core.CloudSim
  import org.cloudbus.cloudsim.datacenters.DatacenterSimple
  import org.cloudbus.cloudsim.hosts.HostSimple
  import org.cloudbus.cloudsim.resources.PeSimple
  import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic
  import org.cloudbus.cloudsim.vms.VmSimple
  import org.cloudsimplus.builders.tables.CloudletsTableBuilder
  import utils.HostUtils.*
  import utils.VMUtils.*
  import utils.CloudletUtils
  import java.util.List
  import utils.configs.SaaSParamsConfig


  @main
  def saaSImplementation(): Unit = {

    //logger instantiation
    val logger = CreateLogger(classOf[HostConfig.type]) 

    //Creates a CloudSim object to initialize the simulation.
    val SaaS_simulation = new CloudSim

    //Creates a Broker that will act on behalf of a cloud user (customer).
    val broker = new DatacenterBrokerSimple(SaaS_simulation)

    //Creates a Datacenter with a list of Hosts.
    val dc0 = new DatacenterSimple(SaaS_simulation, utils.HostUtils.getSimpleHosts(5))

    //create VMs and submit to broker
    //Uses a CloudletSchedulerTimeShared by default to schedule Cloudlets
    broker.submitVmList(utils.VMUtils.getVMSimple("MEDIUM",20,""))

    //Create cloudletts and submit to broker
    broker.submitCloudletList(CloudletUtils.getCloudletSimple(SaaSParamsConfig.getApplicationType,SaaSParamsConfig.getInstanceCount))

    /*Starts the simulation and waits all cloudlets to be executed, automatically
    stopping when there is no more events to process.*/
    SaaS_simulation.start

    /*Prints the results when the simulation is over*/
    logger.info("starting SaaS simulation")
    new CloudletsTableBuilder(broker.getCloudletFinishedList).build()
  }

}

import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.cloudlets.CloudletSimple
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.DatacenterSimple
import org.cloudbus.cloudsim.hosts.HostSimple
import org.cloudbus.cloudsim.resources.PeSimple
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic
import org.cloudbus.cloudsim.vms.{Vm, VmSimple}
import org.cloudsimplus.builders.tables.CloudletsTableBuilder
import utils.CostUtils.printTotalVmsCost
import utils.HostUtils.*
import utils.VMUtils.getCustomVM
import utils.configs.{DataCenterConfig, HostConfig, IaaSParamsConfig, SaaSParamsConfig}
import utils.*

import java.util.List

  object MultiDatacenterImplementation {



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
  val enableDelay = IaaSParamsConfig.getSubmissionDelay

  //Datacenter cost params
  val ramCost = HostConfig.getRamCost
  val timeCost = HostConfig.getTimeCost
  val storageCost = HostConfig.getStorageCost
  val BWCost = HostConfig.getBWCost
  
  //autoscaling params
  val enableVerticalScaling = IaaSParamsConfig.getVerticalScalingEnabled

  //List of Vms which can be configured in Override.conf under IaaS.vm 
  val vmList0 = VMUtils.getCustomVM(Vram,Vstorage,Vbw,Vpe_cap,Vpe_count,VMcount,VCloudletScheduler,enableVerticalScaling)
  val vmList1 = VMUtils.getCustomVM(Vram,Vstorage,Vbw,Vpe_cap,Vpe_count,VMcount,VCloudletScheduler,enableVerticalScaling)

  @main
  def multiDatacenterImplementation(): Unit = {

    //logger instantiation
    val logger = CreateLogger(classOf[MultiDatacenterImplementation.type])

    //Creates a CloudSim object to initialize the simulation.
    val multiDatacenterImplementation = new CloudSim
    //multiDatacenterImplementation.addOnClockTickListener(onClockTickListener)

    //Creates a Broker that will act on behalf of a cloud user (customer).
    val broker0 = new DatacenterBrokerSimple(multiDatacenterImplementation)
    val broker1 = new DatacenterBrokerSimple(multiDatacenterImplementation)

    //Creates a Datacenter with a list of Hosts
    val dc0 = DataCenterUtils.getSimpleDataCenter(multiDatacenterImplementation)
    val dc1 = DataCenterUtils.getSimpleDataCenter(multiDatacenterImplementation)

    //set cost parameters to datacenter
    dc0.getCharacteristics()
      .setCostPerSecond(timeCost)
      .setCostPerMem(ramCost)
      .setCostPerStorage(storageCost)
      .setCostPerBw(BWCost);

    //set cost parameters to datacenter
    dc1.getCharacteristics()
      .setCostPerSecond(timeCost)
      .setCostPerMem(ramCost)
      .setCostPerStorage(storageCost)
      .setCostPerBw(BWCost);

    //set network topology between datacenters and respective brokers as well as between datacenters to implement InterDatacenter vmMigration
    NetworkUtils.createNetwork(multiDatacenterImplementation,dc0,dc1,broker0,broker1)

    //Submit Vms to broker
    broker0.submitVmList(vmList0)
    broker1.submitVmList(vmList1)

    //client cloudlettes are passed through a loadbalancer which assigns jobs to respective broker of nearest Availabiliy zone
    LoadBalanceUtils.loadBalance(broker0,broker1)

    /*Starts the simulation and waits all cloudlets to be executed, automatically
    stopping when there is no more events to process.*/
    multiDatacenterImplementation.start

    /*Prints the results when the simulation is over*/
    logger.info("MultiDatacenterImplementation Datacenter0 : ")
    new CloudletsTableBuilder(broker0.getCloudletFinishedList).build()

    //Print cost data
    printTotalVmsCost(broker0)

    //Print Powerconsumption data
    PowerUtils.getPowerConsumptionStats(dc0.getHostList)

    logger.info("MultiDatacenterImplementation Datacenter1 : ")
    new CloudletsTableBuilder(broker1.getCloudletFinishedList).build()

    //Print Cost data
    printTotalVmsCost(broker1)

    //Print Powerconsumption data
    PowerUtils.getPowerConsumptionStats(dc1.getHostList)

  }

}

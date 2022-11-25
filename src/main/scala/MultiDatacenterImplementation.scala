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

  //List of Vms
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

    dc0.getCharacteristics()
      .setCostPerSecond(timeCost)
      .setCostPerMem(ramCost)
      .setCostPerStorage(storageCost)
      .setCostPerBw(BWCost);

    dc1.getCharacteristics()
      .setCostPerSecond(timeCost)
      .setCostPerMem(ramCost)
      .setCostPerStorage(storageCost)
      .setCostPerBw(BWCost);

    NetworkUtils.createNetwork(multiDatacenterImplementation,dc0,dc1,broker0,broker1)

    //create VMs and submit to broker
    //Uses a CloudletSchedulerTimeShared by default to schedule Cloudlets
    broker0.submitVmList(vmList0)
    broker1.submitVmList(vmList1)

    //Create cloudletts and submit to broker

    LoadBalanceUtils.loadBalance(broker0,broker1)

    /*Starts the simulation and waits all cloudlets to be executed, automatically
    stopping when there is no more events to process.*/
    multiDatacenterImplementation.start

    /*Prints the results when the simulation is over*/
    logger.info("MultiDatacenterImplementation Datacenter0 : ")
    new CloudletsTableBuilder(broker0.getCloudletFinishedList).build()
    printTotalVmsCost(broker0)
    PowerUtils.getPowerConsumptionStats(dc0.getHostList)

    logger.info("MultiDatacenterImplementation Datacenter1 : ")
    new CloudletsTableBuilder(broker1.getCloudletFinishedList).build()
    printTotalVmsCost(broker1)
    PowerUtils.getPowerConsumptionStats(dc1.getHostList)

  }

//  import org.cloudsimplus.listeners.EventInfo
//
//  def onClockTickListener(evt: EventInfo): Unit = {
//    vmList.forEach((vm) => System.out.printf("\t\tTime %6.1f: Vm %d CPU Usage: %6.2f%% (%2d vCPUs. Running Cloudlets: #%d). RAM usage: %.2f%% (%d MB)%n", evt.getTime, vm.getId, vm.getCpuPercentUtilization * 100.0, vm.getNumberOfPes, vm.getCloudletScheduler.getCloudletExecList.size, vm.getRam.getPercentUtilization * 100, vm.getRam.getAllocatedResource))
//  }

}

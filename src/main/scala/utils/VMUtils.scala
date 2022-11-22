package utils

import org.cloudbus.cloudsim.hosts.HostSimple
import org.cloudbus.cloudsim.schedulers.cloudlet.{CloudletSchedulerCompletelyFair, CloudletSchedulerSpaceShared, CloudletSchedulerTimeShared}
import org.cloudbus.cloudsim.vms.Vm
import org.cloudbus.cloudsim.vms.VmSimple
import org.cloudsimplus.autoscaling.VerticalVmScaling
import utils.HostUtils.{bw, getPEList, ram, storage}
import utils.configs.{IaaSParamsConfig, VMConfigLarge, VMConfigMed, VMConfigSmall}
import org.cloudsimplus.autoscaling.VerticalVmScalingSimple
import org.cloudbus.cloudsim.resources.Processor
import org.cloudsimplus.autoscaling.resources.ResourceScalingInstantaneous
import org.cloudbus.cloudsim.schedulers.cloudlet.CloudletSchedulerTimeShared
import org.cloudbus.cloudsim.vms.Vm
import org.cloudbus.cloudsim.vms.VmSimple
import org.cloudsimplus.autoscaling.HorizontalVmScaling
import org.cloudsimplus.autoscaling.HorizontalVmScalingSimple

import java.util.function.Supplier
import scala.jdk.CollectionConverters.*


object VMUtils {
  val logger = CreateLogger(classOf[VMUtils.type]) //logger instantiation

  // Specs of VM type Small
  val ram_small = VMConfigSmall.getVRam
  val storage_small = VMConfigSmall.getStorage
  val bw_small = VMConfigSmall.getBW
  val pe_cap_small = VMConfigSmall.getVPECapacity
  val pe_count_small = VMConfigSmall.getVPECount

  // Specs of VM type Med
  val ram_med = VMConfigMed.getVRam
  val storage_med = VMConfigMed.getStorage
  val bw_med = VMConfigMed.getBW
  val pe_cap_med = VMConfigMed.getVPECapacity
  val pe_count_med = VMConfigMed.getVPECount

  // Specs of VM type Large
  val ram_large = VMConfigLarge.getVRam
  val storage_large = VMConfigLarge.getStorage
  val bw_large = VMConfigLarge.getBW
  val pe_cap_large = VMConfigLarge.getVPECapacity
  val pe_count_large = VMConfigLarge.getVPECount

  //Vertical PE Scaling params
  def getLowerCpuUtilizationThreshold(vm: Vm) = IaaSParamsConfig.getLowerUtilThreshold
  def getUpperCpuUtilizationThreshold(vm:Vm) = IaaSParamsConfig.getUpperUtilThreshold

  //
  val peScalingFactor = 0.1
  
  
  //  retuns list of VMs
  def getVMSimple ( vmtype :String , vmcount: Int , Scheduler : String = "" ): java.util.List[Vm] = {

    val ram = if(vmtype == "SMALL") then ram_small else if(vmtype == "MEDIUM") then ram_med else ram_large
    val storage = if(vmtype == "SMALL") then storage_small else if(vmtype == "MEDIUM") then storage_med else storage_large
    val bw = if(vmtype == "SMALL") then bw_small else if(vmtype == "MEDIUM") then bw_med else bw_large
    val pe_cap = if(vmtype == "SMALL") then pe_cap_small else if(vmtype == "MEDIUM") then pe_cap_med else pe_cap_large
    val pe_count = if(vmtype == "SMALL") then pe_count_small else if(vmtype == "MEDIUM") then pe_count_med else pe_count_large

    scala.collection.immutable.List.tabulate(vmcount)(
      element => new VmSimple( pe_cap,pe_count).setBw(bw).setRam(ram).setSize(storage)
    ).asJava

  }

  def getCustomVM(ram:Int,storage:Int,bw:Int,mipsCapacity:Int,peCount:Int,vmCount:Int,scheduler: String,enableVerticalPEScaling:Boolean = false, enableHorizontalScaling:Boolean= false): java.util.List[Vm] = {


    scala.collection.immutable.List.tabulate(vmCount)(
      element => {
        val cloudletScheduler = if (scheduler == "FAIR") then new CloudletSchedulerCompletelyFair() else if(scheduler == "SPACESHARED") then new CloudletSchedulerSpaceShared() else new CloudletSchedulerTimeShared()
        val vm_t = new VmSimple(mipsCapacity, peCount, cloudletScheduler).setBw(bw).setRam(ram).setSize(storage)
        vm_t.enableUtilizationStats()

        if enableVerticalPEScaling then {
          vm_t.setPeVerticalScaling(createVerticalPeScaling())
          logger.debug("vscaling enabled")
        }

        if enableHorizontalScaling then {
          createHorizontalVmScaling(vm_t)
          logger.debug("hscaling enabled")
        }

        vm_t
      }
    ).asJava

  }

  def createVerticalPeScaling(): VerticalVmScaling ={


    val verticalCpuScaling = new VerticalVmScalingSimple(classOf[Processor], peScalingFactor)
    verticalCpuScaling.setResourceScaling(new ResourceScalingInstantaneous)
    verticalCpuScaling.setLowerThresholdFunction(this.getLowerCpuUtilizationThreshold)
    verticalCpuScaling.setUpperThresholdFunction(this.getUpperCpuUtilizationThreshold)
    verticalCpuScaling

  }


  def createHorizontalVmScaling(vm: Vm): Unit = {
    val horizontalScaling = new HorizontalVmScalingSimple
    horizontalScaling.setOverloadPredicate(isVmOverloaded)

    val vNew = new VmSimple(1000, 2).setRam(512).setBw(1000).setSize(10000)

    horizontalScaling.setVmSupplier(()=> {
      val vNew = new VmSimple(1000, 2).setRam(512).setBw(1000).setSize(1000)
      vNew.setCloudletScheduler(new CloudletSchedulerTimeShared)
    })

    vm.setHorizontalScaling(horizontalScaling)

  }


  def isVmOverloaded(vm: Vm):Boolean = {

    val value = vm.getHostRamUtilization
    if (value>0.7) then logger.warn("Vm overloaded!")
    value > 0.7

  }


}

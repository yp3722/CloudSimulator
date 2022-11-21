package utils

import org.cloudbus.cloudsim.hosts.HostSimple
import org.cloudbus.cloudsim.schedulers.cloudlet.{CloudletSchedulerCompletelyFair, CloudletSchedulerSpaceShared, CloudletSchedulerTimeShared}
import org.cloudbus.cloudsim.vms.Vm
import org.cloudbus.cloudsim.vms.VmSimple
import utils.HostUtils.{bw, getPEList, ram, storage}
import utils.configs.VMConfigSmall
import utils.configs.VMConfigMed
import utils.configs.VMConfigLarge

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

  def getCustomVM(ram:Int,storage:Int,bw:Int,mipsCapacity:Int,peCount:Int,vmCount:Int,scheduler: String): java.util.List[Vm] = {

    val cloudletScheduler = if (scheduler == "FAIR") then new CloudletSchedulerCompletelyFair() else if(scheduler == "SPACESHARED") then new CloudletSchedulerSpaceShared() else new CloudletSchedulerTimeShared()
    scala.collection.immutable.List.tabulate(vmCount)(
      element => {
        val v = new VmSimple(mipsCapacity, peCount, cloudletScheduler).setBw(bw).setRam(ram).setSize(storage)
        v
      }
    ).asJava

  }


}

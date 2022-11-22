package utils
import com.typesafe.config.ConfigFactory
import org.cloudbus.cloudsim.hosts.HostSimple
import org.cloudbus.cloudsim.power.models.PowerModelHostSimple
import org.cloudbus.cloudsim.provisioners.{PeProvisionerSimple, ResourceProvisionerSimple}
import org.cloudbus.cloudsim.resources.PeSimple
import org.cloudbus.cloudsim.schedulers.vm.{VmSchedulerSpaceShared, VmSchedulerTimeShared}
import utils.configs.HostConfig
import utils.CreateLogger

import scala.jdk.CollectionConverters.*
import scala.collection.immutable.List
import java.util.List

object HostUtils {
  val logger = CreateLogger(classOf[HostUtils.type]) //logger instantiation

  //initialize parameters
  val ram = HostConfig.getRam;
  val storage = HostConfig.getStorage;
  val bw = HostConfig.getBW;
  val peCapacity = HostConfig.getPECapacity;
  val peCount = HostConfig.getPECount;

  val startupDelay = HostConfig.getStartupDelay
  val shutdownDelay = HostConfig.getShutdownDelay
  val startupPower = HostConfig.getStartupPower
  val shutdownPower = HostConfig.getShutdownPower
  val StaticPower = HostConfig.getStaticPower
  val MaxPower = HostConfig.getMaxPower

  val vmScheduler = HostConfig.getVmScheduler

  //returns list of PE
  def getPEList():scala.collection.immutable.List[PeSimple]={
    scala.collection.immutable.List.tabulate(peCount)(element => new PeSimple(peCapacity,new PeProvisionerSimple()))
  }

  //returns list of Hosts
  def getSimpleHosts(hostCount: Int): java.util.List[HostSimple] = {
    scala.collection.immutable.List.tabulate(hostCount)(
      element => {
        val h = new HostSimple(ram, bw, storage, getPEList().asJava)

        h.setPowerModel(new PowerModelHostSimple(MaxPower, StaticPower).setStartupDelay(startupDelay)
          .setShutDownDelay(shutdownDelay)
          .setStartupPower(startupPower)
          .setShutDownPower(shutdownPower)
        )
        h.setRamProvisioner(new ResourceProvisionerSimple())
        h.setBwProvisioner(new ResourceProvisionerSimple())
        h.enableUtilizationStats()
        if (vmScheduler == "SPACE") then  h.setVmScheduler(new VmSchedulerSpaceShared()) else h.setVmScheduler(VmSchedulerTimeShared())
        h
      }
    ).asJava
  }
  
}

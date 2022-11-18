package utils
import com.typesafe.config.ConfigFactory
import org.cloudbus.cloudsim.hosts.HostSimple
import org.cloudbus.cloudsim.resources.PeSimple
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

  //returns list of PE
  def getPEList():scala.collection.immutable.List[PeSimple]={
    scala.collection.immutable.List.tabulate(peCount)(element => new PeSimple(peCapacity))
  }

  //returns list of Hosts
  def getSimpleHosts(hostCount: Int): java.util.List[HostSimple] = {
    scala.collection.immutable.List.tabulate(hostCount)(
      element => new HostSimple(ram, bw, storage, getPEList().asJava)
    ).asJava
  }
  
}

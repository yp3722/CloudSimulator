package utils
import scala.jdk.CollectionConverters.*
import org.cloudbus.cloudsim.hosts.HostSimple
import org.cloudbus.cloudsim.vms.{HostResourceStats, Vm, VmResourceStats, VmSimple}
import org.cloudbus.cloudsim.power.models.PowerModelHostSimple


object PowerUtils {

  //logger init
  val logger = CreateLogger(classOf[PowerUtils.type])


   //calculate and print power consumption stats
   def getPowerConsumptionStats(hostList: java.util.List[HostSimple]): Unit = {

    val hosts = hostList.asScala

    //map each list of vm in list of double
    val cpuUtilList = hosts.map((h) => {
      val vms = h.getVmList.asScala
      val utilMean = h.getCpuUtilizationStats.getMean
      val watts = h.getPowerModel.getPower(utilMean)
      watts
    })

    val noOfHosts = cpuUtilList.length
    val totalPowerConsumption = cpuUtilList.reduce((w1,w2)=>w1+w2)
    val avgPowerConsumption = totalPowerConsumption/noOfHosts

    System.out.println("Total Power consumption = "+totalPowerConsumption)
    System.out.println("Avg Power consumption = "+avgPowerConsumption)

  }




}

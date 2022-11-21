package utils
import org.cloudbus.cloudsim.vms.Vm
import org.cloudbus.cloudsim.vms.VmCost
import scala.jdk.CollectionConverters.*
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import java.util.List


object CostUtils {
  //logger init
  val logger = CreateLogger(classOf[CostUtils.type])
  
  //prints cost of running simulation
   def printTotalVmsCost(broker0: DatacenterBrokerSimple): Unit = {

    //get list of VM from brokler
    val VMList = broker0.getVmCreatedList.asScala
    
    //map each list of vm in list of double
    val TotalCostList = VMList.map((v) => {
      val vcost = new VmCost(v)
      vcost.getTotalCost
    })

    //map each list of vm in list of double
    val MemoryCostList = VMList.map((v) => {
      val vcost = new VmCost(v)
      vcost.getMemoryCost
    })

    //map each list of vm in list of double
    val ProcessingCostList = VMList.map((v) => {
      val vcost = new VmCost(v)
      vcost.getProcessingCost
    })

    //map each list of vm in list of double
    val BwCostList = VMList.map((v) => {
      val vcost = new VmCost(v)
      vcost.getBwCost
    })

    //map each list of vm in list of double
    val StorageCostList = VMList.map((v) => {
      val vcost = new VmCost(v)
      vcost.getStorageCost
    })
    
    val totalCost = TotalCostList.reduce((v1, v2) => v1 + v2)
    logger.info("Total Cost : "+ totalCost)

    val memoryCost = MemoryCostList.reduce((v1, v2) => v1 + v2)
    logger.info("Total Memory Cost : " + memoryCost)

    val processingCost = ProcessingCostList.reduce((v1, v2) => v1 + v2)
    logger.info("Total Cost : " + totalCost)

    val bwCost = BwCostList.reduce((v1, v2) => v1 + v2)
    logger.info("Total Memory Cost : " + memoryCost)

    val storageCost = StorageCostList.reduce((v1, v2) => v1 + v2)
    logger.info("Total Memory Cost : " + memoryCost)
  }

}

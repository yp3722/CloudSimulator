package utils

import org.cloudbus.cloudsim.cloudlets.CloudletSimple
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic
import utils.configs.CloudletConfig

import scala.jdk.CollectionConverters.*
import scala.compiletime.ops.string.Length

object CloudletUtils {

  //Initializes params 

  val minimumUtilizationA = CloudletConfig.getMinUtil_A
  val minimumUtilizationB = CloudletConfig.getMinUtil_B
  val maximumUtilizationA = CloudletConfig.getMaxUtil_A
  val maximumUtilizationB = CloudletConfig.getMinUtil_B
  val lengthA = CloudletConfig.getLen_A
  val lengthB = CloudletConfig.getLen_B
  val peCountA = CloudletConfig.getPeCount_A
  val peCountB = CloudletConfig.getPeCount_B
  
  
  //returns list of cloudlets
  def getCloudletSimple(cl_type:String,cl_numbers: Int): java.util.List[CloudletSimple] = {
    
    val initialUtilization = if (cl_type == "A") then minimumUtilizationA else minimumUtilizationB
    val maxResourceUtilization = if (cl_type == "A") then maximumUtilizationA else maximumUtilizationB
    val cl_lenght = if (cl_type=="A") then lengthA else lengthB
    val pe_required = if (cl_type=="A") then peCountA else peCountB
    
    val utilizationModel = new UtilizationModelDynamic(initialUtilization,maxResourceUtilization)
    scala.collection.immutable.List.tabulate(cl_numbers)(
      element => new CloudletSimple(cl_lenght, pe_required, utilizationModel)
    ).asJava
  }

}

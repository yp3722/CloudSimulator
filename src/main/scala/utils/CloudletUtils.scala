package utils

import org.cloudbus.cloudsim.cloudlets.CloudletSimple
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic
import utils.configs.{CloudletConfig, PaaSParamsConfig}

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

  
  //to set random submissiondelay for cloudlets
  val rand = new scala.util.Random
  
  //returns list of cloudlets
  def getCloudletSimple(cl_type:String,cl_numbers: Int): java.util.List[CloudletSimple] = {
    
    val initialUtilization = if (cl_type == "A") then minimumUtilizationA else minimumUtilizationB
    val maxResourceUtilization = if (cl_type == "A") then maximumUtilizationA else maximumUtilizationB
    val cl_lenght = if (cl_type=="A") then lengthA else lengthB
    val pe_required = if (cl_type=="A") then peCountA else peCountB

    //creating a utilization model and assign to cloudlettes
    val utilizationModel = new UtilizationModelDynamic(initialUtilization,maxResourceUtilization)
    scala.collection.immutable.List.tabulate(cl_numbers)(
      element => new CloudletSimple(cl_lenght, pe_required, utilizationModel)
    ).asJava
  }

  //returns list of custom cloudlets based on user requirements
  def getUserApplicationCloudlet(min_util:Double,max_util:Double,cl_len:Int,cl_numbers:Int,pe_required:Int,enableSubmissionDelay:Boolean = false): java.util.List[CloudletSimple] = {


    scala.collection.immutable.List.tabulate(cl_numbers)(
      element => {
        //creating a utilization model and assign to cloudlettes
        val utilizationModel = new UtilizationModelDynamic(min_util, max_util)
        val clet = new CloudletSimple(cl_len, pe_required, utilizationModel)
        //set submission delay 
        if enableSubmissionDelay then clet.setSubmissionDelay(rand.between(1,10)) else {}
        clet
      }
    ).asJava

  }

  //models diffuse style computing task
  //additional smaller tasks get scheduled after certain initial have processed

  def getMapReduceCloudlet(cl_numbers:Int,cl_lenght:Int,pe_required:Int): java.util.List[CloudletSimple] ={

    scala.collection.immutable.List.tabulate(cl_numbers*5)(
      element => {
        //delay models the diffuse computation by adding new cloudlettes
        val delay = if (element>cl_numbers) rand.between(10+element,25+element) else 1
        val c = new CloudletSimple(5*cl_lenght/(delay), pe_required)
        c.setSubmissionDelay(delay)
        c
      }
    ).asJava
  }




}

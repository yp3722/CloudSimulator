package utils.configs

import com.typesafe.config.ConfigFactory
import utils.CreateLogger

object PaaSParamsConfig {

  // Class reads config file and provides values

  val logger = CreateLogger(classOf[PaaSParamsConfig.type]) //logger instantiation
  val defaultConfig = ConfigFactory.parseResources("default.conf");
  // Incase Override.conf is not available uses default.conf
  val config = ConfigFactory.parseResources("Override.conf").withFallback(defaultConfig).resolve()



  //Get parameter values from config file
  
  def getApplicationMinUtil: Double = {
    try {
      val op = config.getDouble("PaaS.application.minUtil")
      logger.debug("(PaaS) Min Util : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" (PaaS) No minUtil found in config")
        0.5
      }
    }

  }

  def getApplicationMaxUtil: Double = {
    try {
      val op = config.getDouble("PaaS.application.maxUtil")
      logger.debug("(PaaS) Max Util : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" (PaaS) No maxUtil found in config")
        1
      }
    }

  }

  def getCloudletLen: Int = {
    try {
      val op = config.getInt("PaaS.application.length")
      logger.debug("(PaaS) Cloudlet Length : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" (PaaS) No lenght found in config")
        10000
      }
    }

  }

  def getPECount: Int = {
    try {
      val op = config.getInt("PaaS.application.peCount")
      logger.debug("No. of PE : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find peCount in config")
        2
      }
    }

  }

  def getInstanceCount: Int = {
    try {
      val op = config.getInt("PaaS.application.application_count")
      logger.debug("No. of Cloudlets : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find instance count in config")
        5
      }
    }

  }

  def getVMType: String = {
    try {
      val op = config.getString("PaaS.vmType")
      logger.debug("Vm Type : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find vm type in config")
        "Medium"
      }
    }

  }

  def getVMCount: Int = {
    try {
      val op = config.getInt("PaaS.vmCount")
      logger.debug("No. of vms: " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find vmCount in config")
        10
      }
    }

  }



}

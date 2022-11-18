package utils.configs

import com.typesafe.config.ConfigFactory
import utils.CreateLogger

object SaaSParamsConfig {

  // Class reads config file and provides values

  val logger = CreateLogger(classOf[SaaSParamsConfig.type]) //logger instantiation
  val defaultConfig = ConfigFactory.parseResources("default.conf");
  // Incase Override.conf is not available uses default.conf
  val config = ConfigFactory.parseResources("Override.conf").withFallback(defaultConfig).resolve()



  //Get parameter values from config file
  
  def getApplicationType: String = {
    try {
      val op = config.getString("SaaS.cloudlet_type")
      logger.debug("(SaaS) Application Selected : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" No Application found in config")
        "A"
      }
    }

  }

  def getInstanceCount: Int = {
    try {
      val op = config.getInt("SaaS.cloudlet_count")
      logger.debug("No. of Cloudlets : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find instance count in config")
        10
      }
    }

  }
}

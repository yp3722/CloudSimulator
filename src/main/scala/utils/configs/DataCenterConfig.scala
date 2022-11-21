package utils.configs

import com.typesafe.config.ConfigFactory
import utils.CreateLogger

object DataCenterConfig {

  // Class reads config file and provides values

  val logger = CreateLogger(classOf[DataCenterConfig.type]) //logger instantiation
  val defaultConfig = ConfigFactory.parseResources("default.conf");
  // Incase Override.conf is not available uses default.conf
  val config = ConfigFactory.parseResources("Override.conf").withFallback(defaultConfig).resolve()


  
  
  def getAllocationPolicy: String = {
    try {
      val op = config.getString("dataCenterConfig.allocationPolicy")
      logger.debug("allocationPolicy set to : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find allocationPolicy in conf")
        "SIMPLE"
      }
    }
  }

  def getHostCount: Int = {
    try {
      val op = config.getInt("dataCenterConfig.hostCount")
      logger.debug("hostCount set to : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find hostCount in conf")
        30
      }
    }
  }


}

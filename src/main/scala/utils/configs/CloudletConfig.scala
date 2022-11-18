package utils.configs

import com.typesafe.config.ConfigFactory
import utils.CreateLogger

object CloudletConfig {

  // Class reads config file and provides values

  val logger = CreateLogger(classOf[CloudletConfig.type]) //logger instantiation
  val defaultConfig = ConfigFactory.parseResources("default.conf");
  // Incase Override.conf is not available uses default.conf
  val config = ConfigFactory.parseResources("Override.conf").withFallback(defaultConfig).resolve()


  
  
  def getMinUtil_A: Double = {
    try {
      val op = config.getDouble("CloudletConfig.applicationA.minUtil")
      logger.debug("Min util Application A set to : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" (Application A) Cant find min util parameter in conf")
        0.3
      }
    }
  }

  def getMinUtil_B: Double = {
    try {
      val op = config.getDouble("CloudletConfig.applicationB.minUtil")
      logger.debug("Max util Application B set to : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" (Application B) Cant find min util parameter in conf")
        0.5
      }
    }

  }

  def getMaxUtil_A: Double = {
    try {
      val op = config.getDouble("CloudletConfig.applicationA.maxUtil")
      logger.debug("Max util Application A set to : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" (Application A) Cant find max util parameter in conf")
        1.0
      }
    }
  }

  def getMaxUtil_B: Double = {
    try {
      val op = config.getDouble("CloudletConfig.applicationB.maxUtil")
      logger.debug("Max util Application B set to : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" (Application B) Cant find max util parameter in conf")
        0.9
      }
    }
  }

  def getLen_A: Int = {
    try {
      val op = config.getInt("CloudletConfig.applicationA.length")
      logger.debug("Cloudlet length for Application A set to : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" (Application A) Cant find lenght parameter in conf")
        10000
      }
    }
  }

  def getLen_B: Int = {
    try {
      val op = config.getInt("CloudletConfig.applicationB.length")
      logger.debug("Cloudlet length for Application B set to : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" (Application B) Cant find lenght parameter in conf")
        15000
      }
    }
  }

  def getPeCount_A: Int = {
    try {
      val op = config.getInt("CloudletConfig.applicationA.peCount")
      logger.debug("Cloudlet peCount for Application A set to : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" (Application A) Cant find peCount parameter in conf")
        1
      }
    }
  }

  def getPeCount_B: Int = {
    try {
      val op = config.getInt("CloudletConfig.applicationB.peCount")
      logger.debug("Cloudlet peCount for Application B set to : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" (Application B) Cant find peCount parameter in conf")
        2
      }
    }
  }

}

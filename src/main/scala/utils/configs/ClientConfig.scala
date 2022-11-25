package utils.configs

import com.typesafe.config.ConfigFactory
import utils.CreateLogger

object ClientConfig {

  // Class reads config file and provides values

  val logger = CreateLogger(classOf[ClientConfig.type]) //logger instantiation
  val defaultConfig = ConfigFactory.parseResources("default.conf");
  // Incase Override.conf is not available uses default.conf
  val config = ConfigFactory.parseResources("Override.conf").withFallback(defaultConfig).resolve()
  
  def getClientA_Proximity: Int = {
    try {
      val op = config.getInt("MultiDatacenter.clientA.proximity")
      logger.debug("Client A proximity : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Client A proximity not found in parameter in conf")
        0
      }
    }
  }

  def getClientAJobCount: Int = {
    try {
      val op = config.getInt("MultiDatacenter.clientA.count")
      logger.debug("Client A job count : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Client A job count not found in parameter in conf")
        15
      }
    }
  }

  def getClientAJobLength: Int = {
    try {
      val op = config.getInt("MultiDatacenter.clientA.lenght")
      logger.debug("Client A job length : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Client A job lenght not found in parameter in conf")
        1000
      }
    }
  }

  def getClientAPeReq: Int = {
    try {
      val op = config.getInt("MultiDatacenter.clientA.peRequired")
      logger.debug("Client A peReq : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Client A job peReq not found in parameter in conf")
        2
      }
    }
  }


  def getClientB_Proximity: Int = {
    try {
      val op = config.getInt("MultiDatacenter.clientB.proximity")
      logger.debug("Client B proximity : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Client B proximity not found in parameter in conf")
        2
      }
    }
  }

  def getClientBJobCount: Int = {
    try {
      val op = config.getInt("MultiDatacenter.clientB.count")
      logger.debug("Client B job count : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Client B job count not found in parameter in conf")
        10
      }
    }
  }

  def getClientBJobLength: Int = {
    try {
      val op = config.getInt("MultiDatacenter.clientB.lenght")
      logger.debug("Client B job length : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Client B job lenght not found in parameter in conf")
        2000
      }
    }
  }

  def getClientBPeReq: Int = {
    try {
      val op = config.getInt("MultiDatacenter.clientA.peRequired")
      logger.debug("Client B peReq : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Client B job peReq not found in parameter in conf")
        2
      }
    }
  }
  
  

}

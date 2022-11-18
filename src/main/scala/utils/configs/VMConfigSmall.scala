package utils.configs

import com.typesafe.config.ConfigFactory
import utils.CreateLogger

object VMConfigSmall {

  // Class reads config file and provides values

  val logger = CreateLogger(classOf[VMConfigSmall.type]) //logger instantiation
  val defaultConfig = ConfigFactory.parseResources("default.conf");
  // Incase Override.conf is not available uses default.conf
  val config = ConfigFactory.parseResources("Override.conf").withFallback(defaultConfig).resolve()

  //Get parameter values from config file
  
  def getVRam: Int = {
    try {
      val op = config.getInt("VMConfig.Small.ram")
      logger.debug("VMConfig Ram size in MB : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find VM Ram Size in conf")
        2048
      }
    }

  }

  def getStorage: Int = {
    try {
      val op = config.getInt("VMConfig.Small.storage")
      logger.debug("VMConfig Storage size in MB : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find VM Storage Size in conf")
        250000
      }
    }

  }

  def getBW: Int = {
    try {
      val op = config.getInt("VMConfig.Small.bw")
      logger.debug("VMConfig bandwidth size in MB : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find VM Bandwidth Size in conf")
        1000
      }
    }

  }

  def getVPECapacity: Int = {
    try {
      val op = config.getInt("VMConfig.Small.mipsCapacity")
      logger.debug("VMConfig VPE capacity in MIPS : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find VM PE Capacity Size in conf")
        10000
      }
    }

  }

  def getVPECount: Int = {
    try {
      val op = config.getInt("VMConfig.Small.peCount")
      logger.debug("VPE count : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find VM PE count in conf")
        2
      }
    }

  }

}

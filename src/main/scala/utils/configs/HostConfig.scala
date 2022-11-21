package utils.configs

import com.typesafe.config.ConfigFactory
import utils.CreateLogger

object HostConfig {

  // Class reads config file and provides values

  val logger = CreateLogger(classOf[HostConfig.type]) //logger instantiation
  val defaultConfig = ConfigFactory.parseResources("default.conf");
  // Incase Override.conf is not available uses default.conf
  val config = ConfigFactory.parseResources("Override.conf").withFallback(defaultConfig).resolve()


  //Get parameter values from config file
  
  def getRam: Int = {
    try {
      val op = config.getInt("hostConfig.ram")
      logger.debug("Hostconfig Ram size in MB : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find Host Ram Size in conf")
        10000
      }
    }

  }

  def getStorage: Int = {
    try {
      val op = config.getInt("hostConfig.storage")
      logger.debug("Hostconfig Storage size in MB : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find Host Storage Size in conf")
        100000
      }
    }

  }

  def getBW: Int = {
    try {
      val op = config.getInt("hostConfig.bw")
      logger.debug("Hostconfig bandwidth size in Mb : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find Host Bandwidth Size in conf")
        100000
      }
    }

  }

  def getPECapacity: Int = {
    try {
      val op = config.getInt("hostConfig.mipsCapacity")
      logger.debug("Hostconfig PE capacity in MIPS : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find Host PE Capacity in conf")
        50000
      }
    }

  }

  def getPECount: Int = {
    try {
      val op = config.getInt("hostConfig.peCount")
      logger.debug("Hostconfig PE count : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find Host PE count in conf")
        16
      }
    }

  }
  
  
  def getRamCost: Double = {
    try {
      val op = config.getDouble("hostConfig.ramCost")
      logger.debug("Hostconfig ramCost : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find ramCost in conf")
        0.002
      }
    }
  }

  def getStorageCost: Double = {
    try {
      val op = config.getDouble("hostConfig.storageCost")
      logger.debug("Hostconfig storageCost : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find storageCost in conf")
        0.0001
      }
    }
  }

  def getBWCost: Double = {
    try {
      val op = config.getDouble("hostConfig.bwCost")
      logger.debug("Hostconfig BWCost : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find BWCost in conf")
        0.0005
      }
    }
  }

  def getTimeCost: Double = {
    try {
      val op = config.getDouble("hostConfig.costPerSec")
      logger.debug("Hostconfig costPerSec : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find costPerSec in conf")
        0.001
      }
    }
  }
  
  

}

package utils.configs

import com.typesafe.config.ConfigFactory
import utils.CreateLogger

object NetworkConfig {

  // Class reads config file and provides values

  val logger = CreateLogger(classOf[NetworkConfig.type]) //logger instantiation
  val defaultConfig = ConfigFactory.parseResources("default.conf");
  // Incase Override.conf is not available uses default.conf
  val config = ConfigFactory.parseResources("Override.conf").withFallback(defaultConfig).resolve()
  
  def getBwLink_b0d0: Double = {
    try {
      val op = config.getDouble("NetworkTopology.bandwidth.brokerDatacenter0")
      logger.debug("BW between broker0 and datacenter0 set to : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" BW between broker0 and datacenter0 not found in parameter in conf")
        100.0
      }
    }
  }

  def getBwLink_b1d1: Double = {
    try {
      val op = config.getDouble("NetworkTopology.bandwidth.brokerDatacenter1")
      logger.debug("BW between broker1 and datacenter1 set to : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" BW between broker1 and datacenter1 not found in parameter in conf")
        110.0
      }
    }
  }

  def getBwLink_d0d1: Double = {
    try {
      val op = config.getDouble("NetworkTopology.bandwidth.datacenters")
      logger.debug("BW between datacenter0 and datacenter1 set to : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" BW between datacenter0 and datacenter1 not found in parameter in conf")
        500.0
      }
    }
  }

  def getLatencyLink_b0d0: Double = {
    try {
      val op = config.getDouble("NetworkTopology.latency.brokerDatacenter0")
      logger.debug("latency between broker0 and datacenter0 set to : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" latency between broker0 and datacenter0 not found in parameter in conf")
        3.0
      }
    }
  }

  def getLatencyLink_b1d1: Double = {
    try {
      val op = config.getDouble("NetworkTopology.latency.brokerDatacenter1")
      logger.debug("latency between broker1 and datacenter1 set to : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" latency between broker1 and datacenter1 not found in parameter in conf")
        6.0
      }
    }
  }

  def getLatencyLink_d0d1: Double = {
    try {
      val op = config.getDouble("NetworkTopology.latency.datacenters")
      logger.debug("latency between datacenter0 and datacenter1 set to : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" latency between datacenter0 and datacenter1 not found in parameter in conf")
        15.0
      }
    }
  }


}

package utils.configs

import com.typesafe.config.ConfigFactory
import utils.CreateLogger

object IaaSParamsConfig {

  // Class reads config file and provides values

  val logger = CreateLogger(classOf[IaaSParamsConfig.type]) //logger instantiation
  val defaultConfig = ConfigFactory.parseResources("default.conf");
  // Incase Override.conf is not available uses default.conf
  val config = ConfigFactory.parseResources("Override.conf").withFallback(defaultConfig).resolve()

  //Get parameter values from config file
  
  def getVRam: Int = {
    try {
      val op = config.getInt("IaaS.VM.ram")
      logger.debug("VMConfig Ram size in MB : " + op)
      if (op > 32000) then 16000 else op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find VM Ram Size in conf")
        16000
      }
    }

  }

  def getStorage: Int = {
    try {
      val op = config.getInt("IaaS.VM.storage")
      logger.debug("VMConfig Storage size in MB : " + op)
      if (op>2048000) then 250000 else op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find VM Storage Size in conf")
        1000000
      }
    }

  }

  def getBW: Int = {
    try {
      val op = config.getInt("IaaS.VM.bw")
      logger.debug("VMConfig bandwidth size in MB : " + op)
      if (op>10000) then 1000 else op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find VM Bandwidth Size in conf")
        5000
      }
    }

  }

  def getVPECapacity: Int = {
    try {
      val op = config.getInt("IaaS.VM.mipsCapacity")
      logger.debug("VMConfig VPE capacity in MIPS : " + op)
      if (op>50000) then 10000 else op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find VM PE Capacity Size in conf")
        20000
      }
    }

  }

  def getVPECount: Int = {
    try {
      val op = config.getInt("IaaS.VM.peCount")
      logger.debug("VPE count : " + op)
      if (op>16) then 8 else op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find VM PE count in conf")
        4
      }
    }

  }

  def getVmCount: Int = {
    try {
      val op = config.getInt("IaaS.VM.vmCount")
      logger.debug("VM count : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find VM count in conf")
        8
      }
    }
  }

    def getScheduler: String = {
      try {
        val op = config.getString("IaaS.VM.scheduler")
        logger.debug("Cloudlet Scheduler : " + op)
        op
      }
      catch {
        case e: Exception => {
          logger.error(" Cant find Scheduler in conf")
          "TIMESHARED"
        }
      }

  }

    def getApplicationMinUtil: Double = {
      try {
        val op = config.getDouble("IaaS.application.minUtil")
        logger.debug("(IaaS) Min Util : " + op)
        op
      }
      catch {
        case e: Exception => {
          logger.error(" (IaaS) No minUtil found in config")
          0.5
        }
      }

    }

    def getApplicationMaxUtil: Double = {
      try {
        val op = config.getDouble("IaaS.application.maxUtil")
        logger.debug("(IaaS) Max Util : " + op)
        op
      }
      catch {
        case e: Exception => {
          logger.error(" (IaaS) No maxUtil found in config")
          1
        }
      }

    }

    def getCloudletLen: Int = {
      try {
        val op = config.getInt("IaaS.application.length")
        logger.debug("(IaaS) Cloudlet Length : " + op)
        op
      }
      catch {
        case e: Exception => {
          logger.error(" (IaaS) No lenght found in config")
          10000
        }
      }

    }

    def getPECount: Int = {
      try {
        val op = config.getInt("IaaS.application.peCount")
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
        val op = config.getInt("IaaS.application.application_count")
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

    def getSubmissionDelay: Boolean = {
      try {
        val op = config.getString("IaaS.application.EnableSubmissionDelay")
        logger.debug("(IaaS) EnableSubmissionDelay : " + op)
        if (op == "TRUE") true else false
      }
      catch {
        case e: Exception => {
          logger.error(" Cant find EnableSubmissionDelay in config")
          false
        }
      }

    }

  def getVerticalScalingEnabled: Boolean = {
    try {
      val op = config.getString("IaaS.autoScaling.enabled")
      logger.debug("(IaaS) autoScaling : " + op)
      if (op == "TRUE") true else false
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find autoScaling in config")
        false
      }
    }

  }

  def getLowerUtilThreshold: Double = {
    try {
      val op = config.getDouble("IaaS.autoScaling.loweUtilThreshold")
      logger.debug("(IaaS) loweUtilThreshold : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find loweUtilThreshold in config")
        0.3
      }
    }
  }

  def getUpperUtilThreshold: Double = {
    try {
      val op = config.getDouble("IaaS.autoScaling.upperUtilThreshold")
      logger.debug("(IaaS) upperUtilThreshold : " + op)
      op
    }
    catch {
      case e: Exception => {
        logger.error(" Cant find upperUtilThreshold in config")
        0.7
      }
    }
  }
    
}

package utils

import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import utils.configs.ClientConfig

object LoadBalanceUtils {

  //Proximity of client to Datacenter a or b
  val proximityA = ClientConfig.getClientA_Proximity
  val proximityB = ClientConfig.getClientB_Proximity

  //Job count for each client (Note jobs will create additional smaller jobs simulating diffuse computing )
  val jobCntA = ClientConfig.getClientAJobCount
  val jobCntB = ClientConfig.getClientBJobCount

  //initial Job len for each client job in Mips
  val jobLenA = ClientConfig.getClientAJobLength
  val jobLenB = ClientConfig.getClientBJobLength

  //Pe requirement for each job from client
  val peReqA = ClientConfig.getClientAPeReq
  val peReqB = ClientConfig.getClientBPeReq

  def loadBalance(b0:DatacenterBrokerSimple,b1:DatacenterBrokerSimple): Unit ={

    if (proximityA==0) then
    {
        b0.submitCloudletList(CloudletUtils.getMapReduceCloudlet(jobCntA,jobLenA,peReqA))
    }
    else
    {
        b1.submitCloudletList(CloudletUtils.getMapReduceCloudlet(jobCntA,jobLenA,peReqA))
    }

    if (proximityB == 0) then {
      b0.submitCloudletList(CloudletUtils.getMapReduceCloudlet(jobCntB, jobLenB, peReqB))
    }
    else
    {
      b1.submitCloudletList(CloudletUtils.getMapReduceCloudlet(jobCntB, jobLenB, peReqB))
    }

  }

}

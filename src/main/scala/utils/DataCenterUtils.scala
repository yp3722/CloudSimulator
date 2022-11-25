package utils

import org.cloudbus.cloudsim.allocationpolicies.{VmAllocationPolicyBestFit, VmAllocationPolicyFirstFit, VmAllocationPolicyRandom, VmAllocationPolicyRoundRobin, VmAllocationPolicySimple}
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.DatacenterSimple
import utils.configs.DataCenterConfig

object DataCenterUtils {
  val logger = CreateLogger(classOf[DataCenterUtils.type]) //logger instantiation

  //get params from config
  val allocationPolicy = DataCenterConfig.getAllocationPolicy
  val hostCount = DataCenterConfig.getHostCount
  val schedulingInterval = DataCenterConfig.getInterval


  //create datacenter according to config
  def getSimpleDataCenter(sim:CloudSim): DatacenterSimple ={
    val allocPolicy = if (allocationPolicy=="BESTFIT") then new VmAllocationPolicyBestFit() else if (allocationPolicy=="FIRSTFIT") then new VmAllocationPolicyFirstFit() else if (allocationPolicy=="ROUNDROBIN") then new VmAllocationPolicyRoundRobin() else new VmAllocationPolicySimple()
    val dc = new DatacenterSimple(sim,HostUtils.getSimpleHosts(hostCount),allocPolicy)
    dc.setSchedulingInterval(schedulingInterval)
    dc
  }

}

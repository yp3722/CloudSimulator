package utils

import org.cloudbus.cloudsim.brokers.{DatacenterBroker, DatacenterBrokerSimple}
import org.cloudbus.cloudsim.core.{CloudSim, Simulation}
import org.cloudbus.cloudsim.datacenters.DatacenterSimple
import org.cloudbus.cloudsim.network.topologies.BriteNetworkTopology
import org.cloudbus.cloudsim.network.topologies.NetworkTopology
import utils.configs.NetworkConfig

object NetworkUtils {
  //logger init
  val logger = CreateLogger(classOf[PowerUtils.type])

  //bandwidth and latency between broker0 and datacenter0 Megabits
  val bw_b0_d0 = NetworkConfig.getBwLink_b0d0
  val lat_b0_d0 = NetworkConfig.getLatencyLink_b0d0

  //bandwidth and latency between broker1 and datacenter1 Megabits
  val bw_b1_d1 = NetworkConfig.getBwLink_b1d1
  val lat_b1_d1 = NetworkConfig.getLatencyLink_b1d1

  //bandwidth and latency between datacenter0 and datacenter1 Megabits
  val bw_d0_d1 = NetworkConfig.getBwLink_d0d1
  val lat_d0_d1 = NetworkConfig.getLatencyLink_d0d1

  //create and add brite network topology to simulation
  def createNetwork(simulation: CloudSim, d0:DatacenterSimple, d1:DatacenterSimple, b0:DatacenterBrokerSimple, b1:DatacenterBrokerSimple): Unit =
  {
    
    val networkTopology = new BriteNetworkTopology
    simulation.setNetworkTopology(networkTopology)
    networkTopology.addLink(d0, b0, bw_b0_d0, lat_b0_d0)
    networkTopology.addLink(d1, b1, bw_b1_d1, lat_b1_d1)
    networkTopology.addLink(d0, d1, bw_d0_d1, lat_d0_d1)

  }
  
  
  
}

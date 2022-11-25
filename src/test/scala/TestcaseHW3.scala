import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import utils.{CloudletUtils, HostUtils, VMUtils}

import java.lang.reflect.Parameter

class TestcaseHW3 extends AnyFlatSpec with Matchers {
  behavior of "Utils functions"

  it should "generate Cloudlette List from given params" in {

    CloudletUtils.getCloudletSimple("A",5).size() should equal (5)
  }

  it should "generate Host List from given params" in {

    HostUtils.getSimpleHosts(10).size() should equal(10)
  }

  it should "generate VM List from given params" in {

    VMUtils.getVMSimple("SMALL",14).size() should equal(14)
  }

  it should "generate VM List from given params and verify if parameters are correctly assigned" in {

    VMUtils.getCustomVM(2000,10000,1000,10000,2,1,"FAIR").get(0).getTotalMipsCapacity should equal(20000.0)
  }

  it should "generate Cloudlette List for diffusing computation from given params" in {

    CloudletUtils.getMapReduceCloudlet(10,1000,2).size()  should be > 10
  }

}

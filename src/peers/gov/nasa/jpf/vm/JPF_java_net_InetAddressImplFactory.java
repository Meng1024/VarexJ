package gov.nasa.jpf.vm;

import de.fosd.typechef.featureexpr.FeatureExpr;
import gov.nasa.jpf.annotation.MJI;
import gov.nasa.jpf.vm.util.InvokationUtils;

public class JPF_java_net_InetAddressImplFactory extends NativePeer {

	@MJI
	public static boolean isIPv6Supported____Z(MJIEnv env, int clsRef, FeatureExpr ctx) {
		return (boolean) InvokationUtils.invoke("java.net.InetAddressImplFactory", "isIPv6Supported");
	}

}

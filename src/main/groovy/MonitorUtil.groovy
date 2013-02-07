class MonitorUtil {
/*
 * Parse the $Name.... field done by CVS 
 */
	public static String parseCVS( String checkoutTag ) {
		int startPosn 	= checkoutTag.indexOf(':') + 1
		int endPosn 	= checkoutTag.substring(1).indexOf('$') 
		return checkoutTag.substring(startPosn, endPosn+1).trim()
	}
}

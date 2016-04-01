public class DynamicConfiguration implements DynamicConfigurationInterface {
	
	public def getInstanceList() {
		[
			new JenkinsInstanceSpecification( 'zikula', "http://ci.zikula.org" ),
		]
	}
	
	public def getRefreshIntervalSecs() {
		return 60
	}
	
	public def getCustomerImage() {
		return ""   // return empty string or null if no image desired - otherwise myimage.jpeg 
	}
	
	public def getPipelineSpecs() {
		return []
	}
	
}


public class DynamicConfiguration implements DynamicConfigurationInterface {
	
	public def getInstanceList() {
		[
			new JenkinsInstanceSpecification( '111', "http://192.168.1.114:8080" ),
			new JenkinsInstanceSpecification( '111', "http://192.168.1.139:8080" ),
			new JenkinsInstanceSpecification( '11', "http://192.168.1.63:8080" ),
		]
	}
	
	public def getRefreshIntervalSecs() {
		return 20
	}
	
	public def getCustomerImage() {
		return ""   // return empty string or null if no image desired - otherwise myimage.jpeg 
	}
	
	public def getPipelineSpecs() {
		[
		'Pipeline 1 -->': 
			[
			new JenkinsJobSpecification( '111:Installer', 'http://192.168.1.111:8080/jenkins/job/spectrum-installer-SNAPSHOT'),
			new JenkinsJobSpecification( '111:LATEST-WEBLOGIC', 'http://192.168.1.111:8080/jenkins/job/LATEST-WEBLOGIC'),
			new JenkinsJobSpecification( '111:LATEST-WEBLOGIC', 'http://192.168.1.111:8080/jenkins/job/LATEST-WEBLOGIC'),
			],
		'Pipeline 2 -->': 
			[
			new JenkinsJobSpecification( '111:Tagged Installer', 'http://192.168.1.111:8080/jenkins/job/spectrum-installer-99_9_9_xxx'),
			new JenkinsJobSpecification( '111:Latest Perf', 'http://192.168.1.111:8080/jenkins/job/install-latest-perf'),
			],
		]
	}
	
}


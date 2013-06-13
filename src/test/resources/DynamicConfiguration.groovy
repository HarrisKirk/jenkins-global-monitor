public class DynamicConfiguration implements DynamicConfigurationInterface {
	
	public def getInstanceList() {
		[
			new JenkinsInstanceSpecification( '114', "http://192.168.1.114:8080" ),
			new JenkinsInstanceSpecification( '55', "http://192.168.1.55:8080" ),
			new JenkinsInstanceSpecification( '139', "http://192.168.1.139:8080" ),
			new JenkinsInstanceSpecification( '63', "http://192.168.1.63:8080" ),
		]
	}
	public def getRefreshIntervalSecs() {
		return 20
	}
	
	public def getPipelineSpecs() {
		[
		'Pipeline 1': 
			[
			new JenkinsJobSpecification( '252:Installer', 'http://192.168.1.252:8080/jenkins/job/spectrum-installer-SNAPSHOT'),
			new JenkinsJobSpecification( '142:LATEST-WEBLOGIC', 'http://192.168.1.142:8080/jenkins/job/LATEST-WEBLOGIC'),
			new JenkinsJobSpecification( '166:LATEST-WEBLOGIC', 'http://192.168.1.166:8080/jenkins/job/LATEST-WEBLOGIC'),
			],
		'Pipeline 2': 
			[
			new JenkinsJobSpecification( '252:Tagged Installer', 'http://192.168.1.252:8080/jenkins/job/spectrum-installer-99_9_9_xxx'),
			new JenkinsJobSpecification( '139:Latest Perf', 'http://192.168.1.139:8080/jenkins/job/install-latest-perf'),
			],
		]
	}
	
}


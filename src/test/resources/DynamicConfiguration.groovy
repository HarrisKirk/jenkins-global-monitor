public class DynamicConfiguration implements DynamicConfigurationInterface {
	public def getInstanceList() {
		[
		new JenkinsInstanceSpecification( '114', "http://192.168.1.114:8080" ),
		new JenkinsInstanceSpecification( '128', "http://192.168.1.128:8080" ),
		new JenkinsInstanceSpecification( '147', "http://192.168.1.147:8080" ),
		new JenkinsInstanceSpecification( '40', "http://192.168.1.40:9090" ),
		new JenkinsInstanceSpecification( '181', "http://192.168.1.181:8080" ),
		new JenkinsInstanceSpecification( '97', "http://192.168.1.97:8080" ),
		new JenkinsInstanceSpecification( '252', "http://192.168.1.252:8080" ),
		new JenkinsInstanceSpecification( '142', "http://192.168.1.142:8080" ),
		new JenkinsInstanceSpecification( '101', "http://192.168.1.101:8080" ),
		new JenkinsInstanceSpecification( '156', "http://192.168.1.156:8080" ),
		new JenkinsInstanceSpecification( '235', "http://192.168.1.235:8080" ),
		new JenkinsInstanceSpecification( '131', "http://192.168.1.131:8080" ),
		new JenkinsInstanceSpecification( '55', "http://192.168.1.55:8080" ),
		new JenkinsInstanceSpecification( '56', "http://192.168.1.56:8080" ),
		new JenkinsInstanceSpecification( '61', "http://192.168.1.61:8080" ),
		new JenkinsInstanceSpecification( '138', "http://192.168.1.138:8080" ),
		new JenkinsInstanceSpecification( '139', "http://192.168.1.139:8080" ),
		new JenkinsInstanceSpecification( '63', "http://192.168.1.63:8080" ),
		]
	}
	public def getRefreshIntervalSecs() {
		return 20
	}
	
	public def getPipelineJobs() {
		return [
			'http://192.168.1.142:8080/jenkins/view/All/job/ln_weblogic',
			'http://192.168.1.166:8080/jenkins/view/All/job/ln_weblogic',
			'http://192.168.1.252:8080/jenkins/view/All/job/spectrum-installer-SNAPSHOT'
		]
	}
	
}


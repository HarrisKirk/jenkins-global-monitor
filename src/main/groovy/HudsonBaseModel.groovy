abstract class HudsonBaseModel  {

	String displayName = "Shaw Systems Hudson Server Farm"

	static DynamicConfigurationInterface globalConfig
	
	List serverList // Constructed server objects with received XML
	List ipAddressList
	List pipelineJobs
	
	HudsonBaseModel() {
		serverList = new ArrayList()
		populateModel()
		this.pipelineJobs = getPipelineJobs()  
	}

	abstract void populateModel()

	def getPipelineJobs() {
		def jobs = []
		globalConfig.getPipelineJobURLs().each {
			def xml = new URL( it + "/lastBuild/api/xml").text
			jobs.add ( new JenkinsJobStatus( xml ) )
		}
		return jobs
	}
	
	void parseIpString ( String ipString ) {
		this.ipAddressList = ipString.tokenize(",")
	}

	int size() { 
		return serverList.size() 
	}
	
	void add( HudsonServer hs ) { 
		serverList.add( hs ) 
	}
	
	static DynamicConfigurationInterface getDynamicConfiguration() {
		if ( globalConfig == null ) { 
			println ''
			println 'Loading configuration from /DynamicConfiguration.groovy...'
			println ''
			def inputStream = DynamicConfigurationInterface.class.getResourceAsStream("/DynamicConfiguration.groovy")
			String groovySource = inputStream.getText()
			GroovyClassLoader gcl = new GroovyClassLoader();
			Class clazz = gcl.parseClass(groovySource);
			globalConfig = clazz.newInstance();
		}
		return globalConfig
	}
	 
}


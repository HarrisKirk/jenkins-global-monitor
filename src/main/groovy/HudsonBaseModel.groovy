abstract class HudsonBaseModel  {

	String displayName = "Shaw Systems Hudson Server Farm"

	static DynamicConfigurationInterface globalConfig
	
	List serverList // Constructed server objects with received XML
	List ipAddressList
	Map pipelineSpecs
	Map pipelineModel
	
	HudsonBaseModel() {
		serverList = new ArrayList()
		populateModel()
		this.pipelineSpecs = globalConfig.getPipelineSpecs()
		this.pipelineModel = createPipelineModel()
	}

	abstract void populateModel()

	// Object model of pipeline job status
	def createPipelineModel() {
		def model = [:]
		this.pipelineSpecs.each { key, value ->
			// TODO: Handle the 'not found' condition
			List jobUrl = value
			jobUrl.each {
				String urlString = it + "/lastBuild/api/xml" 
				def xml = new URL( urlString ).text
				model.put ( key, new JenkinsJobStatus( xml ))
			}
		}
		return model
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


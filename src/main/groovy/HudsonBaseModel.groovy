abstract class HudsonBaseModel  {

	String displayName = "Shaw Systems Hudson Server Farm"

	List serverList // Constructed server objects with received XML
	List ipAddressList
	
	HudsonBaseModel() {
		serverList = new ArrayList()
		populateModel()
	}

	abstract void populateModel()

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
		def inputStream = DynamicConfigurationInterface.class.getResourceAsStream("/DynamicConfiguration.groovy")
		String groovySource = inputStream.getText()
		GroovyClassLoader gcl = new GroovyClassLoader();
		Class clazz = gcl.parseClass(groovySource);
		DynamicConfigurationInterface globalConfig = clazz.newInstance();
		return globalConfig
	}
	 
}


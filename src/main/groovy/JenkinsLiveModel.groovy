class JenkinsLiveModel extends JenkinsBaseModel {

	String displayName = "Shaw Systems Jenkins Server Farm "

	JenkinsLiveModel() {
		super( true )
	}
	
	JenkinsLiveModel( boolean isLiveQueryEnabled ) {
		super( isLiveQueryEnabled )
	}
	

	

	void populateModel() {
		DynamicConfigurationInterface globalConfig =  JenkinsBaseModel.getDynamicConfiguration()
		this.ipAddressList = globalConfig.getInstanceList()

		this.ipAddressList.each {
			def urlWithPort = it.url	
			String xmlMain, xmlBuildTimes 
			try {
				xmlMain 		= this.isLiveQueryEnabled ? getJenkinsXml( "${urlWithPort}/jenkins/view/All/api/xml" ) : ""
				xmlBuildTimes 	= this.isLiveQueryEnabled ? getJenkinsXml( "${urlWithPort}/jenkins/view/All/cc.xml" ) : ""
			} catch (Exception e) {
				xmlMain = null
				xmlBuildTimes = null
			} 
			add( new JenkinsServer( it, xmlMain, xmlBuildTimes ) )
		}
	}
	
	static boolean hasCustomerLogo( String imageName ) {
		def customImageFile = getClass().getResource("/${imageName}")  
		return customImageFile 
	}	
	 

	
}



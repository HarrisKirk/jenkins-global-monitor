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
				xmlMain 		= this.isLiveQueryEnabled ? new URL("${urlWithPort}/jenkins/view/All/api/xml").text : ""
				xmlBuildTimes 	= this.isLiveQueryEnabled ? new URL("${urlWithPort}/jenkins/view/All/cc.xml").text  : ""
			} catch (Exception e) {
				xmlMain = null
				xmlBuildTimes = null
			} 
			add( new JenkinsServer( it, xmlMain, xmlBuildTimes ) )
		}
	}
	 
}



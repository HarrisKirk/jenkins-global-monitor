class HudsonLiveModel extends HudsonBaseModel {

	String displayName = "Shaw Systems Jenkins Server Farm "

	HudsonLiveModel() {
		super( true )
	}
	
	HudsonLiveModel( boolean isLiveQueryEnabled ) {
		super( isLiveQueryEnabled )
	}
	

	

	void populateModel() {
		DynamicConfigurationInterface globalConfig =  HudsonBaseModel.getDynamicConfiguration()
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
			add( new HudsonServer( it, xmlMain, xmlBuildTimes ) )
		}
	}
	 
}



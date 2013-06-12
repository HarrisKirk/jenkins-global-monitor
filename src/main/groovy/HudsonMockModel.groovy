class HudsonMockModel extends HudsonBaseModel {

	String displayName = "Shaw Systems Hudson Server Farm (Test Data)"

	// TODO: Build out this mock server to allow easy simulation of test conditions

	HudsonMockModel() {
		super( true )
	}
	
	void populateModel() {
		this.ipAddressList = 
		[ 
		new JenkinsInstanceSpecification('114','http://192.168.1.114:8080'), 
		new JenkinsInstanceSpecification('55','http://192.168.1.55:8080'), 
		new JenkinsInstanceSpecification('147','http://192.168.1.147:8080') 
		]
		this.ipAddressList.each {
			add( new HudsonServer( it, TestConstants.XML_BASE, TestConstants.XML_BUILD_TIMES ) )
		}
	}
	
	
}

class JenkinsJobStatus {
 
	boolean isBuilding
	String buildResult
	
	JenkinsJobStatus( String xml ) {
		this.isBuilding = extractIsBuilding( xml )
		this.buildResult = extractBuildResult( xml )
	}
	
	boolean extractIsBuilding( def xml ) {
		def build = new XmlSlurper().parseText( xml )
		return build.building == 'true' ? true : false
	}
	
	String extractBuildResult( def xml ) {
		def build = new XmlSlurper().parseText( xml )
		return build.result
	}
	

	
}
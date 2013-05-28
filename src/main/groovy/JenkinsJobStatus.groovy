class JenkinsJobStatus {
 
	boolean isBuilding
	
	JenkinsJobStatus( String xml ) {
		this.isBuilding = extractIsBuilding( xml )
	}
	
	boolean extractIsBuilding( def xml ) {
		def build = new XmlSlurper().parseText( xml )
		return build.building == 'true' ? true : false
	}
	
}
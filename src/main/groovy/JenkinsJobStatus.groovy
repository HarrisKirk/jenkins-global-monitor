class JenkinsJobStatus {

	String jobUrl
	String jobName
	String jenkinsHost
	boolean isJenkinsJobFound 
	boolean isBuilding
	String buildResult
	long timeStamp

	Date buildDate	
	
	JenkinsJobStatus( String jobName, String jobUrl, String xml ) {
		this.jobUrl = jobUrl
		this.jobName = jobName 
		this.jenkinsHost = new URL( jobUrl ).getHost() 
		if ( xml ) {
			this.isJenkinsJobFound = true
			this.isBuilding = extractIsBuilding( xml )
			this.buildResult = extractBuildResult( xml )
			this.timeStamp = extractTimeStamp( xml )
			this.buildDate = new Date( this.timeStamp )
		} else {
			this.isJenkinsJobFound = false
		}
	}
	
	boolean extractIsBuilding( def xml ) {
		def build = new XmlSlurper().parseText( xml )
		return build.building == 'true' ? true : false
	}
	
	String extractBuildResult( def xml ) {
		def build = new XmlSlurper().parseText( xml )
		return build.result
	}
	
	long extractTimeStamp( def xml ) {
		def build = new XmlSlurper().parseText( xml )
		String ts = build.timestamp
		return Long.parseLong( ts )
	}
	
	String toString() {
		"Job: isBuilding: $isBuilding;   buildResult: $buildResult;   timeStamp: $timeStamp"
	}
	
}
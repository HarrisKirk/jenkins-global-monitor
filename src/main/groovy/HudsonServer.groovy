/*
 * Determine the real time status of a Jenkins server based on the 2 Xml streams
*/

class HudsonServer {
	static final String STATUS_COLOR_OK = "GreenYellow"
	static final String STATUS_COLOR_DOWN = "Grey"
	static final String STATUS_COLOR_FAILURES = "IndianRed"
  
    
	String ip
	String url
    String description

	def jobs = [:]
	ArrayList problemJobs = new ArrayList()
	
	ServerStatus status
        
	HudsonServer (JenkinsInstanceSpecification spec, String hudsonApiXml, String hudsonBuildTimesXml ) {
		this.ip = spec.ip
		this.url = "${spec.url}/jenkins/view/All"
		this.status = processServerResponse( hudsonApiXml, hudsonBuildTimesXml )
	}
		
	ServerStatus processServerResponse( String hudsonApiXml, String hudsonBuildTimesXml ) {
		boolean isValidXmlResponse = hudsonApiXml != null && !hudsonApiXml.equals("")
		if ( isValidXmlResponse ) {
			this.jobs = createJobMap ( hudsonApiXml, hudsonBuildTimesXml)
			determineProblemJobs()
			return determineServerStatus()
		} else {
			this.description = "(not available)"
			return new ServerStatus(HudsonServer.STATUS_COLOR_DOWN, 
				this.ip, this.url, "DOWN", this.description, null, null ) 
		}		
	}

	ServerStatus determineServerStatus() {
		if ( problemJobs ) {
			return new ServerStatus(HudsonServer.STATUS_COLOR_FAILURES, 
				this.ip, this.url, "FAILURES", this.description, getMostRecentJob(), problemJobs ) 
		} else {
			return new ServerStatus(HudsonServer.STATUS_COLOR_OK,
				this.ip, this.url, "OK", this.description, getMostRecentJob(), null )
		}
	}
	
	void determineProblemJobs() {
		this.jobs.values().each() {
			if (it.jobColor.startsWith("red") || it.jobColor.startsWith("yellow") ) {
				this.problemJobs.add( it )
			} 
		}
	}		
		
	def createJobMap( String hudsonApiXml, String hudsonBuildTimesXml ) {
		def jobMap = [:]
		def hudson = new XmlParser().parseText( hudsonApiXml )
		this.description = hudson.description.text()
		
		hudson.job.each{
			String jobName = it.name.text()
			def activity = it.color.text().equals( 'grey_anime') ? 'Building' : ''  // handle the special case of first build
			jobMap[jobName] = new HudsonJob( it.name.text(), it.url.text(), it.color.text(), activity, "" ) 
		}
		// merge time information from hudsonBuildTimesXml		
		def projects = new XmlParser().parseText( hudsonBuildTimesXml )
		projects.Project.each{
			jobMap.get( it.@name ).activity 		= it.@activity
			jobMap.get( it.@name ).lastBuildTime 	= it.@lastBuildTime
		}
		
		return jobMap
	}	
		
	HudsonJob getMostRecentJob() {
		List buildingJobs = getJobsInBuildingState() // should be only 1 unless multi-executor Hudson server
		if ( buildingJobs.size() > 0 ) {		
			return buildingJobs.last() 
		} else { 
			List sortedJobs = this.jobs.values().sort{ it.lastBuildTime }
			assert sortedJobs != null   
			return sortedJobs.last()
		}
	}	
	

	List getJobsInBuildingState() {
			return this.jobs.values().findAll{ job ->
				job.isBuilding() == true
			}
	}	


}

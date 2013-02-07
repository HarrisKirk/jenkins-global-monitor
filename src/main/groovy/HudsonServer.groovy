class HudsonServer {
	static final String STATUS_COLOR_OK = "GreenYellow"
	static final String STATUS_COLOR_DOWN = "Grey"
	static final String STATUS_COLOR_FAILURES = "IndianRed"
	static final String STATUS_COLOR_CAUTION = "Gold"
  
    
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
			return determineServerStatus()
		} else {
			this.description = "(not available)"
			return new ServerStatus(HudsonServer.STATUS_COLOR_DOWN, 
				this.ip, this.url, "DOWN", this.description, null, null ) 
		}		
	}

	ServerStatus determineServerStatus() {
		if ( isAllBlueOrDisabledJobs() ) 
			return new ServerStatus(HudsonServer.STATUS_COLOR_OK, 
				this.ip, this.url, "OK", this.description, getMostRecentJob(), null ) 
		else
		if ( isAnyRedJob() )   
			return new ServerStatus(HudsonServer.STATUS_COLOR_FAILURES, 
				this.ip, this.url, "FAILURES", this.description, getMostRecentJob(), problemJobs ) 
		else  
			return new ServerStatus(HudsonServer.STATUS_COLOR_CAUTION, 
				this.ip, this.url, "CAUTION", this.description, getMostRecentJob(), problemJobs ) 
	}	

	boolean isAllBlueOrDisabledJobs() {
		boolean allBlues = true
		jobs.values().each() {
			if ( ! ['blue', 'blue_anime', 'disabled'].contains( it.jobColor ) ) allBlues = false 
		}
		return allBlues	
	}		
	

	boolean isAnyRedJob() {
		boolean reds = false
		jobs.values().each() {
			if (it.jobColor.startsWith("red")) {
				reds = true
			} 
		}
		return reds	
	}		
		
	def createJobMap( String hudsonApiXml, String hudsonBuildTimesXml ) {
		def jobMap = [:]
		// create map of jobs from api/xml
		def hudson = new XmlParser().parseText( hudsonApiXml )
		this.description = hudson.description.text()
		
		def allJobs = hudson.job

		allJobs.each{
			HudsonJob job = new HudsonJob( it.name.text(), it.url.text(), it.color.text(), "", "" )
			String jobName = it.name.text()
			jobMap[jobName] = job 
			if ( ! ['blue', 'blue_anime', 'disabled'].contains( it.color.text() ) ) {   // anything but blue or disabled is a problem
				problemJobs.add( job )
			}
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

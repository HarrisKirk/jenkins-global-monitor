/*
 * Runtime attributes of a jenkins server use by the groovelet 
 */

class ServerStatus {
	String color
	String ip
	String url
	String health
	String message
	JenkinsJob mostRecentJob
	List problemJobs
	
	ServerStatus( 
		String color, String ip, String url, String health, String message, 
		JenkinsJob mostRecentJob, List problemJobs ) {
		
		this.color = color
		this.ip = ip
		this.url = url
		this.health = health
		this.message = message
		this.mostRecentJob = mostRecentJob
		this.problemJobs = problemJobs
	}
}
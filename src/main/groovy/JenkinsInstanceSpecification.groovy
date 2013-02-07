class JenkinsInstanceSpecification {
	String ip
	String url
	
	JenkinsInstanceSpecification( String ip, String url ) {
		this.ip = ip
		this.url = url
	}
	
	String toString() {
		"$ip;$url"
	}
}
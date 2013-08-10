/* 
 * Description of an individual Jenkins server 
 */
class JenkinsInstanceSpecification {
	String ip // ip or description
	String url
	
	JenkinsInstanceSpecification( String ip, String url ) {
		this.ip = ip
		this.url = url
	}
	
	String toString() {
		"$ip;$url"
	}
}
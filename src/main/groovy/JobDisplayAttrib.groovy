class JobDisplayAttrib {
	String linkText
	String linkUrl
	String addlText
	String tdColor 
	
	JobDisplayAttrib( JenkinsJobStatus jjs ) {
		linkText = jjs.jobName
		linkUrl = jjs.jobUrl
		addlText = '8'
		tdColor = 'YellowGreen'
	}
	
	
}
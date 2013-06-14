class JobDisplayAttrib {
	String linkText
	String linkUrl
	String addlText
	String tdColor 
	
	JobDisplayAttrib( JenkinsJobStatus jjs ) {
		linkText = jjs.jobName
		linkUrl = jjs.jobUrl
		addlText = ''
		tdColor = HtmlHelper.STATUS_COLOR_DOWN
	}
	
	String toString() {
		"linkText=$linkText;  linkUrl=$linkUrl;  addlText=$addlText;   tdColor=$tdColor"
	}
}
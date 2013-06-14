class JobDisplayAttrib {
	String linkText
	String linkUrl
	String addlText
	String tdColor 
	
//	String jobUrl
//	String jobName
//	String jenkinsHost
//	boolean isJenkinsJobFound
//	boolean isBuilding
//	String buildResult
//	long timeStamp

	
	JobDisplayAttrib( JenkinsJobStatus jjs ) {
		if ( !jjs.isJenkinsJobFound ) {
			linkText = jjs.jobName
			linkUrl = jjs.jobUrl
			addlText = ''
			tdColor = HtmlHelper.STATUS_COLOR_DOWN
		} else {
			if ( jjs.isBuilding ) {
				linkText = jjs.jobName
				linkUrl = jjs.jobUrl
				addlText = 'building...'
				tdColor = HtmlHelper.STATUS_COLOR_BUILDING
			} else {
				if ( jjs.buildResult.equals("SUCCESS")) {
					linkText = jjs.jobName
					linkUrl = jjs.jobUrl
					addlText = ''
					tdColor = HtmlHelper.STATUS_COLOR_OK
				} else {
					linkText = jjs.jobName
					linkUrl = jjs.jobUrl
					addlText = ''
					tdColor = HtmlHelper.STATUS_COLOR_FAILURES
				}
			}
		}
		
		
	}
	
	String toString() {
		"linkText=$linkText;  linkUrl=$linkUrl;  addlText=$addlText;   tdColor=$tdColor"
	}
}
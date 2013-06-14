class JobDisplayAttrib {
	static final String DISPLAY_BUILDING = 'building...'
	String linkText
	String linkUrl
	String addlText
	String tdColor 
	
	JobDisplayAttrib( JenkinsJobStatus jjs ) {
		linkText = jjs.jobName
		if ( !jjs.isJenkinsJobFound ) {
			linkUrl = jjs.jobUrl
			addlText = ''
			tdColor = HtmlHelper.STATUS_COLOR_DOWN
		} else {
			if ( jjs.isBuilding ) {
				linkUrl = jjs.jobUrl
				addlText = DISPLAY_BUILDING
				tdColor = HtmlHelper.STATUS_COLOR_BUILDING
			} else {
				if ( jjs.buildResult.equals("SUCCESS")) {
					linkUrl = jjs.jobUrl
					addlText = computeBuildText( jjs.timeStamp )
					tdColor = HtmlHelper.STATUS_COLOR_OK
				} else {
					linkUrl = jjs.jobUrl
					addlText = computeBuildText( jjs.timeStamp )
					tdColor = HtmlHelper.STATUS_COLOR_FAILURES
				}
			}
		}
		
		
	}
	
	String computeBuildText( long timeStamp ) {
		return new HudsonTimeComparator( timeStamp ).getDisplayTime()
	}
	
	String toString() {
		"linkText=$linkText;  linkUrl=$linkUrl;  addlText=$addlText;   tdColor=$tdColor"
	}
}
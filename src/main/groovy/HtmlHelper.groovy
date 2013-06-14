class HtmlHelper {
	
	static final String STATUS_COLOR_OK = "GreenYellow"
	static final String STATUS_COLOR_DOWN = "Grey"
	static final String STATUS_COLOR_FAILURES = "IndianRed"
	static final String STATUS_COLOR_CAUTION = "Gold"
	static final String STATUS_COLOR_BUILDING = "Blue"
	
	def getRefreshHtml( String queryParameterValue ) {
	
		if ( queryParameterValue == null ) return new RefreshDisabledHtml()
		if ( queryParameterValue.equals("true") ) return new RefreshEnabledHtml()
		
		return new RefreshDisabledHtml()
		 
	}
	
	boolean getTrueIfExplicitlySet( String queryParameterValue ) {
	
		if ( queryParameterValue == null ) return false
		if ( queryParameterValue.equals("true") ) return true
		
		return false
		 
	}
	
}


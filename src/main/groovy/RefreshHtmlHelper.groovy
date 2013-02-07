class RefreshHtmlHelper {
	
	def getRefreshHtml( String queryParameterValue ) {
	
		if ( queryParameterValue == null ) return new RefreshDisabledHtml()
		if ( queryParameterValue.equals("true") ) return new RefreshEnabledHtml()
		
		return new RefreshDisabledHtml()
		 
	}
	
}


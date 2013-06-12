class HtmlHelper {
	
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


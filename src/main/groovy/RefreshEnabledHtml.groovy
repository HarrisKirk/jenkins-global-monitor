class RefreshEnabledHtml {
	// all html needed when refresh is enabled
	DynamicConfigurationInterface globalConfig =  HudsonBaseModel.getDynamicConfiguration()
	def refreshIntervalSecs = globalConfig.getRefreshIntervalSecs()
	String meta = """ <meta http-equiv="refresh" content="${refreshIntervalSecs}"> """
	String href_url = "?auto_refresh=false"
	String href_string = "Disable Auto-Refresh"
}
	
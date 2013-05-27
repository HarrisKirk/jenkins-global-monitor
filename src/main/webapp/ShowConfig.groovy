import java.text.SimpleDateFormat

HudsonBaseModel hbm = new HudsonLiveModel()
def htmlHelper = new RefreshHtmlHelper().getRefreshHtml(request.getParameter("auto_refresh"))

html.html {
		head {
				title hbm.displayName
				println htmlHelper.meta
		}
		body {
				table (border:'0') {
						tr {
								th {
										img (src:"jenkins-butler.jpeg", width:'70', height:'70', align:'bottom')
								}
								th {
										img (src:"blank.png", width:'70', height:'70', align:'bottom')
								}
						}
				}

				table (summary:'binding', border:'1') {
					tbody {
						tr (bgcolor:'Linen') {
						   th 'Jenkins IP'
						}
						hbm.serverList.each{ server ->
							tr (bgcolor:server.status.color) {
								td {
									a href:"${server.url}", "${server.ip}"
								}
							} // tr
						}  // each
					} // tbody
				} // table
			font (size:'3') { left 'jenkins-global-monitor ' + MonitorUtil.parseCVS( '$Name:  $') }
			font (size:'2') { right 'Compiled with groovyc; Built by Gradle' }
		} // body
}
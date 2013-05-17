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
                                th { '  ' }
                                th {
                                        font (size:'10') { right new SimpleDateFormat("EEEE hh:mm:ss a").format(new Date()) } 
                                }

                        }
                }

				p (align:"LEFT") { a href:"${htmlHelper.href_url}", "${htmlHelper.href_string}"
				}
				
                table (summary:'binding', border:'1') {
                        tbody {
                                tr (bgcolor:'Linen') {
                                        th 'Jenkins IP'
                                        th 'Status'
                                        th 'Server Description '
                                        th 'All Problem Jobs'
                                        th 'Most Recent Job'
                                        th 'When'
                                }


                                hbm.serverList.each{ server ->
                                        tr (bgcolor:server.status.color) {
                        td {    
                           a href:"${server.url}", "${server.ip}" 
                        }
                            td server.status.health
                        td server.description.replaceAll("<(.|\n)*?>", '')

                        if ( server.status.color.equals( HudsonServer.STATUS_COLOR_DOWN ) ) {
                                td ''
                                td ''
                                td ''
                        } else {
							td { 
                                     List problemJobs = server.status.problemJobs 
                                     if ( problemJobs != null ) {
                                        for (int i=0; i<problemJobs.size(); i++) {
                                          a href:"${problemJobs.getAt(i).url}" + "lastBuild/console", "${problemJobs.getAt(i).name}" 
                                        } // for
                                     } // if
                                } // td
                                td {    
                                   a href:"${server.status.mostRecentJob.url}", "${server.status.mostRecentJob.name}" 
                                }

								if ( server.status.mostRecentJob.isBuilding() ) {
									td { strong server.status.mostRecentJob.getRecentBuildMessage() }
								} else {
									td server.status.mostRecentJob.getRecentBuildMessage()
								}
		
                        } // else

                } // tr
                }  // each

                  } // tbody 
                } // table
                font (size:'3') { left 'jenkins-global-monitor ' + MonitorUtil.parseCVS( '$Name:  $') }
                br {} 
                font (size:'2') { right 'Compiled with groovyc; Built by Gradle; Powered by Tomcat and the Java 6 runtime' }        } // body
}
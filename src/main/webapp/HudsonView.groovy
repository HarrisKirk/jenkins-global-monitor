import java.text.SimpleDateFormat

boolean isLiveJenkinsEnabled = new HtmlHelper().getTrueIfExplicitlySet(request.getParameter("jenkins_disabled")) ? false : true
HudsonBaseModel hbm = new HudsonLiveModel( isLiveJenkinsEnabled )
def htmlHelper = new HtmlHelper().getRefreshHtml(request.getParameter("auto_refresh"))

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
				
				
				// Display any pipelines (related jobs run in sequence)
				if ( hbm.pipelineModel.size() >0 ) {
					br {}
					hbm.pipelineModel.each { pipelineName, value ->
						def jobList = value							
						out.println '<table border="2">'
						out.println "<tr>"
							String pipelineColor = 'Linen'
							out.println '<td bgcolor="' + pipelineColor + '">'  + pipelineName + '</td>'
							jobList.each {
								
								JobDisplayAttrib jda = new JobDisplayAttrib( it )
								
								out.println '<td bgcolor="' + jda.tdColor + '">' 
								out.println '<a href="' + jda.linkUrl + '">' + jda.linkText + '</a>' + " " + jda.addlText
								out.println '</td>'
							}			
						out.println "</tr>"
						out.println '</table>'
					}
				}
				
				br {}
				font (size:'3') { left 'jenkins-global-monitor ' }
		} // body
}
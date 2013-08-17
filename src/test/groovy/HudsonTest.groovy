class HudsonTest extends GroovyTestCase {
	
		  public final JenkinsInstanceSpecification dummySpec = new JenkinsInstanceSpecification ("0.0.0.0","http://0.0.0.0:8080")
	
		void testDynamicConfig() {
			DynamicConfigurationInterface globalConfig =  HudsonBaseModel.getDynamicConfiguration()
			assertEquals ( 4, globalConfig.getInstanceList().size()  )
			assertEquals ( '114', globalConfig.getInstanceList()[0].ip )
			assertTrue ( globalConfig.getRefreshIntervalSecs() >10 )
			assertFalse ( globalConfig.getPipelineSpecs() == null )
			assertTrue ( globalConfig.getPipelineSpecs().size() == 2 )
		}
	
		void testPipelineModel() {
			HudsonBaseModel hm = new HudsonMockModel()
			hm.pipelineModel.each { key, value ->
				def jobStatus = value
				jobStatus.each {
					assertTrue( it.timeStamp > 0 )
					if ( it.buildResult ) {
						assert ( !it.isBuilding )
					}
						
				}
			}
		}
		
		void testJobDisplayAttrib() {
			def testCases = [
				(new JobDisplayAttrib ( new JenkinsJobStatus( "JOBA", "http://...", JobTestConstants.XML_EMPTY )).tdColor) : HtmlHelper.STATUS_COLOR_DOWN,
				(new JobDisplayAttrib ( new JenkinsJobStatus( "JOBA", "http://...", JobTestConstants.XML_EMPTY )).linkText) : 'JOBA',
				(new JobDisplayAttrib ( new JenkinsJobStatus( "JOBA", "http://...", JobTestConstants.XML_EMPTY )).addlText) : '',
				
				(new JobDisplayAttrib ( new JenkinsJobStatus( "JOBA", "http://...", JobTestConstants.XML_JOB_STATUS_SUCCESS )).tdColor) : HtmlHelper.STATUS_COLOR_OK,
				(new JobDisplayAttrib ( new JenkinsJobStatus( "JOBA", "http://...", JobTestConstants.XML_JOB_STATUS_SUCCESS )).linkUrl) : "http://...",
				
				(new JobDisplayAttrib ( new JenkinsJobStatus( "JOBA", "http://...", JobTestConstants.XML_JOB_STATUS_FAILURE )).tdColor) : HtmlHelper.STATUS_COLOR_FAILURES,
				
				(new JobDisplayAttrib ( new JenkinsJobStatus( "JOBA", "http://...", JobTestConstants.XML_JOB_STATUS_BUILDING )).tdColor) : HtmlHelper.STATUS_COLOR_BUILDING,
				(new JobDisplayAttrib ( new JenkinsJobStatus( "JOBA", "http://...", JobTestConstants.XML_JOB_STATUS_BUILDING )).addlText) : JobDisplayAttrib.DISPLAY_BUILDING,
			]
			
			testCases.each { testCase, expectedResponse ->
				assertEquals( "Test Case: ${testCase}",   expectedResponse, testCase )
			}
			
		}
		
		void testJenkinsJobStatus() {
			def xml = JobTestConstants.XML_JOB_STATUS_SUCCESS
			JenkinsJobStatus jjs = new JenkinsJobStatus( "132:XYZ", "http://192.168.1.142:8080/jenkins/job/XYZ" , xml )
			assertTrue ( jjs.jobName == '132:XYZ')
			assertTrue ( jjs.jenkinsHost == '192.168.1.142')
			assertTrue ( jjs.isBuilding == false)
			assertTrue ( jjs.buildResult.equals('SUCCESS'))
			assertTrue ( jjs.buildDate.toString().equals('Mon May 27 07:45:15 EDT 2013'))
		}
		
		void testBasicModelAtrributes() {
			[
				new HudsonMockModel(),
				//new HudsonLiveModel(),       // uncomment to also run the live tests in method below
			].each { model ->
				doTestBasicModelAtrributes( model )
			}
				
		}
	
	
		
		void doTestBasicModelAtrributes( HudsonBaseModel hm ) {
			// Useful for writing unit tests temporarily against
			// a live site.
			assertTrue("No servers in model list", hm.size() >0)
			hm.serverList.each{
				assertFalse( "".equals(it.ip) )
				assertTrue("Model: ${hm.displayName}: No jobs on server ${it.ip}", it.jobs.size() >0)
				it.jobs.each{ key, value ->
					assertTrue( TestConstants.HUDSON_JOB_COLOR_LIST.contains(value.jobColor) )
				}
				//assertEquals( "The 2 jobs in Building state were not detected", 2, it.getJobsInBuildingState().size);
				//assertEquals(  hs.status.mostRecentJob.name, "zxB" )
				//assertEquals( hs.status.mostRecentJob.isBuilding(), true )
				
			}
		}
	
		void testAllAutoRefreshSettings() {
			
			def testCases = [
				// the conditions for refresh enabled
				(new HtmlHelper().getRefreshHtml( "true" )) : new RefreshEnabledHtml(),
	
				// the conditions for refresh disabled
				(new HtmlHelper().getRefreshHtml( null )) 				: new RefreshDisabledHtml(),
				(new HtmlHelper().getRefreshHtml( "false" ))  			: new RefreshDisabledHtml(),
				(new HtmlHelper().getRefreshHtml( "non-truefalse" )) 	: new RefreshDisabledHtml(),
				(new HtmlHelper().getRefreshHtml( "" ))  				: new RefreshDisabledHtml(),
	
			]
			
			testCases.each {
				testCase, expectedResponse ->
				assertEquals( "Test Case: ${testCase}",  testCases[ testCase ], expectedResponse )
			}
	
		}
	
	
	
		void testIpListFromProperty() {
			HudsonBaseModel hm = new HudsonMockModel()
			assertTrue( hm.ipAddressList != null && hm.ipAddressList.size >1)
		}
	
	
		void testTimeComparisons() {
			final String MOCK_CURRENT_TIME = "2011-03-27T18:00:00Z"  // 2 PM EDT (daylight savings)
	
			def testCases = [
				"2011-03-27T18:10:00Z":' -10 mins ago',
				"2011-03-27T17:30:00Z":'  30 mins ago',
				"2011-03-27T16:30:00Z":'  90 mins ago',
				"2011-03-27T16:00:00Z":' 120 mins ago',
				"2011-03-27T15:59:00Z":'   2 hrs ago',
				"2011-03-25T18:00:00Z":'  48 hrs ago',
				"2011-03-25T17:59:00Z":'  48 hrs ago',
				"2011-03-25T17:00:00Z":'   2 days ago',
				"2011-03-24T17:00:00Z":'   3 days ago',
			]
	
			testCases.each {
				testCase, expectedResponse ->
				HudsonTimeComparator comparator = new HudsonTimeComparator( testCase, MOCK_CURRENT_TIME )
				assertEquals( "Test Case: ${testCase}",  testCases[ testCase ], comparator.getDisplayTime() )
			}
			
		}
	
		void testTimeComparisonsLong() {
			long simulatedJobTime1 = new Date().getTime() - ( 130 * 1000 )
			HudsonTimeComparator comparator1 = new HudsonTimeComparator( simulatedJobTime1 , new Date().getTime() )
			assertEquals( '   2 mins ago' , comparator1.getDisplayTime() )
			
			long simulatedJobTime2 = new Date().getTime() - ( 250 * 60 * 1000 )
			HudsonTimeComparator comparator2 = new HudsonTimeComparator( simulatedJobTime2 , new Date().getTime() )
			assertEquals( '   4 hrs ago' , comparator2.getDisplayTime() )
		}
	
	
		void testReplace() {
			String testString     = "2011-03-25T21:00:00Z"
			String expectedString = "2011-03-25 21:00:00"
			assertEquals ( HudsonTimeComparator.replaceTandZ( testString ), expectedString )
		}
		
		void testJobMapSize() {
			HudsonServer hs = new HudsonServer( dummySpec, TestConstants.XML_BASE, TestConstants.XML_BUILD_TIMES )
			assertEquals( "Incorrect # jobs ", hs.jobs.size(), 17 )
		}
		
		
	
	
	
	
	//
	// Test the basic server health conditions =============================
	//
	
		void testServerStatus_DOWN() {
			[
				new HudsonServer( dummySpec, "", TestConstants.XML_BUILD_TIMES ),
				new HudsonServer( dummySpec, null, TestConstants.XML_BUILD_TIMES )
			].each {
				assertEquals( "Incorrect Server Status", HudsonServer.STATUS_COLOR_DOWN, it.status.color )
			}
		}
		
		void testServerStatus_OK() {
			HudsonServer hs = new HudsonServer( dummySpec, TestConstants.XML_BASE, TestConstants.XML_BUILD_TIMES )
			assertEquals( "Incorrect Server Status", HudsonServer.STATUS_COLOR_OK, hs.status.color )
		}

	
		void testServerStatus_FAILURES() {
			HudsonServer hs = new HudsonServer( dummySpec, TestConstants.XML_0DISABLED_3RED_BLUE, TestConstants.XML_BUILD_TIMES )
			assertEquals( "Incorrect Server Status", HudsonServer.STATUS_COLOR_FAILURES, hs.status.color )
			assertEquals( 3, hs.status.problemJobs.size )
		}
	
	//
	// Test the most recent activity conditions =============================
	//
	
	// Test the HudsonJob constructors
		void testHudsonJobCtors() {
			HudsonJob job = new HudsonJob( 'latest-demand', 'http...', 'blue', 'Building', '2011-04-10T01:01:19Z' )
			
			assertTrue( job.isBuilding() )
			assertEquals( "Building now...", job.getRecentBuildMessage() )
			
			HudsonJob job2 = new HudsonJob( 'latest-demand', 'http...', 'blue', 'Sleeping', '2011-04-10T01:01:19Z' )
			assertFalse( job2.isBuilding() )
			
		}
	
		
		void testMostRecentJobFound_CASE_Server_Down() {
			def expectedJobName = null
			HudsonServer hs = new HudsonServer( dummySpec, null, null )
	
			assertEquals( expectedJobName, hs.status.mostRecentJob )
		}
	
		void testMostRecentJobFound_CASE_Server_Building() {
			HudsonServer hs = new HudsonServer( dummySpec, TestConstants_2.XML_BASE, TestConstants_2.XML_BUILD_TIMES )
			assertEquals( "latest-demand", hs.status.mostRecentJob.name )
			assertEquals( "Building now...", hs.status.mostRecentJob.getRecentBuildMessage() )
		}
	
		void test2BuildingJobs_CASE_Multiple_Executors() {
			HudsonServer hs = new HudsonServer( dummySpec, TestConstants.XML_2_BUILDING_BASE, TestConstants.XML_2_BUILDING_BUILD_TIMES )
			assertEquals( "The 2 jobs in Building state were not detected", hs.getJobsInBuildingState().size, 2);
			assertEquals( "zxB", hs.status.mostRecentJob.name )
			assertEquals( true, hs.status.mostRecentJob.isBuilding() )
			
		}
	
		
	}
	
class HudsonTest extends GroovyTestCase {

  	public final JenkinsInstanceSpecification dummySpec = new JenkinsInstanceSpecification ("0.0.0.0","http://0.0.0.0:8080")

	void testDynamicConfig() {
		DynamicConfigurationInterface globalConfig =  HudsonBaseModel.getDynamicConfiguration()
		assertEquals ( 18, globalConfig.getInstanceList().size()  )
		assertEquals ( '114', globalConfig.getInstanceList()[0].ip )
		assertTrue ( globalConfig.getRefreshIntervalSecs() >10 )
		assertTrue ( globalConfig.getPipelineJobURLs().size() >1 )
	}

	void testBasicModelAtrributes() {
		[
			new HudsonMockModel(),   
			//new HudsonLiveModel(),       // uncomment to also run the live tests in method below
		].each { model ->
			doTestBasicModelAtrributes( model )
		}
			
	}

	void testJobStatus() {
		def xml = JobTestConstants.XML_JOB_STATUS
		JenkinsJobStatus jjs = new JenkinsJobStatus( xml )
		assertTrue ( jjs.isBuilding == false)
		assertTrue ( jjs.buildResult.equals('SUCCESS'))
		assertTrue ( jjs.buildDate.toString().equals('Mon May 27 07:45:15 EDT 2013'))
	}
	
	void testPipelineJobsOfModel() {
		HudsonBaseModel hm = new HudsonMockModel()
		assertTrue hm.pipelineJobs.size() == 3
		hm.pipelineJobs.each {
			assertTrue( it.timeStamp > 0 )
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
			(new RefreshHtmlHelper().getRefreshHtml( "true" )) : new RefreshEnabledHtml(),  

			// the conditions for refresh disabled
			(new RefreshHtmlHelper().getRefreshHtml( null )) 				: new RefreshDisabledHtml(), 
			(new RefreshHtmlHelper().getRefreshHtml( "false" ))  			: new RefreshDisabledHtml(), 
			(new RefreshHtmlHelper().getRefreshHtml( "non-truefalse" )) 	: new RefreshDisabledHtml(), 
			(new RefreshHtmlHelper().getRefreshHtml( "" ))  				: new RefreshDisabledHtml(),

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
	void testServerStatus_CAUTION() {
		HudsonServer hs = new HudsonServer( dummySpec, TestConstants.XML_BASE_1ABORTED_1YELLOW, TestConstants.XML_BUILD_TIMES )
		assertEquals( "Incorrect Server Status", HudsonServer.STATUS_COLOR_CAUTION, hs.status.color )
		assertEquals( 2, hs.status.problemJobs.size )
		
		HudsonServer hs2 = new HudsonServer( dummySpec, TestConstants_2.XML_BASE_ALL_PENDING, TestConstants_2.XML_BUILD_TIMES_ALL_PENDING )
		assertEquals( "Incorrect Server Status", HudsonServer.STATUS_COLOR_CAUTION, hs2.status.color )

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

	void testMostRecentJobFound_CASE_All_Jobs_Aborted() {
		// Test case when a new hudson server is built.   All jobs are then "Pending" and
		// the xml file from build history is empty.   In that case, all jobs will have 
		// empty string for lastBuildTime and activity. 
		// TODO: Its expected to fail on the next server re-installation  
		
		HudsonServer hs = new HudsonServer( dummySpec, TestConstants_2.XML_BASE_ALL_PENDING, TestConstants_2.XML_BUILD_TIMES_ALL_PENDING )
		def expectedLastBuildTime = ""   
		def expectedActivity = ""   
		assertEquals( expectedLastBuildTime, hs.status.mostRecentJob.lastBuildTime )
		assertEquals( expectedActivity, hs.status.mostRecentJob.activity )
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



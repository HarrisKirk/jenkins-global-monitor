class JenkinsTest extends GroovyTestCase {
	
		public final JenkinsInstanceSpecification dummySpec = new JenkinsInstanceSpecification ("0.0.0.0","http://0.0.0.0:8080")
			
		void testDynamicConfig() {
			DynamicConfigurationInterface globalConfig =  JenkinsBaseModel.getDynamicConfiguration()
			assertEquals ( 3, globalConfig.getInstanceList().size()  )
			assertEquals ( '111', globalConfig.getInstanceList()[0].ip )
			assertTrue ( globalConfig.getRefreshIntervalSecs() >10 )
			assertFalse ( globalConfig.getPipelineSpecs() == null )
			assertTrue ( globalConfig.getPipelineSpecs().size() == 2 )
		}
	
		void testAllAutoRefreshSettings() {
			
			def testCases = [
				// the conditions for refresh enabled
				(new HtmlHelper().getRefreshHtml( "true" )) 			: new RefreshEnabledHtml(),
	
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
				JenkinsTimeComparator comparator = new JenkinsTimeComparator( testCase, MOCK_CURRENT_TIME )
				assertEquals( "Test Case: ${testCase}",  testCases[ testCase ], comparator.getDisplayTime() )
			}
			
		}
		
		void testTimeComparisonsLong() {
			long simulatedJobTime1 = new Date().getTime() - ( 130 * 1000 )
			JenkinsTimeComparator comparator1 = new JenkinsTimeComparator( simulatedJobTime1 , new Date().getTime() )
			assertEquals( '   2 mins ago' , comparator1.getDisplayTime() )
			
			long simulatedJobTime2 = new Date().getTime() - ( 250 * 60 * 1000 )
			JenkinsTimeComparator comparator2 = new JenkinsTimeComparator( simulatedJobTime2 , new Date().getTime() )
			assertEquals( '   4 hrs ago' , comparator2.getDisplayTime() )
		}
	
		void testReplace() {
			String testString     = "2011-03-25T21:00:00Z"
			String expectedString = "2011-03-25 21:00:00"
			assertEquals ( JenkinsTimeComparator.replaceTandZ( testString ), expectedString )
		}

		void testJenkinsJobCtors() {
			JenkinsJob job = new JenkinsJob( 'latest-demand', 'http...', 'blue', 'Building', '2011-04-10T01:01:19Z' )
			
			assertTrue( job.isBuilding() )
			assertEquals( "Building now...", job.getRecentBuildMessage() )
			
			JenkinsJob job2 = new JenkinsJob( 'latest-demand', 'http...', 'blue', 'Sleeping', '2011-04-10T01:01:19Z' )
			assertFalse( job2.isBuilding() )
		}
	
		
		void testMostRecentJobFound_CASE_Server_Down() {
			def expectedJobName = null
			JenkinsServer hs = new JenkinsServer( dummySpec, null, null )
	
			assertEquals( expectedJobName, hs.status.mostRecentJob )
		}
	
}
	
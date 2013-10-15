class JenkinsXmlTest extends GroovyTestCase {

	public final JenkinsInstanceSpecification dummySpec = new JenkinsInstanceSpecification ("0.0.0.0","http://0.0.0.0:8080")
	
/*
 * TODO: Refactor to ONLY test the reading of the xml files. 
 */
		
		// ?????		
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
				new JenkinsMockModel(),
				//new JenkinsLiveModel(),       // uncomment to also run the live tests in method below
			].each { model ->
				doTestBasicModelAtrributes( model )
			}
				
		}
	
	
		// Remove - not unit tests
		void doTestBasicModelAtrributes( JenkinsBaseModel hm ) {
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

//
// Test the basic server health conditions =============================
//
		void testServerStatus_DOWN() {
			[
				new JenkinsServer( dummySpec, "", TestConstants.XML_BUILD_TIMES ),
				new JenkinsServer( dummySpec, null, TestConstants.XML_BUILD_TIMES )
			].each {
				assertEquals( "Incorrect Server Status", JenkinsServer.STATUS_COLOR_DOWN, it.status.color )
			}
		}
		
		void testServerStatus_OK() {
			JenkinsServer hs = new JenkinsServer( dummySpec, TestConstants.XML_BASE, TestConstants.XML_BUILD_TIMES )
			assertEquals( "Incorrect Server Status", JenkinsServer.STATUS_COLOR_OK, hs.status.color )
		}

	
		void testServerStatus_FAILURES() {
			JenkinsServer hs = new JenkinsServer( dummySpec, TestConstants.XML_0DISABLED_3RED_BLUE, TestConstants.XML_BUILD_TIMES )
			assertEquals( "Incorrect Server Status", JenkinsServer.STATUS_COLOR_FAILURES, hs.status.color )
			assertEquals( 3, hs.status.problemJobs.size )
		}
	
//
// Test the most recent activity conditions =============================
//
		void testFirstBuildofJob() {
			// Tests the condition of a first-time build of a jenkins job (where there is no entry in the build times xml)
			JenkinsServer hs = new JenkinsServer( dummySpec, TestConstants_2.BASE_FIRST_JOB, TestConstants_2.BUILD_TIMES_FIRST_JOB )
			assertEquals( "test-job", hs.status.mostRecentJob.name )
			assertEquals( "Building now...", hs.status.mostRecentJob.getRecentBuildMessage() )
		}
	
		void test2BuildingJobs_CASE_Multiple_Executors() {
			JenkinsServer hs = new JenkinsServer( dummySpec, TestConstants.XML_2_BUILDING_BASE, TestConstants.XML_2_BUILDING_BUILD_TIMES )
			assertEquals( "The 2 jobs in Building state were not detected", hs.getJobsInBuildingState().size, 2);
			assertEquals( "zxB", hs.status.mostRecentJob.name )
			assertEquals( true, hs.status.mostRecentJob.isBuilding() )
			
		}
	
		
	}
	
class JobTestConstants {

// http://192.168.1.166:8080/jenkins/job/[[jobname]]/lastBuild/api/xml
		
static final String XML_JOB_STATUS_SUCCESS =
	"""
	<freeStyleBuild>
	<action>
		<cause>
			<shortDescription>Started by user Fred Flintstone</shortDescription>
			<userId>fflintstone</userId>
			<userName>Fred Flintstone</userName>
		</cause>
	</action>
	<action/>
	<action/>
	<building>false</building>
	<duration>5142209</duration>
	<estimatedDuration>5104209</estimatedDuration>
	<fullDisplayName>spectrum-installer-SNAPSHOT #658</fullDisplayName>
	<id>2013-05-27_07-45-15</id>
	<keepLog>false</keepLog>
	<number>658</number>
	<result>SUCCESS</result>
	<timestamp>1369655115579</timestamp>
	<url>
	http://192.168.1.252:8080/jenkins/view/All/job/spectrum-installer-SNAPSHOT/658/
	</url>
	<builtOn/>
	<changeSet>
	<kind>cvs</kind>
	</changeSet>
	</freeStyleBuild>
	"""

	static final String XML_EMPTY = ''
	
	static final String XML_JOB_STATUS_FAILURE =
	"""
	<freeStyleBuild>
	<action>
		<cause>
			<shortDescription>Started by user Fred Flintstone</shortDescription>
			<userId>fflintstone</userId>
			<userName>Fred Flintstone</userName>
		</cause>
	</action>
	<action/>
	<action/>
	<building>false</building>
	<duration>5142209</duration>
	<estimatedDuration>5104209</estimatedDuration>
	<fullDisplayName>spectrum-installer-SNAPSHOT #658</fullDisplayName>
	<id>2013-05-27_07-45-15</id>
	<keepLog>false</keepLog>
	<number>658</number>
	<result>FAILURE</result>
	<timestamp>1369655115579</timestamp>
	<url>
	http://192.168.1.252:8080/jenkins/view/All/job/spectrum-installer-SNAPSHOT/658/
	</url>
	<builtOn/>
	<changeSet>
	<kind>cvs</kind>
	</changeSet>
	</freeStyleBuild>
	"""

	static final String XML_JOB_STATUS_BUILDING =
	"""
	<freeStyleBuild>
	<action>
		<cause>
			<shortDescription>Started by user Fred Flintstone</shortDescription>
			<userId>fflintstone</userId>
			<userName>Fred Flintstone</userName>
		</cause>
	</action>
	<action/>
	<action/>
	<building>true</building>
	<duration>5142209</duration>
	<estimatedDuration>5104209</estimatedDuration>
	<fullDisplayName>spectrum-installer-SNAPSHOT #658</fullDisplayName>
	<id>2013-05-27_07-45-15</id>
	<keepLog>false</keepLog>
	<number>658</number>
	<result>FAILURE</result>
	<timestamp>1369655115579</timestamp>
	<url>
	http://192.168.1.252:8080/jenkins/view/All/job/spectrum-installer-SNAPSHOT/658/
	</url>
	<builtOn/>
	<changeSet>
	<kind>cvs</kind>
	</changeSet>
	</freeStyleBuild>
	"""

	
}


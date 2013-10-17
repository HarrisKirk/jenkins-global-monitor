class TestConstants_2 {

static final String XML_BASE = """<allView><description>&lt;h1&gt;'LATEST' sites and more&lt;/h1&gt;
</description><job><name>EOD-Client-Installer</name><url>http://192.168.1.55:8080/jenkins/job/EOD-Client-Installer/</url><color>blue</color></job><job><name>EOD-latest-demand</name><url>http://192.168.1.55:8080/jenkins/job/EOD-latest-demand/</url><color>blue</color></job><job><name>EOD-latest-nightly</name><url>http://192.168.1.55:8080/jenkins/job/EOD-latest-nightly/</url><color>red</color></job><job><name>EOD-reset-COA</name><url>http://192.168.1.55:8080/jenkins/job/EOD-reset-COA/</url><color>blue</color></job><job><name>latest-demand</name><url>http://192.168.1.55:8080/jenkins/job/latest-demand/</url><color>blue</color></job><job><name>latest-nightly</name><url>http://192.168.1.55:8080/jenkins/job/latest-nightly/</url><color>red_anime</color></job><job><name>latest-nightly-logs</name><url>http://192.168.1.55:8080/jenkins/job/latest-nightly-logs/</url><color>blue</color></job><name>All</name><url>http://192.168.1.55:8080/jenkins/view/All/</url></allView>
"""
static final String XML_BUILD_TIMES =
"""
<Projects>
<Project activity="Sleeping" lastBuildTime="2012-01-09T20:48:24Z" webUrl="http://192.168.1.55:8080/jenkins/job/EOD-Client-Installer/" lastBuildLabel="19" name="EOD-Client-Installer" lastBuildStatus="Success"/>
<Project activity="Sleeping" lastBuildTime="2011-12-22T15:25:54Z" webUrl="http://192.168.1.55:8080/jenkins/job/EOD-latest-demand/" lastBuildLabel="33" name="EOD-latest-demand" lastBuildStatus="Success"/>
<Project activity="Sleeping" lastBuildTime="2012-01-23T20:23:04Z" webUrl="http://192.168.1.55:8080/jenkins/job/EOD-latest-nightly/" lastBuildLabel="144" name="EOD-latest-nightly" lastBuildStatus="Failure"/>
<Project activity="Sleeping" lastBuildTime="2012-01-23T19:58:15Z" webUrl="http://192.168.1.55:8080/jenkins/job/EOD-reset-COA/" lastBuildLabel="6" name="EOD-reset-COA" lastBuildStatus="Success"/>
<Project activity="Building" lastBuildTime="2012-01-23T16:15:05Z" webUrl="http://192.168.1.55:8080/jenkins/job/latest-demand/" lastBuildLabel="100" name="latest-demand" lastBuildStatus="Success"/>
<Project activity="Sleeping" lastBuildTime="2012-01-24T14:47:46Z" webUrl="http://192.168.1.55:8080/jenkins/job/latest-nightly/" lastBuildLabel="405" name="latest-nightly" lastBuildStatus="Success"/>
<Project activity="Sleeping" lastBuildTime="2012-01-24T04:01:17Z" webUrl="http://192.168.1.55:8080/jenkins/job/latest-nightly-logs/" lastBuildLabel="349" name="latest-nightly-logs" lastBuildStatus="Success"/>
</Projects>
"""


static final String XML_BASE_ALL_PENDING = "<hudson><assignedLabel></assignedLabel><mode>NORMAL</mode><nodeDescription>the master Hudson node</nodeDescription><nodeName></nodeName><numExecutors>1</numExecutors><description>&lt;h1&gt;HEAD code builds&lt;/h1&gt;</description><job><name>app-spectrum-base</name><url>http://192.168.1.114:8080/jenkins/job/app-spectrum-base/</url><color>grey</color></job><job><name>app-spectrum-code</name><url>http://192.168.1.114:8080/jenkins/job/app-spectrum-code/</url><color>grey</color></job><job><name>app-spectrum-DB2</name><url>http://192.168.1.114:8080/jenkins/job/app-spectrum-DB2/</url><color>grey_anime</color></job><job><name>app-spectrum-MSSQL</name><url>http://192.168.1.114:8080/jenkins/job/app-spectrum-MSSQL/</url><color>grey</color></job><job><name>bulk-loader</name><url>http://192.168.1.114:8080/jenkins/job/bulk-loader/</url><color>grey</color></job><job><name>data-dictionary</name><url>http://192.168.1.114:8080/jenkins/job/data-dictionary/</url><color>grey</color></job><job><name>dealer-workbench</name><url>http://192.168.1.114:8080/jenkins/job/dealer-workbench/</url><color>grey</color></job><job><name>dealer-workbench-deploy</name><url>http://192.168.1.114:8080/jenkins/job/dealer-workbench-deploy/</url><color>grey</color></job><job><name>mod-adapter-schema</name><url>http://192.168.1.114:8080/jenkins/job/mod-adapter-schema/</url><color>grey</color></job><job><name>mod-eod</name><url>http://192.168.1.114:8080/jenkins/job/mod-eod/</url><color>grey</color></job><job><name>mod-runtime-client</name><url>http://192.168.1.114:8080/jenkins/job/mod-runtime-client/</url><color>grey</color></job><job><name>mod-view-repository</name><url>http://192.168.1.114:8080/jenkins/job/mod-view-repository/</url><color>disabled</color></job><job><name>mod-web</name><url>http://192.168.1.114:8080/jenkins/job/mod-web/</url><color>grey</color></job><job><name>mod-web-base</name><url>http://192.168.1.114:8080/jenkins/job/mod-web-base/</url><color>grey</color></job><job><name>mod-web-help</name><url>http://192.168.1.114:8080/jenkins/job/mod-web-help/</url><color>grey</color></job><job><name>mode-web-views</name><url>http://192.168.1.114:8080/jenkins/job/mode-web-views/</url><color>grey</color></job><job><name>spectrum-domain-tree</name><url>http://192.168.1.114:8080/jenkins/job/spectrum-domain-tree/</url><color>grey</color></job><job><name>spectrum-fitnesse</name><url>http://192.168.1.114:8080/jenkins/job/spectrum-fitnesse/</url><color>grey</color></job><overallLoad></overallLoad><primaryView><name>All</name><url>http://192.168.1.114:8080/jenkins/</url></primaryView><slaveAgentPort>0</slaveAgentPort><useCrumbs>false</useCrumbs><useSecurity>true</useSecurity><view><name>All</name><url>http://192.168.1.114:8080/jenkins/</url></view><view><name>WallMonitor</name><url>http://192.168.1.114:8080/jenkins/view/WallMonitor/</url></view></hudson>"

static final String XML_BUILD_TIMES_ALL_PENDING =
"""
<Projects>
</Projects>
"""

static final String BASE_FIRST_JOB = """
<allView>
<job>
<name>archivalTest</name>
<url>
http://192.168.1.107:9090/jenkins/job/archivalTest/
</url>
<color>blue</color>
</job>
<job>
<name>test-job</name>
<url>http://192.168.1.107:9090/jenkins/job/test-job/</url>
<color>grey_anime</color>
</job>
<name>All</name>
<url>http://192.168.1.107:9090/jenkins/</url>
</allView>
"""

static final String BUILD_TIMES_FIRST_JOB = """
<Projects>
<Project activity="Sleeping" lastBuildTime="2013-08-17T19:40:30Z" webUrl="http://192.168.1.107:9090/jenkins/job/archivalTest/" lastBuildLabel="9" name="archivalTest" lastBuildStatus="Success"/>
</Projects>
"""

 
}
jenkins-global-monitor
======================

Shows a color-coded status of a list of Jenkins servers.  Intended for a wall monitor.    
It is a war file intended to be dropped into a any web container.  Configuration of
Jenkins servers to monitor is a groovy source file compiled on startup.   Currently,
the web page refresh rate is configurable.

RED: The server has a failed job
GREEN: No failed jobs
YELLOW: A job has been aborted or not yet run
GREY: The server cannot be reached.

To build war:
gradle

To deploy:
Copy jenkins-global-monitor.war into Tomcat/webapps folder

After deployment of the war, modify DynamicConfiguration.groovy with your Jenkins
servers.   An example file is provided.

To access the monitoring page: http://[host:port]/jenkins-global-monitor/HudsonView.groovy

// Default Page
Example: http://192.168.1.107:9090/jenkins-global-monitor
Example: http://192.168.1.107:9090/jenkins-global-monitor/HudsonView.groovy

// Show only the Servers without polling each one (useful if many are slow to respond)
Example: http://192.168.1.107:9090/jenkins-global-monitor/HudsonView.groovy?jenkins_disabled=true

 

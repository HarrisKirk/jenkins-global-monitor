jenkins-global-monitor
======================

![Alt text](/doc/screenshots/Image1.PNG  "Wall Monitor Page")

Shows a color-coded status of a list of Jenkins servers.  Intended for a wall monitor.    
It is a war file intended to be dropped into a web container.  

Configuration of Jenkins servers to monitor is a groovy source file compiled on startup and consists of:
- List of Jenkins servers
- Optional list of jobs to be listed from left-to-right in groups.   

Currently,
the web page refresh rate is configurable.

RED: The server has a failed job
GREEN: No failed jobs
YELLOW: A job has been aborted or not yet run
GREY: The server cannot be reached.
BLUE: Build in progress

---------------------------------
To build war:
---------------------------------
gradle clean build

---------------------------------
To deploy:
---------------------------------
gradle -Dtomcat.webapps=C:/MyTomcat/webapps  deploy

---------------------------------
To configure the list of Jenkins Servers:
---------------------------------
After deployment of the war, modify DynamicConfiguration.groovy with your Jenkins
servers.   An example file is provided.

---------------------------------
To Use:
---------------------------------
// Default Page
Example: http://192.168.1.107:9090/jenkins-global-monitor
Example: http://192.168.1.107:9090/jenkins-global-monitor/HudsonView.groovy

// Show only the Servers without polling each one (useful if many are slow to respond)
Example: http://192.168.1.107:9090/jenkins-global-monitor/HudsonView.groovy?jenkins_disabled=true

 

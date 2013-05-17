jenkins-global-monitor
======================

Shows a color-coded status of a list of Jenkins servers.  Intended for a wall monitor.    
It is a war file intended to be dropped into a any web container.  Configuration of
Jenkins servers to monitor is a groovy source file compiled on startup.   Currently,
the web page refreshes every 2 mins.

RED: The server has a failed job
GREEN: No failed jobs
YELLOW: A job has been aborted or not yet run
GREY: The server cannot be reached.

To build war:
gradle build

To deploy:
Copy war into Tomcat/webapps folder

After deployment of the war, modify DynamicConfiguration.groovy with your Jenkins
servers.   An example file is provided.

 

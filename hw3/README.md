#KTH V17P01 Programming Web Services
##Homework 3 (due 15.02.17 23:59) 
##Fannar Magnusson (fannar@kth.se) & Thorsteinn Thorri Sigurdsson (ttsi@kth.se)


###How to run

We need to have the Glassfish web server up and running. Go to the \texttt{bin} folder of the Glassfish installation directory (if it is not on PATH) and run the following command:

./asadmin start-domain domain1

Build the project into a WAR. From the root of the project, run:
 
gradle build

The WAR file can be found under build/libs.

To deploy the application do:

./asadmin deploy --contextroot="/" WAR_FILE.war

The application is then accessible from http://localhost:8080/.

The project is also delivered as an IntelliJ project, with the appropriate run configurations to run the project.
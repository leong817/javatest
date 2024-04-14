# Test for Java Developer

========

As a software engineer, I would like to implement a backend API to register a user, it can also
edit/read/(soft) delete single or multiple user(s)

> Key requirements
1. The application should be written in spring-boot and in one single project (you can have
   multiple modules within one project though)

2. The application will be packaged via docker and can be built, packaged and run via
   docker in one single shell script. Please provide details of how to run your application in
   a readme file

3. Upon successful registration, send out a welcome email (you could use fake smtp
   server).

4. Proper validation, error handling and testing.


Requirements
------------

You need Java JVM 1.8 or newer installed on your machine.

You need to enroll a fake smtp account in the application. This application uses 
ethereal.email.

1. Create an ethereal account from ethereal.email.
2. Capture the username and password
3. Update the application.properties with the username and password from ethereal.
4. Do not close the browser.


Particularly, the entries in application.properties are the fllowing


spring.mail.username=xxxxxxxx
spring.mail.password=xxxxxxxx

Usage
-----

The userRecord-1.0.jar is auto-executable.

If your desktop environment supports it, you can directly double-click
on the .jar file.

Otherwise, run the following command:

    java -jar userRecord-1.0.jar

Dockerization
------------

To package and run the application using Docker, you'll need to create a Dockerfile in the project root directory:

    `FROM openjdk:17-jdk-slim`
    `COPY target/*.jar app.jar`
    `ENTRYPOINT ["java","-jar","/app.jar"]`

If you are in Unix or Linux environment, create a shell script run.sh to build, package, and run the application:

    `#!/bin/bash`
    
    `mvn clean install package`
    
    `docker build -t userrecord .`
    
    `docker run -p 8080:8080 userrecord`

This assumes that mvn, docker and java are in the PATH variable where they can be easily located.

If you are in Windows environment, create a batch file run.bat to build, package, and run the application:

    `mvn clean install package`
    
    `docker build -t userapi .`
    
    `docker run -p 8080:8080 userapi`


Building it
-----------

You need to download and setup Maven.

Once installed, go to project directory and run the following command:

    mvn clean install package

This command will create an executable jar on the target folder.

This will also trigger to run junit tests. We recommend you not to skip unit tests.


Running integration tests
-------------------------

To run unit tests, you will need maven to execute this.

    mvn test

You may also then run the following command:

    mvn integration-test


Usage on Docker
---------------

* Run distributed version: [requires Dockerfile]

      `docker build -t userapi .`

      `docker run -p 8080:8080 userapi`



Contact me
----------

Use my email - wilsonmarasigan@aol.com.
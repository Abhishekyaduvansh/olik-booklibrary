### Library

This service provides APIs to manage library.

It is a Spring Boot application that provides RESTful endpoints to manage a book library.

To run this application a system must have 
1. Java 17+
2. Maven 3+
3. MySql server running on host 3306
4. All mySql details are given in application.properties

To build project, go to book-library and run following

    mvn clean install

To run the project 

    mvn spring-boot:run

This application runs on port 8081 and to test APIs open browser and go to following URL

    http://localhost:8081/swagger-ui.html


Added integration-test to test scenario like create rental records. update rental records
To run integration test, there must be docker installed on the machine.

To write integration-tests, TestContainers has been used. More about test container here - www.testcontainers.org

# Movie service code challenge 
This is a sample code for Backbase company.

## Getting Started
### Built With
* [Spring Boot](https://spring.io/projects/spring-boot) - The Spring boot framework i am using to develop my services.
* [JUnit 5](https://junit.org/junit5/) - The testing framework on JVM.
* [Maven](https://maven.apache.org) - As our build tool.
* [JPA](https://maven.apache.org) - As our ORM for working with H2 Database.
* [Swagger]()  As auto-generating documentation from an API definition by Swagger
* [Microservice Architecture]()  As the architecture used in the implementation of this solution
* [PostgreSQL]()  As database 
* [Java 11]()  As the Java version used 
* [Multilanguage]()  multilingualism for error messages

The microservice architecture is used to serve requests. There are three main parts the Challenge app,
the Challenge-eureka app, and the Challenge-zuul app. Challenge app is a microservice. 
This application provides some REST-APIs for working with movies such as voting to a movie, 
get top-rated movies and checking a movie has a specific reward. The challenge application 
Challenge-eureka app uses eureka for registering microservices, and Challenge-zuul app is a gateway for
routing requests to appropriate microservices. Both Challenge and Challenge-zuul applications register
themselves as clients in eureka application. The picture below shows the architecture.


<img src="https://exampledriven.files.wordpress.com/2016/07/zuul-api-gateway.jpg" width="400"/>
 
[Home](./README.md)



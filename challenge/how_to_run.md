## How To Run
Since the application in prod mode works with PostgreSQL, so it is needed to run PostgreSQL database. 
Please change the username and password and the name of the database in the related properties file, 
if it is needed based on your database configuration. Then it's time to run the applications.
Firstly, the Challenge-eureka application and then, Challenge and Challeng-zuul should be run.
**Also, at the root of each application, a docker file has been provided, and you can containerize the application through docker.

### Using the APIs
Requests can be served by Challeng-zuul and this application finds the appropriate microservice( registered on eureka)
and send the request to it.
For example a typical request to Challenge-zuul for using an API which exists in Challenge application can be a follows:
(http://localhost:8181/challenge/api/v1/movies/top-movies/10) 
As it shows, localhost:8181 is zuul address, challenge  Represents the macro service to which the request should be sent
,and the rest of the URL is the API path.


At the same time APIs can be reached even independently through the below URL.
(http://localhost:8585/swagger-ui.html#/) 



To access the home page, click on the URL in your browser:




[Home](./README.md)
## Scaling

It is needed several instances of Challenge application.
Then, each request received by Zuul is forwarded to a different instance in a round robin fashion.
If we start another instance and register it in Eureka, Zuul will register it automatically 
and start forwarding requests to it.


[Home](./README.md)
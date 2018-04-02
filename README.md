# sample-app

## user-service

In the user-service folder you will find a spring boot application implementing a sample microservice for user objects.

It uses the most recent version of spring boot, version 2.0.0

### build and test

To build the application and run the unit & integration tests, execute:
```
./mvnw clean install
```
After importing the project in your IDE, you can run the integration tests by opening the ``src/integrationtest/java/com/fywss/spring/userservice/domain/user/UserControllerIntegrationTest.java`` file.

### run

To start the application, execute:

```
./mvnw spring-boot:run
```

or

```
java -jar target/user-service-0.0.1-SNAPSHOT.jar
```

### try it out

Point your browser at ``http://localhost:8081/`` to interact with the application.

Make sure to visit the spring boot actuator links at ``http://localhost:8081/actuatorlinks`` and the swagger API testing harness at ``http://localhost:8081/swagger-ui.html``

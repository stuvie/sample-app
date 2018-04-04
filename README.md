# sample-app

## react-user

In the ``react-user`` folder you will find a React.js application implementing a simple front-end.

This project was initialized by cloning ``https://github.com/notrab/create-react-app-redux.git``

## user-service

In the ``user-service`` folder you will find a spring boot application implementing a sample microservice for user objects.

It uses the most recent version of spring boot, version 2.0.0

This project was initialized by running ``./bin/springboot user-service``

### build and test

To build the application and run the unit & integration tests, execute:
```
./mvnw clean install
```
After importing the project in your IDE, you can run the tests under ``src/test/java/com/fywss/spring/userservice/domain/user``:

* unit tests are in ``UserValidationTest.java``
* REST controller integration tests are in ``RestControllerIT.java``

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

If you use Postman, another way to test the APIs is to import the ``user-service.postman_collection.json`` file and run the test requests.


### automated build

Import the Jenkinsfile into a jenkins instance for an automated build pipeline.

### todo

configure Jenkinsfile to deploy to Pivotal Cloud Foundry (PCF)



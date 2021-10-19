# Weather Restful API

### Steps to run application.

1. Checkout project from github or extract zip file
2. Run gradlew clean bootrun
3. The application starts up.

For api documentation refer Swagger UI: http://localhost:8080/swagger-ui.html

---

### Build and Test 

**execute:** gradlew clean build 

This will build application and executes test cases.
Test case reports are available at build/reports/tests/test/index.html

All bdd scenarios are listed in this file -> resources/features/weatherinfo.feature

---

### Technical stack

#### Main Application

- Spring boot
- Spring data jpa 
- Spring web

#### DB
- H2

##### Testing
- BDD-Cuccumber/Gherkin 
- Junit

#### Api Documentation
- Swagger

#### Api key handling
- Bucket4j

#### Build tool
- Gradle

---
This Spring boot application that has 3 layers 

1. Controller
2. Service
3. Repository

Also, there is a request interceptor,RateLimitInterceptor, that looks into the request for the presence of an api key.
Request interceptor check for the presence of api key and, 

1. If the key is not present the request will be rejected with appropriate message.
2. If the key is invalid the request will be rejected with appropriate message.
3. If the key limit has exceeded the request will be rejected with appropriate message.
4. If none of the above condition are met, the request will be forwarded to the controller.

#### WeatherInfoController
This controller receives the request and forwards to WeatherInfoOrchestrationService.

#### WeatherInfoOrchestrationService
This service calls openweathermap service, save/update the information from openweathermap service to database and queries the db for the result.

#### WeatherInfoServiceImpl
This service talks to openweathermap service and retrieves weather data.

#### WeatherInfoRepository
This is Spring data repository that performs database operations.

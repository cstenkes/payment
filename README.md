# Project Payment
Instant Payment System based on specification of Kibit home assignment defined by swiss client

# Prerequisite
- Docker desktop installed with Kubernetes 
- Maven installed
- Java 21 installed (any distribution)

## Run project
> mvn spring-boot:run

or

> target> java -jar payment-0.0.1-SNAPSHOT.jar


## Run test
> mvn clean test

## Run project and test (unit and integration tests)
mvn clean test spring-boot:run

# Logging 
Default info level.

## console output
## logfile:
> /targer/logs/payment.log


## swagger ui - live
http://localhost:8080/swagger-ui/index.html

## swagger generated api doc - downloadable yaml file
http://localhost:8080/v3/api-docs.yaml

## info of the application
http://localhost:8080/actuator/info

## health check the application
http://localhost:8080/actuator/health

## health check the application
http://localhost:8080/actuator/configprops

## base path of the application
http://localhost:8080/payment/api/v1/

# Dockerized run:
- mvn clean install
- commenting back the app part in the docker-compose.yml
- docker-compose up -d

# Scaling using kubernetes
> kubectl apply -f k8s.yml

todo: need to test this kuberenetes script, and impove it.
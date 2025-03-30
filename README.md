# Project Payment
Instant Payment System based on specification of Kibit home assignment defined by swiss client

## Run project
mvn spring-boot:run

or

target> java -jar payment-0.0.1-SNAPSHOT.jar


## Run test
mvn clean test

## Run project and test (unit and integration tests)
mvn clean test spring-boot:run

## api doc - swagger ui
http://localhost:8080/swagger-ui/index.html

## api doc - downloadable yaml file
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

# scaling using kubernetes
kubectl apply -f k8s.yml

todo: need to test
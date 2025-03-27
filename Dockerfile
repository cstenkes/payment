FROM eclipse-temurin:21-alpine

RUN mkdir /opt/app

COPY ./target/payment-*.jar /opt/app/payment.jar

#EXPOSE 8080
CMD ["java", "-jar", "-Duser.timezone=\"Europe/Budapest\"","/opt/app/payment.jar"]
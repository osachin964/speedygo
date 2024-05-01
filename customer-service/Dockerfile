FROM openjdk:11
ADD ./target/customer-service-0.0.1-SNAPSHOT.jar /usr/src/customer-service-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "customer-service-0.0.1-SNAPSHOT.jar"]
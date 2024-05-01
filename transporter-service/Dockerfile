FROM openjdk:11
ADD ./target/transporter-service-0.0.1-SNAPSHOT.jar /usr/src/transporter-service-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "transporter-service-0.0.1-SNAPSHOT.jar"]
FROM openjdk:8-jdk-alpine
LABEL "com.example.ski-resort"="Baranukov Incorporated"
COPY target/ski_resort_baranukov-0.0.1-SNAPSHOT.jar resort_baranukov
ENTRYPOINT ["java","-jar", "resort_baranukov"]
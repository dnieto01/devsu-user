FROM openjdk:17-ea-jdk-slim
VOLUME /tmp
COPY target/devsu-user-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
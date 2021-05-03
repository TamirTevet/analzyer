FROM maven:3.6.0-jdk-11-slim
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package

EXPOSE 8080
WORKDIR /tmp/target
ENTRYPOINT ["java", "-jar", "analyzer-0.0.1-SNAPSHOT.jar"]


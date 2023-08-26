FROM maven:3.9.2 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install

FROM openjdk:20
EXPOSE 8080
COPY --from=build /app/target/spring-boot-docker.jar /app/target/spring-boot-docker.jar
ENTRYPOINT ["java", "-jar", "/app/target/spring-boot-docker.jar", "--spring.config.location=classpath:application-prod.properties"]
LABEL authors="gonzalo"
FROM openjdk:20
EXPOSE 8080
ADD target/spring-boot-docker.jar spring-boot-docker.jar
ENTRYPOINT ["java", "-jar", "spring-boot-docker.jar", "--spring.config.location=classpath:application-prod.properties"]
LABEL authors="gonzalo"
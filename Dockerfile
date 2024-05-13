FROM openjdk:17-jdk

ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Duser.timezone=GMT+9", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=dev", "-jar", "/app.jar"]
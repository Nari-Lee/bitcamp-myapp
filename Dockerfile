FROM openjdk:21-jdk

ARG JAR_FILE=app/build/libs/myapp-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} myapp.jar

ENTRYPOINT [ "java", "-Dspring.profiles.active=prod", "-jar", "myapp.jar" ]

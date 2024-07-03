FROM openjdk:21
EXPOSE 8080
COPY build/libs/application.jar ./
ENTRYPOINT ["java", "-jar", "application.jar"]

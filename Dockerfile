FROM openjdk:21
EXPOSE 8080
COPY build/libs/application/lib ./
COPY build/libs/application/application.jar ./
ENTRYPOINT ["java", "-jar", "application.jar"]

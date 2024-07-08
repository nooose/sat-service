FROM openjdk:21
EXPOSE 8080
COPY application/lib ./lib
COPY application/application.jar ./
ENTRYPOINT ["java", "-jar", "application.jar"]

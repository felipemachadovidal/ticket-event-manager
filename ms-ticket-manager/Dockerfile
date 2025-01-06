
FROM openjdk:17-jdk-slim


WORKDIR /app


COPY target/ms-ticket-manager-*.jar app.jar


EXPOSE 8081


ENTRYPOINT ["java", "-jar", "app.jar"]

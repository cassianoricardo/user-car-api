FROM openjdk

WORKDIR /app

COPY target/user-car-api-0.0.1.jar /app/user-car-api.jar

ENTRYPOINT ["java", "-jar", "user-car-api.jar"]
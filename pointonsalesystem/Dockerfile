FROM openjdk:20-ea-4-jdk

WORKDIR /app

COPY target/pointonsale-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
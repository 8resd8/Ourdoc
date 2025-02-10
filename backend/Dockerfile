FROM bellsoft/liberica-openjdk-alpine:17 AS build
ARG JAR_FILE_PATH=build/libs/ourdoc-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE_PATH} ourdoc.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ourdoc.jar"]

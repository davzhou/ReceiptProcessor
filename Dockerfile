#Build stage

FROM gradle:8.8-jdk22 AS builder
WORKDIR /app
COPY . . 
RUN gradle build

# Package stage

FROM openjdk:22-jdk
ARG JAR_FILE=ReceiptProcessor-1.0-SNAPSHOT.jar
ENV APP_HOME=/app
WORKDIR $APP_HOME
COPY --from=builder $APP_HOME .
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
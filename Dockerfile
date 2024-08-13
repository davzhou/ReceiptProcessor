#Build stage

FROM gradle:8.8-jdk22 AS builder
WORKDIR /opt/app
COPY . . 
RUN gradle build

# Package stage

FROM openjdk:22-jdk
WORKDIR /opt/app
COPY --from=builder /opt/app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/opt/app/app.jar"]
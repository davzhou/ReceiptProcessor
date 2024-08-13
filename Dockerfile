#Build stage

FROM gradle:8.8-jdk22 AS BUILD
WORKDIR /usr/app/
COPY . . 
RUN gradle build

# Package stage

FROM openjdk:22-jdk
ARG JAR_FILE=target/*.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=BUILD $APP_HOME .
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
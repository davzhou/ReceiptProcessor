## Receipt Processor Instructions

I used Spring Boot 3 with Java 22 to build this application.

To run the app on your machine using docker, use the following commands in your terminal:

**Build the docker image**

`docker build --build-arg JAR_FILE=build/libs/\*.jar -t fetch/receipt-processor .`

**Start the container**

`docker run -p 8080:8080 fetch/receipt-processor`

**Endpoints**

`POST localhost:8080/receipts/process`

`GET localhost:8080/receipts/{id}/points`

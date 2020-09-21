# BasicEncrypt

How to start the BasicEncrypt application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/encryption-1.0.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

How to Test
---
The endpoints exposed are 
pushAndRecalculate - POST  http://localhost:8080/AverageDeviation/pushAndRecalculate

pushRecalculateAndEncrypt - POST http://localhost:8080/AverageDeviation/pushRecalculateEncrypt

decrypt - GET http://localhost:8080/AverageDeviation/decrypt

A postman script is attached to test the flow
Encryption.postman_collection.json

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`


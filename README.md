# SoftwareAcademy-P9-Assess
Micro-service ASSESS to manage patient analyse on MEDISCREEN Application. 

This microservice use SPRINT BOOT, Feign Client. 

## Getting Started
EndPoint for global application  : 
* http://localhost:4200

 
## Prerequis
For ASSESS microservice
* Java 1.8 or later
* Spring Boot 2.2.6
* Docker 2.5.0.0 or later (optional)

For Global application
* Java 1.8 or later
* MySQL
* MongoDB
* Spring Boot 2.2.6
* Docker 2.5.0.0 or later (optional)
* Angular
* Zipkin
* Eureka
* Config server

## Installation
Check PatientV2 Readme.md for global installation 

###Docker image construction in project directory : 
docker build --build-arg JAR_FILE=target/*.jar -t p9-assess .

### Docker execution if docker-compose is not use
docker run -p 8086:8086 --name assess p9-assess

## URI
### Get Assess By Patient Id
* directly : GET http://localhost:8086/assess/id?id=id_de_patient
* With zuul : GET http://zuul:9004/microservice-assess/assess/id?id=id_de_patient

### Get Assess By Family Name
* directly : GET http://localhost:8086/assess/familyName?familyName=nom_de_famille
* With zuul : GET http://zuul:9004/microservice-assess/assess/familyName?familyName=nom_de_famille

## Divers
Global architecture : 
![alt text](Architecture.png)

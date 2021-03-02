# SoftwareAcademy-P9-Assess
Micro-service assess to manage patient analyse

## Installation
microservices User, patient and notes must be up

###Docker image construction in project directory : ==> TODO

docker build --build-arg JAR_FILE=target/*.jar -t p9-assess .

### Docker execution :==> TODO

docker run -p 8086:8086 --name assess p9-assess

#### Docker execution for all project
* On user project directory : 

execute command line to start all components: docker-compose up -d

### divers

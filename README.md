# Microservice environment search case study

Case study of the search problem in the microservice environment when data is stored in two different microservices.

### Modules
- gateway service - middleware app when search is performed - ports 8080-8089
- stock service - ports 8090-8099
- CMS service - ports 8100-8109

The main goal of this project is to find the best solution for the search functionality in the distributed system when
data to be searched or sorted is located in different services.

All the services should be run in the docker environment with K8S.

## Requirements
### Approach #1 - selective replication - DONE
Data from the CMS service is replicated to the stock service using Kafka. The stock service is responsible for the search and sorting.
<br />
Scenario:
- Products are created in the stock-service
- Translations of products are created in the CMS service
- When translation is created, the event is sent to the Kafka topic
- The stock service is listening to the topic and when the event is received, the product is updated with the translation data


## Build
### Build project
```shell
mvn clean compile package -Dmaven.test.skip=true
```

### Build with tests
```shell
mvn clean compile package
```

### Build docker images
```shell
clean compile package spring-boot:build-image -Dmaven.test.skip=true -Pbuild-docker-images
```

## Deployment

### Docker compose
Before building the app you need to build the docker images: [Build docker images](#build-docker-images).
<br />Then go to [docker](docker) directory and run scripts.
<br />Do not run all the scripts on your own - use the following scripts to run all the containers.
Windows:
```windows
./start.bat
```

Linux:
```shell
./start.sh
```

### Kubernetes





# Microservice environment search case study

Case study of the search problem in the microservice environment when data is stored in two different microservices.

### Modules

- ms-api-gateway - api gateway - ports 8080-8082
- ms-stock-service - ports 8091-8093
- ms-cms-service - ports 8100-8102

The main goal of this project is to find the best solution for the search functionality in the distributed system when
data to be searched or sorted is located in different services.

All the services should be run in the docker environment with K8S.

## Scenarios

### Approach #1 - selective replication - DONE

Data from the CMS service is replicated to the stock service using Kafka. The stock service is responsible for the
search and sorting.
<br />
Scenario:

- Products are created in the stock-service
- Translations of products are created in the CMS service
- When translation is created, the event is sent to the Kafka topic
- The stock service is listening to the topic and when the event is received, the product is updated with the
  translation data

### Approach #2 - synchronous call to all services and joining results with caching of the query in Redis - TODO

### Approach #3 - search between views in the database - approach simulating usage of different schemas in each microservice. - TODO

## Build

### Build project

```shell
mvn clean compile package -Dmaven.test.skip=true
```

### Build with tests

```shell
mvn clean compile package
```

### Build docker images - docker images are built automatically when the project is built by spring-boot-maven-plugin

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





# Microservice environment search case study

Case study of the search problem in the microservice environment when data is stored in two different microservices.

## Modules

### ms-api-gateway
API gateway - port 8080

#### Technologies and libraries used
- Spring cloud gateway - API gateway
- Resilience4j - circuit breaker
- Wiremock - API mocking for tests

### ms-admin
ADMIN App - port 8081

#### Technologies and libraries used
- Spring boot admin - monitoring of the services

### ms-stock-service 
Stock service connected to PostgreSQL database - stock. <br />
Works on port range: 8091-8093

### ms-cms-service 
Stock service connected to PostgreSQL database - cms. <br />
Works on port range: 8100-8102

The main goal of this project is to find the best solution for the search functionality in the distributed system when
data to be searched or sorted is located in different services.

All the services should be run in the docker environment with K8S.
<br />
All the services should have configuration in YML format.

## Scenarios

### Approach #1 - DONE
selective replication

Data from the CMS service is replicated to the stock service using Kafka. The stock service is responsible for the
search and sorting.
<br />
Scenario:

- Products are created in the stock-service
- Translations of products are created in the CMS service
- When translation is created, the event is sent to the Kafka topic
- The stock service is listening to the topic and when the event is received, the product is updated with the
  translation data

### Approach #2
synchronous call to all services and joining results with caching of the query in Redis - TODO

### Approach #3
search between views in the database - approach simulating usage of different schemas in each microservice. - TODO

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
Docker images are built automatically when the project is built by spring-boot-maven-plugin

```shell
clean compile package spring-boot:build-image -Dmaven.test.skip=true -Pbuild-docker-images
```

## Deployment

### Local deployment
Run all the 3rd party services using Docker Compose in directory: `docker`. 
```docker
docker-compose -f db.yml -p microservice-search up -d
docker-compose -f mongodb.yml -p microservice-search up -d
docker-compose -f kafka.yml -p microservice-search up -d
docker-compose -f redis.yml -p microservice-search up -d
```
All custom services should be run with `local` profile.
```mvn
mvn spring-boot:run -Dspring-boot.run.profiles=local
mvn spring-boot:run -Dspring-boot.run.profiles=local
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

### Docker compose

Before building the app you need to build the docker images: [Build docker images](#build-docker-images).
<br />Then go to [docker](docker) directory and run scripts.
<br />Do not run all the scripts on your own - use the following scripts to run all the containers.
<br />All modules should be run with `docker` profile.
<br />Windows:

```windows
./start.bat
```

Linux:

```shell
./start.sh
```

### Kubernetes
K8S scripts shold be runned in the following order from the project root directory:
- [k8s](k8s) - directory with global configuration - special role and privileges
```docker
kubectl apply -f k8s
```
- 





# Microservice environment search case study

Case study of the search problem in the microservice environment when data is stored in two different microservices.
</br>
The main goal of this project is to find the best solution for the search functionality in the distributed system when
data to be searched or sorted is located in different services.

All the services should be run in the docker environment with K8S.
<br />
All the services should have configuration in YML format.

## Steps to be done on the infrastructure level

- [x] Create Spring Boot microservice project with Spring Cloud Gateway
- [x] Dockerize projects
- [x] Run projects locally
- [x] Run projects in Docker Compose
- [x] Run all projects in Kubernetes
- [x] Enable scaling of the services with load balancing over API Gateway
- [ ] Run all the 3rd party services in K8S
- [ ] Add distributed tracing with Sleuth and Zipkin
- [ ] Run Spring Boot Admin in local environment
- [ ] Enable K8S ingress to make app available over the internet
- [ ] Deploy all the services to the cloud
- [ ] Implement all the approach related functionalities and components in K8S/Docker/Local environment

## Modules

### ms-api-gateway

API gateway - port 8080

#### Technologies and libraries used

- Spring cloud gateway - API gateway
- Resilience4j - circuit breaker
- Wiremock - API mocking for tests
- Spring Security - OIDC authentication scheme

### ms-admin

ADMIN App - port 8081

#### Technologies and libraries used

- Spring boot admin - monitoring of the services

### ms-stock-service

Stock service connected to PostgreSQL database - `stock`. <br />
Works on ports: 8091-8093

### ms-cms-service

Stock service connected to PostgreSQL database - `cms`. <br />
Works on ports: 8100-8102

### ms-keycloak

Authentication server - port 8082. OIDC authentication scheme is used to authenticate users.
Run in Docker Compose.

## Scenarios

### Approach #1 - Selective replication - DONE

Data from the CMS service is replicated to the stock service using Kafka. The stock service is responsible for the
search and sorting.
<br />
Scenario:

- Products are created in the stock-service
- Translations of products are created in the CMS service
- When translation is created, the event is sent to the Kafka topic
- The stock service is listening to the topic and when the event is received, the product is updated with the
  translation data

### Approach #2 - Synchronous calls between services - TODO

synchronous call to all services and joining results with caching of the query in Redis - TODO

### Approach #3 - Composite service layer - TODO

External search service where all the data is replicated to the Elasticsearch instance - TODO

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
docker-compose -f keycloak.yml -p microservice-search up -d
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

K8S scripts should be run in the following order from the project root directory:
</br> Run all scripts from the root directory:

```docker
.\k8s\start.bat
```

Or run each scripts separately:

- [k8s](k8s) - directory with global configuration:
    - special role and privileges
    - ingress
      All the custom modules shall be run with the `k8s` Spring profile.

```docker
kubectl apply -f k8s
```

- [ms-admin](ms-admin) - directory with configuration for the admin module [ms-admin](ms-admin/k8s):

```docker
kubectl apply -f ms-admin/k8s
```

- [ms-api-gateway](ms-api-gateway) - directory with configuration for the admin
  module [ms-api-gateway](ms-api-gateway/k8s):

```docker
kubectl apply -f ms-api-gateway/k8s
```

- [ms-cms-service](ms-cms-service) - directory with configuration for the cms
  module [ms-cms-service](ms-cms-service/k8s):

```docker
kubectl apply -f ms-cms-service/k8s
```

- [ms-stock-service](ms-stock-service) - directory with configuration for the stock
  module [ms-stock-service](ms-stock-service/k8s):

```docker
kubectl apply -f ms-stock-service/k8s
```

## Setup

### Users

Users are stored in Keycloak. <br />
Predefined users: </br>
Keycloak admin users:

- admin/admin

</br>Admin role users:

- admin_user_1/admin_user_1 role `admin`
- admin_user_2/admin_user_2 role `admin`

</br>Normal user:

- user_1/user_1 - role `client`
- user_2/user_2 - role `client`





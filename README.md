# Microservice environment search case study

Case study of the search problem in the microservice environment when data is stored in two different microservices.

### Modules

- gateway service - middleware app when search is performed
- stock service 
- CMS service

The main goal of this project is to find the best solution for the search functionality in the distributed system when
data to be searched or sorted is located in different services.

All the services should be run in the docker environment with K8S.


## Approach #1 - selective replication - DONE
Data from the CMS service is replicated to the stock service using Kafka. The stock service is responsible for the search and sorting.
<br />
Scenario:
- Products are created in the stock-service
- Translations of products are created in the CMS service
- When translation is created, the event is sent to the Kafka topic
- The stock service is listening to the topic and when the event is received, the product is updated with the translation data



## Deployment

### Docker compose





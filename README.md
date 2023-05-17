# Microservice environment search case study

Case study of the search problem in the microservice environment when data is stored in two different microservices.

Applications:

- gateway service - middleware app when search is performed
- stock service 
- CMS service

The main goal of this project is to find the best solution for the search functionality in the distributed system when
data to be searched or sorted is located in different services.


## Approach #1 - selective replication
Data from the CMS service is replicated to the stock service. The stock service is responsible for the search and sorting.

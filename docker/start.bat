@echo off
:: docker compose up all sh files in the docker directory
set COMPOSE_PROJECT_NAME=microservice-search
docker-compose -f db.yml -p %COMPOSE_PROJECT_NAME% up -d
docker-compose -f mongodb.yml -p %COMPOSE_PROJECT_NAME% up -d
docker-compose -f kafka.yml -p %COMPOSE_PROJECT_NAME% up -d
docker-compose -f app.yml -p %COMPOSE_PROJECT_NAME% up -d
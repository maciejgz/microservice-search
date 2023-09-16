@echo off
:: docker compose up all sh files in the docker directory
set COMPOSE_PROJECT_NAME=microservice-search
docker-compose -f db.yml -p %COMPOSE_PROJECT_NAME% up -d
docker-compose -f mongodb.yml -p %COMPOSE_PROJECT_NAME% up -d
docker-compose -f kafka.yml -p %COMPOSE_PROJECT_NAME% up -d
docker-compose -f redis.yml -p %COMPOSE_PROJECT_NAME% up -d

set /a num_replicas=3
set /a delay_seconds=5

for /l %%i in (1,1,%num_replicas%) do (
  docker-compose -f app.yml -p %COMPOSE_PROJECT_NAME% up -d --scale ms-cms-service=%%i
  docker-compose -f app.yml -p %COMPOSE_PROJECT_NAME% up -d --scale ms-stock-service=%%i
  docker-compose -f app.yml -p %COMPOSE_PROJECT_NAME% up -d --scale ms-api-gateway=1
  timeout /t %delay_seconds%
)


@echo off
:: docker compose up all sh files in the docker directory
call start_3rd_party_components.bat

set /a num_replicas=3
set /a delay_seconds=5

for /l %%i in (1,1,%num_replicas%) do (
  docker-compose -f app.yml -p %COMPOSE_PROJECT_NAME% up -d --scale ms-cms-service=%%i
  docker-compose -f app.yml -p %COMPOSE_PROJECT_NAME% up -d --scale ms-stock-service=%%i
  docker-compose -f app.yml -p %COMPOSE_PROJECT_NAME% up -d --scale ms-api-gateway=1
  timeout /t %delay_seconds%
)


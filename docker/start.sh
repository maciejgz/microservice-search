## docker compose up all sh files in the docker directory
docker-compose -f db.yml -p microservice-search up -d
docker-compose -f mongodb.yml -p microservice-search up -d
docker-compose -f kafka.yml -p microservice-search up -d

for i in {1..3}; do
  docker-compose -f app.yml -p %COMPOSE_PROJECT_NAME% up -d --scale ms-cms-service=$i
  docker-compose -f app.yml -p %COMPOSE_PROJECT_NAME% up -d --scale ms-stock-service=$i
  docker-compose -f app.yml -p %COMPOSE_PROJECT_NAME% up -d --scale ms-api-gateway=1
  sleep 5
done


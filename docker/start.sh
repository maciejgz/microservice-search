## docker compose up all sh files in the docker directory
docker-compose -f db.yml -p microservice-search up -d
docker-compose -f mongodb.yml -p microservice-search up -d
docker-compose -f kafka.yml -p microservice-search up -d
docker-compose -f app.yml -p %COMPOSE_PROJECT_NAME% up -d


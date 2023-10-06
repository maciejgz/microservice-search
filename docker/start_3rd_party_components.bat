set COMPOSE_PROJECT_NAME=microservice-search
docker-compose -f db.yml -p %COMPOSE_PROJECT_NAME% up -d
docker-compose -f mongodb.yml -p %COMPOSE_PROJECT_NAME% up -d
docker-compose -f kafka.yml -p %COMPOSE_PROJECT_NAME% up -d
docker-compose -f redis.yml -p %COMPOSE_PROJECT_NAME% up -d
docker-compose -f keycloak.yml -p %COMPOSE_PROJECT_NAME% up -d
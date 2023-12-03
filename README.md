chmod +x gradlew
./gradlew build
DOCKER_BUILDKIT=1 docker build -t registry.gitlab.com/nelbakidze/tg-containers:tg-gateway-1.0-SNAPSHOT --no-cache .
docker push registry.gitlab.com/nelbakidze/tg-containers:tg-gateway-1.0-SNAPSHOT

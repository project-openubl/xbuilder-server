mvn fabric8:build -Dfabric8.mode=kubernetes

# Tag Docker image
docker tag xavier/xavier-integration xavier/xavier-integration:latest
docker tag xavier/xavier-integration xavier/xavier-integration:"$TRAVIS_TAG"

# Push our image to the Image Registry
docker login -u "$DOCKER_USERNAME" -p "$DOCKER_PASSWORD";
docker push xavier/xavier-integration

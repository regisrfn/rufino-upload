# rufino-upload

## - build local docker image 
mvn clean install -DskipTests=true spring-boot:build-image -Dspring-boot.build-image.imageName=regisrufino/upload-api-aws

## - build docker image with jib plugin and push
mvn clean install -DskipTests=true jib:build

## - run container
docker run --name upload-api --env-file .env -p 5000:5000 regisrufino/upload-api-aws
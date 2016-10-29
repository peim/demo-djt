Create docker image

./gradlew build buildDocker

Run application in docker container

docker run -p 8080:8080 -t spring-boot/demo-djt
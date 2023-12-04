FROM eclipse-temurin:17-jdk-alpine
LABEL authors="borislava"

ENTRYPOINT ["top", "-b"]
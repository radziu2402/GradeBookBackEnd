FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

COPY ./gradebookbackend/pom.xml .

RUN mvn dependency:go-offline

COPY ./gradebookbackend/src /app/src

RUN mvn clean package -DskipTests

FROM openjdk:17-slim
WORKDIR /app

COPY --from=build /app/target/*.jar gradebookbackend.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "gradebookbackend.jar"]

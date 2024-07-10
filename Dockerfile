FROM openjdk:17-jdk-slim as build

LABEL authors="harjeevansingh"

WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN chmod +x mvnw
RUN ./mvnw install -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /workspace/app/target/languagemodelservice-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]
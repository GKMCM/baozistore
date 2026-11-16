# Etapa 1: compila o projeto com Maven
FROM maven:3-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -B dependency:go-offline
COPY src ./src
RUN mvn -q -B -DskipTests package

# Etapa 2: executa o .jar
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/baozistore-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

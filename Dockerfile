FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
RUN groupadd -r spring && useradd -r -g spring spring
COPY --from=builder /app/target/ms-fredymoran-02-*.jar app.jar
USER spring:spring
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
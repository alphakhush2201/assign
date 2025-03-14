FROM maven:3.8.6-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar
ENV PORT=8080
EXPOSE ${PORT}
CMD ["sh", "-c", "java -Dserver.port=${PORT} -jar app.jar"]
FROM maven:3.8.6-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Make sure we use the environment variables provided by Railway
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://${PGHOST}:${PGPORT}/${PGDATABASE}
ENV SPRING_DATASOURCE_USERNAME=${PGUSER}
ENV SPRING_DATASOURCE_PASSWORD=${PGPASSWORD}

# Add debugging for database connection
ENV LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_JDBC=DEBUG
ENV LOGGING_LEVEL_COM_ZAXXER_HIKARI=DEBUG
ENV LOGGING_LEVEL_ROOT=INFO

# Increase connection timeout
ENV SPRING_DATASOURCE_HIKARI_CONNECTION_TIMEOUT=30000

EXPOSE 8080
# Add a longer startup delay and enable debug mode
CMD ["sh", "-c", "sleep 20 && java -Ddebug -jar app.jar"]
FROM maven:3.8.6-amazoncorretto-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine
WORKDIR /app
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Add a health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=15s --retries=3 \
  CMD wget -q --spider http://localhost:8080/health || exit 1

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
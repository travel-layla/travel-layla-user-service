# Travel Layla User Service

## Overview

`travel-layla-user-service` is the user management microservice for the Travel Layla system.
This project uses Spring Boot to build a REST API and includes:
- Spring Web
- Spring Security + OAuth2 Resource Server (JWT)
- Spring Data JPA
- PostgreSQL
- Flyway DB migration
- Spring Boot Actuator
- SpringDoc OpenAPI

## Key Features

- User account management API
- JWT-based authentication and authorization
- PostgreSQL database integration
- Flyway migrations for database versioning
- Actuator health checks and monitoring
- Automatic OpenAPI/Swagger API documentation

## Requirements

- Java 17
- Maven
- PostgreSQL

## Run the Application

1. Configure the database connection in `src/main/resources/application.yaml`.
2. Start the application using the Maven wrapper:

   - On Windows:
     ```powershell
     .\mvnw.cmd spring-boot:run
     ```
   - On Linux/macOS:
     ```bash
     ./mvnw spring-boot:run
     ```

3. Or build and run the JAR:

   ```bash
   ./mvnw clean package
   java -jar target/user-0.0.1-SNAPSHOT.jar
   ```

## Testing

Run unit tests with:

```bash
./mvnw test
```

## Project Structure

- `src/main/java/com/travel_layla/user/` - main source code
  - `UserApplication.java` - application entry point
  - `application/feature/` - commands, queries, handlers, and DTOs
  - `domain/` - entities and domain models
  - `infrastructure/` - configuration and supporting services
  - `presentation/` - controllers and API entry points
- `src/main/resources/application.yaml` - application configuration
- `src/test/java/...` - unit tests

## API Documentation

After startup, check OpenAPI/Swagger documentation if configured:

- `http://localhost:8080/swagger-ui.html`
- or `http://localhost:8080/swagger-ui/index.html`

## Notes

- Ensure PostgreSQL is running and the database is created before starting the application.
- Flyway will automatically apply database migrations on startup.
- For additional security or OAuth2 configuration, update `application.yaml` and security settings accordingly.

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

## Database Setup

### PostgreSQL Configuration

1. Ensure PostgreSQL is installed and running.
2. Create the database:
   ```sql
   CREATE DATABASE travel_layla_users;
   ```

3. Update the database connection in `src/main/resources/application.yaml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/travel_layla_users
       username: postgres
       password: your_password
   ```

### Flyway Database Migration

This project uses **Flyway** for database version control and migrations. Flyway automatically applies SQL migrations from `src/main/resources/db/migration/` directory on application startup.

#### How Flyway Works

- **Automatic Execution**: Flyway runs migrations automatically when the application starts.
- **Version Control**: Each migration file is named with a version prefix (e.g., `V1__`, `V2__`, etc.) to track execution order.
- **State Tracking**: Flyway maintains a `flyway_schema_history` table to track applied migrations and prevent re-execution.

#### Migration File Naming Convention

- Naming format: `V<version>__<description>.sql`
  - `<version>`: Numeric version (e.g., 1, 2, 3, 10)
  - `<description>`: Descriptive name (underscores replace spaces)
- Example: `V1__Create_users_table.sql`, `V2__Add_user_profiles.sql`

#### Migration Locations

- Migration files are stored in: `src/main/resources/db/migration/`
- Flyway is configured to scan this directory for SQL files to execute.

#### Creating New Migrations

When you need to modify the database schema:

1. Create a new SQL file in `src/main/resources/db/migration/`
2. Follow the naming convention: `V<next_version>__<description>.sql`
3. Write your SQL DDL/DML statements (e.g., CREATE TABLE, ALTER TABLE, INSERT)
4. Commit and push to the repository
5. On the next application startup, Flyway will automatically apply the migration

**Example Migration File:**
```sql
-- V2__Add_user_profiles.sql
CREATE TABLE app.user_profiles (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES app.users(id),
    bio VARCHAR(500),
    avatar_url VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id)
);
```

#### Flyway Configuration

Configuration in `application.yaml`:
```yaml
spring:
  flyway:
    enabled: true                          # Enable Flyway
    locations: classpath:db/migration      # Migration file location
    baseline-on-migrate: true              # Initialize empty database
    baseline-version: 0                    # Initial version
```

#### Viewing Migration History

After migrations are applied, you can check the migration history in the database:
```sql
-- Connect to your database
SELECT * FROM flyway_schema_history;
```

This table shows:
- `version`: Migration version number
- `description`: Migration description
- `type`: Migration type (SQL or JDBC)
- `installed_by`: User who executed the migration
- `installed_on`: Timestamp of execution
- `execution_time`: Time taken to execute (in ms)
- `success`: Whether the migration was successful (true/false)

#### Common Flyway Operations

**Check Migration Status (via Spring Boot startup logs)**
- Look for log messages like: `Flyway X.X.X by Redgate`
- Successful migrations: `Successfully validated X migrations (execution time XXms)`

**Manually Run Migrations**
- Flyway runs automatically on application startup
- To force a clean database (⚠️ use with caution):
  ```bash
  ./mvnw clean:clean
  ```

**Troubleshooting**

- **Migration not running**: Ensure files are in `src/main/resources/db/migration/`
- **Syntax errors**: Verify SQL syntax; Flyway will log detailed errors
- **Schema not found**: Check database connection and schema name in migrations
- **Duplicate version**: Ensure each migration has a unique version number

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
- `src/main/resources/` - configuration and resources
  - `application.yaml` - application configuration
  - `db/migration/` - Flyway database migration scripts
- `src/test/java/...` - unit tests

## API Documentation

After startup, check OpenAPI/Swagger documentation if configured:

- `http://localhost:8080/swagger-ui.html`
- or `http://localhost:8080/swagger-ui/index.html`

## API Endpoints

### Register Account

**Endpoint**: `POST /api/v1/accounts/register`

**Request Body**:
```json
{
  "email": "user@example.com",
  "password": "securePassword123",
  "firstName": "John",
  "lastName": "Doe"
}
```

**Response** (201 Created):
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "createdAt": "2024-05-27T10:30:00"
}
```

**Validation Rules**:
- Email: Required, must be valid email format, unique in database
- Password: Required, minimum 8 characters
- First Name: Required, 2-100 characters
- Last Name: Required, 2-100 characters

## Notes

- Ensure PostgreSQL is running and the database is created before starting the application.
- Flyway will automatically apply database migrations on startup.
- For additional security or OAuth2 configuration, update `application.yaml` and security settings accordingly.
- Passwords are securely encrypted using BCrypt before storage.

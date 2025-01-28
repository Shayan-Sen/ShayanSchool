# README

## Steps to Set Up PostgreSQL and Launch the Backend Application

### 1. Pull the Docker Image of PostgreSQL
To get started, pull the official PostgreSQL Docker image using the following command:
```bash
docker pull postgres
```

### 2. Create a Container
Run the following command to create a PostgreSQL container with:
- Password: `shayan`
- Port mapping: `5432:5432`

```bash
docker run --name postgres-container -e POSTGRES_PASSWORD=shayan -p 5432:5432 -d postgres
```

### 3. Create a Database and Enable the `uuid-ossp` Extension
Once the container is running, follow these steps to create a database named `practice` and enable the `uuid-ossp` extension:

1. Access the PostgreSQL container:
   ```bash
   docker exec -it postgres-container psql -U postgres
   ```

2. Run the following SQL commands to create the database and enable the extension:
   ```sql
   CREATE DATABASE practice;
   \c practice
   CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
   ```

### 4. Update the `application.properties` File
If you wish to use a different password, database name, or other PostgreSQL settings, ensure you update the corresponding values in the `application.properties` file of the backend application. Look for lines such as:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/practice
spring.datasource.username=postgres
spring.datasource.password=shayan
```

Modify the values as needed to reflect your changes.

### 5. Launch the Backend Application
Run the following command from the root directory of the project to start the backend application:
```bash
./mvnw clean spring-boot:run
```

This will build and launch the Spring Boot application.

### Notes
- Ensure that Docker is installed and running on your system.
- If port `5432` is already in use, map the container to a different host port (e.g., `-p 5433:5432`) and update the `application.properties` file accordingly.
- For troubleshooting or further assistance, consult the project's documentation or the PostgreSQL official docs.


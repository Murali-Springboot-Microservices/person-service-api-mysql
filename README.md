# person-service-api-mysql
Simple project that demonstrates REST APIs, connection to MySQL database and dockerize 


## Features

- **CRUD Operations** for managing `Person` entity
- **Spring Data JPA** for easy database interaction
- **MySQL** running on Docker
- **REST Endpoints** that can be tested
- **Swagger UI** that can be used to test

---

## Technologies Used

- **Java 17** (or compatible version)
- **Spring Boot 4.0.1 (Web, Data JPA)
- **MySQL 8.0** (via Docker)
- **Maven** (build tool)
- **Swagger** (for testing; optional)
- **Lombok** (avoid boiler plate code)
- **MySQL Connector j** (official JDBC driver for MySQL)

---

## Prerequisites

- **Java** (version 17+ recommended)
- **Maven** (to build and run the project)
- **Docker** (if you want to run MySQL in a container)
- **Git** (for cloning this repository)


## Overview
This demo application implements a RESTful API for managing Person records. We handle basic CRUD (Create, Read, Update, Delete) operations through endpoints exposed at http://localhost:8080/api/v1/persons. This project uses a basic Person table and uses Spring Data JPA APIs to interact with MySQL database docker image running locally.

```
Run MySQL in Docker (Optional) If you don’t have MySQL installed locally, spin up a container:
   ```bash
   docker run -d \
    --name mySQLDockerLocaldb \
    -e MYSQL_ROOT_PASSWORD=secret \
    -e MYSQL_DATABASE=mydatabase \
    -p 3306:3306 \
    mysql:8.0

spring.datasource.url=jdbc:mysql://localhost:3306/mySQLDockerLocaldb
spring.datasource.username=root
# It is recommended to use environment variables for sensitive data.
spring.datasource.password=Version8_0

# This will create the table if not exists.
spring.jpa.hibernate.ddl-auto=update

# This will show sql queries in console. Disable in production
spring.jpa.show-sql=true

# Set the root logging level
logging.level.root=INFO

# Explicitly enable Swagger UI and API docs, this will be enabled by default
springdoc.api-docs.enabled=true
springdoc.swagger-ui.enabled=true

# Optional: Customize the paths (defaults are shown)
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/v3/api-docs


## Next Steps
Dockerize.
Add child database tables and implement Liquibase.
Spring Security.
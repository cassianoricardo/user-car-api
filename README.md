# API RESTFUL para Sistema de Usuários de Carro

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![GIT](https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white)

This project is an API built using **Java, Spring boot, Flyway Migrations, H2 as the database, and Spring Security and JWT for authentication control.**

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Authentication](#authentication)
- [Database](#database)
- [Run Spring Boot Application](#run-spring-boot-application)


## Installation

1. Clone the repository:

```bash
git clone https://github.com/cassianoricardo/user-car-api
```
2. Install dependencies with Maven
## Usage

1. Start the application with Maven
2. The API will be accessible at http://localhost:8080/api/swagger-ui/index.html


## Authentication
The API uses Spring Security for authentication control. The following roles are available:

```
USER -> Standard user role for logged-in users.
```

## Database

The project utilizes H2 as the database. The necessary database migrations are managed using Flyway.

The [H2](https://www.h2database.com/html/main.html) Console will be accessible at http://localhost:8080/api/h2-console

## Run Spring Boot application
```
mvn spring-boot:run
```
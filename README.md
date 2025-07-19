

# Tournament Registration System

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

A web-based tournament registration system built with Spring Boot that allows users to register for tournaments, manage participants, and handle tournament logistics.

## Features

- User authentication and authorization with Spring Security
- Tournament creation and management
- Participant registration
- Database persistence with Spring Data JPA and MySQL
- Responsive web interface using Thymeleaf templates
- RESTful API endpoints
- OpenAPI documentation

## Technologies Used

- **Backend**: Java 17, Spring Boot 3.2.5
- **Frontend**: Thymeleaf, HTML5, CSS, JavaScript
- **Database**: MySQL
- **Security**: Spring Security
- **API Documentation**: SpringDoc OpenAPI
- **Build Tool**: Maven

## Prerequisites

- Java 17 JDK
- MySQL 8.0+
- Maven 3.8+

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/TournamentRegistration.git
   cd TournamentRegistration
   ```

2. Configure MySQL database:
   - Create a database named `tournament_db`
   - Update the database configuration in `src/main/resources/application.properties`

3. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Access the application:
   - Web interface: http://localhost:8080
   - API documentation: http://localhost:8080/swagger-ui.html

## Project Structure

```
src/
├── main/
   ├── java/com/example/tournamentregistration/
   │   ├── config/       # Configuration classes
   │   ├── controller/   # MVC and REST controllers
   │   ├── model/        # Entity classes
   │   ├── repository/   # JPA repositories
   │   ├── service/      # Business logic
   │   └── security/     # Security configuration
   └── resources/
       ├── static/       # Static assets
       ├── templates/    # Thymeleaf templates
       └── application.properties

```

## API Documentation

The system provides OpenAPI documentation which can be accessed at:
`http://localhost:8080/swagger-ui.html` when the application is running.






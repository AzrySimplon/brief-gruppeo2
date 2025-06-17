# Gruppeo API

A Spring Boot application for managing groups, persons, and user authentication.

## 🚀 Features

- JWT-based Authentication
- User Management
- Person Management
- Group Management
- List Management
- Role-based Access Control

## 🛠️ Technical Stack

### Backend
- Java 24
- Spring Boot 3.5.0
- Spring Security
- Spring Data JPA
- PostgreSQL (Production)
- H2 Database (Testing)
- JWT (JSON Web Tokens)
- Maven

### Testing
- JUnit
- Spring Security Test
- MockMvc

## 📋 Prerequisites

- Java JDK 24
- Maven
- PostgreSQL Database

## 🔧 Configuration

### Application Properties

```properties
# Database Configuration
spring.datasource.url=your_database_url
spring.datasource.username=your_username
spring.datasource.password=your_password

# JWT Configuration
app.secret-key=your_secret_key
app.expiration-time=86400000
```
## 🚀 Installation & Running

1. Clone the repository
```bash
git clone [repository-url]
```
2. Navigate to project directory
```bash
cd gruppeo
```
3. Build the project
```bash
mvn clean install
```
4. Run the application
```bash
mvn spring-boot:run
```
## 🔒 API Endpoints

### Authentication
- POST `/api/auth/register` - Register new user
- POST `/api/auth/login` - Login user

### Users
- GET `/user` - Get all users
- GET `/user/{id}` - Get user by ID
- GET `/user/username/{username}` - Get user by username
- POST `/user` - Create new user
- PUT `/user/{id}` - Update user
- DELETE `/user/{id}` - Delete user

### Persons
- GET `/person` - Get all persons
- GET `/person/{id}` - Get person by ID
- POST `/person` - Create new person
- PUT `/person/{id}` - Update person
- DELETE `/person/{id}` - Delete person

### Person Lists
- GET `/person-list` - Get all lists
- GET `/person-list/{id}` - Get list by ID
- POST `/person-list` - Create new list
- PUT `/person-list/{id}` - Update list
- DELETE `/person-list/{id}` - Delete list
- POST `/person-list/{id}/add-group` - Add group to list
- POST `/person-list/{id}/add-student` - Add student to list
- POST `/person-list/{id}/add-teacher` - Add teacher to list

## 🧪 Testing Controllers

The application includes a coverage on controllers:
- Auth Controller
- Person Controller
- Person Group Controller
- Person List Controller
- User Controller

Run tests using:
```bash
mvn test
```
## 🔐 Security

- JWT-based authentication
- Password encryption using BCrypt
- Role-based access control
- Secure endpoints with Spring Security

## 📝 Models

### User
- Username
- Password
- Associated Person

### Person
- Name
- Gender (X, F, M)
- French Knowledge Level
- DWWM Status
- Technical Knowledge Level
- Profile
- Teacher Status

### PersonList
- Name
- Number of Members
- Groups

### PersonGroup
- Name
- Size
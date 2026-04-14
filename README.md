# Secure Blog Application

A full-stack blog application with authentication and role-based access control.

## Features
- **JWT Authentication**: Secure stateless authentication.
- **Role-based Authorization**: Different access levels for `ADMIN` and `USER`.
- **Post Management**: Create, Read, Update, and Delete posts (Admin restricted creation/editing).
- **Comment System**: Users can comment on posts; Admins can moderate comments.
- **Admin Controls**: Comprehensive dashboard capabilities for content moderation.
- **Modern UI**: Clean and responsive React interface.

## Tech Stack

### Backend
- **Spring Boot**: Core framework.
- **Spring Security**: JWT and role-based security.
- **Spring Data JPA**: Database ORM.
- **MySQL**: Persistent storage.
- **Lombok (Simulated/Manual)**: Clean DTO and Entity patterns.

### Frontend
- **React (Vite)**: Fast and modern frontend development.
- **Axios**: API communication.
- **React Router DOM**: Client-side routing.
- **JWT-Decode**: Token parsing for role detection.

## Project Structure
```
project-root/
│
├── backend/          # Spring Boot application
│   ├── src/
│   ├── pom.xml
│   └── mvnw
│
├── frontend/         # React Vite application
│   ├── src/
│   ├── package.json
│   └── vite.config.js
│
├── .gitignore
└── README.md
```

## How to Run

### 1. Prerequisites
- Java 21 or higher installed.
- Node.js installed.
- MySQL server running with a database named `secureblog`.

### 2. Run Backend
```bash
cd backend
./mvnw spring-boot:run
```
*The API will be available at `http://localhost:8080`.*

### 3. Run Frontend
```bash
cd frontend
npm install
npm run dev
```
*The application will be available at `http://localhost:5173`.*

## API Base URL
`http://localhost:8080`

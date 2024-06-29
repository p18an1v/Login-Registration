# Movie Ticket Booking System

This project is a Movie Ticket Booking System developed using React for the frontend and Spring Boot for the backend.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [Backend Setup](#backend-setup)
- [Frontend Setup](#frontend-setup)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Features

- User registration and login
- Admin panel and User panel after successful login

## Tech Stack

**Frontend:**
- React
- Axios (for HTTP requests)
- React Router (for routing)
- CSS/SCSS for styling

**Backend:**
- Spring Boot
- Spring Security (for authentication and authorization)
- Hibernate (for ORM)
- MySQL (as the database)

## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed on your machine:

- Java JDK 22 or later
- Node.js and npm
- MySQL

## Backend Setup

   ```bash
   git clone https://github.com/your-username/movie-ticket-booking-system.git
   cd movie-ticket-booking-system/movie_booking_system_Backend

   **applications.properties :-**
   
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
```
## Frontend Setup

```
 npm install
 npm run dev
```

## USAGE

Register a new user: Go to the registration page and fill in the required details.
Login: Use the credentials to log in.

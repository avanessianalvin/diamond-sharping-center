# Diamond Sharping Center
used Alvin's
### Spring Boot + Vue 3 Full-Stack Starter

A ready-to-start full-stack boilerplate for building applications with **Spring Boot (JWT security)** and a **Vue 3 + Vuetify** dashboard frontend.**

---

## ✨ Features
- **Authentication & Authorization**
    - JWT-based security with Spring Security
    - Role-based access control (e.g. `ADMIN`, `USER`)
- **Backend**
    - Spring Boot 3
    - Hibernate & JPA
    - PostgreSQL database
    - Centralized logging (SLF4J/Logback)
    - Lombok for cleaner entity/DTO code
- **Frontend**
    - Vue 3 + Vite
    - Vuetify 3 (Material Design)
    - Dashboard layout with login/logout
    - Server-side pagination & search example
- **API**
    - RESTful endpoints
    - Pagination & filtering
    - JSON responses

---

## 🛠 Tech Stack
| Layer        | Technology                  |
|--------------|-----------------------------|
| Backend      | Spring Boot, Spring Security, Hibernate, Lombok |
| Database     | PostgreSQL                  |
| Frontend     | Vue 3, Vuetify 3, Axios     |
| Build Tools  | Maven (backend), Vite (frontend) |

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Node.js 18+
- PostgreSQL 14+
- Maven 3.9+

### Backend Setup
```bash
# clone repo and enter backend folder if you separated them
cd backend
cp src/main/resources/application.example.yml src/main/resources/application.yml
# edit DB credentials & JWT secret in application.yml
mvn clean install
mvn spring-boot:run
```
### Frontend Setup
```bash
cd frontend
npm install
npm run serve
```

### 🔑 Environment Variables
- application.properties

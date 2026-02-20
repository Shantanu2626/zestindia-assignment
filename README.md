# zestindia-assignment
Created repo for zest india Assignment


---
#Product Service – Spring Boot REST API

A **production-ready Product Management Service** built using **Spring Boot**, featuring  
🔐 **JWT Authentication**, ♻️ **Refresh Token**, ✅ **Validation**, 🧪 **Unit Testing (Mockito)**  
and **clean layered architecture**.

---

## 🚀 Features

- 🔑 JWT Authentication (Access + Refresh Token)
- 👥 Role Based Access (ADMIN / USER)
- 📦 Product CRUD APIs
- 📋 Fetch Items by Product ID
- ✅ DTO-level Validation
- 🧪 Unit Testing with Mockito
- 🌐 RESTful APIs (Spring MVC)
- 🗄️ JPA + Hibernate
- 🐳 Docker ready (optional)

## Authentication Flow

1. User logs in using **email + password**
2. Server returns:
    - **Access Token** (short lived)
    - **Refresh Token** (long lived)
3. Access token used in `Authorization: Bearer <token>`
4. When access token expires → call **/refresh**
5. New access token is issued

---

## 🔑 Auth APIs

### ▶️ Login

```json
{
  "email": "admin@test.com",
  "password": "admin123"
}
{
  "accessToken": "jwt-token",
  "refreshToken": "uuid-refresh-token"
}

POST /api/v1/auth/refresh

Request

{
  "refreshToken": "uuid-refresh-token"
}

Response
{
  "accessToken": "new-jwt-token"
}

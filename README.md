# zestindia-assignment

Created repo for Zest India Assignment

## Product Service – Spring Boot REST API

A production-ready Product Management Service built using Spring Boot, featuring  
🔐 JWT Authentication, ♻️ Refresh Token, ✅ Validation, 🧪 Unit Testing (Mockito)  
and clean layered architecture.

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
- 🐳 Docker ready

---

## 🔐 Authentication Flow

1. User logs in using email + password
2. Server returns:
   - Access Token (short lived)
   - Refresh Token (long lived)
3. Access token used in  
   `Authorization: Bearer <token>`
4. When access token expires → call `/refresh`
5. New access token is issued

---

## 🔑 Auth APIs

### ▶️ Login

**POST** `/api/v1/auth/login`

**Request**
```json
{
  "email": "admin@test.com",
  "password": "admin123"
}  
```
**Response**
```json
{
  "accessToken": "jwt-token",
  "refreshToken": "uuid-refresh-token" 
}
```

**Request**
**POST**  `/api/v1/auth/refresh`
```json
{
  "refreshToken": "uuid-refresh-token"
}
```
**Response** 
```json
{
  "accessToken": "new-jwt-access-token"
}
```
## Product API
### Create Product (ADMIN)
**POST** `/api/v1/products/saveproduct`

### Get All Products
**POST** `/api/v1/products/saveproduct`

### Get Product by ID
**POST** `/api/v1/products/saveproduct`

### Update Product (ADMIN)
**POST** `/api/v1/products/saveproduct`

### Delete Product (ADMIN)
**POST** `/api/v1/products/saveproduct`

### Get Items by Product ID
**POST** `/api/v1/products/saveproduct`

## Validation

✔ Implemented using Jakarta Validation

✔ Applied at DTO level

✔ Invalid input returns 400 BAD REQUEST

✔ Global Exception Handling

✔ Handled using @RestControllerAdvice

✔ ProductNotFound

✔ UserNotFound

✔ UserConflict

✔ Invalid Credentials

✔ Validation Errors

## Testing

Unit tests written using JUnit 5 & Mockito

Service layer tested without loading Spring context

**command**
```bash
mvn test
```

## Swagger Documentation

Swagger UI is enabled.

Access Swagger:

```bash
http://localhost:8080/swagger-ui/index.html
```
## Assignment Checklist

✔ REST APIs

✔ JWT Security

✔ Refresh Token

✔ Role Based Authorization

✔ Validation

✔ Exception Handling

✔ Unit Testing

✔ Swagger

✔ Docker

## Author

Shantanu
Backend Developer – Spring Boot & REST APIs


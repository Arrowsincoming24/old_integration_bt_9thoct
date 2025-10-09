# 🧪 CashCached - Complete Postman Testing Guide

## 📋 Pre-requisites

1. ✅ Application running on `http://localhost:8080`
2. ✅ Postman installed
3. ✅ Default admin account created (admin/Admin@123)

---

## 🔧 Postman Setup

### Base URL
```
http://localhost:8080/fd-simulator
```

### Environment Variables (Optional but Recommended)
Create a Postman environment with:
- `baseUrl`: `http://localhost:8080/fd-simulator`
- `token`: (will be set after login)
- `adminToken`: (will be set after admin login)

---

## 🧪 Test Sequence

### ✅ PHASE 1: Authentication Tests

#### 1.1 Register New User
**Method:** `POST`  
**URL:** `{{baseUrl}}/api/auth/register`  
**Headers:**
```
Content-Type: application/json
```
**Body (raw JSON):**
```json
{
  "username": "testuser",
  "email": "testuser@example.com",
  "password": "Test@123",
  "phoneNumber": "+1234567890",
  "preferredCurrency": "USD",
  "preferredLanguage": "en"
}
```
**Expected Response (200 OK):**
```json
{
  "message": "User registered successfully",
  "userId": 2
}
```

**✅ What to Check:**
- Status code is 200
- Response contains "User registered successfully"
- userId is returned

---

#### 1.2 Login as Admin
**Method:** `POST`  
**URL:** `{{baseUrl}}/api/auth/login`  
**Headers:**
```
Content-Type: application/json
```
**Body (raw JSON):**
```json
{
  "username": "admin",
  "password": "Admin@123"
}
```
**Expected Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "admin",
  "email": "admin@cashcached.com",
  "role": "ADMIN",
  "phoneNumber": null
}
```

**✅ What to Check:**
- Status code is 200
- JWT token is returned
- Role is "ADMIN"
- **IMPORTANT:** Copy the token value for next requests

**Postman Script (Tests tab):**
```javascript
pm.environment.set("adminToken", pm.response.json().token);
```

---

#### 1.3 Login as Regular User
**Method:** `POST`  
**URL:** `{{baseUrl}}/api/auth/login`  
**Headers:**
```
Content-Type: application/json
```
**Body (raw JSON):**
```json
{
  "username": "testuser",
  "password": "Test@123"
}
```
**Expected Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "testuser",
  "email": "testuser@example.com",
  "role": "CUSTOMER",
  "phoneNumber": "+1234567890"
}
```

**✅ What to Check:**
- Status code is 200
- JWT token is returned
- Role is "CUSTOMER"

**Postman Script (Tests tab):**
```javascript
pm.environment.set("token", pm.response.json().token);
```

---

#### 1.4 Test Invalid Login
**Method:** `POST`  
**URL:** `{{baseUrl}}/api/auth/login`  
**Body (raw JSON):**
```json
{
  "username": "admin",
  "password": "wrongpassword"
}
```
**Expected Response (400 Bad Request):**
```json
"Login failed: Bad credentials"
```

**✅ What to Check:**
- Status code is 400
- Error message indicates authentication failure

---

### ✅ PHASE 2: Admin API Tests (Requires Admin Token)

**IMPORTANT:** Add this header to ALL admin requests:
```
Authorization: Bearer {{adminToken}}
```

#### 2.1 Get Dashboard Statistics
**Method:** `GET`  
**URL:** `{{baseUrl}}/api/admin/dashboard/stats`  
**Headers:**
```
Authorization: Bearer {{adminToken}}
```
**Expected Response (200 OK):**
```json
{
  "totalUsers": 2,
  "totalCustomers": 1,
  "totalFixedDeposits": 0,
  "activeFixedDeposits": 0,
  "totalAmount": 0.0
}
```

**✅ What to Check:**
- Status code is 200
- All statistics are returned
- totalUsers should be at least 2 (admin + testuser)

---

#### 2.2 Get All Users
**Method:** `GET`  
**URL:** `{{baseUrl}}/api/admin/users`  
**Headers:**
```
Authorization: Bearer {{adminToken}}
```
**Expected Response (200 OK):**
```json
[
  {
    "id": 1,
    "username": "admin",
    "email": "admin@cashcached.com",
    "phoneNumber": null,
    "role": "ADMIN",
    "enabled": true,
    "preferredCurrency": "USD",
    "preferredLanguage": "EN",
    "createdAt": "2025-10-09T22:30:00"
  },
  {
    "id": 2,
    "username": "testuser",
    "email": "testuser@example.com",
    "phoneNumber": "+1234567890",
    "role": "CUSTOMER",
    "enabled": true,
    "preferredCurrency": "USD",
    "preferredLanguage": "EN",
    "createdAt": "2025-10-09T22:31:00"
  }
]
```

**✅ What to Check:**
- Status code is 200
- Array of users is returned
- Admin user is present
- All user fields are populated

---

#### 2.3 Get All CashCached Products
**Method:** `GET`  
**URL:** `{{baseUrl}}/api/admin/products`  
**Headers:**
```
Authorization: Bearer {{adminToken}}
```
**Expected Response (200 OK):**
```json
[
  {
    "id": 1,
    "productName": "CashCached Short Term",
    "description": "Perfect for short-term savings with flexible tenure options",
    "minAmount": 5000,
    "maxAmount": 100000,
    "minTenureMonths": 3,
    "maxTenureMonths": 12,
    "interestRate": 5.5,
    "isActive": true
  },
  {
    "id": 2,
    "productName": "CashCached Regular",
    "description": "Standard fixed deposit with competitive interest rates",
    "minAmount": 10000,
    "maxAmount": 500000,
    "minTenureMonths": 6,
    "maxTenureMonths": 24,
    "interestRate": 6.5,
    "isActive": true
  }
  // ... 8 more products
]
```

**✅ What to Check:**
- Status code is 200
- 10 products are returned
- All products have "CashCached" prefix
- All products are active

---

#### 2.4 Get Specific Product
**Method:** `GET`  
**URL:** `{{baseUrl}}/api/admin/products/1`  
**Headers:**
```
Authorization: Bearer {{adminToken}}
```
**Expected Response (200 OK):**
```json
{
  "id": 1,
  "productName": "CashCached Short Term",
  "description": "Perfect for short-term savings with flexible tenure options",
  "minAmount": 5000,
  "maxAmount": 100000,
  "minTenureMonths": 3,
  "maxTenureMonths": 12,
  "interestRate": 5.5,
  "isActive": true
}
```

**✅ What to Check:**
- Status code is 200
- Product details are correct
- Product name starts with "CashCached"

---

#### 2.5 Create New Product
**Method:** `POST`  
**URL:** `{{baseUrl}}/api/admin/products`  
**Headers:**
```
Authorization: Bearer {{adminToken}}
Content-Type: application/json
```
**Body (raw JSON):**
```json
{
  "productName": "CashCached Test Product",
  "description": "Test product for Postman",
  "minAmount": 1000,
  "maxAmount": 50000,
  "minTenureMonths": 6,
  "maxTenureMonths": 24,
  "interestRate": 5.0,
  "isActive": true
}
```
**Expected Response (200 OK):**
```json
{
  "id": 11,
  "productName": "CashCached Test Product",
  "description": "Test product for Postman",
  "minAmount": 1000,
  "maxAmount": 50000,
  "minTenureMonths": 6,
  "maxTenureMonths": 24,
  "interestRate": 5.0,
  "isActive": true
}
```

**✅ What to Check:**
- Status code is 200
- New product is created with ID
- All fields match the request

---

#### 2.6 Update Product
**Method:** `PUT`  
**URL:** `{{baseUrl}}/api/admin/products/11`  
**Headers:**
```
Authorization: Bearer {{adminToken}}
Content-Type: application/json
```
**Body (raw JSON):**
```json
{
  "productName": "CashCached Test Product Updated",
  "description": "Updated test product",
  "minAmount": 2000,
  "maxAmount": 60000,
  "minTenureMonths": 6,
  "maxTenureMonths": 36,
  "interestRate": 5.5,
  "isActive": true
}
```
**Expected Response (200 OK):**
```json
{
  "id": 11,
  "productName": "CashCached Test Product Updated",
  "description": "Updated test product",
  "minAmount": 2000,
  "maxAmount": 60000,
  "minTenureMonths": 6,
  "maxTenureMonths": 36,
  "interestRate": 5.5,
  "isActive": true
}
```

**✅ What to Check:**
- Status code is 200
- Product is updated with new values

---

#### 2.7 Delete Product
**Method:** `DELETE`  
**URL:** `{{baseUrl}}/api/admin/products/11`  
**Headers:**
```
Authorization: Bearer {{adminToken}}
```
**Expected Response (200 OK):**
```json
"Product deleted successfully"
```

**✅ What to Check:**
- Status code is 200
- Success message is returned

---

#### 2.8 Get All Fixed Deposits
**Method:** `GET`  
**URL:** `{{baseUrl}}/api/admin/fixed-deposits`  
**Headers:**
```
Authorization: Bearer {{adminToken}}
```
**Expected Response (200 OK):**
```json
[]
```
(Empty array if no FDs created yet)

**✅ What to Check:**
- Status code is 200
- Array is returned (may be empty initially)

---

#### 2.9 Get Audit Logs
**Method:** `GET`  
**URL:** `{{baseUrl}}/api/admin/audit-logs?size=10`  
**Headers:**
```
Authorization: Bearer {{adminToken}}
```
**Expected Response (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "user": {
        "id": 1,
        "username": "admin"
      },
      "action": "PRODUCT_CREATED",
      "entityType": "FdProduct",
      "entityId": 11,
      "timestamp": "2025-10-09T22:35:00",
      "ipAddress": "127.0.0.1"
    }
  ],
  "pageable": {...},
  "totalElements": 1,
  "totalPages": 1
}
```

**✅ What to Check:**
- Status code is 200
- Audit logs are returned
- Actions are logged correctly

---

### ✅ PHASE 3: Customer API Tests (Requires Customer Token)

**IMPORTANT:** Add this header to ALL customer requests:
```
Authorization: Bearer {{token}}
```

#### 3.1 Get Active Products (Public)
**Method:** `GET`  
**URL:** `{{baseUrl}}/api/public/products/active`  
**Headers:** (None required for public endpoint)

**Expected Response (200 OK):**
```json
[
  {
    "id": 1,
    "productName": "CashCached Short Term",
    "description": "Perfect for short-term savings with flexible tenure options",
    "minAmount": 5000,
    "maxAmount": 100000,
    "minTenureMonths": 3,
    "maxTenureMonths": 12,
    "interestRate": 5.5,
    "isActive": true
  }
  // ... more products
]
```

**✅ What to Check:**
- Status code is 200
- Only active products are returned
- No authentication required

---

#### 3.2 Create Fixed Deposit (Customer)
**Method:** `POST`  
**URL:** `{{baseUrl}}/api/customer/fixed-deposits`  
**Headers:**
```
Authorization: Bearer {{token}}
Content-Type: application/json
```
**Body (raw JSON):**
```json
{
  "productId": 1,
  "principalAmount": 10000,
  "tenureInMonths": 6
}
```
**Expected Response (200 OK):**
```json
{
  "id": 1,
  "user": {
    "id": 2,
    "username": "testuser"
  },
  "product": {
    "id": 1,
    "productName": "CashCached Short Term"
  },
  "principalAmount": 10000,
  "interestRate": 5.5,
  "tenureInMonths": 6,
  "maturityAmount": 10275,
  "startDate": "2025-10-09",
  "maturityDate": "2026-04-09",
  "status": "ACTIVE"
}
```

**✅ What to Check:**
- Status code is 200
- FD is created with correct details
- Maturity amount is calculated
- Status is ACTIVE

---

#### 3.3 Get Customer's Fixed Deposits
**Method:** `GET`  
**URL:** `{{baseUrl}}/api/customer/fixed-deposits`  
**Headers:**
```
Authorization: Bearer {{token}}
```
**Expected Response (200 OK):**
```json
[
  {
    "id": 1,
    "product": {
      "productName": "CashCached Short Term"
    },
    "principalAmount": 10000,
    "interestRate": 5.5,
    "tenureInMonths": 6,
    "maturityAmount": 10275,
    "status": "ACTIVE",
    "startDate": "2025-10-09",
    "maturityDate": "2026-04-09"
  }
]
```

**✅ What to Check:**
- Status code is 200
- Only customer's own FDs are returned

---

### ✅ PHASE 4: Error Handling Tests

#### 4.1 Access Admin API Without Token
**Method:** `GET`  
**URL:** `{{baseUrl}}/api/admin/users`  
**Headers:** (No Authorization header)

**Expected Response (401 Unauthorized):**

**✅ What to Check:**
- Status code is 401
- Access is denied

---

#### 4.2 Access Admin API With Customer Token
**Method:** `GET`  
**URL:** `{{baseUrl}}/api/admin/users`  
**Headers:**
```
Authorization: Bearer {{token}}
```
(Using customer token, not admin token)

**Expected Response (403 Forbidden):**

**✅ What to Check:**
- Status code is 403
- Customer cannot access admin endpoints

---

#### 4.3 Register Duplicate Username
**Method:** `POST`  
**URL:** `{{baseUrl}}/api/auth/register`  
**Body (raw JSON):**
```json
{
  "username": "admin",
  "email": "newemail@example.com",
  "password": "Test@123",
  "preferredCurrency": "USD",
  "preferredLanguage": "en"
}
```
**Expected Response (400 Bad Request):**
```json
"Username already exists"
```

**✅ What to Check:**
- Status code is 400
- Duplicate username is rejected

---

#### 4.4 Register Duplicate Email
**Method:** `POST`  
**URL:** `{{baseUrl}}/api/auth/register`  
**Body (raw JSON):**
```json
{
  "username": "newuser",
  "email": "admin@cashcached.com",
  "password": "Test@123",
  "preferredCurrency": "USD",
  "preferredLanguage": "en"
}
```
**Expected Response (400 Bad Request):**
```json
"Email already exists"
```

**✅ What to Check:**
- Status code is 400
- Duplicate email is rejected

---

## 📊 Complete Test Checklist

### Authentication (4 tests)
- [ ] Register new user
- [ ] Login as admin
- [ ] Login as customer
- [ ] Test invalid login

### Admin APIs (9 tests)
- [ ] Get dashboard statistics
- [ ] Get all users
- [ ] Get all products
- [ ] Get specific product
- [ ] Create product
- [ ] Update product
- [ ] Delete product
- [ ] Get fixed deposits
- [ ] Get audit logs

### Customer APIs (3 tests)
- [ ] Get active products (public)
- [ ] Create fixed deposit
- [ ] Get customer's fixed deposits

### Error Handling (4 tests)
- [ ] Access admin API without token
- [ ] Access admin API with customer token
- [ ] Register duplicate username
- [ ] Register duplicate email

**Total: 20 Tests**

---

## 🔍 Common Issues & Solutions

### Issue 1: "401 Unauthorized"
**Solution:** Make sure you're including the JWT token in the Authorization header:
```
Authorization: Bearer <your-token-here>
```

### Issue 2: "403 Forbidden"
**Solution:** You're using a customer token for an admin endpoint. Use the admin token instead.

### Issue 3: Token Expired
**Solution:** JWT tokens expire after 2 minutes. Login again to get a new token.

### Issue 4: "Cannot find symbol" compilation error
**Solution:** Fixed! The DataInitializer now uses `setEnabled()` instead of `setActive()`.

---

## 📝 Postman Collection Structure

Recommended folder structure:
```
CashCached API Tests
├── 1. Authentication
│   ├── Register User
│   ├── Login Admin
│   ├── Login Customer
│   └── Invalid Login
├── 2. Admin APIs
│   ├── Dashboard Stats
│   ├── User Management
│   ├── Product Management
│   ├── FD Management
│   └── Audit Logs
├── 3. Customer APIs
│   ├── View Products
│   ├── Create FD
│   └── View My FDs
└── 4. Error Tests
    ├── No Token
    ├── Wrong Role
    └── Duplicate Data
```

---

## 🎯 Quick Test Script

Run these in order for a complete test:

1. **Register** → Get userId
2. **Login Admin** → Get adminToken
3. **Login Customer** → Get token
4. **Get Dashboard Stats** → Verify counts
5. **Get All Users** → See both users
6. **Get All Products** → See 10 CashCached products
7. **Create FD** → As customer
8. **Get Dashboard Stats** → Verify FD count increased
9. **Get Audit Logs** → See all actions logged

---

**Ready to test! Start with Phase 1 and work through each phase sequentially.** 🚀

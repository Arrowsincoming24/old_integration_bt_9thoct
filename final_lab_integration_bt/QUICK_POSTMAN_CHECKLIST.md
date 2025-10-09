# ⚡ Quick Postman Testing Checklist

## 🔧 Error Fixed
✅ **Fixed compilation error:** Changed `setActive()` to `setEnabled()` and `Language.ENGLISH` to `Language.EN` in DataInitializer.java

---

## 🚀 Start Application
```bash
mvn spring-boot:run
```

**Wait for console output:**
```
============================================================
✓ DEFAULT ADMIN ACCOUNT CREATED
============================================================
Username: admin
Password: Admin@123
Email: admin@cashcached.com
============================================================

✓ Initialized 10 CashCached products successfully

Started FdSimulatorApplication in X.XXX seconds
```

---

## 🧪 Essential Postman Tests (10 Minutes)

### Base URL
```
http://localhost:8080/fd-simulator
```

---

### ✅ TEST 1: Login as Admin (MUST DO FIRST)
```
POST {{baseUrl}}/api/auth/login

Body:
{
  "username": "admin",
  "password": "Admin@123"
}

✓ Check: Get JWT token
✓ Check: Role is "ADMIN"
✓ Action: COPY THE TOKEN!
```

---

### ✅ TEST 2: Get All Users
```
GET {{baseUrl}}/api/admin/users

Headers:
Authorization: Bearer <paste-admin-token-here>

✓ Check: See admin user
✓ Check: Status 200
```

---

### ✅ TEST 3: Get All CashCached Products
```
GET {{baseUrl}}/api/admin/products

Headers:
Authorization: Bearer <paste-admin-token-here>

✓ Check: 10 products returned
✓ Check: All have "CashCached" prefix
✓ Check: Status 200
```

---

### ✅ TEST 4: Get Dashboard Stats
```
GET {{baseUrl}}/api/admin/dashboard/stats

Headers:
Authorization: Bearer <paste-admin-token-here>

✓ Check: totalUsers >= 1
✓ Check: All stats present
✓ Check: Status 200
```

---

### ✅ TEST 5: Register New User
```
POST {{baseUrl}}/api/auth/register

Body:
{
  "username": "testuser",
  "email": "test@example.com",
  "password": "Test@123",
  "phoneNumber": "+1234567890",
  "preferredCurrency": "USD",
  "preferredLanguage": "en"
}

✓ Check: "User registered successfully"
✓ Check: userId returned
✓ Check: Status 200
```

---

### ✅ TEST 6: Login as New User
```
POST {{baseUrl}}/api/auth/login

Body:
{
  "username": "testuser",
  "password": "Test@123"
}

✓ Check: Get JWT token
✓ Check: Role is "CUSTOMER"
✓ Action: COPY THE TOKEN!
```

---

### ✅ TEST 7: Get Active Products (Public - No Token)
```
GET {{baseUrl}}/api/public/products/active

No headers needed!

✓ Check: Products returned
✓ Check: Status 200
```

---

### ✅ TEST 8: Create Fixed Deposit (As Customer)
```
POST {{baseUrl}}/api/customer/fixed-deposits

Headers:
Authorization: Bearer <paste-customer-token-here>

Body:
{
  "productId": 1,
  "principalAmount": 10000,
  "tenureInMonths": 6
}

✓ Check: FD created
✓ Check: maturityAmount calculated
✓ Check: Status is "ACTIVE"
✓ Check: Status 200
```

---

### ✅ TEST 9: Get Customer's FDs
```
GET {{baseUrl}}/api/customer/fixed-deposits

Headers:
Authorization: Bearer <paste-customer-token-here>

✓ Check: See the FD you just created
✓ Check: Status 200
```

---

### ✅ TEST 10: Test Authorization (Should Fail)
```
GET {{baseUrl}}/api/admin/users

Headers:
Authorization: Bearer <paste-customer-token-here>
(Using CUSTOMER token, not ADMIN)

✓ Check: Status 403 Forbidden
✓ Check: Access denied
```

---

## 📊 Expected Results Summary

| Test | Endpoint | Expected Status | Key Check |
|------|----------|----------------|-----------|
| 1 | Login Admin | 200 | Get admin token |
| 2 | Get Users | 200 | See admin user |
| 3 | Get Products | 200 | 10 CashCached products |
| 4 | Dashboard Stats | 200 | All stats present |
| 5 | Register User | 200 | Success message |
| 6 | Login Customer | 200 | Get customer token |
| 7 | Get Products (Public) | 200 | No auth needed |
| 8 | Create FD | 200 | FD created |
| 9 | Get Customer FDs | 200 | See your FD |
| 10 | Wrong Role | 403 | Access denied |

---

## 🔍 What to Look For

### ✅ Success Indicators:
1. Admin login returns token with role "ADMIN"
2. All 10 products have "CashCached" prefix
3. Dashboard stats show correct counts
4. Customer can create FD
5. Authorization works (403 for wrong role)

### ❌ Common Errors to Check:

#### Error: 401 Unauthorized
**Cause:** Missing or invalid token  
**Fix:** Make sure Authorization header is set correctly

#### Error: 403 Forbidden
**Cause:** Wrong role (customer trying to access admin endpoint)  
**Fix:** Use admin token for admin endpoints

#### Error: 400 Bad Request
**Cause:** Invalid data in request body  
**Fix:** Check JSON format and required fields

#### Error: 500 Internal Server Error
**Cause:** Application error  
**Fix:** Check application console logs

---

## 🎯 Critical Checks

### 1. Admin Account Works
```bash
✓ Can login with admin/Admin@123
✓ Gets ADMIN role in response
✓ Token is valid for admin endpoints
```

### 2. Products Are CashCached
```bash
✓ All 10 products exist
✓ All have "CashCached" prefix:
  - CashCached Short Term
  - CashCached Regular
  - CashCached Senior
  - CashCached Tax Saver
  - CashCached Flexi
  - CashCached Premium
  - CashCached Monthly Income
  - CashCached Youth
  - CashCached Corporate
  - CashCached Cumulative
```

### 3. Authentication Works
```bash
✓ Can register new users
✓ Can login with credentials
✓ JWT tokens are generated
✓ Tokens work for authenticated endpoints
✓ Role-based access control works
```

### 4. Database Initialized
```bash
✓ Admin user created
✓ 10 products created
✓ Can create FDs
✓ Audit logs working
```

---

## 🐛 If Application Won't Start

### Check Console for:
1. **Port already in use:** Kill process on port 8080
2. **Compilation errors:** Run `mvn clean install` first
3. **Database errors:** H2 should work out of the box
4. **Missing dependencies:** Run `mvn clean install -U`

### Quick Fixes:
```bash
# Clean and rebuild
mvn clean install

# Start fresh
mvn spring-boot:run

# Check if port 8080 is free
netstat -ano | findstr :8080
```

---

## 📝 Postman Environment Setup (Optional)

Create environment with these variables:
```
baseUrl = http://localhost:8080/fd-simulator
adminToken = (set after admin login)
token = (set after customer login)
```

Use in requests:
```
{{baseUrl}}/api/admin/users
Authorization: Bearer {{adminToken}}
```

---

## ✅ Success Checklist

After running all tests, you should have:
- [x] Admin account working
- [x] 10 CashCached products visible
- [x] User registration working
- [x] Customer login working
- [x] FD creation working
- [x] Authorization working (403 for wrong role)
- [x] Dashboard stats showing correct data
- [x] Audit logs recording actions

---

## 🎉 All Tests Pass?

**Congratulations!** Your CashCached application is working correctly:
- ✅ Authentication fixed
- ✅ Admin account created
- ✅ Products renamed to CashCached
- ✅ All APIs functional
- ✅ Authorization working

**Next Steps:**
1. Test the web UI at `http://localhost:8080/fd-simulator/login`
2. Login as admin to see the new dashboard
3. View the ER diagram in admin panel
4. Create test FDs through the UI

---

**Time to complete: ~10 minutes**  
**Total tests: 10 essential + 10 additional in full guide**  
**Status: Ready for testing** 🚀

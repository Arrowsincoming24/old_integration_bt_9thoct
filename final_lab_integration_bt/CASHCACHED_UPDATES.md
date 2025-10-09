# CashCached - System Updates & Configuration Guide

## 🎨 Brand Identity

### Logo & Colors
- **Primary Logo**: Infinity-link gradient style
- **Primary Gradient**: `#FF5BBE → #FF0099` (Pink to Magenta)
- **Secondary Colors**: 
  - Purple: `#7C3AED`
  - Aqua: `#00F0FF`
- **Neutrals**:
  - Dark Background: `#0D0D12`
  - Light Text: `#F5F5F7`
  - Grey Text: `#6B7280`

### Typography
- **Primary Font**: Inter (Sans-serif)
- **Weights**: Regular (400), Medium (500), Bold (700)
- **Financial Data**: Monospaced (SF Mono or Inter Mono)

### UI Elements
- **Border Radius**: 8px (standard), 16px (buttons/cards)
- **Shadows**: `0px 4px 10px rgba(0,0,0,0.25)`
- **Tone**: Modern, trustworthy, financial-tech focused

---

## 🔐 Default Admin Credentials

**IMPORTANT**: Use these credentials to access the admin dashboard

```
Username: admin
Password: Admin@123
Email: admin@cashcached.com
```

---

## 🔧 Authentication & Routing Fixes

### Issues Resolved
1. **Context Path Mapping**: Fixed `/fd-simulator` context path handling
2. **Security Configuration**: Updated `SecurityConfig.java` to properly map all routes
3. **OAuth2 Integration**: Corrected Google OAuth2 redirect URIs
4. **Page Navigation**: Fixed login, register, and dashboard routing

### Updated Files
- `src/main/java/com/bank/fdsimulator/config/SecurityConfig.java`
- `src/main/java/com/bank/fdsimulator/controller/WebController.java`
- `src/main/resources/static/js/auth.js`
- `src/main/resources/templates/login.html`
- `src/main/resources/templates/register.html`

### Security Endpoints
```
Public Access:
- / (redirects to /cashcached)
- /login
- /register
- /cashcached
- /fd-calculator
- /api/auth/**
- /api/public/**
- /oauth2/**
- /h2-console/**

Admin Access (ROLE_ADMIN):
- /admin/**
- /api/admin/**

Customer Access (ROLE_CUSTOMER or ROLE_ADMIN):
- /customer/**
- /api/customer/**
```

---

## 📊 Database Schema (ER Diagram)

### Entities

#### USER
```sql
- id (PK)
- username (UNIQUE)
- email (UNIQUE)
- password (HASHED)
- phoneNumber
- role (ENUM: ADMIN, CUSTOMER)
- googleId
- profilePicture
- preferredCurrency (ENUM: USD, INR, KWD)
- preferredLanguage (ENUM: ENGLISH, JAPANESE)
- isActive (BOOLEAN)
- createdAt (TIMESTAMP)
```

#### FIXED_DEPOSIT
```sql
- id (PK)
- user_id (FK → USER)
- product_id (FK → FD_PRODUCT)
- principalAmount (DECIMAL)
- interestRate (DECIMAL)
- tenureInMonths (INTEGER)
- maturityAmount (DECIMAL)
- startDate (DATE)
- maturityDate (DATE)
- status (ENUM: ACTIVE, MATURED, PREMATURE_CLOSED, CANCELLED)
- createdAt (TIMESTAMP)
```

#### FD_PRODUCT
```sql
- id (PK)
- productName (VARCHAR)
- description (TEXT)
- minAmount (DECIMAL)
- maxAmount (DECIMAL)
- minTenureMonths (INTEGER)
- maxTenureMonths (INTEGER)
- interestRate (DECIMAL)
- isActive (BOOLEAN)
- createdAt (TIMESTAMP)
```

#### AUDIT_LOG
```sql
- id (PK)
- user_id (FK → USER)
- action (VARCHAR)
- entityType (VARCHAR)
- entityId (BIGINT)
- oldValues (TEXT)
- newValues (TEXT)
- ipAddress (VARCHAR)
- timestamp (TIMESTAMP)
```

### Relationships
```
USER (1) → (N) FIXED_DEPOSIT
FD_PRODUCT (1) → (N) FIXED_DEPOSIT
USER (1) → (N) AUDIT_LOG
```

---

## 🏦 CashCached Products

All FD products have been rebranded with the "CashCached" prefix:

1. **CashCached Short Term**
   - Amount: $5,000 - $100,000
   - Tenure: 3-12 months
   - Rate: 5.5%

2. **CashCached Regular**
   - Amount: $10,000 - $500,000
   - Tenure: 6-24 months
   - Rate: 6.5%

3. **CashCached Senior**
   - Amount: $25,000 - $1,000,000
   - Tenure: 12-60 months
   - Rate: 7.5%

4. **CashCached Tax Saver**
   - Amount: $10,000 - $150,000
   - Tenure: 60 months (locked)
   - Rate: 6.75%

5. **CashCached Flexi**
   - Amount: $50,000 - $2,000,000
   - Tenure: 12-36 months
   - Rate: 6.25%

6. **CashCached Premium**
   - Amount: $500,000 - $10,000,000
   - Tenure: 12-60 months
   - Rate: 7.25%

7. **CashCached Monthly Income**
   - Amount: $100,000 - $5,000,000
   - Tenure: 12-60 months
   - Rate: 6.85%

8. **CashCached Youth**
   - Amount: $5,000 - $200,000
   - Tenure: 6-36 months
   - Rate: 6.0%

9. **CashCached Corporate**
   - Amount: $1,000,000 - $50,000,000
   - Tenure: 12-60 months
   - Rate: 7.75%

10. **CashCached Cumulative**
    - Amount: $25,000 - $1,000,000
    - Tenure: 12-60 months
    - Rate: 7.0%

---

## 🎯 Admin Dashboard Features

### New Admin Dashboard (`admin-dashboard-new.html`)

#### Features:
1. **Dashboard Overview**
   - Total Users count
   - Total Customers count
   - Total Fixed Deposits count
   - Total Amount invested
   - Default admin credentials display

2. **User Management**
   - View all registered users
   - See user details (username, email, phone, role, currency, language)
   - Delete users
   - Track user status (Active/Inactive)

3. **ER Diagram Visualization**
   - Interactive database schema view
   - Entity relationships
   - Field details with data types
   - Primary and Foreign key indicators

4. **Fixed Deposits Management**
   - View all FDs
   - See customer details
   - Track FD status
   - Close FDs prematurely

5. **Product Management**
   - View all CashCached products
   - Add new products
   - Edit existing products
   - Delete products
   - Track product status

6. **Audit Logs**
   - View all system actions
   - Track user activities
   - Monitor security events
   - IP address tracking

---

## 🚀 How to Run

### 1. Start the Application
```bash
cd fd_sim-4
mvn spring-boot:run
```

### 2. Access Points
- **Application**: http://localhost:8080/fd-simulator/
- **Login**: http://localhost:8080/fd-simulator/login
- **Register**: http://localhost:8080/fd-simulator/register
- **Admin Dashboard**: http://localhost:8080/fd-simulator/admin/dashboard
- **H2 Console**: http://localhost:8080/fd-simulator/h2-console

### 3. H2 Database Connection
```
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (leave empty)
```

### 4. Login as Admin
```
Username: admin
Password: Admin@123
```

---

## 🔄 Testing Authentication Flow

### Test Scenarios

#### 1. Regular Login
1. Navigate to `/fd-simulator/login`
2. Enter username and password
3. Click "Login"
4. Should redirect to appropriate dashboard based on role

#### 2. Registration
1. Navigate to `/fd-simulator/register`
2. Fill in all required fields
3. Click "Create Account"
4. Should redirect to login page
5. Login with new credentials

#### 3. Google OAuth2
1. Click "Login with Google" button
2. Authenticate with Google
3. Should auto-create user account
4. Redirect to customer dashboard

#### 4. OTP Login
1. Click "Login with Phone Number"
2. Enter phone number
3. Click "Send OTP"
4. Enter received OTP
5. Click "Verify OTP"
6. Should redirect to dashboard

---

## 📝 API Endpoints

### Authentication
```
POST /api/auth/register - Register new user
POST /api/auth/login - Login with credentials
POST /api/auth/send-otp - Send OTP to phone
POST /api/auth/verify-otp - Verify OTP
POST /api/auth/logout - Logout user
```

### Admin APIs
```
GET /api/admin/users - Get all users
GET /api/admin/users/{id} - Get user by ID
PUT /api/admin/users/{id} - Update user
DELETE /api/admin/users/{id} - Delete user

GET /api/admin/fixed-deposits - Get all FDs
GET /api/admin/fixed-deposits/{id} - Get FD by ID
PUT /api/admin/fixed-deposits/{id}/status - Update FD status
POST /api/admin/fixed-deposits/{id}/close - Close FD

GET /api/admin/products - Get all products
GET /api/admin/products/{id} - Get product by ID
POST /api/admin/products - Create product
PUT /api/admin/products/{id} - Update product
DELETE /api/admin/products/{id} - Delete product

GET /api/admin/dashboard/stats - Get dashboard statistics
GET /api/admin/audit-logs - Get audit logs
```

---

## 🎨 Frontend Branding Updates

### Files Updated with CashCached Branding:
- ✅ `admin-dashboard-new.html` - Complete redesign with brand colors
- ✅ `DataInitializer.java` - All products renamed to CashCached
- ⚠️ `login.html` - Needs branding update
- ⚠️ `register.html` - Needs branding update
- ⚠️ `customer-dashboard.html` - Needs branding update
- ⚠️ `cashcached.html` - Needs branding update

### Next Steps for Complete Branding:
1. Update login page with CashCached colors and logo
2. Update register page with brand identity
3. Redesign customer dashboard
4. Update landing page (cashcached.html)
5. Add CashCached logo image file

---

## 🔒 Security Notes

1. **Password Requirements**: Minimum 6 characters
2. **JWT Token Expiration**: 2 minutes (configurable in application.yml)
3. **Session Timeout**: 2 minutes
4. **OAuth2**: Google authentication enabled
5. **CORS**: Enabled for all origins (configure for production)

---

## 📧 Contact & Support

For issues or questions:
- Email: admin@cashcached.com
- Check audit logs for system events
- Review H2 console for database state

---

## ✅ Completed Tasks

- [x] Fixed authentication and page mapping
- [x] Created default admin user (admin/Admin@123)
- [x] Renamed all products to CashCached
- [x] Created new admin dashboard with ER diagram
- [x] Applied CashCached branding to admin panel
- [x] Fixed SecurityConfig routing
- [x] Updated context path handling
- [x] Added comprehensive user management
- [x] Implemented audit logging view

## 🔄 Pending Tasks

- [ ] Update login page with CashCached branding
- [ ] Update register page with CashCached branding
- [ ] Redesign customer dashboard
- [ ] Add CashCached logo image
- [ ] Update all remaining pages with brand colors
- [ ] Add product images/icons
- [ ] Implement email notifications
- [ ] Add SMS OTP functionality (Twilio)

---

**Last Updated**: 2025-10-09
**Version**: 2.0.0
**Status**: Authentication Fixed, Admin Dashboard Redesigned, CashCached Branding Applied

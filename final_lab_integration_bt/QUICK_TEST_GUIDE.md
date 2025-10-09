# Quick Testing Guide - FD Simulator

## 🚀 Application is Running!

**Base URL**: http://localhost:8080/fd-simulator/

---

## 📋 Quick Test Checklist

### 1. CASHCACHED Landing Page ✅
**URL**: http://localhost:8080/fd-simulator/

**What to Test**:
- [ ] Pink-to-magenta gradient design
- [ ] Navigation bar with logo
- [ ] Hero section with CTA buttons
- [ ] 6 feature cards
- [ ] Product showcase (should load 10 products)
- [ ] Stats section
- [ ] Footer

---

### 2. FD Calculator ✅
**URL**: http://localhost:8080/fd-simulator/fd-calculator

**What to Test**:
- [ ] Select different FD products
- [ ] Move amount slider (₹5K - ₹1Cr)
- [ ] Move tenure slider (3-60 months)
- [ ] Watch calculations update in real-time
- [ ] View maturity amount
- [ ] Check interest earned
- [ ] View chart visualization

---

### 3. Login Page ✅
**URL**: http://localhost:8080/fd-simulator/login

**What to Test**:
- [ ] Login form displays
- [ ] Email/Phone field
- [ ] Password field
- [ ] Google OAuth2 button
- [ ] Register link
- [ ] Forgot password link

---

### 4. H2 Database Console ✅
**URL**: http://localhost:8080/fd-simulator/h2-console

**Connection Details**:
```
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (leave empty)
```

**What to Check**:
- [ ] Connect to database
- [ ] View `FD_PRODUCT` table (should have 10 products)
- [ ] View `USER` table
- [ ] View `FIXED_DEPOSIT` table
- [ ] Run SQL queries

---

## 🔑 Test Credentials

### Create Admin User (via H2 Console)
```sql
INSERT INTO USER (id, email, phone, password, role, created_at) 
VALUES (1, 'admin@cashcached.com', '9999999999', 
'$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhkm', 
'ADMIN', CURRENT_TIMESTAMP);
-- Password: admin123
```

### Create Customer User (via H2 Console)
```sql
INSERT INTO USER (id, email, phone, password, role, created_at) 
VALUES (2, 'customer@test.com', '8888888888', 
'$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhkm', 
'CUSTOMER', CURRENT_TIMESTAMP);
-- Password: admin123
```

---

## 🧪 Feature Testing Workflow

### Test Admin Features
1. Login as admin
2. Go to: http://localhost:8080/fd-simulator/admin/dashboard
3. Test:
   - [ ] View all FD products
   - [ ] Add new product
   - [ ] Edit product
   - [ ] Delete product
   - [ ] View users per product

### Test Customer Features
1. Login as customer
2. Go to: http://localhost:8080/fd-simulator/customer/dashboard
3. Test:
   - [ ] View dashboard
   - [ ] Create new FD
   - [ ] View FD portfolio
   - [ ] Calculate maturity
   - [ ] Request premature withdrawal

---

## 🎨 Brand Verification

### Colors to Verify
- **Primary Gradient**: Pink (#FF5BBE) to Magenta (#FF0099)
- **Dark Background**: #0D0D12
- **Light Background**: #F5F5F7

### Typography to Verify
- **Font**: Inter (Google Fonts)
- **Financial Data**: Monospace font
- **Border Radius**: 16px on buttons/cards

---

## 🐛 Known Issues

1. **API Endpoint Error**: `/api/public/fd-products` returns 500
   - Products should still load on landing page via other means
   
2. **External Services Not Configured**:
   - Google OAuth2 won't work (needs credentials)
   - Email notifications won't work (needs Gmail credentials)
   - SMS notifications won't work (needs Twilio credentials)

---

## 📊 Quick Status Check

Run these commands to verify:

```powershell
# Check if application is running
Invoke-WebRequest -Uri "http://localhost:8080/fd-simulator/" -UseBasicParsing

# Check if Java process is running
Get-Process -Name java

# Check if port 8080 is listening
netstat -ano | findstr ":8080"
```

---

## 🛑 Stop Application

```powershell
# Stop Java process
Stop-Process -Name java -Force
```

---

## 🔄 Restart Application

```powershell
# Navigate to project directory
cd c:\Users\Aarav\OneDrive\Desktop\team9\fd_sim-4

# Run application
java -jar target\fd-simulator-1.0.0.jar
```

---

## 📝 Testing Notes

**Date**: October 9, 2025  
**Time**: 09:40 IST  
**Database**: H2 In-Memory  
**Status**: ✅ RUNNING

**Your browser should now be open** at the landing page!

Explore all features and report any issues you find.

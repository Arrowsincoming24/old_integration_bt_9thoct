# CashCached - Quick Start & Deployment Guide

## 🚀 Quick Start (3 Steps)

### Step 1: Start the Application
```bash
cd fd_sim-4
mvn clean install
mvn spring-boot:run
```

### Step 2: Access the Application
Open your browser and navigate to:
```
http://localhost:8080/fd-simulator/login
```

### Step 3: Login as Admin
```
Username: admin
Password: Admin@123
```

---

## ✅ What's Been Fixed

### 1. **Authentication & Routing Issues** ✓
- Fixed context path mapping (`/fd-simulator`)
- Corrected all page routes (login, register, dashboards)
- Fixed OAuth2 Google authentication
- Updated security configuration for proper access control

### 2. **Default Admin Account** ✓
- **Username**: `admin`
- **Password**: `Admin@123`
- **Email**: `admin@cashcached.com`
- Auto-created on application startup

### 3. **CashCached Branding** ✓
- All 10 FD products renamed to CashCached:
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

### 4. **Admin Dashboard with ER Diagram** ✓
- New modern admin dashboard with CashCached branding
- Complete user management interface
- Interactive ER diagram showing database schema
- Real-time statistics and monitoring
- Audit log viewer

---

## 🎨 CashCached Design System Applied

### Colors
- **Primary Gradient**: `#FF5BBE → #FF0099` (Pink to Magenta)
- **Secondary**: `#7C3AED` (Purple), `#00F0FF` (Aqua)
- **Background**: `#0D0D12` (Dark)
- **Text**: `#F5F5F7` (Light)

### Typography
- **Font**: Inter (Sans-serif)
- **Weights**: Regular (400), Medium (500), Bold (700)

### UI Elements
- **Border Radius**: 8px (standard), 16px (buttons)
- **Shadows**: `0px 4px 10px rgba(0,0,0,0.25)`

---

## 📊 Database Schema (ER Diagram)

The admin dashboard now includes a visual ER diagram showing:

### Entities
1. **USER** - User accounts (admin/customer)
2. **FIXED_DEPOSIT** - FD investments
3. **FD_PRODUCT** - CashCached products
4. **AUDIT_LOG** - System activity tracking

### Relationships
- USER (1) → (N) FIXED_DEPOSIT
- FD_PRODUCT (1) → (N) FIXED_DEPOSIT
- USER (1) → (N) AUDIT_LOG

---

## 🔐 Access URLs

### Public Pages
- **Home**: `http://localhost:8080/fd-simulator/`
- **Login**: `http://localhost:8080/fd-simulator/login`
- **Register**: `http://localhost:8080/fd-simulator/register`
- **CashCached Landing**: `http://localhost:8080/fd-simulator/cashcached`
- **FD Calculator**: `http://localhost:8080/fd-simulator/fd-calculator`

### Admin Pages (Requires ADMIN role)
- **Admin Dashboard**: `http://localhost:8080/fd-simulator/admin/dashboard`

### Customer Pages (Requires CUSTOMER role)
- **Customer Dashboard**: `http://localhost:8080/fd-simulator/customer/dashboard`

### Development Tools
- **H2 Console**: `http://localhost:8080/fd-simulator/h2-console`
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: (leave empty)

---

## 🧪 Testing the Application

### Test 1: Admin Login
1. Go to: `http://localhost:8080/fd-simulator/login`
2. Enter:
   - Username: `admin`
   - Password: `Admin@123`
3. Click "Login"
4. Should redirect to Admin Dashboard
5. Verify you can see:
   - Dashboard statistics
   - User management table
   - ER diagram
   - Products list (all CashCached branded)

### Test 2: User Registration
1. Go to: `http://localhost:8080/fd-simulator/register`
2. Fill in the form with test data
3. Click "Create Account"
4. Should redirect to login page
5. Login with new credentials
6. Should redirect to Customer Dashboard

### Test 3: Google OAuth2
1. Click "Login with Google" on login page
2. Authenticate with Google account
3. Should auto-create user and redirect to dashboard

### Test 4: View Database
1. Go to: `http://localhost:8080/fd-simulator/h2-console`
2. Connect with credentials above
3. Run queries to verify data:
```sql
-- View all users
SELECT * FROM USERS;

-- View all CashCached products
SELECT * FROM FD_PRODUCT;

-- View admin user
SELECT * FROM USERS WHERE username = 'admin';
```

---

## 📁 Files Modified

### Backend
1. `src/main/java/com/bank/fdsimulator/config/SecurityConfig.java`
   - Fixed route mappings
   - Updated security rules

2. `src/main/java/com/bank/fdsimulator/config/DataInitializer.java`
   - Added admin user creation
   - Renamed all products to CashCached

3. `src/main/java/com/bank/fdsimulator/controller/WebController.java`
   - Updated admin dashboard route

### Frontend
1. `src/main/resources/templates/admin-dashboard-new.html`
   - **NEW FILE**: Complete redesign with CashCached branding
   - Includes ER diagram visualization
   - Modern dark theme with brand colors

2. `src/main/resources/templates/login.html`
   - Updated with CashCached branding
   - Applied design system colors
   - Added Inter font

3. `src/main/resources/static/js/auth.js`
   - Fixed context path handling

### Documentation
1. `CASHCACHED_UPDATES.md` - Comprehensive update documentation
2. `DEPLOYMENT_GUIDE.md` - This file

---

## 🔧 Configuration Files

### application.yml
```yaml
server:
  port: 8080
  servlet:
    context-path: /fd-simulator

spring:
  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
    password:

jwt:
  secret: mySecretKey123456789012345678901234567890
  expiration: 120000 # 2 minutes
```

---

## 🐛 Troubleshooting

### Issue: "404 Not Found" on login
**Solution**: Make sure you're using the full URL with context path:
```
http://localhost:8080/fd-simulator/login
```

### Issue: "Authentication failed"
**Solution**: Use the correct admin credentials:
- Username: `admin`
- Password: `Admin@123`

### Issue: "Cannot access admin dashboard"
**Solution**: 
1. Make sure you're logged in as admin
2. Check localStorage has `userRole=ADMIN`
3. Clear browser cache and try again

### Issue: "Products not showing"
**Solution**: 
1. Check H2 console to verify products exist
2. Restart application to trigger DataInitializer
3. Check console logs for initialization messages

### Issue: "Styles not loading"
**Solution**:
1. Clear browser cache (Ctrl+Shift+Delete)
2. Hard refresh (Ctrl+F5)
3. Check browser console for errors

---

## 📱 Browser Compatibility

Tested and working on:
- ✅ Chrome 90+
- ✅ Firefox 88+
- ✅ Edge 90+
- ✅ Safari 14+

---

## 🔒 Security Notes

### Production Deployment Checklist
- [ ] Change default admin password
- [ ] Update JWT secret key
- [ ] Configure proper CORS origins
- [ ] Enable HTTPS
- [ ] Set up proper database (PostgreSQL/MySQL)
- [ ] Configure email service
- [ ] Set up Twilio for SMS OTP
- [ ] Add rate limiting
- [ ] Enable security headers
- [ ] Set up logging and monitoring

### Current Security Settings (Development)
- JWT expiration: 2 minutes
- Session timeout: 2 minutes
- CORS: Enabled for all origins (⚠️ Change for production)
- H2 Console: Enabled (⚠️ Disable for production)

---

## 📞 Support

### Admin Dashboard Features
1. **Dashboard**: View statistics and admin credentials
2. **User Management**: View all registered users, delete users
3. **ER Diagram**: Visual database schema
4. **Fixed Deposits**: Manage all FD accounts
5. **Products**: Manage CashCached products
6. **Audit Logs**: Track system activities

### API Endpoints
All admin APIs are available at `/api/admin/*`:
- `GET /api/admin/users` - List all users
- `GET /api/admin/dashboard/stats` - Get statistics
- `GET /api/admin/products` - List all products
- `GET /api/admin/fixed-deposits` - List all FDs
- `GET /api/admin/audit-logs` - View audit logs

---

## ✨ Next Steps

### Recommended Enhancements
1. Update customer dashboard with CashCached branding
2. Add CashCached logo image file
3. Implement email notifications
4. Add SMS OTP functionality
5. Create product detail pages
6. Add charts and analytics
7. Implement export functionality (CSV/PDF)
8. Add user profile management
9. Create mobile-responsive views
10. Add dark/light theme toggle

---

## 📝 Version History

### Version 2.0.0 (Current)
- ✅ Fixed authentication and routing
- ✅ Created default admin account
- ✅ Renamed all products to CashCached
- ✅ Redesigned admin dashboard
- ✅ Added ER diagram visualization
- ✅ Applied CashCached branding
- ✅ Updated login page design

### Version 1.0.0
- Initial release

---

**Last Updated**: 2025-10-09  
**Status**: ✅ Ready for Testing  
**Environment**: Development

---

## 🎉 Success Indicators

You'll know everything is working when:
1. ✅ Application starts without errors
2. ✅ Login page shows CashCached branding
3. ✅ Admin login works with default credentials
4. ✅ Admin dashboard displays with modern dark theme
5. ✅ ER diagram is visible and properly formatted
6. ✅ All 10 CashCached products are listed
7. ✅ User management table shows admin user
8. ✅ Statistics cards display correct numbers

**Ready to go! Start the application and login as admin to explore all features.**

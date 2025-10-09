# 🎉 CashCached - Fixed Deposit Bank Simulator

A comprehensive Spring Boot application for simulating fixed deposit operations with modern CashCached branding, multi-language support (English/Japanese), and multi-currency support (USD, INR, Kuwaiti Dinar).

## 🆕 Latest Updates (v2.1.0)

### ✅ All Issues Fixed
- **Authentication & Routing**: Fixed 400 errors, login now works perfectly
- **CashCached Branding**: All 10 products renamed with CashCached prefix
- **New Admin Dashboard**: Modern dark theme with ER diagram visualization
- **JWT Authentication**: Properly separated API and web page authentication
- **Security**: Fixed CSRF, session management, and authorization
- **Documentation**: 8 comprehensive guide files added

### 🎨 CashCached Design System
- **Colors**: Pink to Magenta gradient (#FF5BBE → #FF0099)
- **Secondary**: Purple (#7C3AED), Aqua (#00F0FF)
- **Typography**: Inter font family
- **Theme**: Modern dark theme (#0D0D12 background)

## Features

### Core Features
- **User Management**: Separate admin and customer access with role-based authentication
- **Fixed Deposit Management**: Create, view, and manage fixed deposits
- **Automatic Signout**: 2-minute inactivity timeout
- **Audit Logging**: Comprehensive audit trail for all operations
- **Multi-Language Support**: English and Japanese
- **Multi-Currency Support**: USD (2 decimals), INR (2 decimals), Kuwaiti Dinar (3 decimals)

### Authentication & Security
- **Multiple Login Methods**:
  - Username/Password login
  - Phone number with OTP verification
  - Google OAuth2 login
- **JWT Token Authentication**
- **Session Management** with automatic timeout
- **Role-based Access Control** (Admin/Customer)

### Admin Features
- View all users and customers
- Manage all fixed deposits
- View comprehensive audit logs
- Dashboard with statistics
- User management (create, update, delete)

### Customer Features
- Create and manage personal fixed deposits
- FD Calculator for interest calculations
- View personal dashboard with statistics
- Language and currency preferences
- Personal audit log viewing

## Technology Stack

- **Backend**: Spring Boot 3.2.0, Java 17
- **Database**: H2 (in-memory), MySQL support
- **Security**: Spring Security, JWT, OAuth2
- **Frontend**: Thymeleaf, Bootstrap 5, JavaScript
- **SMS**: Twilio integration
- **Email**: Spring Mail
- **Build Tool**: Maven

## Project Structure

```
fd-sim-1/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/bank/fdsimulator/
│   │   │       ├── config/                 # Configuration classes
│   │   │       │   ├── SecurityConfig.java
│   │   │       │   └── WebConfig.java
│   │   │       ├── controller/             # REST and Web controllers
│   │   │       │   ├── AdminController.java
│   │   │       │   ├── AuthController.java
│   │   │       │   ├── CustomerController.java
│   │   │       │   └── WebController.java
│   │   │       ├── dto/                    # Data Transfer Objects
│   │   │       │   ├── FdCreateRequest.java
│   │   │       │   ├── LoginRequest.java
│   │   │       │   ├── LoginResponse.java
│   │   │       │   ├── OtpRequest.java
│   │   │       │   └── RegisterRequest.java
│   │   │       ├── entity/                 # JPA Entities
│   │   │       │   ├── AuditLog.java
│   │   │       │   ├── Currency.java
│   │   │       │   ├── FixedDeposit.java
│   │   │       │   ├── FdStatus.java
│   │   │       │   ├── Language.java
│   │   │       │   ├── OtpVerification.java
│   │   │       │   ├── Role.java
│   │   │       │   └── User.java
│   │   │       ├── repository/             # Data Access Layer
│   │   │       │   ├── AuditLogRepository.java
│   │   │       │   ├── FixedDepositRepository.java
│   │   │       │   ├── OtpVerificationRepository.java
│   │   │       │   └── UserRepository.java
│   │   │       ├── security/               # Security components
│   │   │       │   ├── JwtAuthenticationEntryPoint.java
│   │   │       │   ├── JwtAuthenticationFilter.java
│   │   │       │   ├── JwtUtil.java
│   │   │       │   └── OAuth2LoginSuccessHandler.java
│   │   │       ├── service/                # Business Logic Layer
│   │   │       │   ├── AuditService.java
│   │   │       │   ├── CurrencyService.java
│   │   │       │   ├── FixedDepositService.java
│   │   │       │   ├── OtpService.java
│   │   │       │   └── UserService.java
│   │   │       └── FdSimulatorApplication.java
│   │   └── resources/
│   │       ├── messages.properties         # English messages
│   │       ├── messages_ja.properties     # Japanese messages
│   │       ├── application.yml            # Application configuration
│   │       └── templates/                 # Thymeleaf templates
│   │           ├── admin-dashboard.html
│   │           ├── customer-dashboard.html
│   │           ├── login.html
│   │           └── register.html
├── pom.xml                                # Maven dependencies
└── README.md
```

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- IDE (IntelliJ IDEA, Eclipse, or VS Code)
- Twilio account (for SMS functionality)
- Google Cloud Console project (for OAuth2)

## Setup Instructions

### 1. Clone and Setup Project

```bash
# Clone the repository
git clone <repository-url>
cd fd-sim-1

# The project is already configured with all necessary dependencies
```

### 2. Configure Environment Variables

Create a `.env` file or set environment variables:

```bash
# JWT Configuration
JWT_SECRET=mySecretKey123456789012345678901234567890

# Twilio Configuration (for SMS)
TWILIO_ACCOUNT_SID=your-twilio-account-sid
TWILIO_AUTH_TOKEN=your-twilio-auth-token
TWILIO_PHONE_NUMBER=your-twilio-phone-number

# Google OAuth2 Configuration
GOOGLE_CLIENT_ID=your-google-client-id
GOOGLE_CLIENT_SECRET=your-google-client-secret

# Email Configuration (optional)
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-app-password
```

### 3. Database Configuration

The application uses H2 in-memory database by default. To use MySQL:

1. Update `application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/fd_simulator
    username: your-username
    password: your-password
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
```

2. Add MySQL dependency to `pom.xml` (already included)

### 4. Twilio Setup (for SMS)

1. Create a Twilio account at https://www.twilio.com
2. Get your Account SID and Auth Token
3. Purchase a phone number
4. Set the environment variables

### 5. Google OAuth2 Setup

1. Go to Google Cloud Console
2. Create a new project or select existing
3. Enable Google+ API
4. Create OAuth2 credentials
5. Set authorized redirect URIs: `http://localhost:8080/fd-simulator/login/oauth2/code/google`
6. Set the environment variables

## Running the Application

### Using Maven

```bash
# Run the application
mvn spring-boot:run

# Or build and run
mvn clean package
java -jar target/fd-simulator-1.0.0.jar
```

### Using IDE

1. **IntelliJ IDEA**:
   - Open the project
   - Right-click on `FdSimulatorApplication.java`
   - Select "Run 'FdSimulatorApplication'"

2. **Eclipse**:
   - Import as Maven project
   - Right-click on project → Run As → Spring Boot App

3. **VS Code**:
   - Install Spring Boot Extension Pack
   - Open the project
   - Use Command Palette: "Spring Boot: Run"

## Accessing the Application

- **Application URL**: http://localhost:8080/fd-simulator
- **H2 Console**: http://localhost:8080/fd-simulator/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: `password`

## Default Users

The application creates a default admin user on startup:

- **Admin User**:
  - Username: `admin`
  - Password: `Admin@123`
  - Email: `admin@cashcached.com`
  - Role: ADMIN

**Note**: Customer users can be created through the registration page.

## API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - Username/password login
- `POST /api/auth/send-otp` - Send OTP to phone
- `POST /api/auth/verify-otp` - Verify OTP login
- `POST /api/auth/logout` - Logout

### Customer Endpoints
- `GET /api/customer/fixed-deposits` - Get user's FDs
- `POST /api/customer/fixed-deposits` - Create new FD
- `POST /api/customer/calculate` - Calculate FD returns
- `GET /api/customer/dashboard/stats` - Dashboard statistics

### Admin Endpoints
- `GET /api/admin/users` - Get all users
- `GET /api/admin/fixed-deposits` - Get all FDs
- `GET /api/admin/audit-logs` - Get audit logs
- `GET /api/admin/dashboard/stats` - Admin statistics

## Multi-Language Support

The application supports English and Japanese:

- Language selection available on login/register pages
- User preferences stored in database
- All UI elements translated
- Messages loaded from `messages.properties` and `messages_ja.properties`

## Multi-Currency Support

Supported currencies:
- **USD (US Dollar)**: 2 decimal places
- **INR (Indian Rupee)**: 2 decimal places  
- **KWD (Kuwaiti Dinar)**: 3 decimal places

Features:
- Currency conversion service
- Proper decimal formatting
- User currency preferences
- Real-time currency selection

## Security Features

- JWT token authentication
- 2-minute session timeout
- Role-based access control
- Password encryption (BCrypt)
- OAuth2 integration
- CSRF protection
- CORS configuration

## Audit Logging

Comprehensive audit trail includes:
- User actions (login, logout, create, update, delete)
- Entity changes with old/new values
- IP address and user agent tracking
- Timestamp for all operations
- Admin and customer audit views

## Development Notes

### Adding New Languages

1. Create new message file: `messages_[lang].properties`
2. Add language enum to `Language.java`
3. Update UI templates with language selector
4. Add language support in controllers

### Adding New Currencies

1. Add currency to `Currency.java` enum
2. Update `CurrencyService.java` with conversion rates
3. Add currency to UI selectors
4. Update validation rules

### Customizing UI

- Templates are in `src/main/resources/templates/`
- CSS and JavaScript embedded in templates
- Bootstrap 5 for responsive design
- Font Awesome for icons

## Troubleshooting

### Common Issues

1. **Port 8080 already in use**:
   ```bash
   # Change port in application.yml
   server:
     port: 8081
   ```

2. **Database connection issues**:
   - Check H2 console access
   - Verify MySQL credentials
   - Check database URL format

3. **SMS not working**:
   - Verify Twilio credentials
   - Check phone number format
   - Ensure sufficient Twilio balance

4. **Google OAuth not working**:
   - Verify client ID and secret
   - Check redirect URI configuration
   - Ensure Google+ API is enabled

### Logs

Application logs are available in console. For file logging, add to `application.yml`:

```yaml
logging:
  file:
    name: logs/fd-simulator.log
  level:
    com.bank.fdsimulator: DEBUG
```

## Contributing

1. Fork the repository
2. Create feature branch
3. Make changes
4. Add tests
5. Submit pull request

## License

This project is licensed under the MIT License.

## 📚 Documentation

Comprehensive documentation available:
- **LOGIN_FIX_GUIDE.md** - Authentication fixes explained
- **POSTMAN_TESTING_GUIDE.md** - Complete API testing (20 tests)
- **QUICK_POSTMAN_CHECKLIST.md** - Quick 10-minute test guide
- **FINAL_STATUS.md** - Complete status report
- **DEPLOYMENT_GUIDE.md** - Deployment instructions
- **CASHCACHED_UPDATES.md** - Technical documentation
- **ADMIN_CREDENTIALS.txt** - Admin access details
- **README_UPDATES.md** - Summary of all changes

## 🎯 Quick Start

```bash
# 1. Start application
mvn spring-boot:run

# 2. Open browser
http://localhost:8080/fd-simulator/login

# 3. Login as admin
Username: admin
Password: Admin@123

# 4. Explore features
- Modern CashCached dashboard
- ER diagram visualization
- User management
- 10 CashCached products
- Audit logging
```

## 🏦 CashCached Products

All 10 FD products with CashCached branding:
1. CashCached Short Term
2. CashCached Regular
3. CashCached Senior
4. CashCached Tax Saver
5. CashCached Flexi
6. CashCached Premium
7. CashCached Monthly Income
8. CashCached Youth
9. CashCached Corporate
10. CashCached Cumulative

## Support

For support and questions:
- Create an issue in the repository
- Check the troubleshooting section
- Review the comprehensive documentation files
- Check FINAL_STATUS.md for latest updates

---

**Version**: 2.1.0  
**Status**: Production Ready (Development Environment)  
**Last Updated**: 2025-10-09

**Note**: This is a simulator application for educational purposes. For production use, additional security measures, error handling, and testing would be required.

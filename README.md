<div align="center">

<img src="https://img.shields.io/badge/AutoLix-Marketplace%20Auto-blue?style=for-the-badge&logo=car&logoColor=white" alt="AutoLix Banner"/>

# 🚗 AutoLix — Premium Car Marketplace

### *Elevate Your Driving Legacy*

> Beyond transportation lies an unparalleled experience.

[![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=flat-square&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-Template%20Engine-005F0F?style=flat-square&logo=thymeleaf&logoColor=white)](https://www.thymeleaf.org)
[![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?style=flat-square&logo=mysql&logoColor=white)](https://www.mysql.com)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg?style=flat-square)](https://www.gnu.org/licenses/gpl-3.0)

---

[📖 About](#-about) • [✨ Features](#-features) • [🖼️ Screenshots](#%EF%B8%8F-screenshots) • [🏗️ Architecture](#%EF%B8%8F-architecture) • [🚀 Getting Started](#-getting-started) • [📁 Project Structure](#-project-structure) • [🔐 Security](#-security) • [📜 License](#-license)

</div>

---

## 📖 About

**AutoLix** is a full-stack, verified car marketplace web application built with **Spring Boot** and **Thymeleaf**. It allows users to browse, list, compare, and evaluate vehicles in a sleek, modern dark-themed UI. AutoLix combines the power of a relational database with intelligent features like AI-driven price estimation, a head-to-head comparison engine, and real-time analytics — all in one platform.

Whether you're a buyer hunting your next dream ride or a seller looking to get the best value, AutoLix provides tools that go far beyond a typical classifieds site.

---

## ✨ Features

### 🏠 Homepage & Discovery
- Dynamic hero banner with curated vehicle spotlights
- **Today's Offers** — rotating carousel of freshest listings
- **Vehicle Types** browser — filter by Sedan, SUV, Hatchback, Coupe, and more
- Smart search bar with multi-parameter filtering (brand, model, price range, mileage, fuel, color, power, year)

### 🛒 Marketplace
- Paginated, filterable vehicle grid with card previews
- Full listing detail page with image gallery, technical specs table, seller info, and price badge
- Price quality indicator (e.g., "Preț foarte bun")
- Star rating system per listing with average score display
- One-click **Favorites** toggle directly from listing cards

### 💰 Smart Price Estimator
- Modal-based estimator tool powered by an intelligent pricing algorithm
- Inputs: Brand, Model, Body Type, Category, Year, KM, Horsepower, Engine Displacement
- Outputs a realistic **price range** in seconds
- Inline listing preview with a direct **"Publică Anunțul Acum"** CTA

### ⚔️ AutoLix Versus Engine
- Side-by-side comparison of any two vehicles from your Favorites
- Automatic pro/con analysis per vehicle (mileage, year, power, price)
- **AutoLix Verdict** — AI-curated recommendation based on technical metrics
- Reset arena to pick new contenders

### ❤️ My Favorites
- Persistent favorites list per user account
- Quick access to saved listings with thumbnail, key specs, and price
- Direct links to full listing or one-click removal

### 🗄️ Cars Database (Admin)
- Full CRUD interface for the vehicle inventory
- Advanced filtering by brand, color, and fuel type
- Paginated data table with all technical fields
- Inline Edit and Delete actions per record

### 📊 Analytics Dashboard
- Platform-level statistics and insights (via `AnalyticsController`)
- Tracks views, interactions, and listing performance

### 👤 User Management
- Secure login / signup with session handling
- Profile editing (username, avatar, contact info)
- Role-based access: standard users vs. admin panel

---

## 🖼️ Screenshots

| Page | Preview |
|------|---------|
| 🔐 **Login** | Clean auth screen with branded background |
| 🏠 **Homepage** | Hero + search + Today's Offers carousel |
| 🛒 **Marketplace** | Full-featured vehicle grid with filters |
| 📋 **Listing Detail** | Gallery + specs + rating + seller card |
| 💰 **Price Estimator** | Smart modal with live range preview |
| ⚔️ **Versus Engine** | 1v1 comparison with verdict |
| ❤️ **Favorites** | Saved listings dashboard |
| 🗄️ **Cars Database** | Admin data management table |

> Screenshots are available in the `/docs/screenshots/` directory or visible in the project wiki.

---

## 🏗️ Architecture

AutoLix follows the standard **MVC (Model-View-Controller)** pattern implemented via Spring Boot:

```
Client Browser
      │
      ▼
┌─────────────────────────────┐
│     Spring Boot Application  │
│                             │
│  Controllers (REST + MVC)   │
│  ┌───────────────────────┐  │
│  │ AuthController        │  │
│  │ HomepageController    │  │
│  │ MasinaController      │  │
│  │ FavoriteController    │  │
│  │ EstimateController    │  │
│  │ RatingController      │  │
│  │ AdminController       │  │
│  │ AnalyticsController   │  │
│  └───────────────────────┘  │
│           │                  │
│  Services & Repositories     │
│  ┌───────────────────────┐  │
│  │ FavoriteService       │  │
│  │ FileStorageService    │  │
│  │ MasinaRepository      │  │
│  │ UtilizatorRepository  │  │
│  │ RatingRepository      │  │
│  └───────────────────────┘  │
│           │                  │
│  Entities (JPA)              │
│  ┌───────────────────────┐  │
│  │ Masina               │  │
│  │ Utilizator           │  │
│  │ Favorite             │  │
│  │ Rating               │  │
│  └───────────────────────┘  │
└─────────────────────────────┘
      │
      ▼
  MySQL Database
```

### Tech Stack

| Layer | Technology |
|-------|-----------|
| **Backend** | Java 17+, Spring Boot 3.x |
| **ORM** | Spring Data JPA / Hibernate |
| **Templating** | Thymeleaf |
| **Security** | Spring Security |
| **Database** | MySQL |
| **Build Tool** | Maven |
| **Frontend** | HTML5, CSS3, JavaScript |
| **File Storage** | Local filesystem (`/uploads`) |

---

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.8+
- MySQL 8.0+
- Git

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/autolix.git
cd autolix
```

### 2. Configure the Database

Create a MySQL database and update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/autolix_db
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

### 3. Seed Initial Data *(Optional)*

An initial dataset is available in:

```
src/main/resources/data.sql
```

Spring Boot will auto-execute it on startup if `spring.sql.init.mode=always` is set.

### 4. Build & Run

```bash
mvn clean install
mvn spring-boot:run
```

The application will start at:

```
http://localhost:8080
```

### 5. Default Admin Credentials

> ⚠️ Change these immediately after first login.

```
Username: admin
Password: admin123
```

---

## 📁 Project Structure

```
autolix/
├── src/
│   └── main/
│       ├── java/com/example/demo/
│       │   ├── config/
│       │   │   └── WebConfig.java
│       │   ├── controller/
│       │   │   ├── AdminController.java
│       │   │   ├── AnalyticsController.java
│       │   │   ├── AuthController.java
│       │   │   ├── EstimateController.java
│       │   │   ├── FavoriteController.java
│       │   │   ├── HomepageController.java
│       │   │   ├── MasinaController.java
│       │   │   └── RatingController.java
│       │   ├── dto/
│       │   │   ├── EditProfileForm.java
│       │   │   └── SignUpForm.java
│       │   ├── entity/
│       │   │   ├── Favorite.java
│       │   │   ├── Masina.java
│       │   │   ├── Rating.java
│       │   │   └── Utilizator.java
│       │   ├── repository/
│       │   │   ├── FavoriteRepository.java
│       │   │   ├── MasinaRepository.java
│       │   │   ├── RatingRepository.java
│       │   │   └── UtilizatorRepository.java
│       │   ├── security/
│       │   │   └── (Spring Security config)
│       │   └── service/
│       │       ├── DataInitializer.java
│       │       ├── FavoriteService.java
│       │       └── FileStorageService.java
│       └── resources/
│           ├── static/
│           │   ├── images/
│           │   │   ├── brands/
│           │   │   ├── css/
│           │   │   ├── types/
│           │   │   └── uploads/
│           ├── templates/
│           │   ├── fragments/
│           │   ├── homepage.html
│           │   ├── marketplace.html
│           │   ├── listing.html
│           │   ├── favorites.html
│           │   ├── estimator.html
│           │   ├── masini.html
│           │   ├── analytics.html
│           │   ├── login.html
│           │   ├── signup.html
│           │   └── ...
│           ├── application.properties
│           └── data.sql
├── uploads/
├── pom.xml
└── README.md
```

---

## 🔐 Security

AutoLix uses **Spring Security** for authentication and authorization:

- Password hashing via **BCrypt**
- Session-based authentication
- Role-based route protection (`ROLE_USER`, `ROLE_ADMIN`)
- CSRF protection enabled by default
- Admin-only access to `/admin/**` and `/masini/**` routes
- Unauthorized access redirects to a custom `access-denied.html` page

---

## 🌐 Key Routes

| Route | Description | Access |
|-------|-------------|--------|
| `GET /` | Homepage | Public |
| `GET /marketplace` | Browse all listings | Public |
| `GET /listing/{id}` | Vehicle detail page | Public |
| `GET /favorites` | Saved favorites | Authenticated |
| `GET /estimator` | Smart Price Estimator | Authenticated |
| `GET /versus` | Versus Engine | Authenticated |
| `GET /masini` | Cars Database | Admin |
| `GET /admin` | Admin panel | Admin |
| `GET /analytics` | Platform analytics | Admin |
| `GET /login` | Login page | Public |
| `GET /signup` | Registration page | Public |

---

## 📦 Dependencies *(pom.xml highlights)*

```xml
<!-- Spring Boot Starter Web -->
<dependency>spring-boot-starter-web</dependency>

<!-- Spring Boot Starter Security -->
<dependency>spring-boot-starter-security</dependency>

<!-- Spring Boot Starter Data JPA -->
<dependency>spring-boot-starter-data-jpa</dependency>

<!-- Thymeleaf + Spring Security dialect -->
<dependency>spring-boot-starter-thymeleaf</dependency>
<dependency>thymeleaf-extras-springsecurity6</dependency>

<!-- MySQL Connector -->
<dependency>mysql-connector-j</dependency>

<!-- Validation -->
<dependency>spring-boot-starter-validation</dependency>
```

---

## 🛠️ Development Notes

- Vehicle images are stored locally under `/uploads` and served via `FileStorageService`
- The `DataInitializer` bean seeds the database with demo listings on first run
- All templates use a shared **fragment** system (header, navbar, footer) for DRY layouts
- The price estimator uses a custom algorithm based on market depreciation curves

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit your changes: `git commit -m 'Add: your feature description'`
4. Push to the branch: `git push origin feature/your-feature`
5. Open a Pull Request

Please ensure your code follows the existing conventions and includes appropriate comments.

---

## 📜 License

```
AutoLix © 2006–2016
This is free software, licensed under GPLv3 or higher.
You are welcome to redistribute it under certain conditions.
AutoLix comes with ABSOLUTELY NO WARRANTY.
```

See [LICENSE](LICENSE) for the full license text.

---

<div align="center">

Made with ❤️ and ☕ by the AutoLix Team

*"Beyond transportation lies an unparalleled experience."*

</div>

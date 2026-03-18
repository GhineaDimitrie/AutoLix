<div align="center">

<br/>

```
 █████╗ ██╗   ██╗████████╗ ██████╗ ██╗     ██╗██╗  ██╗
██╔══██╗██║   ██║╚══██╔══╝██╔═══██╗██║     ██║╚██╗██╔╝
███████║██║   ██║   ██║   ██║   ██║██║     ██║ ╚███╔╝ 
██╔══██║██║   ██║   ██║   ██║   ██║██║     ██║ ██╔██╗ 
██║  ██║╚██████╔╝   ██║   ╚██████╔╝███████╗██║██╔╝ ██╗
╚═╝  ╚═╝ ╚═════╝    ╚═╝    ╚═════╝ ╚══════╝╚═╝╚═╝  ╚═╝
        M A R K E T P L A C E   A U T O   •   V E R I F I E D   L I S T I N G S
```

<br/>

**A full-stack verified car marketplace — browse, list, compare & evaluate vehicles with confidence.**

<br/>

[![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=flat-square&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-Template%20Engine-005F0F?style=flat-square&logo=thymeleaf&logoColor=white)](https://www.thymeleaf.org)
[![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?style=flat-square&logo=mysql&logoColor=white)](https://www.mysql.com)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-Auth-6DB33F?style=flat-square&logo=springsecurity&logoColor=white)](https://spring.io/projects/spring-security)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue?style=flat-square)](https://www.gnu.org/licenses/gpl-3.0)

<br/>

[🚀 Quick Start](#-quick-start) · [✨ Features](#-features) · [🛠️ Tech Stack](#%EF%B8%8F-tech-stack) · [📂 Structure](#-project-structure) · [🔐 Security](#-security) · [🌐 Routes](#-key-routes)

<br/>

---

</div>

## 🚗 What is AutoLix?

**AutoLix** is a full-stack car marketplace web application built with **Spring Boot** and **Thymeleaf**. It enables users to browse verified vehicle listings, post their own ads with AI-assisted price estimation, compare cars head-to-head, and track favorites — all within a sleek dark-themed UI.

Whether you're a buyer hunting your next dream ride or a seller looking to get the best value, AutoLix provides tools that go far beyond a typical classifieds site.

---

## ✨ Features

### 🏠 Homepage & Discovery
- Dynamic hero banner with curated vehicle spotlights.
- **Today's Offers** — rotating carousel of the freshest listings.
- **Vehicle Types** browser — filter by Sedan, SUV, Hatchback, Coupe, and more.
- Smart search bar with multi-parameter filtering: brand, model, price range, mileage, fuel, color, power, year.

---

### 🛒 Marketplace
- Paginated, filterable vehicle grid with full card previews.
- Listing detail page with image gallery, technical specs table, seller info, and price badge.
- Price quality indicator *(e.g., "Preț foarte bun")*.
- Star rating system per listing with average score display.
- One-click **Favorites** toggle directly from listing cards.

---

### 💰 Smart Price Estimator
Enter your car's details and get a realistic market price range in under a minute.

```
Brand: BMW        Model: E90
Body:  Sedan      Category: Sport
Year:  2011       KM: 320.000
Power: 150 CP     Engine: 1995 ccm
```

```
💡 Estimated Price Range
────────────────────────────
  5.200 € — 6.400 €
────────────────────────────
  ✅ Ready to publish your listing
```

---

### ⚔️ AutoLix Versus Engine
- Side-by-side comparison of any two vehicles from your Favorites.
- Automatic **pro/con breakdown** per car: mileage, year, power, price.
- **AutoLix Verdict** — smart recommendation based on technical metrics.
- Reset the arena and pick new contenders anytime.

---

### ❤️ My Favorites
- Persistent favorites list tied to your account.
- Saved listings with thumbnail, key specs, and price at a glance.
- Direct links to the full listing or one-click removal.

---

### 🗄️ Cars Database *(Admin)*
- Full **CRUD interface** for the vehicle inventory.
- Filter by brand, color, and fuel type.
- Paginated data table with all technical fields.
- Inline **Edit** and **Delete** actions per record.

---

### 📊 Analytics Dashboard *(Admin)*
- Platform-level statistics and listing performance insights.
- Tracks views, interactions, and marketplace activity over time.

---

## 🛠️ Tech Stack

| Layer | Technology | Purpose |
|-------|-----------|---------|
| **Backend** | Java 17+, Spring Boot 3.x | Core application logic & REST/MVC controllers |
| **Templating** | Thymeleaf | Server-side HTML rendering |
| **ORM** | Spring Data JPA / Hibernate | Database entities & queries |
| **Security** | Spring Security + BCrypt | Auth, roles, CSRF protection |
| **Database** | MySQL 8.0+ | Relational data storage |
| **Build** | Maven | Dependency management & packaging |
| **Frontend** | HTML5, CSS3, JavaScript | UI layout, animations, interactivity |
| **File Storage** | Local filesystem (`/uploads`) | Vehicle image management |

---

## 🚀 Quick Start

### Prerequisites

- Java 17+
- Maven 3.8+
- MySQL 8.0+
- Git

### 1 · Clone the Repository

```bash
git clone https://github.com/your-username/autolix.git
cd autolix
```

### 2 · Configure the Database

Create a MySQL database, then update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/autolix_db
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

### 3 · Seed Initial Data *(Optional)*

An initial dataset is available at `src/main/resources/data.sql`. To auto-execute on startup, add:

```properties
spring.sql.init.mode=always
```

### 4 · Build & Run

```bash
mvn clean install
mvn spring-boot:run
```

The application starts at:

```
http://localhost:8080
```

> ⚠️ Default admin credentials are set in `DataInitializer.java`. Change them immediately after first login.

---

## 📂 Project Structure

```
autolix/
│
├── src/main/java/com/example/demo/
│   ├── config/
│   │   └── WebConfig.java
│   ├── controller/
│   │   ├── AdminController.java
│   │   ├── AnalyticsController.java
│   │   ├── AuthController.java
│   │   ├── EstimateController.java
│   │   ├── FavoriteController.java
│   │   ├── HomepageController.java
│   │   ├── MasinaController.java
│   │   └── RatingController.java
│   ├── dto/
│   │   ├── EditProfileForm.java
│   │   └── SignUpForm.java
│   ├── entity/
│   │   ├── Favorite.java
│   │   ├── Masina.java
│   │   ├── Rating.java
│   │   └── Utilizator.java
│   ├── repository/
│   │   ├── FavoriteRepository.java
│   │   ├── MasinaRepository.java
│   │   ├── RatingRepository.java
│   │   └── UtilizatorRepository.java
│   ├── security/
│   └── service/
│       ├── DataInitializer.java
│       ├── FavoriteService.java
│       └── FileStorageService.java
│
├── src/main/resources/
│   ├── static/images/
│   │   ├── brands/
│   │   ├── css/
│   │   ├── types/
│   │   └── uploads/
│   ├── templates/
│   │   ├── fragments/
│   │   ├── homepage.html
│   │   ├── marketplace.html
│   │   ├── listing.html
│   │   ├── favorites.html
│   │   ├── estimator.html
│   │   ├── masini.html
│   │   ├── analytics.html
│   │   ├── login.html
│   │   ├── signup.html
│   │   └── ...
│   ├── application.properties
│   └── data.sql
│
├── uploads/
├── pom.xml
└── README.md
```

### Key Files

| File | Description |
|------|-------------|
| `MasinaController.java` | Handles listing CRUD, search, filtering, and detail pages |
| `EstimateController.java` | Powers the Smart Price Estimator modal logic |
| `FavoriteService.java` | Manages per-user favorites: add, remove, list |
| `DataInitializer.java` | Seeds the database with demo listings on first run |
| `app_window.slint` → `app_window.slint` | *N/A — Thymeleaf templates handle UI server-side* |
| `data.sql` | Initial vehicle and user data for demo purposes |

---

## 🔐 Security

AutoLix uses **Spring Security** for authentication and authorization:

- Passwords hashed with **BCrypt**
- Session-based authentication
- Role-based route protection: `ROLE_USER` and `ROLE_ADMIN`
- CSRF protection enabled by default
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
| `POST /masini/add` | Add new listing | Admin |
| `POST /masini/edit/{id}` | Edit listing | Admin |
| `DELETE /masini/{id}` | Delete listing | Admin |
| `GET /login` | Login page | Public |
| `GET /signup` | Registration page | Public |

---

## 🗺️ Roadmap

- [ ] Real-time chat between buyer and seller
- [ ] Advanced analytics with charts & trends
- [ ] Email notifications for saved search alerts
- [ ] Mobile-responsive redesign
- [ ] Vehicle history report integration
- [ ] Multi-image upload with drag & drop reorder

---

## 🤝 Contributing

Contributions, issues and feature requests are welcome!

1. Fork the repository
2. Create your branch: `git checkout -b feature/my-feature`
3. Commit your changes: `git commit -m "feat: add my feature"`
4. Push and open a PR: `git push origin feature/my-feature`

Please follow [Conventional Commits](https://www.conventionalcommits.org/) for commit messages.

---

## 📜 License

```
AutoLix © 2006–2016
This is free software, licensed under GPLv3 or higher.
You are welcome to redistribute it under certain conditions.
AutoLix comes with ABSOLUTELY NO WARRANTY.
```

See [`LICENSE`](LICENSE) for the full text.

---

<div align="center">

<br/>

Built with ☕ Java · 🌿 Spring Boot · 🚗 AutoLix Team

*Marketplace auto • verified listings*

<br/>

**[⭐ Star this repo](https://github.com/your-username/autolix)** if you found it useful!

</div>

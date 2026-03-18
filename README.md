🏎️ AutoLix – Smart Auto Marketplace
AutoLix is a modern Spring Boot web application designed to transform the car buying and selling experience.
It offers advanced analytical tools, side-by-side comparisons, and smart price estimations, all wrapped in a sleek, intuitive Dark Mode interface.

✨ Features
🧠 1. Smart Price Estimator
Enter vehicle details (Make, Model, Year, KM, Power).

Get an instant realistic price range based on our internal logic.

Quick technical preview of the listing before publishing.

⚔️ 2. Versus Engine (Comparison Arena)
Select two cars from your Favorites list for a detailed 1v1 analysis.

Automatically highlights advantages (e.g., "Better Mileage", "Newer Model Year").

Receive an AutoLix Verdict based on technical specs and market value.

📊 3. Full Marketplace & Analytics
Complete vehicle database with advanced filtering by Make, Color, or Fuel type.

Admin dashboard for quick listing management (Edit/Delete).

Integrated Rating System (⭐) for user feedback on every car.

❤️ 4. Personalized Experience
Favorites System to save your dream cars for later.

Secure and customizable user profiles.

Fluid, responsive UI designed for a premium feel.

🛠️ Tech Stack
Java & Spring Boot – Robust backend logic and application security.

Thymeleaf – Dynamic HTML rendering for the frontend.

Spring Data JPA – Efficient database management and ORM.

MySQL – Persistent storage for vehicles, users, and ratings.

Custom CSS – Modern Dark Mode UI built from scratch.

🚀 Installation & Usage
Prerequisites
JDK 17 or newer.

Maven installed.

MySQL Server up and running.

Clone & Run
Bash
# Clone the repository
git clone https://github.com/your-username/autolix.git
cd autolix

# Configure your database in src/main/resources/application.properties
# (Update Username, Password, and MySQL URL)

# Run the application
mvn spring-boot:run
Access the app in your browser at: http://localhost:8080

📂 Project Structure
Plaintext
autolix/
│── src/main/java/com/example/demo/
│   ├── controller/      # App routes (Auth, Admin, Car Logic)
│   ├── entity/          # Data models (User, Car, Rating, Favorite)
│   ├── repository/      # Database interfaces
│   ├── service/         # Business logic (Price Calculation, File Storage)
│── src/main/resources/
│   ├── templates/       # HTML/Thymeleaf view files
│   ├── static/          # CSS, JS, and System Images
│── pom.xml              # Project dependencies (Spring Starters)
🧪 Example Usage
Sign up as a new user.

Browse the marketplace and add cars to your Favorites.

Go to the Versus Engine and compare two cars to see which one offers better value.

Use the Price Estimator to see if your own car is priced correctly for the market.

📜 License
This project is licensed under the MIT License – feel free to use, modify, and distribute it for your own portfolio.

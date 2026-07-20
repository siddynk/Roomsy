**Roomsy** is a full-stack accommodation platform that streamlines the interaction between tenants, PG owners, and administrators through a role-based booking workflow built with Spring Boot.

A full-stack PG (Paying Guest) accommodation finder platform built with Spring Boot. Tenants can browse and book PG visits, owners can list their properties, and admins manage the entire platform from a dedicated dashboard.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Spring Boot 3.2.5, Java 17 |
| ORM | Hibernate / Spring Data JPA |
| View Engine | Thymeleaf 3.1 |
| Database (Dev) | H2 In-Memory |
| Database (Prod) | MySQL |
| Auth | BCrypt + HTTP Session |
| Frontend | Vanilla CSS + JavaScript |
| Build Tool | Maven |
| Hosting | Railway |

---

## Features

### Tenant
- Register and login
- Browse verified PG listings with filters (city, gender, room type, budget)
- View PG details including amenities and owner contact
- Book a visit (future dates only — enforced frontend + backend)
- Track booking status (PENDING / CONFIRMED / REJECTED)
- Leave a review after visit date has passed (star rating + comment)

### Owner
- Register and list PGs for free
- Add photo via URL, amenities, description, pricing
- Manage booking requests — confirm or reject visits
- Edit or delete PG listings
- Toggle listing Active / Inactive

### Admin
- Separate login portal (`/admin/login`)
- Dashboard with platform-wide stats
- Manage all clients — search, filter, view, edit, delete
- Manage all PG listings — view, toggle status, delete
- Manage all bookings — confirm, reject, delete
- No access to Spring Security — session-based admin guard

---

## Project Structure

```
src/main/java/com/roomsy/
├── RoomsyApplication.java
├── config/
│   ├── DataLoader.java          # Seeds sample data on startup
│   └── SessionUtils.java        # Session management helper
├── entity/                      # JPA entities (DB tables)
│   ├── User.java
│   ├── PgListing.java
│   ├── Booking.java
│   ├── Client.java
│   └── Review.java
├── repository/                  # Spring Data JPA interfaces
├── service/                     # Business logic
└── controller/                  # HTTP request handlers
    ├── PublicController.java    # /, /listings, /pg/{id}
    ├── AuthController.java      # /login, /register, /logout
    ├── TenantController.java    # /tenant/**
    ├── OwnerController.java     # /owner/**
    ├── AdminController.java     # /admin/**
    └── ApiController.java       # /api/** (REST endpoints)

src/main/resources/
├── application.properties           # H2 config (default/dev)
├── application-mysql.properties     # MySQL config (prod)
├── templates/                       # Thymeleaf HTML (18 pages)
│   ├── public/
│   ├── auth/
│   ├── tenant/
│   ├── owner/
│   └── admin/
└── static/
    ├── css/roomsy.css               # Shared design system + dark mode
    └── js/theme.js                  # Dark/light toggle
```

---

## How to Run Locally

### Prerequisites
- JDK 17 or higher
- IntelliJ IDEA
- Internet connection (Maven downloads dependencies automatically)

### Steps

1. Clone the repository:
```bash
git clone https://github.com/siddynk/Roomsy.git
cd Roomsy
```

2. Open in IntelliJ IDEA:
   - File → Open → select the `Roomsy` folder
   - IntelliJ detects `pom.xml` → click **Open as Maven Project**
   - Wait for Maven to download all dependencies (bottom progress bar)

3. Run the application:
   - Open `src/main/java/com/roomsy/RoomsyApplication.java`
   - Click the green ▶️ Run button
   - Wait for: `Started RoomsyApplication in X seconds`

4. Open browser:
```
http://localhost:8080
```

### To change the port
In `src/main/resources/application.properties`:
```properties
server.port=9090
```

---

## Default Credentials

| Role | Email | Password |
|---|---|---|
| Admin | admin@roomsy.com | admin123 |
| Tenant (sample) | priya@gmail.com | password123 |
| Owner (sample) | rajan@gmail.com | password123 |

---

## H2 Database Console (Dev Only)

While the app is running, access the database UI at:
```
http://localhost:8080/h2-console
```

Login with:
- **JDBC URL:** `jdbc:h2:mem:roomsydb;DB_CLOSE_DELAY=-1`
- **User Name:** `sa`
- **Password:** *(leave blank)*

Useful queries:
```sql
SELECT * FROM USERS;
SELECT * FROM PG_LISTINGS;
SELECT * FROM BOOKINGS;
SELECT * FROM REVIEWS;
SELECT * FROM CLIENTS;
```

> Note: H2 is in-memory — all data resets on every restart. Only the 7 seeded sample records reload automatically via `DataLoader.java`.

---

## REST API Endpoints

| Method | URL | Description |
|---|---|---|
| GET | /api/pgs | All active PG listings |
| GET | /api/pgs/{id} | Single PG by ID |
| GET | /api/pgs/search?q= | Search PGs by name/location |
| GET | /api/clients | All clients |
| GET | /api/clients/{id} | Single client |
| PUT | /api/clients/{id} | Update client |
| DELETE | /api/clients/{id} | Delete client |
| GET | /api/bookings | All bookings |
| GET | /api/stats | Platform statistics JSON |

---

## Deploying to Railway

### Prerequisites
- GitHub account with the project pushed
- Railway account (free at railway.app)

### Step 1 — Push to GitHub
```bash
git init
git add .
git commit -m "Initial commit"
git remote add origin https://github.com/siddynk/Roomsy.git
git branch -M main
git push -u origin main
```

### Step 2 — Deploy on Railway
1. Go to [railway.app](https://railway.app) → Login with GitHub
2. Click **New Project → Deploy from GitHub repo**
3. Select your `Roomsy` repository
4. Railway auto-detects Spring Boot and starts building

### Step 3 — Add MySQL Database
1. Inside your Railway project → click **+ New → Database → MySQL**
2. Railway creates a MySQL instance automatically

### Step 4 — Set Environment Variables
In your Roomsy service → **Variables** tab → add:
```
SPRING_PROFILES_ACTIVE = mysql
```
Then click **"Trying to connect a database? Add Variable"** to inject MySQL credentials automatically.

### Step 5 — Configure MySQL Properties
Ensure `src/main/resources/application-mysql.properties` contains:
```properties
spring.datasource.url=jdbc:mysql://${MYSQLHOST}:${MYSQLPORT}/${MYSQLDATABASE}
spring.datasource.username=${MYSQLUSER}
spring.datasource.password=${MYSQLPASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

### Step 6 — Push and Deploy
```bash
git add .
git commit -m "Configure MySQL for Railway"
git push
```
Railway detects the push and redeploys automatically. Build takes 2–3 minutes.

Your app will be live at:
```
https://your-app-name.up.railway.app
```

### How Railway Build Works Internally
```
1. Railway detects push via GitHub webhook
2. Clones your repository
3. Detects pom.xml → runs: mvn clean package -DskipTests
4. Packages everything into a fat JAR (includes embedded Tomcat)
5. Runs: java -jar roomsy-0.0.1-SNAPSHOT.jar
6. Spring Boot connects to MySQL via injected environment variables
7. App goes live on Railway's public URL
```

---

## Key Design Decisions

**No Spring Security** — Session-based auth with BCrypt achieves the same security goals with less complexity. Suitable for college project scope.

**H2 for development** — Zero setup for evaluators. Data resets cleanly on every restart giving a predictable demo state.

**Review eligibility by date** — Tenants can review after their visit date passes, not after owner approval. This prevents owners from silencing negative reviews by rejecting bookings retroactively.

**URL-based photos** — No file server needed. Owners paste a direct image URL (Unsplash, imgbb, postimages). Keeps the project self-contained and deployable anywhere.

**Comma-separated amenities** — Avoids a many-to-many join table for this scope. The `getAmenitiesList()` method on the entity splits the string transparently.

**Dual profile config** — `application.properties` for H2 (dev), `application-mysql.properties` for MySQL (prod). Activated via `SPRING_PROFILES_ACTIVE=mysql` on Railway.

---

## Author

**Sid** — B.E. Information Science, Sapthagiri College of Engineering, Bengaluru
- GitHub: [siddynk](https://github.com/siddynk)
- LinkedIn: [nayak-sid](https://linkedin.com/in/nayak-sid)
- LeetCode: [sidspams](https://leetcode.com/sidspams)

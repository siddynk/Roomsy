# Roomsy

PG (paying guest) finder platform — tenants browse listings and book visits, owners manage PGs, admins track clients.

## Run locally

```bash
mvn spring-boot:run
```

Open http://localhost:8080

Default H2 in-memory DB — no MySQL setup needed. Sample data loads on first start.

### Demo logins (seed data)

| Role   | Email              | Password     |
|--------|--------------------|--------------|
| Tenant | priya@gmail.com    | password123  |
| Owner  | rajan@gmail.com    | password123  |
| Admin  | admin@roomsy.com   | admin123     |

## Production deploy

Set these environment variables on your host (Railway, Render, etc.):

| Variable | Required | Description |
|----------|----------|-------------|
| `SPRING_PROFILES_ACTIVE` | Yes | Use `mysql,prod` |
| `MYSQLHOST` | Yes | MySQL host |
| `MYSQLPORT` | Yes | MySQL port (usually 3306) |
| `MYSQLDATABASE` | Yes | Database name |
| `MYSQLUSER` | Yes | DB username |
| `MYSQLPASSWORD` | Yes | DB password |
| `APP_ADMIN_EMAIL` | Yes | Admin login email |
| `APP_ADMIN_PASSWORD` | Yes | Admin login password (use a strong password) |

Optional — store a BCrypt hash instead of plain text:

```bash
# Generate hash (Java snippet or online BCrypt tool), then set:
APP_ADMIN_PASSWORD='$2a$10$...'
```

Profiles:

- **mysql** — MySQL datasource (`application-mysql.properties`)
- **prod** — disables H2 console, turns off SQL logging, enables Thymeleaf cache

## API

Public (no auth):

- `GET /api/pgs` — active listings
- `GET /api/pgs/{id}` — single listing
- `GET /api/pgs/search?q=...` — search

All other `/api/*` routes require an admin session (log in at `/admin/login` first).

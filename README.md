# 🚰 Village Water Monitoring System

A full-stack web application to help Gram Panchayats manage rural piped water supply digitally.

---

## 🧱 Tech Stack

| Layer      | Technology               |
|------------|--------------------------|
| Backend    | Spring Boot 3.2 (Java 17)|
| Frontend   | Angular 17 (Standalone)  |
| Database   | PostgreSQL 14+           |
| UI Library | Angular Material 17      |

---

## 📁 Project Structure

```
village-water-system/
├── backend/           ← Spring Boot project
│   └── src/main/java/com/village/water/
│       ├── controller/     ← REST endpoints
│       ├── service/        ← Business logic
│       ├── repository/     ← JPA data access
│       ├── entity/         ← JPA entities
│       ├── dto/            ← Request/Response DTOs
│       ├── exception/      ← Global error handling
│       ├── config/         ← CORS configuration
│       └── seed/           ← Demo data seeder
│
└── frontend/          ← Angular project
    └── src/app/
        ├── pages/          ← Login, Dashboard, WaterLog, Issues, Assets
        ├── components/     ← Layout (sidenav shell)
        ├── services/       ← ApiService, AuthService
        └── models/         ← TypeScript interfaces
```

---

## ⚙️ Prerequisites

- Java 17+
- Maven 3.8+
- Node.js 18+ and npm 9+
- Angular CLI 17: `npm install -g @angular/cli`
- PostgreSQL 14+

---

## 🗄️ Database Setup

1. Open PostgreSQL (psql or pgAdmin)
2. Create the database:

```sql
CREATE DATABASE village_water_db;
```

3. The schema is auto-created by Spring Boot (`ddl-auto=update`).  
   No manual SQL needed.

4. Default DB credentials used by the app:
   - Host: `localhost:5432`
   - Database: `village_water_db`
   - Username: `postgres`
   - Password: `postgres`

   To change these, edit:
   `backend/src/main/resources/application.properties`

---

## 🚀 Running the Backend

```bash
cd backend
./mvnw spring-boot:run
```

Or with Maven installed:

```bash
cd backend
mvn spring-boot:run
```

The backend starts at: **http://localhost:8080**

On first run, demo data is automatically seeded.

### Demo Login Credentials

| Role     | Phone      | Password  |
|----------|------------|-----------|
| Admin    | 9876543210 | admin123  |
| Operator | 9123456789 | op123     |
| Operator | 9988776655 | op456     |

---

## 🌐 Running the Frontend

```bash
cd frontend
npm install
ng serve
```

The frontend starts at: **http://localhost:4200**

---

## 📡 API Endpoints

### Auth
| Method | Endpoint         | Description    |
|--------|-----------------|----------------|
| POST   | /api/auth/login  | User login     |

**Request:**
```json
{ "phone": "9876543210", "password": "admin123" }
```

**Response:**
```json
{ "id": 1, "name": "Rajesh Sharma", "phone": "9876543210", "role": "ADMIN" }
```

---

### Villages
| Method | Endpoint             | Description         |
|--------|---------------------|---------------------|
| GET    | /api/villages        | Get all villages    |
| GET    | /api/villages/{id}   | Get village by ID   |
| POST   | /api/villages?name=X | Create village      |

---

### Water Logs
| Method | Endpoint                          | Description              |
|--------|----------------------------------|--------------------------|
| POST   | /api/water-logs                   | Create a water log       |
| GET    | /api/water-logs                   | Get all logs             |
| GET    | /api/water-logs/village/{id}      | Get logs by village      |
| GET    | /api/water-logs/recent?limit=10   | Get recent N logs        |

**Create Request:**
```json
{
  "villageId": 1,
  "tankLevel": 75,
  "pumpStatus": "ON",
  "quality": "GOOD"
}
```

---

### Issues
| Method | Endpoint                  | Description                        |
|--------|--------------------------|-------------------------------------|
| POST   | /api/issues               | Report a new issue                 |
| GET    | /api/issues               | Get all issues                     |
| GET    | /api/issues?status=OPEN   | Filter by status (OPEN/RESOLVED)   |
| GET    | /api/issues/village/{id}  | Get issues by village              |
| PATCH  | /api/issues/{id}/resolve  | Mark issue as RESOLVED             |

**Create Request:**
```json
{
  "villageId": 1,
  "title": "Pipe Leakage",
  "description": "Near the main well"
}
```

---

### Assets
| Method | Endpoint           | Description               |
|--------|--------------------|---------------------------|
| POST   | /api/assets         | Add new asset             |
| GET    | /api/assets         | Get all assets            |
| GET    | /api/assets/village/{id} | Get assets by village |
| PUT    | /api/assets/{id}    | Update asset              |
| DELETE | /api/assets/{id}    | Delete asset              |

**Create Request:**
```json
{
  "villageId": 1,
  "name": "Main Pump A",
  "type": "PUMP",
  "status": "ON"
}
```

---

### Dashboard
| Method | Endpoint        | Description                     |
|--------|----------------|---------------------------------|
| GET    | /api/dashboard  | Get dashboard summary + logs    |

**Response:**
```json
{
  "totalIssues": 5,
  "resolvedIssues": 2,
  "openIssues": 3,
  "activePumps": 3,
  "latestTankLevel": 85,
  "latestVillageName": "Rampur",
  "recentLogs": [ ... ]
}
```

---

## 🗃️ Database Schema

```sql
-- Users
users (
  id          BIGSERIAL PRIMARY KEY,
  name        VARCHAR NOT NULL,
  phone       VARCHAR UNIQUE NOT NULL,
  password    VARCHAR NOT NULL,
  role        VARCHAR NOT NULL  -- ADMIN | OPERATOR
)

-- Villages
villages (
  id    BIGSERIAL PRIMARY KEY,
  name  VARCHAR UNIQUE NOT NULL
)

-- Assets
assets (
  id          BIGSERIAL PRIMARY KEY,
  name        VARCHAR NOT NULL,
  type        VARCHAR NOT NULL,    -- PUMP | TANK
  status      VARCHAR NOT NULL,    -- ON | OFF
  village_id  BIGINT REFERENCES villages(id)
)

-- Water Logs
water_logs (
  id           BIGSERIAL PRIMARY KEY,
  village_id   BIGINT REFERENCES villages(id),
  tank_level   INTEGER NOT NULL,   -- 0-100 percentage
  pump_status  VARCHAR NOT NULL,   -- ON | OFF
  quality      VARCHAR NOT NULL,   -- GOOD | BAD
  timestamp    TIMESTAMP DEFAULT now()
)

-- Issues
issues (
  id           BIGSERIAL PRIMARY KEY,
  title        VARCHAR NOT NULL,
  description  TEXT,
  status       VARCHAR NOT NULL,   -- OPEN | RESOLVED
  village_id   BIGINT REFERENCES villages(id),
  created_at   TIMESTAMP DEFAULT now()
)
```

---

## 🎨 Frontend Pages

| Page         | Route          | Description                              |
|--------------|----------------|------------------------------------------|
| Login        | /login         | Phone + password authentication          |
| Dashboard    | /dashboard     | Summary cards + recent 10 water logs     |
| Water Logs   | /water-logs    | Log form + full log history with filters |
| Issues       | /issues        | Report issues + filter/resolve           |
| Assets       | /assets        | Add/edit/delete pumps and tanks          |

---

## 🔄 System Flow

```
Operator logs in
    → Enters daily water log (tank %, pump status, quality)
    → Reports issues if needed (pipe leak, motor failure)

Admin logs in
    → Views dashboard (summary + recent logs)
    → Reviews open issues
    → Marks issues as resolved
    → Manages assets
```

---

## 🐛 Troubleshooting

**Backend won't start:**
- Check PostgreSQL is running
- Verify DB credentials in `application.properties`
- Make sure database `village_water_db` exists

**Frontend CORS error:**
- Ensure backend is running on port 8080
- Check `CorsConfig.java` allows `http://localhost:4200`

**npm install fails:**
- Use Node.js 18+: `node --version`
- Delete `node_modules` and `package-lock.json`, then re-run `npm install`

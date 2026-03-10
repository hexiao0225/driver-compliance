# Driver Compliance System

A full-stack Driver Compliance MVP — React + GraphQL + Kotlin/Spring Boot + PostgreSQL.

## Architecture

```
Browser (Vercel)
  └─ React + Apollo Client
       │  VITE_API_URL points to Render backend
       ▼
Spring Boot (Render) — Docker container
  └─ Netflix DGS GraphQL at /graphql
  └─ CORS allows FRONTEND_URL origin
       │  SPRING_DATASOURCE_URL points to Neon
       ▼
PostgreSQL (Neon)
  └─ Flyway migrations run on startup
  └─ Seeded with 4 drivers (VALID / EXPIRING / EXPIRED / EXPIRED)
```

---

## Local Development

### Prerequisites
- Docker Desktop — [download here](https://www.docker.com/products/docker-desktop/) (must be running)
- JDK 21 — `brew install --cask temurin@21`
- Gradle 8.5 (one-time wrapper bootstrap) — `brew install gradle`
- Node 20+

### Steps

1. **Start Postgres** (maps to host port 5433 to avoid conflicts with any local Postgres)
   ```bash
   docker run -d \
     --name compliance-db \
     -e POSTGRES_DB=compliance \
     -e POSTGRES_USER=postgres \
     -e POSTGRES_PASSWORD=postgres \
     -p 5433:5432 \
     postgres:16-alpine
   ```

2. **Bootstrap the Gradle wrapper** (one-time only, inside `backend/`)
   ```bash
   cd backend
   gradle wrapper --gradle-version 8.5
   ```

3. **Start the backend**
   ```bash
   SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5433/compliance ./gradlew bootRun
   ```
   Flyway will run the migrations and seed 4 drivers automatically.

4. **Verify the API**
   Open [http://localhost:8080/graphiql](http://localhost:8080/graphiql) and run:
   ```graphql
   { drivers { id name email status } }
   ```
   You should see 4 drivers with statuses: VALID / EXPIRING / EXPIRED / EXPIRED.

5. **Start the frontend** (in a new terminal, inside `frontend/`)
   ```bash
   cd frontend
   npm install
   npm run dev
   ```
   Open [http://localhost:5173](http://localhost:5173) — the Vite proxy forwards `/graphql` to `localhost:8080`, so no CORS config needed locally.

### Stopping the database
```bash
docker rm -f compliance-db
```

---

## Deploy to Production

> **Order matters.** Each step depends on the one before it.

### Step 1 — Neon (Database)
- Create a free project at [neon.tech](https://neon.tech)
- Go to **Connection Details** → select **JDBC** from the dropdown
- Copy the JDBC connection string: `jdbc:postgresql://<host>/neondb?user=...&password=...&sslmode=require`
  - ⚠️ Use the JDBC format (starts with `jdbc:`), not the raw `postgresql://` format
  - ⚠️ Remove `channel_binding=require` if present — the JDBC driver doesn't support it

### Step 2 — GitHub
- Push this repo to GitHub (already done if you're reading this)

### Step 3 — Render (Backend)
- **New Web Service** → connect your GitHub repo
- Runtime: **Docker** | Root directory: `backend`
- The `render.yaml` at the repo root is pre-configured for this service
- Set these environment variables in the Render dashboard:

  | Variable | Value | Notes |
  |---|---|---|
  | `SPRING_DATASOURCE_URL` | your Neon JDBC URL | From Step 1 |
  | `SPRING_DATASOURCE_USERNAME` | Neon DB username | From Neon connection details |
  | `SPRING_DATASOURCE_PASSWORD` | Neon DB password | From Neon connection details |
  | `FRONTEND_URL` | your Vercel URL | Add **after** Step 4; controls CORS |

- First deploy takes ~5 min (Docker build + Gradle compile)
- Flyway runs migrations automatically on startup — check logs to confirm

### Step 4 — Vercel (Frontend)
- **Import project** from GitHub
- Root directory: `frontend` | Framework: Vite (auto-detected)
- Set this environment variable:

  | Variable | Value | Notes |
  |---|---|---|
  | `VITE_API_URL` | your Render backend URL | e.g. `https://driver-compliance.onrender.com` |

  - ⚠️ `VITE_*` variables are **baked into the build** at compile time — changing them requires a redeploy
  - The `vercel.json` at the repo root handles SPA routing (`/*` → `index.html`)

### Step 5 — Final CORS wiring
- Copy your Vercel URL (e.g. `https://your-app.vercel.app`)
- Go to Render → your service → **Environment** → add/update `FRONTEND_URL`
- Trigger a **Manual Deploy** on Render
- The backend reads `FRONTEND_URL` at startup and adds it to the CORS allowed origins

---

## Key wiring summary

| From | To | How |
|---|---|---|
| Frontend (Vercel) | Backend (Render) | `VITE_API_URL` env var → Apollo Client `uri` |
| Backend (Render) | Database (Neon) | `SPRING_DATASOURCE_URL` env var → HikariCP |
| Backend CORS | Frontend origin | `FRONTEND_URL` env var → `CorsConfig.kt` |
| Local frontend | Local backend | Vite dev proxy (`/graphql` → `localhost:8080`) |

---

## Live URLs

- **Frontend**: https://frontend-smoky-nine-12.vercel.app
- **Backend GraphQL**: https://driver-compliance.onrender.com/graphql
- **GraphiQL playground**: https://driver-compliance.onrender.com/graphiql

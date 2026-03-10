# Driver Compliance System

A full-stack Driver Compliance MVP — React + GraphQL + Kotlin/Spring Boot + PostgreSQL.

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
   Open [http://localhost:5173](http://localhost:5173) — the driver list loads via the Vite proxy.

### Stopping the database
```bash
docker rm -f compliance-db
```

---

## Deploy to Production

### 1. Neon (Database)
- Create a free project at [neon.tech](https://neon.tech)
- Copy the connection string (format: `postgresql://user:pass@host/dbname?sslmode=require`)

### 2. GitHub
- Push this repository to GitHub

### 3. Render (Backend)
- **New Web Service** → connect your GitHub repo
- Runtime: **Docker**
- Root directory: `backend` (or use `render.yaml` — already configured)
- Set these environment variables in the Render dashboard:
  - `SPRING_DATASOURCE_URL` — your Neon connection string (use `jdbc:postgresql://...` prefix)
  - `SPRING_DATASOURCE_USERNAME` — Neon DB username
  - `SPRING_DATASOURCE_PASSWORD` — Neon DB password
  - `FRONTEND_URL` — your Vercel URL (add after step 4)

### 4. Vercel (Frontend)
- **Import project** from GitHub
- Framework preset: **Vite**
- Root directory: `frontend`
- Add environment variable:
  - `VITE_API_URL` = your Render backend URL (e.g. `https://driver-compliance-backend.onrender.com`)
- Deploy — Vercel uses `vercel.json` at the repo root automatically

### 5. Final wiring
- Copy the Vercel URL back into Render's `FRONTEND_URL` env var and redeploy

---

## Live URLs

Fill in after deployment:

- **Frontend**: https://_____.vercel.app
- **Backend GraphQL**: https://_____.onrender.com/graphql
- **GraphiQL playground**: https://_____.onrender.com/graphiql

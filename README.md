# Food ordering system

Full-stack food delivery demo: **Spring Boot 3** REST API (MySQL, JWT, Spring Security) and a **React (Vite)** storefront with cart, checkout, order history, wishlist, search, and a separate **admin console** for managing users and restaurants.

## Tech stack

| Layer | Details |
|--------|---------|
| Backend | Java 17, Spring Boot 3.2, Spring Data JPA, Spring Security + JWT, Spring Mail |
| Database | MySQL 8 |
| Frontend | React 19, TypeScript, Vite 8, Tailwind CSS 4 |

## Database schema & diagrams

Assets live in the [`Database Schema/`](Database%20Schema/) folder.

### Entity-relationship diagram (ERD)

Entity-relationship overview: users place orders; restaurants receive orders and own menu items; orders contain line items that reference menu items.

![Entity-relationship diagram: USERS, RESTAURANTS, ORDERS, MENU_ITEMS, ORDER_ITEMS](Database%20Schema/database-erd.png)

In this codebase, **restaurants are modeled as `users` rows** with role `RESTAURANT` (and fields like `restaurant_name`), not a separate `restaurants` table. There are also **cart** entities for the shopping cart. Order and order-item relationships match the diagram.

### Core user flow – order placement

High-level flow from browsing to confirmation (JWT auth, cart, validation, persisted order with status `PLACED`, optional email).

![Flowchart: core user flow for order placement](Database%20Schema/order-placement-flowchart.png)

**Implementation note:** checkout in this project is triggered with **`POST /api/user/cart/checkout`** (authenticated customer), not `POST /api/orders`. The flowchart describes the same logical steps.

## Repository layout

- `src/main/java/...` — Spring Boot application (`com.food.foodorderingsystem`)
- `food-ordering-app/` — React SPA (run with `npm run dev`)
- `db/` — optional SQL helpers (e.g. extra seed data)
- `Database Schema/` — ERD (`database-erd.png`) and order-placement flowchart (`order-placement-flowchart.png`)
- `src/main/resources/application.properties.example` — template for local configuration (**secrets are not committed**)

## Prerequisites

- JDK 17+
- MySQL with a database named `food_ordering_system` (or adjust the URL in config)
- Node.js 20+ (for the frontend)

## Backend setup

1. Create the database:

   ```sql
   CREATE DATABASE food_ordering_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

2. Copy the config template and edit secrets:

   ```bash
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```

   Set at least:

   - `spring.datasource.password`
   - `jwt.secret` (use a long random string in production)

   Optional: Gmail SMTP for order notifications — set `spring.mail.username`, `spring.mail.password`, and `app.notifications.email.enabled=true`.

3. Run the API:

   ```bash
   ./mvnw spring-boot:run
   ```

   Default URL: **http://localhost:8080**

   On startup, `DataSeeder` creates demo users and menus when those emails are not already present.

## Frontend setup

```bash
cd food-ordering-app
npm install
npm run dev
```

App URL: **http://localhost:5173**

The dev server expects the API at `http://127.0.0.1:8080` (see `food-ordering-app/src/lib/api.ts`). CORS is allowed for `localhost:5173` in `SecurityConfig`.

Production build:

```bash
cd food-ordering-app
npm run build
```

## Using the app

| Who | Where | Notes |
|-----|--------|--------|
| Customers | `/login` → sign in as **Customer** or register | Browse restaurants, cart, orders, wishlist |
| Restaurants | `/login` → **Restaurant** | Manage menu and orders via API (`/api/restaurant/...`) |
| Admins | **`/admin/login`** | Dark “admin console” UI — list/filter users & restaurants, edit or delete accounts |

Do **not** use the public `/login` page for admin access; use **`/admin/login`** so the experience stays separate.

## Demo accounts (from `DataSeeder`)

These are created only if no user exists yet with the same email.

| Role | Email | Password |
|------|--------|----------|
| Admin | `admin@foodapp.com` | `Admin@123` |
| Restaurant (example) | `restaurant@foodapp.com` | `Restaurant@123` |

Additional seeded restaurants use the same password pattern: **`Restaurant@123`** (see `DataSeeder` for all emails).

## API overview (high level)

- **Public:** `/api/auth/signup`, `/api/auth/signin/*`, `/api/public/restaurants`, menus
- **Customer (JWT `ROLE_USER`):** `/api/user/cart`, `/api/user/orders`
- **Restaurant (`ROLE_RESTAURANT`):** `/api/restaurant/...` (menu, orders)
- **Admin (`ROLE_ADMIN`):** `/api/admin/users`, `/api/admin/orders`

Administrator accounts cannot be created through public signup; only the admin sign-in endpoint and seeded data apply.

## Security note

`application.properties` is **gitignored** so passwords and JWT secrets stay local. If you ever committed real secrets, rotate them and avoid pushing them again.

## License / project

Academic or demo use unless otherwise specified by the repository owner.

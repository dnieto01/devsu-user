# devsu-user

API Spring Boot para clientes: JPA y Redis (locks distribuidos).

## Requisitos

| Entorno | Necesitas |
|--------|-----------|
| **Docker (recomendado)** | Docker 24+ y Docker Compose v2 |
| **Solo JVM** | JDK 17+, Maven (o `./mvnw`), y según perfil Postgres y/o Redis |

## Datos iniciales (`BaseDatos.sql`)

Al arranque, si la tabla `person` está **vacía**, se ejecuta `src/main/resources/BaseDatos.sql`. Desactivar: `app.database.seed.enabled=false` (en `application.properties`; el perfil **`test`** lo desactiva en `application-test.properties`).

## Modelo de perfiles (alineado con devsu-account)

| Perfil | Uso típico | Comportamiento principal |
|--------|------------|---------------------------|
| **`prod`** | Docker / entorno real | Lock **Redis**, JDBC **Postgres** (`application-prod.properties`) |
| **`local`** | IDE / máquina local | Lock en **memoria**; H2 en `application-local.properties` |
| **`test`** | CI / tests (`spring.profiles.active=test`) | Lock en memoria, H2 `create-drop`, `application-test.properties` |

Prioridad del perfil activo: **`SPRING_PROFILES_ACTIVE`** → **`SCOPE_SUFFIX`** (variable `SCOPE`, ver `ScopeUtils`) → **`local`**.

Beans: `LockServiceLocal` en `local` y `test`; `LockService` + `RedisConfig` en el resto; `DebugConfig` solo si **no** es `prod`.

## Habilitar con Docker (esta API sola)

```bash
cp .env.example .env    # opcional
docker compose up --build
```

Por defecto: **`SPRING_PROFILES_ACTIVE=prod`**, **Postgres** y **Redis**.

| Qué | Valor por defecto |
|-----|-------------------|
| API | <http://localhost:8095> |
| Salud | `curl -s http://localhost:8095/ping` → `pong` |

Servicios: `postgres`, `redis`, `api`. Variables: **`.env.example`**.

## Habilitar sin Docker (desarrollo local)

```bash
./mvnw spring-boot:run
```

Sin variables, el perfil suele ser **`local`** (H2, lock en memoria). Para prod-like: Postgres + Redis y `SPRING_PROFILES_ACTIVE=prod`.

**Tests:** `./mvnw test`  
**JAR:** `./mvnw -q -DskipTests package` → `java -jar target/devsu-user-0.0.1-SNAPSHOT.jar`

## Habilitar junto a devsu-account

```bash
cd ../devsu-compose
docker compose up --build
```

- Cuentas: **8096** — Usuarios: **8095**  
- `devsu-account` sigue usando Kafka/Mongo; **user** solo Postgres + Redis en ese stack.

## Imagen Docker sin Compose

```bash
docker build -t devsu-user:local .
docker run --rm -p 8095:8095 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/devsu \
  -e SPRING_DATASOURCE_USERNAME=devsu \
  -e SPRING_DATASOURCE_PASSWORD=devsu \
  -e REDIS_HOST=host.docker.internal \
  devsu-user:local
```

## Build Maven

```bash
./mvnw test
./mvnw -DskipTests package
```

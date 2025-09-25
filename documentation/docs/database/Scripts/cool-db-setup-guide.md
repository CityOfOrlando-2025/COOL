# COOL Database Setup Guide (Dev Testing)

This guide is to help you set up the COOL project database locally using Docker and MySQL.  
Included are all the required tables, lookup data, and a test admin user.

---

## Quick Start

### 1. Environment Variables (**`.env`** file)

We use an **`.env`** file to keep database credentials out of the codebase and GitHub.  
#### 1.1 A **`.env.sample`** file is included in the repo with placeholder values:

**`.env.sample`**
```
   MYSQL_ROOT_PW=REPLACE_ME_ROOT_PW
   MYSQL_USER=cooldev
   MYSQL_APP_PW=REPLACE_ME_APP_PW
   MYSQL_DATABASE=cool_db
```
#### 1.2 Copy the sample file to create your local **`.env`**: 
```
    cp .env.sample .env
```

#### 1.3 Update **`.env`** with your own secure values.

**`.env`** (example)
```
   MYSQL_ROOT_PW=MySecureRootPW123
   MYSQL_USER=cooldev
   MYSQL_APP_PW=MySecureAppPW123
   MYSQL_DATABASE=cool_db
```
#### 1.4 Ensure **`.env`** is ignored by Git so it's never committed: 

**`.gitignore`**

```
# Ignore environment file
.env
```

> **Note:**
> The **`.env`** is a hidden **dotfile** like **`.gitignore`** and has no name before the dot. 
> This file is used by Docker Compose and needs to be named exactly **`.env`**.

### 2. Start Docker MySQL

#### 2.1 If using **`docker run`**:

```
docker run --name cool-mysql \
-e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PW} \
-e MYSQL_DATABASE=${MYSQL_DATABASE} \
-e MYSQL_USER=${MYSQL_USER} \
-e MYSQL_PASSWORD=${MYSQL_APP_PW} \
-p 3306:3306 \
-v ./init:/docker-entrypoint-initdb.d \
-d mysql:8.0
```

#### 2.2 If using **`docker compose`**:

```
# 'up' builds and starts the container
# '-d' runs it in the background (detached)
docker compose up -d
```

---

### 3. Resetting After Testing (Clean State)

#### 3.1 If using **`docker run`**:

```
# Stop the container
docker stop cool-mysql

# Remove the container and associated volume
docker rm -v cool-mysql
```

#### 3.2 If using **`docker compose`**:

```
# Stops and removes the container and associated volume
docker compose down -v
```

---

### 4. Troubleshooting

#### 4.1 Missing Environment Variables

If you see warnings like this when running **`docker compose up -d`**:
> time="2025-09-25T14:37:18-04:00" level=warning msg="The "MYSQL_USER" variable is not set. Defaulting to a blank string."
> time="2025-09-25T14:37:18-04:00" level=warning msg="The "MYSQL_APP_PW" variable is not set. Defaulting to a blank string."
> time="2025-09-25T14:37:18-04:00" level=warning msg="The "MYSQL_ROOT_PW" variable is not set. Defaulting to a blank string."
> time="2025-09-25T14:37:18-04:00" level=warning msg="The "MYSQL_DATABASE" variable is not set. Defaulting to a blank string."

This means **`Docker Compose`** could not find your environment variable. Most often this happens because the **`.env`** file was never created
from the **`.env.sample`** template, or the values inside **`.env`** are still placeholders. 

**Fix:**
##### 1. **Copy** the template file to create your personal **`.env`**:
```
cp .env.sample .env
```
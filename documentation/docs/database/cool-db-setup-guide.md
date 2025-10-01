# COOL Database Setup Guide (Dev Testing)

This guide is to help you set up the COOL project database locally using Docker and MySQL.  
Included are all the required tables, lookup data, and a test admin user.

---

### 1. Project Files

Our project includes a small but important set of files organized in a specific hierarchy. Keeping this structure intact is essential because Docker Compose and MySQL rely on these paths to initialize the database correctly.

```
project-root/    
├── docker-compose.yaml  
├── .env.sample  
├── .gitignore  
└── initdb/  
    ├── cool-ddl.sql  
    └── seed-dev.sql
```

> #### Note on Operating Systems:
>
> Once the Docker containers are running, everyone is working inside the same Linux environment. 
> This means commands such as **`docker compose up -d, docker exec -it ...`**, and SQL queries will be identical across Mac, Linux, and Windows.

#### 1.1 File and Folder Explanations

`docker-compose.yaml`
- Defines the services used in the project (for example, the MySQL container).
- References the **`initdb/`** folder to automatically load the database schema and seed data when the container is created. 
- Points to the **`.env`** file for sensitive environment variables (passwords).

#### 1.2 Environment Variables (**`.env`** file)

We use an **`.env`** file to keep database credentials out of the codebase and GitHub.  
An **`.env.sample`** file is included in the repo with placeholder values:

**`.env.sample`**
```
   MYSQL_ROOT_PW=REPLACE_ME_ROOT_PW
   MYSQL_USER=cooldev
   MYSQL_APP_PW=REPLACE_ME_APP_PW
   MYSQL_DATABASE=cool_db
```
#### 1.3 Navigate to your project folder and copy the sample file: 

>**Note:**

>You must place your **`.env`** file in the **root** directory. 

Example structure:
```
project-root/
├── docker-compose.yaml
├── .env
├── .env.sample
├── .gitignore
└── initdb/
    ├── cool-ddl.sql
    └── seed-dev.sql
```

Open your terminal in the project folder and run: 
```
# Mac / Linus (bash or zsh)
cp .env.sample .env
```
```
# Windows (Command Prompt)
copy .env.sample .env
```
```
# Windows (PowerShell)
Copy-Item .env.sample .env
```

#### 1.4 Update **`.env`** with your own secure values.

>**Note:**
>
>In your project root folder, locate the newly copied **`.env`** file. 
>
>Open it with your text editor and **change** the following lines by replacing **`MYSQL_ROOT_PW`** and **`MYSQL_APP_PW`** with **your own passwords**. 

**`.env`** (example)
```
   MYSQL_ROOT_PW=MySecureRootPW123
   MYSQL_USER=cooldev
   MYSQL_APP_PW=MySecureAppPW123
   MYSQL_DATABASE=cool_db
```
#### 1.5 Ensure **`.env`** is ignored by Git so it's never committed: 

Open the **`.gitignore`** file:     

- In your project root folder, locate the **`.gitignore`** file. 
- Open it with your text editor and at the bottom of the file **add** the following line: 

```
# Ignore the local environment file with real passwords
.env
```

Save the **`.gitignore`** file and close the editor. 

> **Note:**
> The **`.env`** is a hidden **dotfile** like **`.gitignore`** and has no name before the dot. 
> This file is used by Docker Compose and needs to be named exactly **`.env`**.


### 2. Docker Setup

We'll set up MySQL in Docker. You can do this in two ways: 

- **Option A (Recommended): Docker Compose** - Easiest and most consistent across the team.
- **Option B: Manual Run Command** - Enter everything in yourself.

#### 2.1 Docker Overview

- Docker runs software inside **containers**.
- A container is a lightweight virtual machine. 
- We're using Docker here so everyone has the **same MySQL setup** 

#### 2.2 Install Docker Desktop: 
   - [Docker for Windows](https://docs.docker.com/desktop/setup/install/windows-install/)

   - [Docker for Mac](https://docs.docker.com/desktop/setup/install/mac-install/)

   - [Docker for Linux](https://docs.docker.com/desktop/setup/install/linux/)

Verify that docker desktop is working:

In your terminal type: 
```
docker --version
```
Docker Desktop must be running **before** trying to run any **`docker-compose`** files in your terminal.

#### 2.3 Working with Docker Compose

This project uses a **`docker-compose.yaml`** file.

- The **`docker-compose.yaml`** file must be placed in the **project root folder**. 

Example structure:
```
project-root/
├── docker-compose.yaml
├── .env
├── .env.sample
├── .gitignore
└── initdb/
    ├── cool-ddl.sql
    └── seed-dev.sql
```

#### 2.1 Using **`docker compose`** to start your container (Recommended):

Using **Docker Compose** is the easiest and most consistent way to bring up your container. The settings defined in the **`docker-compose.yaml`** ensure that every time you run your container your settings remain the same. This keeps the setup simple and consistent for the whole team.

From the project root (where **`docker-compose.yaml`** is located) open your terminal: 

```
docker compose up -d
```

- This creates and starts the **cool-mysql** container with MySQL running inside it.
- `up` - Builds and starts the services defined in your **`docker-compose.yml`** file.
- `-d` (detached mode) - Runs your services in the background so your terminal stays free instead of locking on logs.

Your container should now be running in Docker Desktop. 

#### 2.2 Connecting to the Database

>**Note:**
> If you encounter initialization errors see the **Troubleshooting** section at the end of this guide.

In your terminal, type:
```
docker exec -it cool-mysql mysql -u root -p
```

Enter the **root password** you set in your **`.env`** file (`MYSQL_ROOT_PW`) 

You should then see the MySQL prompt: 

`mysql>`

Enter:
```
mysql> SHOW DATABASES;
```
You should see something that looks like this: 
```
+--------------------+
| Database           |
+--------------------+
| cool_db            |
| information_schema |
| mysql              |
| performance_schema |
| sys                |
+--------------------+
5 rows in set (0.00 sec)
```
Our database **`cool_db`** is showing after connecting to MySQL in the container. 

#### 2.3 Switch to the project database:

To switch to the project database type:

`mysql> USE cool_db;`

It should look like this: 
```
mysql> USE cool_db;
Reading table information for completion of table and column names
You can turn off this feature to get a quicker startup with -A

Database changed
```
#### 2.3 List all Tables

To confirm the tables are being populated run: 
```
SHOW TABLES;
```

#### 2.4 Verify the Results

You should see a list of all the defined by the DDL scripts (e.g. user_role, app_user, bin, device, etc.)

If no tables appear, it means the initialization scripts didn't run correctly. Double-check that your **`.sql`** files are located in the **`init/`** folder mapped to **`/docker-entrypoint-initdb.d/`** in your **`docker-compose.yaml`**. 

Example output: 
```
mysql> SHOW TABLES;
+----------------------+
| Tables_in_cool_db    |
+----------------------+
| action_log           |
| app_user             |
| bin                  |
| device               |
| device_condition     |
| device_status        |
| device_type          |
| loan                 |
| loan_action_type     |
| loan_log             |
| loan_status          |
| location             |
| transaction_status   |
| user_action_type     |
| user_location_access |
| user_role            |
+----------------------+
16 rows in set (0.00 sec)
```

### 4. Populating the Database (Manual Inserts)
 
>#### **Note:**
> This section provides **sample insert statements** for a few key tables to demonstrate the manual
> process of adding records. It is not an exhaustive list of all required inserts. For a 
> **complete dataset covering all tables, see **Section 6 (Automatic Inserts).**
>
> The commands in this section are the same on Mac, Linux, and Windows since they are run inside of the Docker container.

#### 2.2 If using **`docker run`**:

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


When the database container starts, all of the tables defined in the DDL are created, but they are **empty**.
To actually use the system, you must populate the lookup tables (roles, statues, types, etc.) and add some starter
records.

#### 4.1 Connect to the Database

In your terminal, type:
```
docker exec -it cool-mysql mysql -u root -p
```

Enter the **root password** you set in your **`.env`** file (`MYSQL_ROOT_PW`) 

You should then see the MySQL prompt: 

`mysql>`

Switch to the project database:

`USE cool_db;`

#### 4.2 Insert Lookup Data

Lookup tables hold the fixed lists that the system depends on (roles, device types, statuses). These **must** be populated first. 

Example: 
```
-- User Roles
INSERT INTO user_role (user_role_name, dl_required, is_active) VALUES
('Admin', 0, 1),
('Employee', 0, 1),
('Citizen', 1, 1);
```

```
-- Device Status
INSERT INTO device_status (device_status_name) VALUES
('Available'),
('Loaned'),
('Maintenance'),
('Retired'),
('Lost');
```

#### 4.3 Insert a Test User (app_user)
Once the lookups exist, you can add a user. This table depends on **`user_role`**, so make sure the roles were inserted first.  

```
-- Test Admin User
INSERT INTO app_user (app_user_full_name, email, password_hash, password_salt, user_role_id)
VALUES ('Test Admin', 'admin@example.com', 'hashed_pw_here', 'salt_here', 1);
```
- `user_role_id = 1` assumes "Admin" is role ID 1.
- (_Placeholder values used here for testing. However, in production these should be replaced with securely generated hashes._)


```
-- Test Location
INSERT INTO location (location_name, street_address, city, state, zip_code, contact_phone)
VALUES ('Downtown Community Center', '123 Main St', 'Orlando', 'FL', '32801', '407-555-1234');
```
#### 4.4 Verify Data
Check that the rows were inserted: 

```
SELECT * FROM user_role;
SELECT * FROM device_status;
SELECT * FROM location;
SELECT * FROM app_user;
```
### 5. Loading Seed Data (Automatic Inserts)

> #### **Note:** 
> This section provides a **seed script** that automatically populates the schema with a consistent baseline dataset across **all tables**. Use this when you want to quickly bring up the database to a usable state without entering data manually.
>
> The commands in this section are the same on Mac, Linux, and Windows, since they are run inside the Docker container.

#### 5.1 Setup
- Copy **`seed-dev.sql`** inside the **`./init`** directory (alongside the **`cool-ddl.sql`**). 
- During container startup, MySQL executes all **`.sql`** files in **`./init`** the **first time** the database is created.
- This ensures that each environment is provisioned with a consistent, known dataset.

#### 5.2 Start the Database
From your project folder open your terminal and type: 
```
docker compose up -d
```


#### 5.3 Verify the Seeded Tables
After the container starts, connect to MySQL.

In your terminal type:
```
docker exec -it cool-mysql mysql -u root - p
```
- `docker exec` - Tells docker to run a command inside an already running container.
- `-it` - Combines two flags: 
   - `-i` - Keeps the session open for interactive input (so you can type commands).
   - `-t` - Gives you a terminal interface inside the container. 
- `cool-mysql` - The name of your running container. 
- `-u root` - Logs into MySQL as the `root` user. 
- `-p` - Tells MySQL to prompt you for a password (you'll type it in next).

Enter your password: 
```
Enter password: 
```
Switch to your project database

```
USE cool_db;
```
Check to see if the seed worked by running:
```
SHOW TABLES;
```
```
SELECT * FROM user_role;
```
```
SELECT * FROM app_user;
```
```
SELECT * FROM location;
```

You should see all schema tables plus sample data from the seed file.

#### 5.4 Resetting and Reloading

If you need to wipe the database and reload fresh seed data: 

```
docker compose down -v
docker compose up -d
```

This clears all data and re-runs the DDL and seed file.

### 3. Troubleshooting the Database Initialization

#### 3.1 Missing Environment Variables

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
#### 3.2 Access Denied for Root User

If you see an error like this when trying to connect: 
```
$ docker exec -it cool-mysql mysql -u root -p
Enter password:
ERROR 1045 (28000): Access denied for user 'root'@'localhost' (using password: YES)
```
It usually means that your **`.env`** file still contains **placeholder values**, such as **`REPLACE_ME_ROOT_PW`**, instead of your real password.

**Fix:**

##### 1. Open your local **`.env`** file.
##### 2. Make sure the **`MYSQL_ROOT_PW`** entry is updated with your secure password. For example:

```
MySQL_ROOT_PW=MySecureRootPW123
```
##### 3. **Restart** the container so that the changes take effect. 

```
docker compose down -v
docker compose up -d
```

##### 4. Try connecting again with: 
```
docker exec -it cool-mysql mysql -u root -p
```

Enter the updated password when prompted. 

---
> #### **Note:** 
>
>**Section 4** (Manual Inserts): Intended for **granular testing** and validation of individual tables.
>
>**Section 5** (Automatic Inserts): Intended for quickly populating the schema with a consistent baseline dataset using a **seed script**. 
---

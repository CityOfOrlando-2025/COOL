# COOL Database Setup Guide (Dev Testing)

This guide is to help you set up the COOL project database locally using Docker and MySQL.  

---

## 1. Project Files

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

### 1.1 File and Folder Explanations

#### 1.1.1 `docker-compose.yaml`

- Defines the services used in the project (for example, the MySQL container).
- References the **`initdb/`** folder to automatically load the database schema and seed data when the container is created. 
- Points to the **`.env`** file for sensitive environment variables (passwords).

#### 1.1.2`.env.sample`

- A template environment file with placeholder values.
- Devs will need to replace placeholer passwords with their own passwords with a copy `.env` file.
- Ensures that secrets (passwords) are not accidently committed publicly to Git. This file makes sure that the correct format is used by teammates.  

#### 1.1.3 `.gitignore`

- Tells Git which files to exclude from version control.
- Prevents committing sensitive files (like `.env`) or system-specific log files.

#### 1.1.4 `initdb/` folder

- Contains SQL scripts that are automatically run the first time the MySQL container starts.

#### 1.1.5 `cool-ddl.sql`

- Database schema (tables, constraints, indexes)

#### 1.1.6 `seed-dev.sql`

- Test data or lookup values for local development. 

### 1.2 Environment Variables (**`.env`** file)

We use an **`.env`** file to keep database credentials out of the codebase and GitHub.  
An **`.env.sample`** file is included in the repo with placeholder values:

**`.env.sample`**
```
   MYSQL_ROOT_PASSWORD=REPLACE_ME_ROOT_PASSWORD
   MYSQL_USER=cooldev
   MYSQL_PASSWORD=REPLACE_ME_PASSWORD
   MYSQL_DATABASE=cool_db
```
#### 1.2.1 Navigate to your project folder and copy the sample file: 

>**Note:**
>
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

#### 1.2.2 Update **`.env`** with your own secure values.

>**Note:**
>
>In your project root folder, locate the newly copied **`.env`** file. 
>
>Open it with your text editor and **change** the following lines by replacing **`MYSQL_ROOT_PASSWORD`** and **`MYSQL_PASSWORD`** with **your own passwords**. 

**`.env`** (example)
```
   MYSQL_ROOT_PASSWORD=MySecureRootPW123
   MYSQL_USER=cooldev
   MYSQL_PASSWORD=MySecureAppPW123
   MYSQL_DATABASE=cool_db
```
#### 1.2.3 Ensure **`.env`** is ignored by Git so it's never committed: 

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

## 2. Docker Setup

We'll set up MySQL in Docker. You can do this in two ways: 

- **Option A (Recommended): Docker Compose** - Easiest and most consistent across the team.
- **Option B: Manual Run Command** - Enter everything yourself.

### 2.1 Docker Overview

- Docker runs software inside **containers**.
- A container is a lightweight virtual machine. 
- We're using Docker here so everyone has the **same MySQL setup** 

#### 2.1.1 Install Docker Desktop: 
   - [Docker for Windows](https://docs.docker.com/desktop/setup/install/windows-install/)

   - [Docker for Mac](https://docs.docker.com/desktop/setup/install/mac-install/)

   - [Docker for Linux](https://docs.docker.com/desktop/setup/install/linux/)

Verify that docker desktop is working:

In your terminal type: 
```
docker --version
```
Docker Desktop must be running **before** trying to run any **`docker-compose`** files in your terminal.

### 2.2 Working with Docker Compose (Recommended - Automated Container Setup)

This project uses a **`docker-compose.yaml`** file.

- The **`docker-compose.yaml`** file must be placed in the project **root** folder. 

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

#### 2.2.1 Using **`docker compose`** to start your container (Recommended):

Using **Docker Compose** is the easiest and most consistent way to bring up your container. The settings defined in the **`docker-compose.yaml`** ensure that every time you run your container your settings remain the same. This keeps the setup simple and consistent for the whole team.

From the project root (where **`docker-compose.yaml`** is located) open your terminal: 

```
docker compose up -d
```

- This creates and starts the **cool-mysql** container with MySQL running inside it.
- `up` - Builds and starts the services defined in your **`docker-compose.yml`** file.
- `-d` (detached mode) - Runs your services in the background so your terminal stays free instead of locking on logs.

Your container should now be running in Docker Desktop. 

#### 2.2.2 Connecting to the Database

>**Note:**
> If you encounter initialization errors see the **Troubleshooting** section at the end of this guide.

In your terminal, type:
```
docker exec -it cool-mysql mysql -u root -p
```

Enter the **root password** you set in your **`.env`** file (`MYSQL_ROOT_PASSWORD`) 

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

#### 2.2.3 Switch to the project database:

To switch to the project database type:

`mysql> USE cool_db;`

It should look like this: 
```
mysql> USE cool_db;
Reading table information for completion of table and column names
You can turn off this feature to get a quicker startup with -A

Database changed
```
#### 2.2.4 List all Tables

To confirm the tables are being populated run: 
```
SHOW TABLES;
```

#### 2.2.5 Verify the Results

You should see a list of all the defined by the DDL scripts (e.g. user_role, app_user, bin, device, etc.)

If no tables appear, it means the initialization scripts didn't run correctly. Double-check that your **`.sql`** files are located in the **`initdb/`** folder mapped to **`/docker-entrypoint-initdb.d/`** in your **`docker-compose.yaml`**. 

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
When you run a container with Docker Compose it automatically runs everything in the **`initdb/`** including our **`seed-dev.sql`**. 

To check if the data was inserted, type into your terminal:
```
SELECT * FROM user_role;
SELECT * FROM device_status;
SELECT * FROM location;
SELECT * FROM app_user;
```

Example output: 

```
mysql> SELECT * FROM user_role;
+--------------+----------------+-------------+-----------+
| user_role_id | user_role_name | dl_required | is_active |
+--------------+----------------+-------------+-----------+
|            1 | Admin          |           0 |         1 |
|            2 | Employee       |           0 |         1 |
|            3 | Citizen        |           1 |         1 |
+--------------+----------------+-------------+-----------+
3 rows in set (0.02 sec)
```
### 2.3 Docker Manual Inserts

If you don't want to use **`docker-compose.yaml`**, you can start MySQL manually with **`docker run`**. 
>**Note:** The exact command format depends on the terminal you are using. 

This will create an empty **`cool_db`** so you can load your schema and insert your own data. 

#### 2.3.1 Use **`.env`** and **`docker run`** 

>**Note:** You must add **`MYSQL_PASSWORD`** to your **`.env`** so that **`docker run`** works properly. 
in Windows (Command Prompt or PowerShell) 
>**Note:** Replace the example passwords before running.

```
docker run -d --name cool-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=ChangeThisRootPW! -e MYSQL_DATABASE=cool_db mysql:8.0
```

#### 2.3.2 Using **`docker run`** on Git Bash / macOS / Linux
```
docker run -d --name cool-mysql \
  -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=ChangeThisRootPW! \
  -e MYSQL_DATABASE=cool_db \
  mysql:8.0
```
#### 2.3.3 Load the Schema (**`cool-ddl.sql`**) 

>**Note:** When prompted, enter the root password you set in the **`docker run`** command.

##### 2.3.3.1 Using Windows
```
type initdb\cool-ddl.sql | docker exec -i cool-mysql mysql -u root -p cool_db
```

##### 2.3.3.2 Using Git Bash / macOS / Linux
```
cat initdb/cool-ddl.sql | docker exec -i cool-mysql mysql -u root -p cool_db
```
#### 2.3.4 Connect to the Database

```
docker exec -it cool-mysql mysql -u root -p
```
At the MySQL prompt:
```
mysql> USE cool_db;
```

#### 2.3.5 Insert Lookup Data (insert these first)
Lookup tables must be populated first so foreign keys in core tables have valid targets. 
These relationships are enforced by the **`cool-ddl.sql`**.

User Roles
#### 2.3. Add your Own Data (Simple Examples)

Open a MySQL shell:
```
docker exec -it cool-mysql mysql -u root -p
```

Then type: 
```
USE cool_db;
```

Create a **role** you can reference later:
```
INSERT INTO user_role (user_role_name, dl_required, is_active)
VALUES ('Admin', 0, 1);
```

Create a **location**:
```
INSERT INTO location (location_name, street_address, city, state, zip_code, contact_phone)
VALUES ('Callahan Neighborhood Center', '101 N. Parramore Ave Ste. 1713', 'Orlando', 'FL', '32801', '407-246-4442');
```

Create a **user** and link to your **role** by name:
```
INSERT INTO app_user (app_user_full_name, email, password_hash, password_salt, user_role_id)
SELECT 'Jane Doe', 'jane@workemail.com', 'hashed_pw_here', 'salt_here', ur.user_role_id
FROM user_role ur
WHERE ur.user_role_name = 'Admin';
```

Check to make sure your data is populating in the database.
```
SHOW TABLES;
SELECT * FROM user_role;
SELECT app_user_full_name, email FROM app_user;
```

#### 2.3.4 Stop and Remove the Database
If you didn't mount any volumes (we didn't in this tutorial) you can quickly and easily remove the container and discard all the data. Your next run will have no saved data. 

```
docker stop cool-mysql
docker rm cool-mysql
```

## 3. Troubleshooting the Database Initialization

### 3.1 Docker Compose 
#### 3.1.1 Missing Environment Variables

If you see warnings like this when running **`docker compose up -d`**:
> time="2025-09-25T14:37:18-04:00" level=warning msg="The "MYSQL_USER" variable is not set. Defaulting to a blank string."

> time="2025-09-25T14:37:18-04:00" level=warning msg="The "MYSQL_PASSWORD" variable is not set. Defaulting to a blank string."

> time="2025-09-25T14:37:18-04:00" level=warning msg="The "MYSQL_ROOT_PASSWORD" variable is not set. Defaulting to a blank string."  

> time="2025-09-25T14:37:18-04:00" level=warning msg="The "MYSQL_DATABASE" variable is not set. Defaulting to a blank string."  

This means **`Docker Compose`** could not find your environment variable. Most often this happens because the **`.env`** file was never created
from the **`.env.sample`** template, or the values inside **`.env`** are still placeholders. 

**Fix:**
**Copy** the template file to create your personal **`.env`**:
```
cp .env.sample .env
```
#### 3.1.2 Access Denied for Root User

If you see an error like this when trying to connect: 
```
$ docker exec -it cool-mysql mysql -u root -p
Enter password:
ERROR 1045 (28000): Access denied for user 'root'@'localhost' (using password: YES)
```
It usually means that your **`.env`** file still contains **placeholder values**, such as **`REPLACE_ME_ROOT_PASSWORD`**, instead of your real password.

**Fix:**

##### 1. Open your local **`.env`** file.
##### 2. Make sure the **`MYSQL_ROOT_PASSWORD`** entry is updated with your secure password. For example:

```
MySQL_ROOT_PASSWORD=MySecureRootPW123
```
##### 3.1.3 **Restart** the container so that the changes take effect. 

```
docker compose down -v
docker compose up -d
```

##### 3.1.4 Try connecting again with: 
```
docker exec -it cool-mysql mysql -u root -p
```

Enter the updated password when prompted. 

---
*************************************************************************************************************************
*************************************************************************************************************************
*************************************************************************************************************************
DELETE BELOW THIS LINE AFTER REVIEW
### 2.3 Docker Manual Inserts
 
 If you don't want to use **`docker-compose.yaml`**, you can start MySQL manually with **`docker run`**. 
>**Note:** The exact command format depends on the terminal you are using.

#### 2.3.1 Using **`docker run`** in Windows (Command Prompt or PowerShell):

> **Note**: You must **change the passwords** in the replacement text in **`MYSQL_ROOT_PASSWORD`** AND **`MYSQL_PASSWORD`** before hitting ENTER.

In your terminal paste:

```
docker run -d --name cool-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=ChangeThisRootPW! -e MYSQL_DATABASE=cool_db -e MYSQL_USER=cooldev -e MYSQL_PASSWORD=ChangeThisAppPW! -v %cd%\initdb:/docker-entrypoint-initdb.d mysql:8.0
```

#### 2.3.2 Using **`docker run`** on Mac / Linus / Git Bash on Windwos / WSL:

In your terminal paste: 

```
docker run --name cool-mysql \
-e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD} \
-e MYSQL_DATABASE=${MYSQL_DATABASE} \
-e MYSQL_USER=${MYSQL_USER} \
-e MYSQL_PASSWORD=${MYSQL_PASSWORD} \
-p 3306:3306 \
-v ./initdb:/docker-entrypoint-initdb.d \
-d mysql:8.0
```

When the database container starts, all of the tables defined in the DDL are created, but they are **empty**.
To actually use the system, you must populate the lookup tables (roles, statues, types, etc.) and add some starter
records.

#### 2.3.3 Connect to the Database

In your terminal, type:
```
docker exec -it cool-mysql mysql -u root -p
```

Enter the **root password** you set in your **`.env`** file (`MYSQL_ROOT_PASSWORD`) 

You should then see the MySQL prompt: 

`mysql>`

Switch to the project database:

`USE cool_db;`

#### 2.3.4 Insert Lookup Data

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

#### 2.3.5 Insert a Test User (app_user)
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
#### 2.3.6 Verify Rows
Check that the rows were inserted: 

```
SELECT * FROM user_role;
SELECT * FROM device_status;
SELECT * FROM location;
SELECT * FROM app_user;
```
#### 2.3.7 Loading Seed Data (Automatic Inserts)

> #### **Note:** 
> This section provides a **seed script** that automatically populates the schema with a consistent baseline dataset across **all tables**. Use this when you want to quickly bring up the database to a usable state without entering data manually.

#### 2.3.8 Verify the Seeded Tables

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
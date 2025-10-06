# COOL Database Setup Guide (Dev Testing)

This guide is to help you set up the COOL project database locally using Docker and MySQL.  

---

## 1. Project Files

Our project includes a small but important set of files organized in a specific hierarchy. Keeping this structure intact is essential because Docker Compose and MySQL rely on these paths to initialize the database correctly.

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

> #### Note on Operating Systems:
>
> Once the Docker containers are running, everyone is working inside the same Linux environment. 
> This means commands such as **`docker compose up -d`**, **`docker exec -it ...`**, and SQL queries will be identical across Mac, Linux, and Windows.

### 1.1 File and Folder Explanations

#### 1.1.1 `docker-compose.yaml`

- Defines the services used in the project (for example, the MySQL container).
- References the **`initdb/`** folder to automatically load the database schema and seed data the first time the container is created (when the database volume is empty).
- Configures persistent data storage so your database content survives container restarts and removals. 
- Automatically reads environment variables (passwords) from the **`.env`** file in the same directory.

#### 1.1.2`.env.sample`

- A template environment file with placeholder values.
- Devs will need to create a copy of this file named **`.env`** and replace the placeholder passwords with their own passwords.
- Ensures that secrets (passwords) are not accidentally committed publicly to Git. This file makes sure that the correct format is used by teammates.  

#### 1.1.3 `.gitignore`

- Tells Git which files to exclude from version control.
- Prevents committing sensitive files (like `.env`) or system-specific log files.

#### 1.1.4 `initdb/` folder

- Contains SQL scripts that are automatically run **only** when the database volume is **empty** (typically the first time you run **`docker compose up -d`**).
- **`cool-ddl.sql`** - Creates the database schema (lookup tables, core entity tables, and join tables).    
- **`seed-dev.sql`** - Inserts initial test data for development.

#### 1.1.5 `cool-ddl.sql`

- Database schema (tables, constraints, indexes)

#### 1.1.6 `seed-dev.sql`

- Test data or lookup values for local development. 

>**`Note`**:
> When you first run **`docker compose up -d`**, Docker automatically loads **`seed-dev.sql`**. 
>The **seed script** should only be executed once.
> Because columns like **`email`** and **`app_user`** are **UNIQUE**, re-running the seed script without resetting the database will cause deplicate-key errors.

To start fresh and rerun the seed data, open your terminal and type:
```
docker compose down -v
docker compose up -d
```
This wipes your old volume and re-initializes the database cleanly.

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

##### 1.2.1.1 Using Windows

Command Prompt
```
copy .env.sample .env
```

PowerShell
```
Copy-Item .env.sample .env
```
##### 1.2.1.2 Using Git Bash / macOS / Linux
Open your terminal in the project folder and run: 
```
cp .env.sample .env
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
> This file is used by **Docker Compose** and needs to be named exactly **`.env`**.

#### 1.2.4 Verify your .env is in .gitignore
In your terminal type: 
```
git check-ignore .env
```
This will output **`.env`** if it's properly ignored.


## 2. Docker Setup
We'll set up MySQL in Docker using **Docker Compose**, which provides and automated and consistent setup across the entire team.

### 2.1 Docker Overview

- Docker runs software inside **containers**.
- A container is a lightweight virtual machine. 
- We're using Docker here so everyone has the **same MySQL setup** 

#### 2.1.1 Check to see if Docker is Already Installed
In your terminal type: 
```
docker --version
```
If you see a version number, Docker is already installed and you can skip to section 2.2

#### 2.1.2 Install Docker Desktop: 
   - [Docker for Windows](https://docs.docker.com/desktop/setup/install/windows-install/)

   - [Docker for Mac](https://docs.docker.com/desktop/setup/install/mac-install/)

   - [Docker for Linux](https://docs.docker.com/desktop/setup/install/linux/)

Verify that docker desktop is working:

In your terminal type: 
```
docker --version
```
Docker Desktop must be running **before** trying to run any **Compose** files in your terminal.

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

#### 2.2.1 Using **`docker compose`** to start your container:

Using **Docker Compose** is the easiest and most consistent way to bring up your container. The settings defined in the **`docker-compose.yaml`** ensure that every time you run your container your settings remain the same. This keeps the setup simple and consistent for the whole team.

From the project root (where **`docker-compose.yaml`** is located) open your terminal: 

```
docker compose up -d
```

- This creates and starts the **cool-mysql** container with MySQL running inside it.
- `up` - Builds and starts the services defined in your **`docker-compose.yaml`** file.
- `-d` (detached mode) - Runs your services in the background so your terminal stays free instead of locking on logs.

Your container should now be running in Docker Desktop. 

#### 2.2.2 Connecting to the Database

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
If you don't see **`cool_db`** in the database list, see section 3.1. 

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

You should see a list of all tables defined by the DDL scripts (e.g. user_role, app_user, bin, device, etc.).

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
When you **first** run the container with **Docker Compose** it automatically runs everything in the **`initdb/`** including our **`seed-dev.sql`**. These scripts create the database structure and insert the initial test data.

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
#### 2.2.6 Data Persistence

>**Note:**
>This project uses **persistent storage**. Your data will remain saved even if you stop or remove the container.

**What this means**    
When you use Docker Compose, the database files are stored inside a **persistent volume** on your computer. This volume is separate from the container, so your database survives normal shutdowns and restarts.

**Your data will still be there if you:**    
- Run **`docker compose stop`**    
- Run **`docker compose down`** (removes the container but keeps the data)    
- Restart Docker Desktop    
- Restart your computer

**Confirm volume still Exists:**    
Enter into your terminal:
```
docker volume ls
```
You should see a volume named **cool_db_data**

Example Output: 
```
> docker compose up -d
[+] Running 2/2
 ✔ Volume "cool_db_data"  Created                                                              0.0s
 ✔ Container cool-mysql   Started                                                              0.4s

> docker volume ls
DRIVER    VOLUME NAME
local     cool_db_data
local     jenkins_home
local     minikube
```

**How Initialization Scripts Work**
The SQL scripts in the **`initdb/`** folder (**`cool-ddl.sql`** and **`seed-dev.sql`**) are only executed **once** during the first time the database is created. 

After that, MySQL uses the saved data in the persistent volume instead of re-running the scripts.

**Initialization scripts only run when**:    
- The database volume is completely empty (for example, the very first time you run **`docker compose up -d`**)

#### 2.2.7 Resetting the Database (Fresh Start)
If you need to completely wipe the database and start over (for example to rerun the initialization scripts or reload the test data), remove the existing volume by typing into your terminal: 
```
docker compose down -v 
docker compose up -d
```
This command deletes the old volume, recreates a new one, and re-runs the initialization scripts automatically.

>**Tip:**
> Use this only when you want to clear **all data** and rebuild your database from scratch.

## 3. Troubleshooting the Database Initialization

### 3.1 Docker Compose 
#### 3.1.1 Missing Environment Variables

If you see warnings like this when running **`docker compose up -d`**:
> time="2025-09-25T14:37:18-04:00" level=warning msg="The "MYSQL_USER" variable is not set. Defaulting to a blank string."

> time="2025-09-25T14:37:18-04:00" level=warning msg="The "MYSQL_PASSWORD" variable is not set. Defaulting to a blank string."

> time="2025-09-25T14:37:18-04:00" level=warning msg="The "MYSQL_ROOT_PASSWORD" variable is not set. Defaulting to a blank string."  

> time="2025-09-25T14:37:18-04:00" level=warning msg="The "MYSQL_DATABASE" variable is not set. Defaulting to a blank string."  

This means the **Docker Compose** file could not find your environment variable. Most often this happens because the **`.env`** file was never created from the **`.env.sample`** template, or the values inside **`.env`** are still placeholders. 

**Fix:**
**Copy** the template file to create your personal **`.env`**:
```
cp .env.sample .env
```
You will now need to shutdown and restart the container. 

>**Warning**: The **`-v`** flag deletes all database data. Only use this if you want to completely reset.
```
docker compose down -v
```
```
docker compose up -d
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

1. Open your local **`.env`** file.
2. Make sure the **`MYSQL_ROOT_PASSWORD`** entry is updated with your secure password.     
   For example:
   ```
   MYSQL_ROOT_PASSWORD=MySecureRootPW123
   ```

3. Remove the container and volume (this deletes all data), then restart:
   ```
   docker compose down -v
   docker compose up -d
   ```
4. Try connecting again:
   ```
   docker exec -it cool-mysql mysql -u root -p
   ```
5. Enter the updated password when prompted.

---

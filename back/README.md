# 🏠 MDD API

## 📌 Description
Chatop API is the backend of a rental management application. It enables the management of users, rentals, and messages between users.

## 🚀 Technologies Used
![Java](https://img.shields.io/badge/Java-23-orange)
![Maven](https://img.shields.io/badge/Maven-3.9.9-purple)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.5-green)
![MySQL](https://img.shields.io/badge/MySQL-8.5-darkblue)

- **Language**: Java 23
- **Framework**: Spring Boot 3.4.5
- **Database**: MySQL 8.5
- **Authentication**: JWT (JSON Web Token)
- **API Documentation**: Swagger

---

## ⚙️ Installation and Configuration

### ✅ Prerequisites
- Java 23
- Maven
- MySQL 8.5
- Postman *(optional, for API testing)*

### 🔧 Installation Steps

#### 1️⃣ Clone the Project
```sh
git clone <repository_url>
cd back
```

#### 2️⃣ Configure the Database
```sh
mysql -u root -p
```
Inside the MySQL shell, run:
```sql
CREATE DATABASE mdd_db;
CREATE USER 'mdd-user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON chatop_db.* TO 'mdd-user'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

#### 4️⃣ Create environment variables file

Create a `.env` file at the root of the project with the following content:

```
JWT_SECRET=your_secret
MYSQL_DATABASE=db_name
MYSQL_USER=db_user
MYSQL_PASSWORD=db_user_password
```


### 🚀 Build and run the project

Build the project with Maven:

```bash
mvn clean install
```
### 🚀 Launch the backend server

### Option 1: Using Maven

```bash
mvn spring-boot:run
```

### Option 2: Running the JAR file

```bash
java -jar target/*.jar
```

---


## 🔐 Security
All routes require authentication *(except signup and login)*.  
Authentication is done via **JWT**.  
Tokens must be included in the `Authorization` header of requests.

---

## 📖 API Documentation
After launching the project, Swagger is available at:
```
http://localhost:3002/api/swagger-ui.html
```

---

## 🤝 Contribution
Contributions are **welcome**!  
Fork the project and submit a **Pull Request (PR)**.

---

## 📝 License
This project is **licensed under the MIT License**.

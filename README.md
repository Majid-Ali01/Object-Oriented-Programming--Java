# Quiz Application

A desktop-based Quiz Application developed in :contentReference[oaicite:2]{index=2} using Object-Oriented Programming concepts with GUI and database integration.

This project allows users to attempt quizzes, view scores, and track history, while admins can manage quiz questions.

---

## Features

### User Features
✅ Enter username  
✅ Select topic  
✅ Attempt quiz  
✅ View score  
✅ Quiz history tracking  
✅ Search previous records  

### Admin Features
✅ Secure login  
✅ Add new quiz questions  
✅ Manage multiple topics  

### General Features
✅ Random quiz system  
✅ Score calculation  
✅ History management  
✅ Password encryption (SHA-256)  
✅ Database connectivity  

---

## Technologies Used

- :contentReference[oaicite:3]{index=3}
- :contentReference[oaicite:4]{index=4}
- :contentReference[oaicite:5]{index=5}
- :contentReference[oaicite:6]{index=6}
- Object-Oriented Programming

---

## OOP Concepts Implemented

- Abstraction
- Inheritance
- Polymorphism
- Encapsulation
- Interface
- Abstract Class

---

## Database Tables

### Admin Table
```sql
admin(
    id,
    username,
    password
)
```

### Topic Table
```sql
topic(
    topic_id,
    topic_name
)
```

### Question Table
```sql
question(
    id,
    topic,
    question,
    optA,
    optB,
    optC,
    optD,
    correct
)
```

### History Table
```sql
history(
    id,
    username,
    topic,
    score
)
```

---

## Project Structure

```bash
QuizApp/
│
├── QuizApp.java
├── README.md
├── database.sql
└── screenshots/
```

---

## Installation

### 1 Clone repository
https://github.com/Majid-Ali01/java_file
```

### 2 Open project in IDE

Recommended:
- :contentReference[oaicite:7]{index=7}
- :contentReference[oaicite:8]{index=8}
- :contentReference[oaicite:9]{index=9}

### 3 Setup database

Create database:

```sql
CREATE DATABASE quizapp;
```

Import tables.

### 4 Update database credentials

Inside `QuizApp.java`

```java
static final String DB_URL  = "jdbc:mysql://localhost:3306/quizapp";
static final String DB_USER = "root";
static final String DB_PASS = "your_password";
```

### 5 Run project

Compile and run:

```bash
javac QuizApp.java
java QuizApp
```

---

## System Flow

```text
Admin Login
   ↓
Add Questions
   ↓
User Select Topic
   ↓
Attempt Quiz
   ↓
Score Calculation
   ↓
History Saved
```

---

## Screenshots

Add screenshots here.

---

## Future Improvements

- Edit/Delete Questions
- User authentication
- Timer-based quizzes
- Leaderboard
- Online multiplayer quiz

---

## Authors

- Majid Ali
- Qadir Bux

---

## University Project

2nd Semester OOP Project  
Sukkur IBA University

---

## License

This project is for educational purposes.

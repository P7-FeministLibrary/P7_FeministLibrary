## 📖 The Matilda Library

##  Table of Contents
- [Description](#description)
- [Features](#features)
- [Screenshots](#screenshots)
- [Technologies](#tools--technologies)
- [Installation](#installation-and-execution)
- [Testing](#tests)
- [Architecture](#architecture)
- [Database Model](#database-model)
- [Team](#team)

## Description
*The Matilda Library* is a console-based Java application designed to manage a digital collection of books, authors, and genres — with a focus on feminist literature. It provides an intuitive terminal interface that allows users to add, edit, search, and organize books while maintaining relationships between books, authors, and genres. The application was developed in Java 21 with an MVC architecture and connection to a PostgreSQL database, which will allow the library administrator to perform basic book management operations (CRUD): add, list, update, delete and search for books by title, author or genre.

## Features
- Book Management — Add, update, delete, and list books.
- Author Management — Register and manage authors.
- Genre Management — Classify books by genre.
- Search Functionality — Find books by title, author, or genre.
- Database Integration — Uses JDBC for persistent storage.
- Testing — Includes integration and unit tests using JUnit 5, Mockito, and H2 in-memory database.
- Console Styling — Uses color-coded messages for better user interaction.

## Screenshots
<img width="822" height="371" alt="Captura de pantalla 2025-10-13 a las 17 11 39" src="https://github.com/user-attachments/assets/fe904527-a478-41ac-8e37-186f9fa21ca0" />
<img width="822" height="380" alt="Captura de pantalla 2025-10-13 a las 17 12 08" src="https://github.com/user-attachments/assets/d43df2ff-77db-4575-8b1b-8e4d9fca27e9" />
<img width="941" height="379" alt="Captura de pantalla 2025-10-13 a las 12 04 10" src="https://github.com/user-attachments/assets/e090f727-4c03-4067-84f9-d1a75472fbd7" />

## Tools & Technologies:
- Visual Studio Code
- Git / GitHub
- Java 21
- PostgreSQL 17
- Apache Maven 3.13.0
- JUnit 5
- Mockito
- H2 Database

## Installation and Execution:

### 1️. Clone the repository:
```bash
git clone https://github.com/P7-FeministLibrary/P7_FeministLibrary.git
```

### 2️. Set up the environment variables:
Create a .env file in the root of the project with your database credentials:

DB_HOST=localhost
DB_PORT=5432
DB_NAME=feminist_library
DB_USER=postgres
DB_PASSWORD=your_password

### 3️. Compile the project:
```bash
mvn compile
```

### 4️. Run the application:
```bash
mvn exec:java -Dexec.mainClass="com.feministlibrary.App"
```
## Testing
This project includes both **unit** and **integration tests** using:

- **JUnit 5** – for unit testing DAO methods and controllers
-  
- **Mockito** – for mocking static classes (like DBManager)
-  
- **H2 in-memory database** – for testing real SQL queries without affecting PostgreSQL  

To run tests:
```bash
mvn test
```

##  Architecture
The project follows an **MVC (Model-View-Controller)** architecture and uses the **DAO (Data Access Object)** pattern.
src/main/

  ├── config/ # Database connection (DBManager)
  
  ├── controller/ # Application logic connecting view and model
  
  ├── model/ # Data models and DAOs (Book, Author, Genre)
  
  ├── view/ # Console interface for user interaction
  
  ├── App.java # Main entry point
  
  └── Style.java
  
src/test

## Database Model
The database is normalized and consists of the following main tables:

- **book** (id, title, description, isbn)
- 
- **author** (id, name, last_name)
- 
- **genre** (id, genre)
- 
- **book_author** (id_book, id_author)
- 
- **book_genre** (id_book, id_genre)

Each book can have multiple authors and genres (many-to-many relationships).

## Team

👩‍💻 Estefania Secanell (Scrum Master & Developer) – [GitHub](https://github.com/Abaraira)

👩‍💻 Suraya Mattar (Product Owener & Developer) [Linkedin](https://github.com/surayac)

👩‍💻 Jashaira (Developer) – [GitHub](https://github.com/JMileny89)

👩‍💻 Ana Aguilera (Developer) – [Linkedin](https://www.linkedin.com/in/ana-aguilera-morales-011b1a238/)

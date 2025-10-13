# 📚 Matilda Library

## Description

Is a console application developed in Java 21 with an MVC architecture and connection to a PostgreSQL database, which will allow the library administrator to perform basic book management operations (CRUD): add, list, update, delete and search for books by title, author or genre.

## Screenshot
<img width="826" height="258" alt="Captura de pantalla 2025-10-13 a las 12 04 35" src="https://github.com/user-attachments/assets/12832dec-71e0-49f4-957f-5805773946bc" />
<img width="941" height="379" alt="Captura de pantalla 2025-10-13 a las 12 04 10" src="https://github.com/user-attachments/assets/ba87fed2-1b60-4794-a10a-4af764b0f6f7" />

## Technologies:

- Java 21

- PostgreSQL 17

- Apache Maven 3.13.0

- Unitat 5 , Mockito

## Tools:

- Visual Studio Code

- Git / GitHub

## Installation and Execution:

### 1️⃣ Clone the repository:

git clone https://github.com/P7-FeministLibrary/P7_FeministLibrary.git

### 2️⃣ Set up the environment variables:

Create a .env file in the root of the project with your database credentials:

DB_HOST=localhost

DB_PORT=5432

DB_NAME=feminist_library

DB_USER=postgres

DB_PASSWORD=your_password

### 3️⃣ Compile the project:
mvn compile

### 4️⃣ Run the application:
mvn exec:java -Dexec.mainClass="com.feministlibrary.App"

## Tests

The project includes unit tests developed with JUnit 5 to validate CRUD operations and the database connection.

## Team

👩‍💻 Estefania Secanell – [github](https://github.com/Abaraira)

👩‍💻 Suraya Mattar – [Linkedin](https://www.linkedin.com/in/suraya-mattar/)

👩‍💻 Jashaira – [GitHub](https://github.com/JMileny89)

👩‍💻 Ana Aguilera – [Linkedin] (https://www.linkedin.com/in/ana-aguilera-morales-011b1a238/)

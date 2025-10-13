📚 Feminist Library

🎯 Objective:

The Feminist Library in our neighborhood wants to modernize. The project aims to develop a book management system that allows administrators to:

1. View the list of books.
   
2. Add new books.
  
3. Edit existing information.

4. Remove books from the catalog.

5. Search for books by title.

6. Search for books by authors.
   
7. Search for books by literary genre.

The entire process (creation, modification, and deletion of records) is carried out in Java, while the data is stored and displayed in a PostgreSQL database.
The application runs from the terminal, using an MVC architecture and the DAO design pattern.

💻 Technologies:

- Java 21

- PostgreSQL 17

- Apache Maven 3.13.0

- JUnit 5 for unit testing.

- Dotenv for environment variable management.

- JDBC Driver for connection with the database.

🔧 Tools:

- Visual Studio Code

- Git / GitHub

- Trello for task management and project tracking.

🛠️ Installation and Execution:

1️⃣ Clone the repository:

https://github.com/P7-FeministLibrary/P7_FeministLibrary.git

2️⃣ Set up the environment variables:

Create a .env file in the root of the project with your database credentials:

DB_HOST=localhost

DB_PORT=5432

DB_NAME=feminist_library

DB_USER=postgres

DB_PASSWORD=your_password

3️⃣ Compile the project:
mvn compile

4️⃣ Run the application:
mvn exec:java -Dexec.mainClass="com.feministlibrary.App"

🧩 Project Architecture:

The project follows the MVC (Model - View - Controller) pattern along with the DAO (Data Access Object) design.

![Captura de pantalla_10-10-2025_153945_chatgpt com](https://github.com/user-attachments/assets/305fa797-7697-424c-b966-afe9a16a6aba)

🗃️ Database:

PostgreSQL is used as a management system.

The main tables are:

author

<img width="1111" height="392" alt="Captura de pantalla 2025-10-12 131859" src="https://github.com/user-attachments/assets/bbb5aacd-1ca5-42ea-b720-b2d387fbaa37" />

book

<img width="1120" height="446" alt="Captura de pantalla 2025-10-12 132017" src="https://github.com/user-attachments/assets/718a9131-bf0a-44c6-95e6-89eb2ec46976" />


genre

<img width="1107" height="323" alt="Captura de pantalla 2025-10-12 132342" src="https://github.com/user-attachments/assets/4045fd28-e84f-44ef-ae74-da35f18899db" />


book_author (relación mani to mani)

<img width="1114" height="342" alt="Captura de pantalla 2025-10-12 132051" src="https://github.com/user-attachments/assets/d32e7739-182a-43b8-a493-665b37ba0aea" />

book_genre (relación mani to mani)

<img width="1104" height="326" alt="Captura de pantalla 2025-10-12 132247" src="https://github.com/user-attachments/assets/232ebe3d-944e-4c80-a702-02302083592d" />


Relaciones:

A book can have one or several authors.

A book can belong to one or several genres.

⚙️ Features:

✅ List all the books
✅ Add a new book
✅ Edit information of an existing book
✅ Delete a book
✅ Search for a book by title
✅ Search for a book by author
✅ Search for a book by literary genre
✅ Data visualization in console

Main menu

<img width="443" height="259" alt="Captura de pantalla 2025-10-10 150833" src="https://github.com/user-attachments/assets/d572970b-665c-4591-816c-5397d43a7ff8" />

Insertion of a book

<img w<img width="943" height="300" alt="Captura de pantalla 2025-10-10 151153" src="https://github.com/user-attachments/assets/bb48fcba-0dd3-4ac2-8638-0b27767e03a4" />


🧠 Example of Use

1️⃣ When running the application, a menu will be displayed in the terminal.
2️⃣ The user will be able to choose an option (view, add, edit, or delete books).
3️⃣ The data will be sent to the PostgreSQL database via JDBC.
4️⃣ The output will display confirmation or error messages depending on the action performed.

🧪 Tests

The project includes unit tests developed with JUnit 5 to validate CRUD operations and the database connection.

🤝 Team

👩‍💻 Stef – GitHub

👩‍💻 Suraya – GitHub

👩‍💻 Jashaira – GitHub

👩‍💻 Ana – GitHub

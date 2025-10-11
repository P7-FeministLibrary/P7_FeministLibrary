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

book

author

genre

book_author (relación mani to mani)

book_genre (relación mani to mani)

Relaciones:

A book can have one or several authors.

A book can belong to one or several genres.

⚙️ Features:

✅ List all the books
✅ Add a new book
✅ Edit information of an existing book
✅ Delete a book
✅ Buscar un libro por título
✅ Buscar un libro por autor  
✅ Buscar un libro por género literario
✅ Visualización de datos en consola


<img width="443" height="259" alt="Captura de pantalla 2025-10-10 150833" src="https://github.com/user-attachments/assets/d572970b-665c-4591-816c-5397d43a7ff8" />
<img w<img width="943" height="300" alt="Captura de pantalla 2025-10-10 151153" src="https://github.com/user-attachments/assets/bb48fcba-0dd3-4ac2-8638-0b27767e03a4" />



Menú principal

Inserción de un libro

Búsqueda por autor

Listado general

🧠 Ejemplo de Uso

1️⃣ Al ejecutar la aplicación, se mostrará un menú en la terminal.
2️⃣ El usuario podrá elegir una opción (ver, agregar, editar o eliminar libros).
3️⃣ Los datos se enviarán a la base de datos PostgreSQL mediante JDBC.
4️⃣ La salida mostrará mensajes de confirmación o error según la acción realizada.

🧪 Tests

El proyecto incluye pruebas unitarias desarrolladas con JUnit 5,
para validar las operaciones CRUD y la conexión a la base de datos.

🤝 Equipo

👩‍💻 Stef – GitHub

👩‍💻 Suraya – GitHub

👩‍💻 Jashaira – GitHub

👩‍💻 Ana – GitHub

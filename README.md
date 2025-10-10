📚 Biblioteca Feminista
🎯 Objetivo

La Biblioteca Feminista de nuestro barrio se quiere modernizar.
El proyecto tiene como meta desarrollar un sistema de gestión de libros que permita a los administradores:

1. Ver lista de libros
2. Añadir nuevos libros.
3. Editar información existente.
4. Eliminar libros del catálogo.
5. Buscar libros por título.
6. Buscar libros por autoras.
7. Buscar libros por género literario.
   
Todo el proceso (creación, modificación y eliminación de registros) se realiza desde Java,
mientras que los datos se almacenan y visualizan en una base de datos PostgreSQL.

La aplicación se ejecuta desde la terminal, utilizando una arquitectura MVC y el patrón de diseño DAO.

💻 Tecnologías

Java 21

PostgreSQL 17

Apache Maven 3.13.0

JUnit 5 para pruebas unitarias

Dotenv para gestión de variables de entorno

JDBC Driver para conexión con la base de datos

🔧 Herramientas

Visual Studio Code

Git / GitHub

Trello para la gestión de tareas y seguimiento del proyecto

🛠️ Instalación y Ejecución

1️⃣ Clonar el repositorio

https://github.com/P7-FeministLibrary/P7_FeministLibrary.git

2️⃣ Configurar las variables de entorno

Crea un archivo .env en la raíz del proyecto con tus credenciales de base de datos:

DB_HOST=localhost

DB_PORT=5432

DB_NAME=biblioteca_feminista

DB_USER=postgres

DB_PASSWORD=tu_contraseña

3️⃣ Compilar el proyecto
mvn compile

4️⃣ Ejecutar la aplicación
mvn exec:java -Dexec.mainClass="com.feministlibrary.App"

🧩 Arquitectura del Proyecto

El proyecto sigue el patrón MVC (Modelo - Vista - Controlador) junto con el diseño DAO (Data Access Object).

🗃️ Base de Datos

Se utiliza PostgreSQL como sistema gestor.

Las tablas principales son:

![Captura de pantalla_10-10-2025_153945_chatgpt com](https://github.com/user-attachments/assets/305fa797-7697-424c-b966-afe9a16a6aba)

book

author

genre

book_author (relación mani to mani)

book_genre (relación mani to mani)

Relaciones:

Un libro puede tener uno o varios autores.

Un libro puede pertenecer a uno o varios géneros.

⚙️ Funcionalidades

✅ Listar todos los libros
✅ Agregar un libro nuevo
✅ Editar información de un libro existente
✅ Eliminar un libro
✅ Buscar por título, autor o género literario
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

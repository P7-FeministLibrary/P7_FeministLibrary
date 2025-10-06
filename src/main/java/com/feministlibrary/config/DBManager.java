package com.feministlibrary.config;

import java.sql.Connection;
import java.sql.DriverManager;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.SQLException;

public class DBManager {

    private static final Dotenv dotenv = Dotenv.load();

    private static final String URL = "jdbc:postgresql://"
            + dotenv.get("DB_HOST") + ":"
            + dotenv.get("DB_PORT") + "/"
            + dotenv.get("DB_NAME");
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("You have successfully connected to The Matilda Library!");
        } catch (SQLException e) {
            System.err.println("Error when trying to connect: " + e.getMessage());
        }
    }


    /*
     * private static final String URL =
     * "jdbc:postgresql://localhost:5432/feministlibrary";
     * private static final String USER = "postgres";
     * private static final String PASS = "6789";
     * private static Connection connection;
     * 
     * public static Connection init() {
     * try {
     * connection = DriverManager.getConnection(URL, USER, PASS);
     * System.out.println("Conexión existosa");
     * } catch (Exception e) {
     * System.out.println(e.getMessage());
     * }
     * return connection;
     * }
     * 
     * public static void close() {
     * try {
     * connection.close();
     * System.out.println("Desconexión exitosa");
     * } catch (Exception e) {
     * System.out.println(e.getMessage());
     * }
     * 
     * }
     */
}

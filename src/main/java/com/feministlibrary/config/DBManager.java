package com.feministlibrary.config;

import java.sql.Connection;
import java.sql.DriverManager;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.SQLException;



public class DBManager {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try { 
                Dotenv dotenv = Dotenv.load();

            String url = "jdbc:postgresql://"
                        + dotenv.get("DB_HOST") + ":"
                        + dotenv.get("DB_PORT") + "/"
                        + dotenv.get("DB_NAME");
            String user = dotenv.get("DB_USER");
            String password = dotenv.get("DB_PASSWORD");

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("You have successfully connected to the Feminist Library");
        } catch (SQLException e) {
            System.err.println("Error when trying to connect to Feminist Library: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("General Error: " + e.getMessage());
        }
    } else {
        System.out.println("Connection established");
    }
        return connection;
    }

    public static void main(String[] args) {
        getConnection();
    }

    /*
    private static final String URL = "jdbc:postgresql://localhost:5432/feministlibrary";
    private static final String USER = "postgres";
    private static final String PASS = "6789";
    private static Connection connection;

    public static Connection init() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conexión existosa");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static void close() {
        try {
            connection.close();
            System.out.println("Desconexión exitosa");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    } */
}

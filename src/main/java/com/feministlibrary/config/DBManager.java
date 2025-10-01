package com.feministlibrary.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBManager {
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
            System.out.println("Desconxión exitosa");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

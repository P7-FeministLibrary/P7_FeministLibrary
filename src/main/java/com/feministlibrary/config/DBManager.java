package com.feministlibrary.config;

import java.sql.Connection;

public class DBManager {
    private static final String URL = "jdbc:postgresql://localhost:5423/feministlibrary";
   	private static final String USER = "postgres";
	private static final String PASS = "12345";
	private static Connection connection;
}

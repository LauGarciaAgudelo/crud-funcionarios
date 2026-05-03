package com.universidad.funcionarios.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

public class DatabaseConnection {

    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        try {
            Properties props = new Properties();

            InputStream input = DatabaseConnection.class
                    .getClassLoader()
                    .getResourceAsStream("db.properties");

            if (input == null) {
                throw new RuntimeException("No se encontró el archivo db.properties");
            }

            props.load(input);

            URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");

        } catch (Exception e) {
            throw new RuntimeException("Error cargando configuración de base de datos", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("Error conectando a la base de datos", e);
        }
    }
}
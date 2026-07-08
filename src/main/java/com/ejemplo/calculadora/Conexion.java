package com.ejemplo.calculadora;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL =
            "jdbc:mysql://localhost:3306/calculadora";

    private static final String USER = "root";
    private static final String PASSWORD = "Tapiero123";

    public static Connection conectar() {

        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
}

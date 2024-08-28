/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.multibahana.inventoryapp.config;

/**
 *
 * @author mohammadnuridin
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:postgresql://localhost:5432/inventory_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "asdf;lkj";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}

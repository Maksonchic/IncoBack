package com.inco.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Storage {
    private static Statement dbStatement = null;

    public static void initStorage() {
        initDb();
    }


    private static void initDb() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");

            Connection dbConnection = DriverManager.getConnection(
                    "jdbc:mariadb://45.133.18.174:3306/db_inco_test",
                    "testhot",
                    "Qwerty_1");
            dbStatement = dbConnection.createStatement();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static Statement getStatement() {
        return Storage.dbStatement;
    }
}

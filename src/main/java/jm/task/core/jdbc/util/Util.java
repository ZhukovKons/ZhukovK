package jm.task.core.jdbc.util;


import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

public class Util {
    private String LOGIN;
    private String PASSWORD;
    private String URL;
    private Connection connection;

    public Util(String login, String password, String url) {
        LOGIN = login;
        PASSWORD = password;
        URL = url;

        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);

            connection = DriverManager.getConnection(url,login,password);
        } catch (SQLException throwables) {
            System.out.println("ERROR CONNECT");
            throwables.printStackTrace();
        }

    }

    public Connection getConnection() {
        return connection;
    }
}

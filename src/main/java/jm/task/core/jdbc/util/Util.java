package jm.task.core.jdbc.util;


import com.mysql.fabric.jdbc.FabricMySQLDriver;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static String LOGIN = "root";
    private static String PASSWORD = "qweszxc";
    private static String URL = "jdbc:mysql://localhost:3306/mysqldbtest?useSSL=false";
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Driver driver = new FabricMySQLDriver();
                DriverManager.registerDriver(driver);
                connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                connection.setAutoCommit(false);
            }
        } catch (SQLException e) {
            System.out.println("Driver connection error!");
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        StandardServiceRegistry serviceRegistryBuilder = new StandardServiceRegistryBuilder().configure().build();
        Metadata metadata = new MetadataSources(serviceRegistryBuilder).getMetadataBuilder().build();
        final SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        return sessionFactory;
    }
}

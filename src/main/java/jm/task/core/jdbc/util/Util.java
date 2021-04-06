package jm.task.core.jdbc.util;


import com.mysql.fabric.jdbc.FabricMySQLDriver;
import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.spi.Mapping;


import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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
        Properties properties = new Properties();
        properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

        properties.setProperty(Environment.HBM2DDL_AUTO,"update");
        properties.setProperty(Environment.DRIVER, "com.mysql.jdbc.Driver");
        properties.setProperty(Environment.USER, LOGIN);
        properties.setProperty(Environment.PASS, PASSWORD);
        properties.setProperty(Environment.URL, URL);
        properties.setProperty(Environment.SHOW_SQL, "false");

        Configuration configuration = new Configuration().addProperties(properties);

        configuration.addAnnotatedClass(User.class);
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = null;
        try {
            sessionFactory = configuration.buildSessionFactory(serviceRegistryBuilder.build());
        }catch (IllegalStateException | HibernateException e){
            System.out.println("Driver connection error!");
        }

        return sessionFactory;
    }
}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package jm.task.core.jdbc.service;

import java.sql.Connection;
import java.util.List;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

public class UserServiceImpl implements UserService {
    private static final String LOGIN = "root";
    private static final String PASSWORD = "qweszxc";
    private static final String URL = "jdbc:mysql://localhost:3306/mysqldbtest?useSSL=false&serverTimezone=UTC";
    private Connection connection;
    private UserDaoJDBCImpl daoJDBC = new UserDaoJDBCImpl();

    public UserServiceImpl() {
        Util util = new Util(LOGIN, PASSWORD, URL);
        daoJDBC.setConnection(util.getConnection());
    }

    public void createUsersTable() {
        this.daoJDBC.createUsersTable();
    }

    public void dropUsersTable() {
        this.daoJDBC.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        daoJDBC.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        this.daoJDBC.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return this.daoJDBC.getAllUsers();
    }

    public void cleanUsersTable() {
        this.daoJDBC.cleanUsersTable();
    }
}

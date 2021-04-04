//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package jm.task.core.jdbc.service;

import java.sql.Connection;
import java.util.List;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

public class UserServiceImpl implements UserService {

    private Connection connection;
    private UserDao daoJDBC = new UserDaoJDBCImpl();
    //private UserDao daoJDBC = new UserDaoHibernateImpl();

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

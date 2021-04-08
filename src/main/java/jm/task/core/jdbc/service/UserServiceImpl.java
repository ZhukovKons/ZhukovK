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

    //private UserDao userDao = new UserDaoJDBCImpl();
    private UserDao userDao = new UserDaoHibernateImpl();

    public void createUsersTable() {
        this.userDao.createUsersTable();
    }

    public void dropUsersTable() {
        this.userDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        this.userDao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return this.userDao.getAllUsers();
    }

    public void cleanUsersTable() {
        this.userDao.cleanUsersTable();
    }
}

package jm.task.core.jdbc.dao;

import com.fasterxml.classmate.AnnotationConfiguration;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = null;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

    }

    @Override
    public void dropUsersTable() {

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        ArrayList listUsers = new ArrayList();

        // todo listArray
        if (!listUsers.isEmpty()) listUsers.add(new User());
        return listUsers;
    }

    @Override
    public void cleanUsersTable() {

    }

    public void setConnection(Connection connection) {
        // todo sessionFactory = new AnnotationConfiguratio;
    }
}

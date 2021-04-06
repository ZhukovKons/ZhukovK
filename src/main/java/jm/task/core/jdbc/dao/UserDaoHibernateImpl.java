package jm.task.core.jdbc.dao;

import com.fasterxml.classmate.AnnotationConfiguration;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            if (session.createNativeQuery("create table IF NOT EXISTS users " +
                    "(id bigint not null, " +
                    "age int not null, " +
                    "lastName varchar(50) not null, " +
                    "name varchar(50) not null, " +
                    "primary key (id))")
                    .executeUpdate() > 0) {
                session.getTransaction().commit();
            } else {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            if (session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate() > 0) {
                session.getTransaction().commit();
            } else {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

//            User user = new User();
//            user.setName(name);
//            user.setLastName(lastName);
//            user.setAge(age);
            if ((long) session.save(new User(name, lastName, age)) > 0) {
                session.getTransaction().commit();
                System.out.println(String.format(" User с именем – %s добавлен в базу данных ", name));
            } else {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(session.get(User.class, (long) id));
            if (session.get(User.class, (long) id) == null) {
                session.getTransaction().commit();
            } else {
                session.getTransaction().rollback();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Dont remove User by Id");
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            /*session.createSQLQuery("DELETE FROM User");
            System.out.println("!!!! removeUserById");
            session.beginTransaction().commit();*/
        }
    }
}

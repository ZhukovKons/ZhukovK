package jm.task.core.jdbc.dao;

import com.fasterxml.classmate.AnnotationConfiguration;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {  //todo Класс не рабоатет. Ушёл на 15 минут.
    private final SessionFactory sessionFactory = Util.getSessionFactory();

    @Override
    public void createUsersTable() {

    }

    @Override
    public void dropUsersTable() {

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();

            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            if ((long) session.save(user) > 0) {
                session.getTransaction().commit();
                session.clear();
                System.out.println(String.format(" User с именем – %s добавлен в базу данных ", name));
            } else {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.delete(session.get(User.class, (long) id));
            if (session.get(User.class, (long) id) == null) {
                session.beginTransaction().commit();
            } else {
                session.beginTransaction().rollback();
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
    public void cleanUsersTable() {  // Сюда не смотреть
        try (Session session = sessionFactory.openSession()) {
            /*session.createSQLQuery("DELETE FROM User");
            System.out.println("!!!! removeUserById");
            session.beginTransaction().commit();*/
        }
    }
}

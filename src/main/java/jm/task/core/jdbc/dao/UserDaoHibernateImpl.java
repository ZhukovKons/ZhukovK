package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            if (session.createNativeQuery("create table IF NOT EXISTS users " +
                    "(id int not null auto_increment, " +
                    "age int not null, " +
                    "lastName varchar(50) not null, " +
                    "name varchar(50) not null, " +
                    "primary key (id))")
                    .executeUpdate() > 0) {
                session.getTransaction().commit();
            } else {
                session.getTransaction().rollback();
            }
        }catch (NullPointerException n){
            System.out.println("Не удалось создать БД");
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
        }catch (NullPointerException n){
            System.out.println("Не удалось удалить БД");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            if ((long) session.save(new User(name, lastName, age)) > 0) {
                session.getTransaction().commit();
                System.out.println(String.format(" User с именем – %s добавлен в базу данных ", name));
            } else {
                session.getTransaction().rollback();
            }
        }catch (NullPointerException n){
            System.out.println("Не удалось сохранить пользователя: " + name);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            if (session.get(User.class, id) == null) {
                session.getTransaction().commit();
                session.flush();
            } else {
                session.getTransaction().rollback();
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("Dont remove User by Id");
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).list();
        }catch (NullPointerException n){
            System.out.println("Не удалось сохранить пользователя: ");
            return new ArrayList<User>(1);
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            if(session.createSQLQuery("TRUNCATE TABLE users").executeUpdate() > 0) {
                session.getTransaction().commit();
            }else{
                session.getTransaction().rollback();
            }
        }catch (NullPointerException n){
            System.out.println("Не удалось очистить таблицу");
        }
    }
}

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
        runExecute("create table IF NOT EXISTS users " +
                "(id int not null auto_increment, " +
                "age int not null, " +
                "lastName varchar(50) not null, " +
                "name varchar(50) not null, " +
                "primary key (id))", 0);
    }

    @Override
    public void dropUsersTable() {
        runExecute("DROP TABLE IF EXISTS users", 0);
    }

    @Override
    public void cleanUsersTable() {
        runExecute("TRUNCATE TABLE users", 0);
    }

    @Override
    public void removeUserById(long id) {
        if (runExecute("delete from users where id=" + id, id) == 0)
            System.out.println("Не удалось найти пользователя id: " + id);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println(" User с именем – " + name + " добавлен в базу данных ");
        } catch (Exception n) {
            session.getTransaction().rollback();
            System.out.println("Не удалось сохранить пользователя: " + name);
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).list();
        } catch (NullPointerException n) {
            System.out.println("Не удалось сохранить пользователя: ");
            return new ArrayList<>(1);
        }
    }

    private int runExecute(String hql, long id) {
        Session session = null;
        int returnResulatat = 0;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            returnResulatat = session.createNativeQuery(hql).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            switch (Thread.currentThread().getStackTrace()[2].getMethodName()) {
                case "removeUserById":
                    System.out.println("Ошибка удаления пользователя id:" + id);
                    break;
                case "createUsersTable":
                    System.out.println("Не удалось создать БД");
                    break;
                case "cleanUsersTable":
                    System.out.println("Не удалось удалить БД");
                    break;
                case "dropUsersTable":
                    System.out.println("Не удалось очистить таблицу");
                    break;

            }
        } finally {
            if (session != null) session.close();
        }
        return returnResulatat;
    }
}

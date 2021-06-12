package jm.task.core.jdbc.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection = Util.getConnection();

    public void createUsersTable() {
        runExecute("CREATE TABLE users ( "
                + "id INT NOT NULL AUTO_INCREMENT, "
                + "name VARCHAR(50) NOT NULL,"
                + "lastName VARCHAR(50) NOT NULL,age INT NOT NULL, "
                + "PRIMARY KEY (id))");
    }

    public void dropUsersTable() {
        runExecute("drop table users");
    }

    public void saveUser(String name, String lastName, byte age) {
        String stringSQL = "insert into users (name, lastName, age) values (?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(stringSQL)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
            commit();
            System.out.println(String.format(" User с именем – %s добавлен в базу данных ", name));
        } catch (NullPointerException | SQLException e) {
            System.out.println("! Пользователь не сохранён");
            rollback();
        }
    }

    public void removeUserById(long id) {
        String stringSQL = "delete from users where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(stringSQL)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            commit();
        } catch (NullPointerException | SQLException e) {
            System.out.println("! Не удалось удалить пользователя id:" + id);
            rollback();
        }
    }

    public List<User> getAllUsers() {
        ArrayList listUsers = new ArrayList();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from users");

            while (resultSet.next()) {
                User u = new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4));
                u.setId(resultSet.getLong(1));
                listUsers.add(u);
            }
        } catch (SQLException var7) {
            System.out.println("! Ошибка получения списка пользователей");
        }
        if (listUsers.isEmpty()) listUsers.add(new User());
        return listUsers;
    }

    public void cleanUsersTable() {
        runExecute("delete from users");
    }

    private boolean runExecute(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            commit();
            return true;
        } catch (NullPointerException | SQLException e) {
            System.out.println("Ошибка выполнений: " + Thread.currentThread().getStackTrace()[2]);
            rollback();
            return false;
        }
    }

    private void commit() throws SQLException {
        connection.commit();
    }

    private void rollback() {
        try {
            connection.rollback();
        } catch (SQLException throwables) {}            //ignore
    }
}

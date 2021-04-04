//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package jm.task.core.jdbc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() {
    }

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

        try (PreparedStatement statement = Util.getConnection().prepareStatement(stringSQL)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
            commit();
            System.out.println(String.format(" User с именем – %s добавлен в базу данных ", name));
        } catch (NullPointerException | SQLException e) {
            printError("DONT saved");
            rollback();
        }

    }

    public void removeUserById(long id) {
        String stringSQL = "delete from users where id = ?";

        try (PreparedStatement statement = Util.getConnection().prepareStatement(stringSQL)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            commit();
        } catch (NullPointerException | SQLException e) {
            printError("DONT remove");
            rollback();
        }
    }

    public List<User> getAllUsers() {
        ArrayList listUsers = new ArrayList();

        try (Statement statement = Util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from users");

            while (resultSet.next()) {
                User u = new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4));
                u.setId(resultSet.getLong(1));
                listUsers.add(u);
            }
        } catch (SQLException var7) {
            printError("DONT RRESULT");
        }
        // if(!listUsers.isEmpty())  listUsers.add(new User());

        return listUsers;
    }

    public void cleanUsersTable() {
        runExecute("delete from users");
    }

    private boolean runExecute(String sql) {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(sql);
            commit();
            return true;
        } catch (NullPointerException | SQLException e) {
            printError("RUN: " + Thread.currentThread().getStackTrace()[2].getMethodName() + " ");
            rollback();
            return false;
        }
    }
    private void commit() throws SQLException {
        Util.getConnection().commit();
    }

    private void rollback(){
        try {
            Util.getConnection().rollback();
        } catch (SQLException throwables) {             //ignore
        }
    }


    private void printError(String message) {
        char esc = 27;
        String error = esc + "[31mERROR: " + esc + "[0m";
        String m = esc + "[32m" + message + esc + "[0m";
        String urlError = " | " + esc + "[35m" + Thread.currentThread().getStackTrace()[2] + esc + "[0m";
        //System.out.println(error + m + urlError);
    }
}

package jm.task.core.jdbc.dao;

import com.mysql.cj.jdbc.ConnectionImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {


            statement.executeUpdate("DROP TABLE IF EXISTS users");
            statement.executeUpdate("CREATE TABLE users (user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, first_name VARCHAR(45) NOT NULL, second_name VARCHAR(45) NOT NULL, age INT NOT NULL )");

        } catch (SQLException e) {

            System.out.println("Не удалось создать таблицу пользователей");
            throw new RuntimeException();
        }
    }

    public void dropUsersTable() {

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DROP TABLE IF EXISTS users");

        } catch (SQLException e) {
            System.out.println("Не удалось удалить таблицу пользователей");
            throw new RuntimeException();

        }
    }

    public void saveUser(String name, String lastName, byte age) {

        final String sqlSaveUser = "INSERT INTO users (first_name, second_name, age) VALUES (?, ?, ?)";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSaveUser)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Не удалось добавить пользователей в таблицу");
            throw new RuntimeException();
        }
    }

    public void removeUserById(long id) {
        final String sqlRemoveUser = "DELETE FROM users where user_id = ?";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlRemoveUser)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {

            System.out.println("Не удалось удалить пользователя из таблицы");
            throw new RuntimeException();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new LinkedList<>();

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery("Select * FROM users");
            while (rs.next()) {

                userList.add(new User(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getByte(4)
                ));

            }
            return userList;

        } catch (SQLException e) {

            System.out.println("Не удалось вывести пользователей");
            e.printStackTrace();
            return null;
        }

    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("TRUNCATE TABLE users");

        } catch (SQLException e) {
            System.out.println("Не удалось очистить таблицу");
            throw new RuntimeException();
        }

    }

}




package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
    }

    public void createUsersTable() {

        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            statement.executeUpdate("CREATE TABLE users (user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, first_name VARCHAR(45) NOT NULL, second_name VARCHAR(45) NOT NULL, age INT NOT NULL )");
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Не удалось создать таблицу пользователей");
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    throw new RuntimeException();
                }
            }
        }
    }

    public void dropUsersTable() {

        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Не удалось удалить таблицу пользователей");
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    throw new RuntimeException();
                }
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        final String sqlSaveUser = "INSERT INTO users (first_name, second_name, age) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSaveUser)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("Не удалось добавить пользователей в таблицу");
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    throw new RuntimeException();
                }
            }
        }
    }

    public void removeUserById(long id) {
        final String sqlRemoveUser = "DELETE FROM users where user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRemoveUser)) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {

            System.out.println("Не удалось удалить пользователя из таблицы");
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    throw new RuntimeException();
                }
            }
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
                        rs.getByte(4)));
            }

        } catch (SQLException e) {
            System.out.println("Не удалось вывести пользователей");
            e.printStackTrace();
            return null;
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("DELETE FROM users");
                    connection.commit();
        } catch (SQLException e) {
            System.out.println("Не удалось очистить таблицу");
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    throw new RuntimeException();
                }
            }
        }

    }
}




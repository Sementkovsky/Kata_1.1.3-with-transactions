package jm.task.core.jdbc.util;


import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static String URL = "jdbc:mysql://localhost:3306/kata_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Futurama";


    public static Connection getConnection() throws SQLException {

        Connection connection;
        Driver driver = new com.mysql.cj.jdbc.Driver();
        DriverManager.registerDriver(driver);

        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return connection;

    }
}

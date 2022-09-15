package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        User user1 = new User("Isaak", "Asimov", (byte) 56);
        User user2 = new User("Ray", "Brudberry", (byte) 50);
        User user3 = new User("Boris", "Strugatskiy", (byte) 49);
        User user4 = new User("Alexander", "Beljaev", (byte) 44);
        List<User> userList = new ArrayList<>(Arrays.asList(user1, user2, user3, user4));

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        for (User user : userList) {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        }
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}



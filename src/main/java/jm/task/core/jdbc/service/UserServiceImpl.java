package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao implementation = new UserDaoJDBCImpl();

    public void createUsersTable() {
        implementation.createUsersTable();
    }

    public void dropUsersTable() {
        implementation.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        implementation.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        implementation.removeUserById(id);
    }

    public List<User> getAllUsers() {
       return implementation.getAllUsers();
    }

    public void cleanUsersTable() {
        implementation.cleanUsersTable();
    }
}

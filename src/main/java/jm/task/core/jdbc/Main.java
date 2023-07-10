package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Name 1", "Last Name 1", (byte) 15);
        userService.saveUser("Name 2", "Last Name 2", (byte) 17);
        userService.saveUser("Name 3", "Last Name 3", (byte) 50);
        userService.saveUser("Name 4", "Last Name 4", (byte) 34);

        List<User> userList = userService.getAllUsers();
        System.out.println(userList);

        userService.cleanUsersTable();
        userService.dropUsersTable();


    }
}

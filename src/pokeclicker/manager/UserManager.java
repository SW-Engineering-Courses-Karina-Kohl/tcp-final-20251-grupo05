package pokeclicker.manager;

import pokeclicker.database.UserDB;
import pokeclicker.model.User;

public class UserManager {

    private UserManager() {
    }

    public static boolean createUser(String name) {
        if (UserDB.getUser(name) != null) {
            System.out.println("User already exists!");
            return false;
        }
        User user = new User(name, 1.0, 0, null);
        UserDB.insertUser(user);
        return true;
    }

    public static User updateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (UserDB.getUser(user.getName()) == null) {
            throw new IllegalArgumentException("User does not exist");
        }

        User updatedUser = UserDB.updateUser(user);

        return updatedUser;
    }

    public static User getUser(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        User user = UserDB.getUser(name);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return user;
    }

    public static void deleteUser(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        User user = UserDB.getUser(name);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        UserDB.deleteUser(name);
    }



}
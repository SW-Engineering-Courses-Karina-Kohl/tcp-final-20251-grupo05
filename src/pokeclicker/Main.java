package pokeclicker;

import pokeclicker.manager.UserManager;
import pokeclicker.model.User;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Creating user: Rodrigo");
            User rodrigo = UserManager.createUser("Rodrigo");
            System.out.println("User created: " + rodrigo.getName() + " - Money: " + rodrigo.getMoney());

            System.out.println("Creating user: Carol");
            User carol = UserManager.createUser("Carol");
            System.out.println("User created: " + carol.getName() + " - Money: " + carol.getMoney());

            System.out.println("Trying to create duplicate user: Rodrigo");
            try {
                UserManager.createUser("Rodrigo");
            } catch (IllegalArgumentException e) {
                System.out.println("Expected error: " + e.getMessage());
            }

            System.out.println("\nRegistered users list:");
            for (User u : UserManager.getAllUsers()) {
                System.out.println("User: " + u.getName() + " - Money: " + u.getMoney());
            }

            System.out.println("\nSearching user by name: Carol");
            User found = UserManager.getUserByName("Carol");
            if (found != null) {
                System.out.println("Found: " + found.getName() + " - Money: " + found.getMoney());
            } else {
                System.out.println("User not found.");
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
package pokeclicker;

import java.io.*;
import java.util.List;

public class UserManager {
    private List<User> users;
    private static final String FILE_PATH = "users.txt";

    public void registerUser(String name) throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.createNewFile();
        }

        if (isUserInFile(name)) {
            throw new IllegalArgumentException("User already exists");
        }

        User newUser = new User(name);
        users.add(newUser);

        try (FileWriter fw = new FileWriter(FILE_PATH, true);
                BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(name);
            bw.newLine();
        }
    }

    private boolean isUserInFile(String name) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public User getUserByName(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

}

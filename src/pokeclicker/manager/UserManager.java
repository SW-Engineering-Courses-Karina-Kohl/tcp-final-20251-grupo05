package pokeclicker.manager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import pokeclicker.PokemonFogo;
import pokeclicker.model.User;
import pokeclicker.model.pokemon.Pokemon;

public class UserManager {
    private List<User> users;
    private static final String FILE_PATH = "users.txt";

    public UserManager() {
        this.users = loadUsersFromFile();
    }

    private List<User> loadUsersFromFile() {
        List<User> userList = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return userList;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                String favPokemonName = parts.length > 1 && !"null".equals(parts[1]) ? parts[1] : null;
                double money = (parts.length > 2 && !"null".equals(parts[2])) ? Double.parseDouble(parts[2]) : 0.0;

                User user = new User(name);
                if (favPokemonName != null) {
                    // LEMBRAR: metodo buscarPokemonPorNome deve ser implementado
                    // Pokemon favPokemon = buscarPokemonPorNome(favPokemonName);
                    // user.setFavPokemon(favPokemon);
                }
                user.earnMoney(money);
                userList.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void registerUser(String name, Pokemon favPokemon, double money) throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.createNewFile();
        }

        if (isUserInFile(name)) {
            throw new IllegalArgumentException("User already exists");
        }

        String favPokemonName = (favPokemon == null) ? "null" : favPokemon.getName();
        String moneyStr = String.valueOf(money);

        User newUser = new User(name);
        newUser.setFavPokemon(favPokemon);
        newUser.earnMoney(money);
        users.add(newUser);

        try (FileWriter fw = new FileWriter(FILE_PATH, true);
                BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(name + "," + favPokemonName + "," + moneyStr);
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

    public List<User> getAllUsers() {
        return users;
    }

    public void checkPC(String userName) {
        User user = getUserByName(userName);
        if (user == null) {
            System.out.println("User not found: " + userName);
            return;
        }
        List<Pokemon> pokemons = user.getPokemons();
        if (pokemons.isEmpty()) {
            System.out.println("PC is empty.");
        } else {
            System.out.println("Pokémons in " + userName + "'s PC:");
            for (Pokemon p : pokemons) {
                System.out.println("- " + p.getName());
            }
        }
    }

    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        Pokemon charmander = new PokemonFogo("Charmander", 36);
        try {
            // Teste de cadastro
            userManager.registerUser("Ash", charmander, 100.0);
            userManager.registerUser("Misty", null, 50.0);

            // Teste de busca
            System.out.println("Usuário Ash: " + userManager.getUserByName("Ash"));
            System.out.println("Usuários cadastrados: " + userManager.getAllUsers());

            // Teste de verificação do PC
            userManager.checkPC("Ash");
            userManager.checkPC("Misty");
            userManager.checkPC("Brock"); // Usuário não cadastrado

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package pokeclicker;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pokeclicker.database.UserDB;
import pokeclicker.database.AbilityDB;
import pokeclicker.database.PokemonDB;
import pokeclicker.database.ItemDB;
import pokeclicker.game.Shop;
import pokeclicker.manager.ability.AbilityManager;
import pokeclicker.manager.item.ItemManager;
import pokeclicker.manager.UserManager;
import pokeclicker.manager.pokemon.PokemonManager;
import pokeclicker.manager.ShopManager;
import pokeclicker.model.Ability;
import pokeclicker.model.User;
import pokeclicker.model.common.PokeType;
import pokeclicker.database.PokemonAbilityDB;
import pokeclicker.model.item.Item;
import pokeclicker.model.item.ItemType;
import pokeclicker.model.pokemon.Pokemon;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        try {
            Parent root = FXMLLoader.load(Main.class.getResource("/pokeclicker/views/initialScene.fxml"));
            Scene scene = new Scene(root);

            String cssPath = this.getClass().getResource("/css/initialScene.css").toExternalForm();
            scene.getStylesheets().add(cssPath);

            // Set window icon
            Image windowIcon = new Image("/img/windowIcon.png");
            stage.getIcons().add(windowIcon);
            scene.setFill(Color.BLUE);
            // Set title
            stage.setTitle("PokeClicker - Click and evolve your Pokemons!");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw e;
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UserDB.createUserTable();
        AbilityDB.createAbilityTable();
        PokemonDB.createPokemonTable();
        PokemonAbilityDB.createPokemonAbilityTable();
        ItemDB.createItemTable();

        while (true) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Criar usuário");
            System.out.println("2. Listar usuários");
            System.out.println("3. Criar habilidade");
            System.out.println("4. Listar habilidades");
            System.out.println("5. Criar Pokémon");
            System.out.println("6. Listar Pokémons");
            System.out.println("7. Criar item");
            System.out.println("8. Listar itens");
            System.out.println("9. Comprar no Shop");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            int op = Integer.parseInt(scanner.nextLine());

            try {
                switch (op) {
                    case 1 -> {
                        System.out.print("Nome do usuário: ");
                        String nome = scanner.nextLine();
                        boolean criado = UserManager.createUser(nome);
                        System.out.println(criado ? "Usuário criado!" : "Usuário já existe.");
                    }
                    case 2 -> {
                        System.out.println("Usuários:");
                        String name = scanner.nextLine();
                        System.out.println(UserManager.getUser(name));
                    }
                    case 3 -> {
                        System.out.print("Nome da habilidade: ");
                        String nome = scanner.nextLine();
                        System.out.print("Descrição: ");
                        String desc = scanner.nextLine();
                        System.out.print("Tipo (FIRE/WATER/GRASS): ");
                        PokeType tipo = PokeType.valueOf(scanner.nextLine().toUpperCase());
                        System.out.print("Dano: ");
                        double dano = Double.parseDouble(scanner.nextLine());
                        System.out.print("Cura: ");
                        double cura = Double.parseDouble(scanner.nextLine());
                        Ability a = AbilityManager.createAbility(nome, desc, tipo, dano, cura);
                        System.out.println("Criada: " + a);
                    }
                    case 4 -> {
                        System.out.println("Habilidades:");
                        for (var a : AbilityManager.getAllAbilities(Optional.empty())) {
                            System.out.println(a);
                        }
                    }
                    case 5 -> {
                        System.out.print("Nome do Pokémon: ");
                        String nome = scanner.nextLine();
                        System.out.print("Vida total: ");
                        int vida = Integer.parseInt(scanner.nextLine());
                        System.out.print("Tipo (FIRE/WATER/GRASS): ");
                        PokeType tipo = PokeType.valueOf(scanner.nextLine().toUpperCase());
                        System.out.print("Preço: ");
                        double preco = Double.parseDouble(scanner.nextLine());
                        Pokemon p = PokemonManager.createPokemon(nome, vida, List.of(), preco, tipo);
                        System.out.println("Criado: " + p);
                    }
                    case 6 -> {
                        System.out.println("Pokémons:");
                        for (Pokemon p : PokemonManager.getAllPokemons(Optional.empty())) {
                            System.out.println(p);
                        }
                    }
                    case 7 -> {
                        System.out.print("Nome do item: ");
                        String nome = scanner.nextLine();
                        System.out.print("Preço: ");
                        double preco = Double.parseDouble(scanner.nextLine());
                        System.out.print("Descrição: ");
                        String desc = scanner.nextLine();
                        System.out.print("Tipo (MONEY_MULTIPLIER/POKEMON): ");
                        ItemType tipo = ItemType.valueOf(scanner.nextLine().toUpperCase());
                        System.out.print("Dano ou multiplicador: ");
                        double valor = Double.parseDouble(scanner.nextLine());
                        Item item = ItemManager.createItem(nome, preco, desc, tipo, valor);
                        System.out.println("Criado: " + item);
                    }
                    case 8 -> {
                        System.out.println("Itens:");
                        for (Item i : ItemManager.getAllItems(Optional.empty())) {
                            System.out.println(i);
                        }
                    }
                    case 9 -> {
                        System.out.print("Nome do usuário: ");
                        String nome = scanner.nextLine();
                        User user = UserManager.getUser(nome);
                        Shop shop = ShopManager.getShop(user);
                        user.setMoney(10000000000.0);
                        System.out.println("Itens/Pokémons disponíveis para compra:");
                        int idx = 1;
                        for (var p : shop.getPurchasables()) {
                            System.out.println(idx + ". " + p);
                            idx++;
                        }
                        System.out.print("Escolha o número para comprar: ");
                        int escolha = Integer.parseInt(scanner.nextLine());
                        var lista = shop.getPurchasables();
                        if (escolha > 0 && escolha <= lista.size()) {
                            ShopManager.buyPokemonOrItem(user, shop, lista.get(escolha - 1));
                            System.out.println("Compra realizada!");
                        } else {
                            System.out.println("Opção inválida.");
                        }
                    }
                    case 0 -> {
                        System.out.println("Saindo...");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        launch(args);
    }
}
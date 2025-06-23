package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import pokeclicker.database.*;
import pokeclicker.manager.ability.AbilityFilter;
import pokeclicker.manager.item.ItemFilter;
import pokeclicker.model.Ability;
import pokeclicker.model.User;
import pokeclicker.model.common.PokeType;
import pokeclicker.model.item.ItemType;
import pokeclicker.model.item.MoneyMultiplierItem;
import pokeclicker.model.pokemon.FirePokemon;
import pokeclicker.model.pokemon.LevelType;

public class TestUtils {
    public static final String TEST_DB_URL = "jdbc:sqlite:test.db";

    public static void setupTestDatabase() throws SQLException {
        SQLiteConnection.setUrl(TEST_DB_URL);
        cleanTestDatabase();
    }

    public static void cleanTestDatabase() throws SQLException {
        try (Connection conn = SQLiteConnection.connect();
                Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS user");
            stmt.execute("DROP TABLE IF EXISTS pokemon");
            stmt.execute("DROP TABLE IF EXISTS item");
            stmt.execute("DROP TABLE IF EXISTS ability");
            stmt.execute("DROP TABLE IF EXISTS pokemonAbility");

            UserDB.createUserTable();
            PokemonDB.createPokemonTable();
            ItemDB.createItemTable();
            AbilityDB.createAbilityTable();
            PokemonAbilityDB.createPokemonAbilityTable();
        }
    }

    public static void resetDatabaseUrl() {
        SQLiteConnection.setUrl("jdbc:sqlite:game.db");
    }

    public static Connection getConnection() throws SQLException {
        return SQLiteConnection.connect();
    }

    public static void insertTestAbility() throws SQLException {
        Ability ability = new Ability(
            "Fireball", 
            "Throws a huge fireball",
            PokeType.FIRE ,
            20.0, 
            0.0
        );
        AbilityDB.insertAbility(ability);
    }

    public static AbilityFilter getTestAbilityFilter() {
        AbilityFilter abilityFilter = new AbilityFilter();
        abilityFilter.setType(PokeType.FIRE);
        abilityFilter.setMinDamage(10.0);
        abilityFilter.setMaxDamage(50.0);
        abilityFilter.setMinCure(0.0);
        abilityFilter.setMaxCure(20.0);
        return abilityFilter;
    }

    public static void insertTestItem() throws SQLException {
        MoneyMultiplierItem item = new MoneyMultiplierItem(
            "Double",           
            50.0,               
            "Double the users money", 
            2.0                 
        );
        ItemDB.insertItem(item, "testUser");    
    }

    public static ItemFilter getTestAllItemsFilter() throws SQLException {
        ItemFilter filter = new ItemFilter();
        filter.setType(ItemType.MONEY_MULTIPLIER);
        filter.setMinPrice(10.0);
        filter.setMaxPrice(100.0);
        filter.setNameContains("Dou");
        filter.setAvailable(true);
        filter.setDescriptionContains("money");
        filter.setUser("testUser");
        return filter;
    }

    public static void insertTestPokemon() throws SQLException {
        FirePokemon firePokemon = new FirePokemon(
            "Charmander",        
            LevelType.BEGINNER,        
            0.0,                   
            40,                     
            40,                     
            true,                  
            200.0,                 
            "src/img/charmander.png"
        );
        PokemonDB.insertPokemon(firePokemon, "testUser");
    }

    public static void insertUser() throws SQLException {
        FirePokemon firePokemon = new FirePokemon(
            "Charmander",        
            LevelType.BEGINNER,        
            0.0,                   
            40,                     
            40,                     
            true,                  
            200.0,                 
            "src/img/charmander.png"
        );
        User user = new User("testUser", 2.0, 1000.0, firePokemon);
        UserDB.insertUser(user);
    }
}

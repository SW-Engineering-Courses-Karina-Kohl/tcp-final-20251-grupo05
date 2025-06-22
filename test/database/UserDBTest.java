package test.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pokeclicker.model.User;
import pokeclicker.model.pokemon.Pokemon;
import test.TestUtils;
import pokeclicker.database.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class UserDBTest {
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        TestUtils.setupTestDatabase();
        connection = TestUtils.getConnection();
    }

    @AfterEach
    void tearDown() throws SQLException {
        //TestUtils.cleanTestDatabase();
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        TestUtils.resetDatabaseUrl();
    }

    @Test
    void testCreateUserTable() throws SQLException {
        User testUser = new User("testUser", 1.0, 100.0, null);
        UserDB.insertUser(testUser);

        User retrieved = UserDB.getUser("testUser");
        assertNotNull(retrieved, "Table should exist and allow user insertion");
        assertEquals("testUser", retrieved.getName());
    }

    @Test
    void testInsertAndGetUser() {
        User testUser = new User("testUser", 1.5, 200.0, null);

        UserDB.insertUser(testUser);
        User retrieved = UserDB.getUser("testUser");

        assertNotNull(retrieved, "User should be retrievable after insertion");
        assertEquals("testUser", retrieved.getName());
        assertEquals(1.5, retrieved.getMoneyMultiplier());
        assertEquals(200.0, retrieved.getMoney());
        assertNull(retrieved.getFavoritePokemon());
    }
}
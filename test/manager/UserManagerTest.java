package test.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.TestUtils;
import pokeclicker.database.UserDB;
import pokeclicker.model.User;
import pokeclicker.manager.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    @BeforeEach
    void setUp() throws SQLException {
        TestUtils.setupTestDatabase();
    }

    @AfterEach
    void tearDown() throws SQLException {
        TestUtils.cleanTestDatabase();
        TestUtils.resetDatabaseUrl();
    }

    @Test
    void testCreateUser_Success() {
        User user = UserManager.createUser("testUser");

        assertNotNull(user);
        assertEquals("testUser", user.getName());
        assertEquals(1.0, user.getMoneyMultiplier());
        assertEquals(0, user.getMoney());
        assertNull(user.getFavoritePokemon());

        // Verify user exists in DB
        User dbUser = UserDB.getUser("testUser");
        assertNotNull(dbUser);
    }

    @Test
    void testCreateUser_EmptyName() {
        assertThrows(IllegalArgumentException.class,
                () -> UserManager.createUser(""),
                "Should throw for empty username");

        assertThrows(IllegalArgumentException.class,
                () -> UserManager.createUser(null),
                "Should throw for null username");
    }

    @Test
    void testCreateUser_DuplicateName() {
        UserManager.createUser("duplicateUser");

        assertThrows(IllegalArgumentException.class,
                () -> UserManager.createUser("duplicateUser"),
                "Should throw for duplicate username");
    }

    @Test
    void testUpdateUser_Success() {
        // Create initial user
        UserManager.createUser("updateUser");
        User originalUser = UserManager.getUser("updateUser");

        // Modify user
        User modifiedUser = new User("updateUser", 2.0, 100.0, null);
        User updatedUser = UserManager.updateUser(modifiedUser);

        // Verify updates
        assertNotNull(updatedUser);
        assertEquals("updateUser", updatedUser.getName());
        assertEquals(2.0, updatedUser.getMoneyMultiplier());
        assertEquals(100.0, updatedUser.getMoney());

        // Verify DB was updated
        User dbUser = UserManager.getUser("updateUser");
        assertEquals(2.0, dbUser.getMoneyMultiplier());
        assertEquals(100.0, dbUser.getMoney());
    }

    @Test
    void testUpdateUser_NullUser() {
        assertThrows(IllegalArgumentException.class,
                () -> UserManager.updateUser(null),
                "Should throw for null user");
    }

    @Test
    void testUpdateUser_NonExistent() {
        User nonExistentUser = new User("ghost", 1.0, 0, null);

        assertThrows(IllegalArgumentException.class,
                () -> UserManager.updateUser(nonExistentUser),
                "Should throw for non-existent user");
    }

    @Test
    void testGetUser_Success() {
        UserManager.createUser("getUserTest");
        User user = UserManager.getUser("getUserTest");

        assertNotNull(user);
        assertEquals("getUserTest", user.getName());
    }

    @Test
    void testGetUser_EmptyName() {
        assertThrows(IllegalArgumentException.class,
                () -> UserManager.getUser(""),
                "Should throw for empty username");

        assertThrows(IllegalArgumentException.class,
                () -> UserManager.getUser(null),
                "Should throw for null username");
    }

    @Test
    void testGetUser_NotFound() {
        assertThrows(IllegalArgumentException.class,
                () -> UserManager.getUser("nonExistentUser"),
                "Should throw for non-existent user");
    }

    @Test
    void testDeleteUser_Success() {
        UserManager.createUser("deleteMe");

        // Verify exists first
        assertNotNull(UserManager.getUser("deleteMe"));

        // Delete
        UserManager.deleteUser("deleteMe");

        // Verify deleted
        assertThrows(IllegalArgumentException.class,
                () -> UserManager.getUser("deleteMe"),
                "Should throw after deletion");
    }

    @Test
    void testDeleteUser_EmptyName() {
        assertThrows(IllegalArgumentException.class,
                () -> UserManager.deleteUser(""),
                "Should throw for empty username");

        assertThrows(IllegalArgumentException.class,
                () -> UserManager.deleteUser(null),
                "Should throw for null username");
    }

    @Test
    void testDeleteUser_NotFound() {
        assertThrows(IllegalArgumentException.class,
                () -> UserManager.deleteUser("neverExisted"),
                "Should throw for non-existent user");
    }
}
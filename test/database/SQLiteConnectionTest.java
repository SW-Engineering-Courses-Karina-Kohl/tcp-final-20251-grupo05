package test.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import pokeclicker.database.*;
import test.TestUtils;

import static org.junit.jupiter.api.Assertions.*;

class SQLiteConnectionTest {
    private Connection connection;
    
    @BeforeEach
    void setUp() throws SQLException {
        TestUtils.setupTestDatabase();
        connection = TestUtils.getConnection();
    }

    @AfterEach
    void tearDown() throws SQLException {
        TestUtils.cleanTestDatabase();
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        TestUtils.resetDatabaseUrl();
    }

    @Test
    void testConnect_SuccessfulConnection() {
        connection = SQLiteConnection.connect();

        assertNotNull(connection, "Connection should not be null");
        try {
            assertFalse(connection.isClosed(), "Connection should be open");
        } catch (SQLException e) {
            fail("Should not throw SQLException when checking if connection is closed");
        }
    }

    @Test
    void testConnect_CanExecuteSimpleQuery() {
        connection = SQLiteConnection.connect();

        assertNotNull(connection, "Connection should not be null");
        try {
            var stmt = connection.createStatement();
            var rs = stmt.executeQuery("SELECT 1");
            assertTrue(rs.next(), "Should be able to retrieve results");
            assertEquals(1, rs.getInt(1), "Simple query should return 1");
        } catch (SQLException e) {
            fail("Should be able to execute simple query on valid connection");
        }
    }

    @Test
    void testConnect_ReturnsNewConnectionEachTime() {
        Connection firstConnection = SQLiteConnection.connect();
        Connection secondConnection = SQLiteConnection.connect();

        assertNotNull(firstConnection, "First connection should not be null");
        assertNotNull(secondConnection, "Second connection should not be null");
        assertNotSame(firstConnection, secondConnection,
                "Each call to connect() should return a new connection");

        try {
            if (firstConnection != null)
                firstConnection.close();
            if (secondConnection != null)
                secondConnection.close();
        } catch (SQLException e) {
        }
    }
}
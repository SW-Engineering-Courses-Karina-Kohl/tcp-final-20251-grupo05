package pokeclicker.database;

import pokeclicker.model.item.Item;
import pokeclicker.model.item.MoneyMultiplierItem;
import pokeclicker.model.item.PokemonItem;
import pokeclicker.manager.item.ItemFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemDB {
    public static void createItemTable() {
        String sql = "CREATE TABLE IF NOT EXISTS item (" +
                "name TEXT PRIMARY KEY," +
                "price REAL," +
                "type TEXT," +
                "available BOOLEAN," +
                "description TEXT," +
                "multiplierOrDamage REAL" +
                ");";
        try (java.sql.Connection conn = SQLiteConnection.connect();
                java.sql.Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Item table created or already exists.");
        } catch (java.sql.SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    public static void insertItem(Item item) {
        String sql = "INSERT INTO item (name, price, type, available, description, multiplierOrDamage) VALUES (?, ?, ?, ?, ?, ?)";
        try (java.sql.Connection conn = SQLiteConnection.connect();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getName());
            pstmt.setDouble(2, item.getPrice());
            pstmt.setString(3, item.getType().toString());
            pstmt.setBoolean(4, item.isAvailable());
            pstmt.setString(5, item.getDescription());
            pstmt.setDouble(6, item.getMultiplierOrDamage());
            pstmt.executeUpdate();
            System.out.println("Item inserted successfully.");
        } catch (java.sql.SQLException e) {
            System.out.println("Error inserting item: " + e.getMessage());
        }
    }

    public static Item getItem(String name) {
        Item item;
        String sql = "SELECT * FROM item WHERE name = ?";
        try (java.sql.Connection conn = SQLiteConnection.connect();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            java.sql.ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String itemName = rs.getString("name");
                double price = rs.getDouble("price");
                String type = rs.getString("type");
                boolean available = rs.getBoolean("available");
                String description = rs.getString("description");
                double multiplierOrDamage = rs.getDouble("multiplierOrDamage");

                switch (type) {
                    case "Money Multiplier" ->
                        item = new MoneyMultiplierItem(itemName, price, description, multiplierOrDamage);
                    case "Pokemon" -> item = new PokemonItem(itemName, price, description, multiplierOrDamage);
                    default -> throw new IllegalArgumentException("Unknown item type: " + type);
                }
                item.setAvailable(available);
                return item;
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Error retrieving item: " + e.getMessage());
        }
        return null;
    }

    public static List<Item> getAllItems(Optional<ItemFilter> filter) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM item";
        if (filter.isPresent()) {
            ItemFilter itemFilter = filter.get();
            String conditions = getItemConditions(itemFilter);

            if (!conditions.equals("1")) {
                sql += " WHERE " + conditions;
            }
        }
        try (java.sql.Connection conn = SQLiteConnection.connect();
                java.sql.Statement stmt = conn.createStatement();
                java.sql.ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String itemName = rs.getString("name");
                double price = rs.getDouble("price");
                String type = rs.getString("type");
                boolean available = rs.getBoolean("available");
                String description = rs.getString("description");
                double multiplierOrDamage = rs.getDouble("multiplierOrDamage");

                Item item;
                switch (type) {
                    case "Money Multiplier" ->
                        item = new MoneyMultiplierItem(itemName, price, description, multiplierOrDamage);
                    case "Pokemon" -> item = new PokemonItem(itemName, price, description, multiplierOrDamage);
                    default -> throw new IllegalArgumentException("Unknown item type: " + type);
                }
                item.setAvailable(available);
                items.add(item);
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Error retrieving all items: " + e.getMessage());
        }
        return items;
    }

    public static void updateItem(Item item) {
        String sql = "UPDATE item SET price = ?, available = ?, description = ?, multiplierOrDamage = ? WHERE name = ?";
        try (java.sql.Connection conn = SQLiteConnection.connect();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, item.getPrice());
            pstmt.setBoolean(2, item.isAvailable());
            pstmt.setString(3, item.getDescription());
            pstmt.setDouble(4, item.getMultiplierOrDamage());
            pstmt.setString(5, item.getName());
            pstmt.executeUpdate();
            System.out.println("Item updated successfully.");
        } catch (java.sql.SQLException e) {
            System.out.println("Error updating item: " + e.getMessage());
        }
    }

    public static void deleteItem(String name) {
        String sql = "DELETE FROM item WHERE name = ?";
        try (java.sql.Connection conn = SQLiteConnection.connect();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("Item deleted successfully.");
        } catch (java.sql.SQLException e) {
            System.out.println("Error deleting item: " + e.getMessage());
        }
    }

    private static String getItemConditions(ItemFilter filter) {
        List<String> conditions = new ArrayList<>();
        if (filter.getType() != null) {
            conditions.add("type = '" + filter.getType().toString() + "'");
        }
        if (filter.getMinPrice() != null) {
            conditions.add("price >= " + filter.getMinPrice());
        }
        if (filter.getMaxPrice() != null) {
            conditions.add("price <= " + filter.getMaxPrice());
        }
        if (filter.getNameContains() != null) {
            conditions.add("name LIKE '%" + filter.getNameContains() + "%'");
        }
        if (filter.getAvailable() != null) {
            conditions.add("available = " + filter.getAvailable());
        }
        if (filter.getDescriptionContains() != null) {
            conditions.add("description LIKE '%" + filter.getDescriptionContains() + "%'");
        }

        if (conditions.isEmpty()) {
            return "1";
        }
        return String.join(" AND ", conditions);
    }

}

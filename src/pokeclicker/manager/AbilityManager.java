package pokeclicker.manager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import pokeclicker.model.Ability;
import pokeclicker.model.common.PokeType;


public class AbilityManager {

    private static final List<Ability> abilities = new ArrayList<>();
    private static final String PATH = "abilities.txt";

    static {
        loadFromFile();
    }

    private AbilityManager() {
    }

    public static Ability createAbility(String name, String description, PokeType type, double damage, double cure)
            throws IllegalArgumentException {
        if (abilityNameExists(name)) {
            throw new IllegalArgumentException("The ability already exists!");
        }

        Ability newAbility = new Ability(name, description, type, damage, cure);
        abilities.add(newAbility);
        saveToFile(newAbility);
        return newAbility;
    }

    private static void loadFromFile() {
        File file = new File(PATH);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 5) {
                    try {
                        String name = parts[0];
                        String description = parts[1];
                        String typeStr = parts[2];
                        double damage = Double.parseDouble(parts[3]);
                        double cure = Double.parseDouble(parts[4]);

                        PokeType type = PokeType.fromString(typeStr);
                        abilities.add(new Ability(name, description, type, damage, cure));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error parsing line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading abilities from file: " + e.getMessage());
        }
    }

    private static void saveToFile(Ability ability) {
        try (FileWriter writer = new FileWriter(PATH, true)) {
            String line = String.format(Locale.US, "%s;%s;%s;%.2f;%.2f%n",
                    ability.getName(),
                    ability.getDescription(),
                    ability.getType().toString(),
                    ability.getDamage(),
                    ability.getCure());

                writer.write(line);

            } catch (IOException e) {
                System.err.println("Error saving ability to file: " + e.getMessage());
            }
        }

    private static boolean abilityNameExists(String name) {
        return abilities.stream()
                .anyMatch(h -> h.getName().equalsIgnoreCase(name));
    }

    public static List<Ability> getAllAbilities() {
        return new ArrayList<>(abilities);
    }

    public static Ability getAbilityByName(String name) {
        return abilities.stream()
                .filter(h -> h.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
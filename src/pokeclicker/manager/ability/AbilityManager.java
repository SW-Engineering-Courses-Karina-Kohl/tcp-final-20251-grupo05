package pokeclicker.manager.ability;

import java.util.List;
import java.util.Optional;
import pokeclicker.database.AbilityDB;
import pokeclicker.model.Ability;
import pokeclicker.model.common.PokeType;

public class AbilityManager {

    private AbilityManager() {
    }

    public static Ability createAbility(String name, String description, PokeType type, double damage, double cure)
            throws IllegalArgumentException {
        if (AbilityDB.getAbility(name) != null) {
            throw new IllegalArgumentException("The ability already exists!");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Ability name cannot be null or empty");
        }

        Ability newAbility = new Ability(name, description, type, damage, cure);
        AbilityDB.insertAbility(newAbility);
        return newAbility;
    }

    public static List<Ability> getAllAbilities(Optional<AbilityFilter> filter) {
        if (filter.isPresent()) {
            return AbilityDB.getAllAbilities(filter);
        }
        return AbilityDB.getAllAbilities(null);
    }

    public static Ability getAbilityByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Ability name cannot be null or empty");
        }
        Ability ability = AbilityDB.getAbility(name);
        if (ability == null) {
            throw new IllegalArgumentException("Ability not found");
        }
        return ability;
    }

    public static void deleteAbility(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Ability name cannot be null or empty");
        }
        Ability ability = AbilityDB.getAbility(name);
        if (ability == null) {
            throw new IllegalArgumentException("Ability not found");
        }
        AbilityDB.deleteAbility(name);
    }

    public static void updateAbility(Ability ability) {
        if (ability == null) {
            throw new IllegalArgumentException("Ability cannot be null");
        }
        if (AbilityDB.getAbility(ability.getName()) == null) {
            throw new IllegalArgumentException("Ability does not exist");
        }

        AbilityDB.updateAbility(ability);
    }
}
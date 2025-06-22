/*
 * package test.database;
 * 
 * import org.junit.jupiter.api.AfterEach;
 * import org.junit.jupiter.api.BeforeAll;
 * import org.junit.jupiter.api.BeforeEach;
 * import org.junit.jupiter.api.Test;
 * import pokeclicker.database.AbilityDB;
 * import pokeclicker.manager.ability.AbilityFilter;
 * import pokeclicker.model.Ability;
 * import pokeclicker.model.common.PokeType;
 * 
 * import java.util.List;
 * import java.util.Optional;
 * 
 * import static org.junit.jupiter.api.Assertions.*;
 * 
 * /**
 * Test suite para a classe AbilityDB.
 * Estes testes rodam contra um banco de dados SQLite em memória para garantir
 * velocidade e isolamento.
 */
/*
 * public class AbilityDBTest {
 * 
 * private Ability testAbility1;
 * private Ability testAbility2;
 * 
 * @BeforeAll
 * static void setUpAll() {
 * // Cria a tabela no banco de dados em memória uma vez antes de todos os
 * testes.
 * AbilityDB.createAbilityTable();
 * }
 * 
 * @BeforeEach
 * void setUp() {
 * // Prepara objetos de teste reutilizáveis antes de cada teste.
 * testAbility1 = new Ability("Tackle", "A basic attack", PokeType.GRASS, 10.0,
 * 0.0);
 * testAbility2 = new Ability("Water Gun", "Shoots water", PokeType.WATER, 15.0,
 * 0.0);
 * }
 * 
 * @AfterEach
 * void tearDown() {
 * // Limpa a tabela depois de cada teste para garantir que os testes sejam
 * independentes.
 * // Isso é crucial para a confiabilidade dos testes.
 * AbilityDB.deleteAbility(testAbility1.getName());
 * AbilityDB.deleteAbility(testAbility2.getName());
 * }
 * 
 * @Test
 * void testInsertAndGetAbility() {
 * // Arrange: Insere a habilidade no banco de dados.
 * AbilityDB.insertAbility(testAbility1);
 * 
 * // Act: Busca a habilidade pelo nome.
 * Ability retrievedAbility = AbilityDB.getAbility("Tackle");
 * 
 * // Assert: Verifica se a habilidade retornada não é nula e se seus dados
 * estão corretos.
 * assertNotNull(retrievedAbility,
 * "A habilidade buscada não deveria ser nula.");
 * assertEquals("Tackle", retrievedAbility.getName(),
 * "O nome da habilidade deve ser o mesmo.");
 * assertEquals(10.0, retrievedAbility.getDamage(),
 * "O dano da habilidade deve ser o mesmo.");
 * assertEquals(PokeType.GRASS, retrievedAbility.getType(),
 * "O tipo da habilidade deve ser o mesmo.");
 * }
 * 
 * @Test
 * void testGetAbility_WhenNotFound_ReturnsNull() {
 * // Act: Tenta buscar uma habilidade que não existe.
 * Ability retrievedAbility = AbilityDB.getAbility("NonExistentAbility");
 * 
 * // Assert: Verifica se o resultado é nulo.
 * assertNull(retrievedAbility,
 * "Buscar uma habilidade inexistente deve retornar nulo.");
 * }
 * 
 * @Test
 * void testUpdateAbility() {
 * // Arrange: Insere a habilidade inicial.
 * AbilityDB.insertAbility(testAbility1);
 * 
 * // Cria uma versão modificada da habilidade.
 * Ability updatedInfo = new Ability("Tackle", "A powerful physical attack",
 * PokeType.GRASS, 25.0, 0.0);
 * 
 * // Act: Atualiza a habilidade no banco de dados.
 * AbilityDB.updateAbility(updatedInfo);
 * 
 * // Busca a habilidade novamente.
 * Ability retrievedAbility = AbilityDB.getAbility("Tackle");
 * 
 * // Assert: Verifica se os dados foram atualizados.
 * assertEquals("A powerful physical attack", retrievedAbility.getDescription(),
 * "A descrição deveria ter sido atualizada.");
 * assertEquals(25.0, retrievedAbility.getDamage(),
 * "O dano deveria ter sido atualizado.");
 * }
 * 
 * @Test
 * void testDeleteAbility() {
 * // Arrange: Insere a habilidade.
 * AbilityDB.insertAbility(testAbility1);
 * assertNotNull(AbilityDB.getAbility("Tackle"),
 * "A habilidade deve existir antes de ser deletada.");
 * 
 * // Act: Deleta a habilidade.
 * AbilityDB.deleteAbility("Tackle");
 * 
 * // Assert: Verifica se a habilidade não existe mais.
 * assertNull(AbilityDB.getAbility("Tackle"),
 * "A habilidade não deveria mais ser encontrada após a deleção.");
 * }
 * 
 * @Test
 * void testGetAllAbilities_NoFilter() {
 * // Arrange: Insere duas habilidades.
 * AbilityDB.insertAbility(testAbility1);
 * AbilityDB.insertAbility(testAbility2);
 * 
 * // Act: Busca todas as habilidades sem filtro.
 * List<Ability> allAbilities = AbilityDB.getAllAbilities(Optional.empty());
 * 
 * // Assert: Verifica se a lista contém as duas habilidades.
 * assertEquals(2, allAbilities.size(),
 * "A lista deveria conter 2 habilidades.");
 * assertTrue(allAbilities.stream().anyMatch(a -> a.getName().equals("Tackle")),
 * "A lista deveria conter a habilidade 'Tackle'.");
 * assertTrue(allAbilities.stream().anyMatch(a ->
 * a.getName().equals("Water Gun")),
 * "A lista deveria conter a habilidade 'Water Gun'.");
 * }
 * 
 * @Test
 * void testGetAllAbilities_WithFilterByType() {
 * // Arrange: Insere duas habilidades de tipos diferentes.
 * AbilityDB.insertAbility(testAbility1); // NORMAL
 * AbilityDB.insertAbility(testAbility2); // WATER
 * 
 * // Cria um filtro para buscar apenas habilidades do tipo WATER.
 * AbilityFilter filter = new AbilityFilter();
 * filter.setType(PokeType.WATER);
 * 
 * // Act: Busca as habilidades com o filtro.
 * List<Ability> filteredAbilities =
 * AbilityDB.getAllAbilities(Optional.of(filter));
 * 
 * // Assert: Verifica se apenas a habilidade correta foi retornada.
 * assertEquals(1, filteredAbilities.size(),
 * "A lista filtrada deveria conter apenas 1 habilidade.");
 * assertEquals("Water Gun", filteredAbilities.get(0).getName(),
 * "A habilidade na lista filtrada deveria ser 'Water Gun'.");
 * }
 * }
 */

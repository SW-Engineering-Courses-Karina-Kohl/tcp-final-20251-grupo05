package pokeclicker.model.item;
/* 
import pokeclicker.model.common.Activatable;
import pokeclicker.model.common.MockActivatable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;*/

class ItemTest {
/*/
    private ConcreteItem testItem;
    private String initialName = "Lucky Egg";
    private double initialPrice = 2000.0;
    private String initialDescription = "Aumenta o dinheiro ganho.";
    // **CORREÇÃO AQUI:** Usando ItemType.MONEY_MULTIPLIER
    private ItemType initialType = ItemType.MONEY_MULTIPLIER;
    private double initialValue = 2.0; // Multiplicador de dinheiro

    @BeforeEach
    void setUp() {
        testItem = new ConcreteItem(initialName, initialPrice, initialDescription, initialType, initialValue);
    }

    @Test
    @DisplayName("Deve inicializar Item com valores corretos através do construtor")
    void testConstructorInitialization() {
        assertNotNull(testItem, "O item não deve ser nulo após a inicialização");
        assertEquals(initialName, testItem.getName(), "O nome do item deve ser 'Lucky Egg'");
        assertEquals(initialPrice, testItem.getPrice(), "O preço do item deve ser 2000.0");
        assertEquals(initialDescription, testItem.getDescription(), "A descrição do item deve ser a esperada");
        assertTrue(testItem.isAvailable(), "O item deve estar disponível por padrão");
        // **CORREÇÃO AQUI:** Verificando ItemType.MONEY_MULTIPLIER
        assertEquals(initialType, testItem.getType(), "O tipo do item deve ser MONEY_MULTIPLIER");
        assertEquals(initialValue, testItem.getMultiplierOrDamage(), "O valor (multiplicador) deve ser 2.0");
    }

    @Test
    @DisplayName("Deve retornar o nome correto do item")
    void testGetName() {
        assertEquals(initialName, testItem.getName(), "getName deve retornar o nome correto");
    }

    @Test
    @DisplayName("Deve retornar o preço correto do item")
    void testGetPrice() {
        assertEquals(initialPrice, testItem.getPrice(), "getPrice deve retornar o preço correto");
    }

    @Test
    @DisplayName("Deve retornar o status de disponibilidade correto")
    void testIsAvailable() {
        assertTrue(testItem.isAvailable(), "isAvailable deve retornar true por padrão");
        testItem.setAvailable(false);
        assertFalse(testItem.isAvailable(), "isAvailable deve retornar false após ser desativado");
    }

    @Test
    @DisplayName("Deve mudar o status de disponibilidade para true")
    void testSetAvailableTrue() {
        testItem.setAvailable(false);
        assertFalse(testItem.isAvailable());
        testItem.setAvailable(true);
        assertTrue(testItem.isAvailable(), "setAvailable deve mudar o status para true");
    }

    @Test
    @DisplayName("Deve mudar o status de disponibilidade para false")
    void testSetAvailableFalse() {
        testItem.setAvailable(true);
        assertTrue(testItem.isAvailable());
        testItem.setAvailable(false);
        assertFalse(testItem.isAvailable(), "setAvailable deve mudar o status para false");
    }

    @Test
    @DisplayName("Deve retornar a descrição correta do item")
    void testGetDescription() {
        assertEquals(initialDescription, testItem.getDescription(), "getDescription deve retornar a descrição correta");
    }

    @Test
    @DisplayName("Deve permitir a atualização da descrição do item")
    void testSetDescription() {
        String newDescription = "Um item que dobra o dinheiro!";
        testItem.setDescription(newDescription);
        assertEquals(newDescription, testItem.getDescription(), "setDescription deve atualizar a descrição");
    }

    @Test
    @DisplayName("Deve retornar o tipo de item correto")
    void testGetType() {
        assertEquals(initialType, testItem.getType(), "getType deve retornar o tipo de item correto");
    }

    @Test
    @DisplayName("Deve retornar o multiplicador ou dano correto")
    void testGetMultiplierOrDamage() {
        assertEquals(initialValue, testItem.getMultiplierOrDamage(), "getMultiplierOrDamage deve retornar o valor correto");
    }

    @Test
    @DisplayName("O método activate deve retornar a instância de Activatable fornecida")
    void testActivate() {
        MockActivatable mockActivatable = new MockActivatable();
        Activatable activated = testItem.activate(mockActivatable);
        assertSame(mockActivatable, activated, "O método activate deve retornar a mesma instância de Activatable");
    }

    @Test
    @DisplayName("Deve lidar com descrição nula")
    void testSetDescriptionNull() {
        testItem.setDescription(null);
        assertNull(testItem.getDescription(), "A descrição deve ser nula se definida como nula");
    }

    @Test
    @DisplayName("Deve lidar com descrição vazia")
    void testSetDescriptionEmpty() {
        testItem.setDescription("");
        assertEquals("", testItem.getDescription(), "A descrição deve ser vazia se definida como vazia");
    }*/
}
package pokeclicker;

public class PokemonManager {
    public void register(String nome, Nivel nivel, List<Habilidade> habilidades, int vida, double experiencia,
            boolean capturado) {
        Pokemon pokemon = new Pokemon(nome, nivel, habilidades, vida, experiencia, capturado);
        // Adicionar lógica para armazenar o Pokémon registrado

    }
}

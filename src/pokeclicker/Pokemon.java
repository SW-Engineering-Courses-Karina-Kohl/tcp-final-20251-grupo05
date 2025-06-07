package pokeclicker;

import java.util.List;

public abstract class Pokemon {
    private String nome;
    private Nivel nivel;
    private List<Habilidade> habilidades;
    private double experiencia;
    private int vida;
    private boolean capturado;

    public Pokemon(String nome, Nivel nivel, List<Habilidade> habilidades) {
        this.nome = nome;
        this.nivel = nivel;
        this.habilidades = habilidades;
        this.experiencia = 0.0;
        this.vida = 100; // Valor inicial de vida
        this.capturado = false; // Inicialmente não capturado
    }

    public String getNome() {
        return nome;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public List<Habilidade> getHabilidades() {
        return habilidades;
    }

    public double getExperiencia() {
        return experiencia;
    }

    public int getVida() {
        return vida;
    }

    public boolean isCapturado() {
        return capturado;
    }
}

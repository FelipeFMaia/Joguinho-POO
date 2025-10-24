package Modelo;

/**
 * Classe abstrata para todos os personagens que possuem movimento
 * (seja controlado ou automático).
 * Herda de Personagem.
 */
public abstract class Animado extends Personagem {

    public Animado(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }
    
    // No futuro, se tivermos lógicas de animação (ex: trocar a imagem a cada X frames),
    // poderíamos adicionar aqui. Por enquanto, ela apenas categoriza.
}
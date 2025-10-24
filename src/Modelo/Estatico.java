package Modelo;

/**
 * Classe abstrata para todos os personagens que são estáticos,
 * ou seja, não se movem por conta própria.
 * Herda de Personagem.
 */
public abstract class Estatico extends Personagem {

    public Estatico(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }
}
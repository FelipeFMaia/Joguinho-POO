package Modelo;

public class Chave extends Personagem {
    
    public Chave(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.bTransponivel = true; // Pode passar por cima para coletar
    }

    @Override
    public void atualizar(java.util.ArrayList<Personagem> faseAtual, Hero hero) {
        // Estático, não faz nada
    }

    @Override
    public String aoColidirComHeroi(Hero h) {
        // Avisa o ControleDeJogo que uma chave foi coletada
        return "CHAVE_COLETADA"; 
    }
}
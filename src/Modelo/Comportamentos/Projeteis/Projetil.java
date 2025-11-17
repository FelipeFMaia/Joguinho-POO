package Modelo.Comportamentos.Projeteis;

import Auxiliar.Desenho;
import Modelo.Hero;
import Modelo.Mortal;
import Modelo.Personagem;
import java.io.Serializable;
import java.util.ArrayList;

// Classe abstrata para todos os projéteis (Neve, Fogo, Lama)
// Define o comportamento comum: é Mortal e se auto-remove. As classes filhas só precisam implementar como se movem.
public abstract class Projetil extends Personagem implements Serializable, Mortal {
    private static final long serialVersionUID = 1L;
    
    public Projetil(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.bTransponivel = true; // Projéteis podem passar por cima de itens
    }

    public abstract boolean move();
    
    @Override
    public void atualizar(ArrayList<Personagem> faseAtual, Hero hero) {
        // Se o movimento falhar (bater na borda), o projétil é removido.
        if(!this.move())
            Desenho.acessoATelaDoJogo().removePersonagem(this);
    }

    @Override
    public void desenhar() {
        super.desenhar();
    }
    
    @Override
    public String aoColidirComHeroi(Hero hero) {
        return "HERO_DIED";
    }
}

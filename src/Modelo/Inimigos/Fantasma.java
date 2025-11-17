package Modelo.Inimigos;

import Modelo.Hero;
import Modelo.Comportamentos.Ataque.AtaqueNulo;
import Modelo.Comportamentos.Movimento.MovimentoChaser;
import Modelo.Mortal;
import Modelo.Personagem;
import java.io.Serializable;

public class Fantasma extends Personagem implements Serializable, Mortal {
    private static final long serialVersionUID = 1L;
    
    public Fantasma(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.setbTransponivel(false);
        
        setComportamentoMovimento(new MovimentoChaser());
        setComportamentoAtaque(new AtaqueNulo());
    }
    
    @Override
    public String aoColidirComHeroi(Hero h) {
        return "HERO_DIED";
    }
}

package Modelo.Inimigos;

import Modelo.Comportamentos.Ataque.AtaqueEmV;
import Modelo.Comportamentos.Movimento.MovimentoCircular;
import Modelo.Personagem;
import java.io.Serializable;
import Modelo.Hero;
import Modelo.Mortal;

public class Penguim extends Personagem implements Serializable, Mortal {
    private static final long serialVersionUID = 1L;
    
    public Penguim(String sNomeImagePNG, int linha, int coluna){
        super(sNomeImagePNG, linha, coluna);
        this.setbTransponivel(false);
        setComportamentoMovimento(new MovimentoCircular(10));
        setComportamentoAtaque(new AtaqueEmV("GeloProjetil.png"));
    }
    
    @Override
    public String aoColidirComHeroi(Hero h) {
        return "HERO_DIED";
    }
}

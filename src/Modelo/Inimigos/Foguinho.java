package Modelo.Inimigos;

import Modelo.Comportamentos.Ataque.AtaqueEmCruz;
import Modelo.Comportamentos.Movimento.MovimentoZigueZague;
import Modelo.Personagem;
import java.io.Serializable;
import Modelo.Hero;
import Modelo.Mortal;

public class Foguinho extends Personagem implements Serializable, Mortal {
    private static final long serialVersionUID = 1L;
    
    public Foguinho(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.setbTransponivel(false);
        setComportamentoMovimento(new MovimentoZigueZague(10));
        setComportamentoAtaque(new AtaqueEmCruz("FogoProjetil.png"));
    }
    
    @Override
    public String aoColidirComHeroi(Hero h) {
        return "HERO_DIED"; // Isso fará com que processaTudo retorne "HERO_DIED"
    }

}
package Modelo.Inimigos;

import Modelo.Comportamentos.Ataque.AtaqueEmV;
import Modelo.Comportamentos.Movimento.MovimentoParado;
import Modelo.Personagem;
import java.io.Serializable;
import Modelo.Hero;
import Modelo.Mortal;

public class Musgo extends Personagem implements Serializable, Mortal {
    private static final long serialVersionUID = 1L;
    
    public Musgo(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.setbTransponivel(true);
        setComportamentoMovimento(new MovimentoParado());
        setComportamentoAtaque(new AtaqueEmV("PantanoProjetil.png"));
    }
    
    @Override
    public String aoColidirComHeroi(Hero h) {
        return "HERO_DIED"; // Isso fará com que processaTudo retorne "HERO_DIED"
    }
}
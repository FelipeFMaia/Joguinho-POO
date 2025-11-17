package Modelo.Inimigos;

import Modelo.Comportamentos.Ataque.AtaqueEmCruz;
import Modelo.Comportamentos.Movimento.MovimentoQuadrado;
import Modelo.Personagem;
import java.io.Serializable;
import Modelo.Hero;
import Modelo.Mortal;

public class Capivara extends Personagem implements Serializable, Mortal {
    private static final long serialVersionUID = 1L;
    
    public Capivara(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.setbTransponivel(false);
        setComportamentoMovimento(new MovimentoQuadrado(3, 5));
        setComportamentoAtaque(new AtaqueEmCruz("PantanoProjetil.png"));
    }
    
    
    @Override
    public String aoColidirComHeroi(Hero h) {
        return "HERO_DIED"; // Isso fará com que processaTudo retorne "HERO_DIED"
    }
}
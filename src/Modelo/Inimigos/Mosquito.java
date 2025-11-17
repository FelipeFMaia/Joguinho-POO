package Modelo.Inimigos;

import Modelo.Comportamentos.Ataque.AtaqueNulo;
import Modelo.Comportamentos.Movimento.MovimentoChaser;
import Modelo.Personagem;
import java.io.Serializable;
import Modelo.Hero;
import Modelo.Mortal;

public class Mosquito extends Personagem implements Serializable, Mortal {
    private static final long serialVersionUID = 1L;
    
    public Mosquito(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.setbTransponivel(true);
        setComportamentoMovimento(new MovimentoChaser());
        setComportamentoAtaque(new AtaqueNulo());
    }
    
    @Override
    public String aoColidirComHeroi(Hero h) {
        return "HERO_DIED"; // Isso fará com que processaTudo retorne "HERO_DIED"
    }
}
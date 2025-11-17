package Modelo.Inimigos;

import Modelo.Comportamentos.Ataque.ComportamentoAtaqueAtirador;
import Modelo.Comportamentos.Ataque.IFabricaProjetil;
import Auxiliar.Posicao;
import Modelo.Comportamentos.Ataque.BolhaDeAr;
import Modelo.Personagem;
import java.io.Serializable;
import Modelo.Hero;
import Modelo.Mortal;

class FabricaBolha implements IFabricaProjetil {
    @Override
    public Personagem criarProjetil(Posicao p) {
        return new BolhaDeAr("GeloProjetil2.png", p.getLinha(), p.getColuna());
    }
}

public class Morsa extends Personagem implements Serializable, Mortal {
    private static final long serialVersionUID = 1L;
    
    public Morsa(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.setbTransponivel(false);
        
        setComportamentoAtaque(
            new ComportamentoAtaqueAtirador(new FabricaBolha())
        );
    }
    
    @Override
    public String aoColidirComHeroi(Hero h) {
        return "HERO_DIED"; // Isso fará com que processaTudo retorne "HERO_DIED"
    }

}
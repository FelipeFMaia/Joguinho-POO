package Modelo.Comportamentos.Ataque;

import Auxiliar.Consts;
import Modelo.Personagem;
import java.io.Serializable;

// Lógica que controla o timer e usada para criar o projétil,
public class ComportamentoAtaqueAtirador implements Serializable, ComportamentoAtaque {
    private static final long serialVersionUID = 1L;
    
    private int iContaIntervalos;
    private final IFabricaProjetil fabricaProjetil;

    public ComportamentoAtaqueAtirador(IFabricaProjetil fabricaProjetil) {
        this.fabricaProjetil = fabricaProjetil;
        this.iContaIntervalos = 0;
    }

    @Override
    public void executar(Modelo.Personagem p, java.util.ArrayList<Modelo.Personagem> faseAtual, Modelo.Hero hero) {
        this.iContaIntervalos++;
        if(this.iContaIntervalos == Consts.TIMER) {
            this.iContaIntervalos = 0;
            
            // Usa a fábrica para criar o projétil 
            Personagem projetil = this.fabricaProjetil.criarProjetil(p.getPosicao());
            
            // Adiciona o projétil ao jogo
            Auxiliar.Desenho.acessoATelaDoJogo().addPersonagem(projetil);
        }
    }
}


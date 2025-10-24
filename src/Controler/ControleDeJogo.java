package Controler;

import Modelo.Chaser;
import Modelo.Personagem;
import Modelo.Hero;
import Modelo.Mortal;
import Modelo.Coletavel;
import Modelo.Esfera;
import auxiliar.Posicao;
import java.util.ArrayList;

public class ControleDeJogo {
    
    public void desenhaTudo(ArrayList<Personagem> e) {
        for (int i = 0; i < e.size(); i++)
            e.get(i).autoDesenho();
    }
    
    public String processaTudo(ArrayList<Personagem> umaFase) {
        Hero hero = (Hero) umaFase.get(0);
        Personagem pIesimoPersonagem;
        
        for (int i = 1; i < umaFase.size(); i++) {
            pIesimoPersonagem = umaFase.get(i);
            
            if (hero.getPosicao().igual(pIesimoPersonagem.getPosicao())) {
                
                // 1. O personagem é Mortal?
                if (pIesimoPersonagem instanceof Mortal) {
                    return "HERO_DIED"; // Informa a Tela que o herói morreu
                
                // 2. O personagem é a "saída" da fase? (Definimos a Esfera)
                } else if (pIesimoPersonagem instanceof Esfera) {
                    return "NEXT_LEVEL"; // Informa a Tela para passar de fase
                    
                // 3. O personagem é Coletável?
                } else if (pIesimoPersonagem instanceof Coletavel) {
                    umaFase.remove(pIesimoPersonagem);
                    // (Aqui poderíamos retornar "ITEM_COLLECTED" se quiséssemos
                    // que a Tela soubesse para adicionar pontos, por exemplo)
                }
            }
        }
        
        // Atualiza a direção do Chaser (lógica antiga)
        for (int i = 1; i < umaFase.size(); i++) {
            pIesimoPersonagem = umaFase.get(i);
            if (pIesimoPersonagem instanceof Chaser) {
                ((Chaser) pIesimoPersonagem).computeDirection(hero.getPosicao());
            }
        }
        
        return "GAME_RUNNING"; // Se nada aconteceu, o jogo continua
    }

    /*Retorna true se a posicao p é válida para Hero com relacao a todos os personagens no array*/
    public boolean ehPosicaoValida(ArrayList<Personagem> umaFase, Posicao p) {
        Personagem pIesimoPersonagem;
        for (int i = 1; i < umaFase.size(); i++) {
            pIesimoPersonagem = umaFase.get(i);
            if (!pIesimoPersonagem.isbTransponivel()) {
                if (pIesimoPersonagem.getPosicao().igual(p)) {
                    return false;
                }
            }
        }
        return true;
    }
}

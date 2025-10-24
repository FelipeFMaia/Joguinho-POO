package Controler;

import Modelo.*; // Importa todas as nossas classes do Modelo
import java.util.ArrayList;

/**
 * Esta classe é responsável por criar e carregar
 * os ArrayLists de Personagem para cada fase do jogo.
 * (Cumpre o Requisito 4)
 */
public class GerenciadorFase {
    
    public ArrayList<Personagem> carregarFase(int numeroFase) {
        ArrayList<Personagem> fase = new ArrayList<>();
        
        // O Herói sempre é criado e adicionado primeiro (índice 0)
        Hero hero = null;

        switch (numeroFase) {
            case 1:
                // Posição inicial do Herói na Fase 1
                hero = new Hero("Robbo.png", 0, 7);
                fase.add(hero);

                // Adiciona os outros personagens da Fase 1
                // (Este é o layout antigo do protótipo)
                fase.add(new ZigueZague("bomba.png", 5, 5));
                fase.add(new BichinhoVaiVemHorizontal("roboPink.png", 3, 3));
                fase.add(new BichinhoVaiVemHorizontal("roboPink.png", 6, 6));
                fase.add(new BichinhoVaiVemVertical("Caveira.png", 10, 10));
                fase.add(new Caveira("caveira.png", 9, 1));
                fase.add(new Chaser("chaser.png", 12, 12));
                fase.add(new Esfera("esfera.png", 10, 13));
                
                // Adicione aqui as paredes e itens da Fase 1
                // Ex: fase.add(new Parede("bricks.png", 1, 1));
                
                break;

            case 2:
                // Posição inicial do Herói na Fase 2
                hero = new Hero("Robbo.png", 5, 5);
                fase.add(hero);
                
                // Adicione os personagens da Fase 2
                fase.add(new Chaser("chaser.png", 1, 1));
                fase.add(new Chaser("chaser.png", 1, 10));
                fase.add(new Chaser("chaser.png", 10, 1));
                
                break;
                
            // Adicione os 'case 3:', 'case 4:', 'case 5:' aqui
            
            default:
                // Fase padrão ou tela de "Fim de Jogo"
                hero = new Hero("Robbo.png", 7, 7);
                fase.add(hero);
                break;
        }
        
        return fase;
    }
}
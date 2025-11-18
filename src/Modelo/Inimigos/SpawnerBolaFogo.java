package Modelo.Inimigos;

import Auxiliar.Desenho;
import Modelo.Hero;
import Modelo.Personagem;
import java.io.Serializable;
import java.util.ArrayList;

// Este é um Personagem invisível que atua como um "Spawner".
// A única função é adicionar BolasDeFogo ao jogo em intervalos de tempo.
public class SpawnerBolaFogo extends Personagem implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private int timer;
    private final int delay; // Tempo em ticks entre spawns

    private static final int TEMPO_DE_SPAWN = 100; 

    public SpawnerBolaFogo(int linha, int coluna) {
        // Usa uma imagem placeholder, pois será invisível
        super("blackTile.png", linha, coluna); 
        
        this.bTransponivel = true; // O Herói pode passar por cima
        this.delay = TEMPO_DE_SPAWN;
        this.timer = delay; // Começa o timer
    }

    @Override
    public void atualizar(ArrayList<Personagem> e, Hero h) {
        // Contar o timer
        timer--;

        // Se o timer zerar...
        if (timer <= 0) {
            
            // Criar a BolaDeFogo na posição EXATA deste Spawner
            BolaDeFogo novaBola = new BolaDeFogo("FogoMeteoro.png", 
                                                this.pPosicao.getLinha(), 
                                                this.pPosicao.getColuna());
            
            // Adicionar a bola de fogo à tela
            Desenho.acessoATelaDoJogo().addPersonagem(novaBola);
            
            // Resetar o timer
            timer = delay;
        }
    }

    @Override
    public void desenhar() {
        // Não desenha nada, é invisível
    }
}

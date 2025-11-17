package Modelo.Inimigos;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Auxiliar.Posicao;
import Controler.Tela;
import Modelo.Comportamentos.Ataque.*;
import Modelo.Comportamentos.Movimento.*;
import Modelo.Hero;
import Modelo.Mensagem;
import Modelo.Mortal;
import Modelo.Municao;
import Modelo.Personagem;
import Modelo.Portal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

// Chefão Final
// Alterna entre 2 comportamentos (Perseguir e Atirar)
public class BossFinal extends Personagem implements Serializable, Mortal {
    private static final long serialVersionUID = 1L;

    private int vida;
    private int faseAtualDoBoss;
    private int timerComportamento;
    private int timerMunicao = 0;
    
    private static final int INTERVALO_MUNICAO = (5 * 10);
    
    // Define quanto tempo cada comportamentos do boss dura
    // (100 ticks * 150ms = 15 segundos por fase)
    private static final int TEMPO_POR_FASE = 20; 

    public BossFinal(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        
        this.setbTransponivel(false); // É um obstáculo sólido
        this.vida = 10; // Precisa de 10 tiros do herói para morrer
        this.faseAtualDoBoss = 1;
        this.timerComportamento = 0;

        // Isso força o boss a se reposicionar antes de atirar.
        setComportamentoMovimento(new MovimentoChaser());
        setComportamentoAtaque(new AtaqueNulo()); // Comportamento 1: Apenas persegue
    }

    @Override
    public void atualizar(ArrayList<Personagem> faseAtual, Hero hero) {
        // Executa o comportamento de movimento e ataque atuais
        super.atualizar(faseAtual, hero);

        // Atualiza o timer da fase do Boss
        timerComportamento++;

        // Verifica se é hora de mudar de comportamento
        if (timerComportamento > TEMPO_POR_FASE) {
            timerComportamento = 0; // Reseta o timer
            faseAtualDoBoss++;
            
            if (faseAtualDoBoss > 2) { // 2 comportamento
                faseAtualDoBoss = 1;
            }
            
            // Chama o método que aplica o novo comportamento
            mudarComportamento(faseAtualDoBoss);
        }
        
        timerMunicao++; // Incrementa o timer de munição
        
        if (timerMunicao > INTERVALO_MUNICAO) {
            timerMunicao = 0; // Reseta o timer
            
            // Chama o método auxiliar para criar a munição
            this.spawnarMunicao();
        }
    }

    private void mudarComportamento(int novaFase) {
        System.out.println("BOSS MUDOU PARA O COMPORTAMENTO: " + novaFase);
        
        switch (novaFase) {
            case 1 -> {
                // Perseguição (para reposicionamento)
                setComportamentoMovimento(new MovimentoChaser());
                setComportamentoAtaque(new AtaqueNulo()); // Só se move, não ataca
            }
                
            case 2 -> {
                // Torreta (para ataque focado)
                setComportamentoMovimento(new MovimentoParado());
                setComportamentoAtaque(new AtaqueMirado("BossProjetil.png")); // Atira mirado
            }
        }
    }


    @Override
    public void morrer() {
        this.vida--; // Perde 1 de vida
        System.out.println("Boss foi atingido! Vida restante: " + this.vida);

        if (this.vida <= 0) {
            // Agora sim morre
            super.morrer(); // Seta a flag bEstaVivo = false
            
            // Ao morrer, spawna o Portal de saída
            triggerFimDeFase();
        }
    }
    
    //Cria o Portal de saída no local da morte do Boss
    private void triggerFimDeFase() {
        int linha = this.getPosicao().getLinha();
        int coluna = this.getPosicao().getColuna();
        
        // Cria o portal de saída
        Portal saida = new Portal("ZPortaFinal.png", linha, coluna);
        
        // Ir para Créditos Finais
        saida.setDestinoFase(6); 
        
        // Adiciona o portal e a mensagem ao jogo
        Desenho.acessoATelaDoJogo().addPersonagem(saida);
        Desenho.acessoATelaDoJogo().addPersonagem(
            new Mensagem("CHEFE DERROTADO!\n\nEntre no portal...", true)
        );
    }
    
    @Override
    public String aoColidirComHeroi(Hero h) {
        return "HERO_DIED"; // Faz o herói morrer ao encostar
    }
    
    // Tenta encontrar uma posição aleatória válida no mapa para adicionar um item de Municao.
    private void spawnarMunicao() {
        Random rand = new Random();
        Posicao posSpawn = new Posicao(0, 0);
        
        // Precisamos acessar a Tela para verificar se a posição é válida
        Tela tela = Desenho.acessoATelaDoJogo();
        if (tela == null) return; // Segurança
        
        // Tenta achar um lugar válido (ex: 10 tentativas)
        // (Isso evita um loop infinito se o mapa estiver cheio)
        int tentativas = 0;
        boolean achouLugar = false;
        
        while (tentativas < 10 && !achouLugar) {
            // Sorteia uma posição
            int linha = rand.nextInt(Consts.RES);
            int coluna = rand.nextInt(Consts.RES);
            posSpawn.setPosicao(linha, coluna);
            
            // Pergunta à Tela se esta posição é válida (não é uma parede)
            if (tela.ehPosicaoValida(posSpawn)) {
                achouLugar = true;
            }
            tentativas++;
        }

        // Se encontrou um lugar válido, cria a munição
        if (achouLugar) {
            // Criar munição
            Municao novaMunicao = new Municao("HeroiProjetil.png", posSpawn.getLinha(), posSpawn.getColuna());
            
            // Adiciona a munição ao jogo
            tela.addPersonagem(novaMunicao);
            
            System.out.println("Municao dropada em: " + posSpawn.getLinha() + "," + posSpawn.getColuna());
        }
    }

}

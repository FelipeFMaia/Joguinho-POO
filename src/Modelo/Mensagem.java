package Modelo;

import Auxiliar.Desenho;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

/**
 * Um personagem temporário que desenha uma mensagem na tela e
 * depois se autodestrói.
 */
public class Mensagem extends Personagem {
    
    private String texto;
    private int timer = 0;
    // Duração da mensagem (em ticks. 30 ticks * 150ms = 4.5 segundos)
    private static final int DURACAO = 30; 
    private boolean isBlocking; // Esta mensagem pausa o jogo?

/**
     * NOVO Construtor: cria uma mensagem que pode pausar o jogo.
     * @param texto A mensagem
     * @param pausaOJogo True se a lógica do jogo deve parar
     */
    public Mensagem(String texto, boolean pausaOJogo) {
        super("blackTile.png", 0, 0); 
        this.texto = texto;
        this.bTransponivel = true;
        this.isBlocking = pausaOJogo;
        
        // IMPORTANTE: O pause é ativado pela Tela (Passo 5), não aqui.
    }
    
    // Helper para o Passo 5
    public boolean isBlocking() {
        return this.isBlocking;
    }
    
    // Helper para o Passo 5
    public String getTexto() {
        return this.texto;
    }
    
    // <<-- MUDANÇA: Este é um caso especial.
    // O autoDesenho() foi renomeado para desenhar() e MANTÉM sua lógica,
    // pois sua única responsabilidade é desenhar o HUD.
    @Override
    public void desenhar() {
        // NÃO chamamos super.desenhar() porque não queremos desenhar a blackTile
        
        // <<-- MUDANÇA: A lógica de timer foi movida para atualizar()
        if (timer > DURACAO) {
            return; // Se o timer acabou, apenas não desenhe.
        }

        // Pega o Graphics da Tela
        java.awt.Graphics g = Desenho.getGraphicsDaTela();
        
        // ... (resto da lógica de desenho do g.fillRect, g.drawString, etc.)
        g.setColor(new Color(0, 0, 0, 250)); 
        g.fillRect(50, 0, 450, 400);
        
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.WHITE);
        
        int y = 230;
        for (String linha : texto.split("\n")) {
            g.drawString(linha, 70, y);
            y += 25; 
        }
    }
    
    // <<-- MUDANÇA: Novo método atualizar() para controlar o timer
    @Override
    public void atualizar(ArrayList<Personagem> faseAtual, Hero hero) {
        timer++;
        if (timer > DURACAO) {
            // V-V-V- MUDANÇA CRÍTICA V-V-V
            // Se esta era uma mensagem bloqueante, avisa a Tela para DESPAUSAR
            if (this.isBlocking) {
                Desenho.acessoATelaDoJogo().setGamePaused(false);
            }
            // ^-^-^- FIM DA MUDANÇA -^-^-^
            
            Desenho.acessoATelaDoJogo().removePersonagem(this);
            return;
        }
    }
}
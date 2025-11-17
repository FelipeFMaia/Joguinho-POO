package Modelo.Fases;

import Modelo.Inimigos.BossFinal;
import Modelo.*;
import java.util.ArrayList;

import java.util.Random;
import Modelo.Municao;
import Auxiliar.Posicao;
import Auxiliar.Consts;
import Auxiliar.Desenho;

public class Fase5 implements IFase {

    @Override
    public ArrayList<Personagem> carregarPersonagensIniciais() {
        ArrayList<Personagem> fase = new ArrayList<>();
        
        // Adiciona o Herói
        fase.add(new Hero("Heroi.png", 1, 1)); // Canto superior esquerdo
        
        // Adiciona o BossFinal
        fase.add(new BossFinal("BOSS.png", 7, 7)); // No centro

        // Adiciona as Paredes
        for (int i = 0; i < 12; i++) {
            fase.add(new Parede("BossParede.png", 0, i));
            fase.add(new Parede("BossParede.png", 11, i));
        }
        for (int i = 1; i < 11; i++) {
            fase.add(new Parede("BossParede.png", i, 0));
            fase.add(new Parede("BossParede.png", i, 11));
        }
        
        // Completa a área externa com tiles pretos
        for (int i = 0; i < 12; i++) {
            fase.add(new Parede("ZPisoPreto.png", 12, i));
            fase.add(new Parede("ZPisoPreto.png", 13, i));
            fase.add(new Parede("ZPisoPreto.png", 14, i));
            fase.add(new Parede("ZPisoPreto.png", 15, i));
        }
        for (int i = 0; i < 16; i++) {
            fase.add(new Parede("ZPisoPreto.png", i, 12));
            fase.add(new Parede("ZPisoPreto.png", i, 13));
            fase.add(new Parede("ZPisoPreto.png", i, 14));
            fase.add(new Parede("ZPisoPreto.png", i, 15));
        }
        
        // Disponibiliza municao para enfrentar o boss
        fase.add(new Municao("HeroiProjetil.png", 1, 1));
        fase.add(new Municao("HeroiProjetil.png", 1, 10));
        fase.add(new Municao("HeroiProjetil.png", 10, 10));
        fase.add(new Municao("HeroiProjetil.png", 10, 1));
        
        return fase;
    }

    // Fase de Boss não usa a mecânica de 3 itens
    @Override
    public ArrayList<Personagem> getPersonagensColeta_1() { return new ArrayList<>(); }
    @Override
    public ArrayList<Personagem> getPersonagensColeta_2() { return new ArrayList<>(); }
    @Override
    public Personagem getPersonagemColeta_3() { return null; }

    @Override
    public String getBackgroundTile() {
        return "BossPiso.png";
    }
    
    @Override
    public String getMensagemInicial() {
        return "FASE FINAL!\n\nDerrote o chefe!";
    }
    
    @Override
    public String getMensagemVitoria() {
        return "Parabéns, héroi!\nVenceu essa jornada incrível!\n Você é demais!";
    }

}

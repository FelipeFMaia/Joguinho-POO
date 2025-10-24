package Controler;

import Modelo.Personagem;
import Modelo.Caveira;
import Modelo.Hero;
import Modelo.Chaser;
import Modelo.BichinhoVaiVemHorizontal;
import Auxiliar.Consts;
import Auxiliar.Desenho;
import Modelo.BichinhoVaiVemVertical;
import Modelo.Esfera;
import Modelo.ZigueZague;
import auxiliar.Posicao;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.swing.JButton;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Tela extends javax.swing.JFrame implements MouseListener, KeyListener, DropTargetListener {

    private GerenciadorFase gFase;
    private int nivelAtual;
    private int vidas;
    private int pontuacao;
    private ArrayList<Personagem> faseAtual;
    private ControleDeJogo cj = new ControleDeJogo();
    private Graphics g2;
    private int cameraLinha = 0;
    private int cameraColuna = 0;
    private final Set<Integer> teclasPressionadas = new HashSet<>();
    
    public Tela() {
        Desenho.setCenario(this);
        initComponents();
        this.addMouseListener(this);
        /*mouse*/
        this.addKeyListener(this);
        // drag and drop
        new DropTarget(this, this);
        /*teclado*/
        /*Cria a janela do tamanho do tabuleiro + insets (bordas) da janela*/
        this.setSize(Consts.RES * Consts.CELL_SIDE + getInsets().left + getInsets().right,
                Consts.RES * Consts.CELL_SIDE + getInsets().top + getInsets().bottom);

        // Instancia o Gerenciador e carrega a primeira fase
        this.gFase = new GerenciadorFase();
        this.nivelAtual = 1;
        this.faseAtual = gFase.carregarFase(this.nivelAtual);

        this.atualizaCamera(); // Agora o herói existe e podemos atualizar a câmera
        
        // Instancia o Gerenciador e carrega a primeira fase
        this.gFase = new GerenciadorFase();
        this.nivelAtual = 1;
        this.vidas = 3;      // Novo
        this.pontuacao = 0;  // Novo
        
        this.faseAtual = gFase.carregarFase(this.nivelAtual);
        
        this.atualizaCamera();
    }
    
    public Hero getHero() {
        if (this.faseAtual.isEmpty() || !(this.faseAtual.get(0) instanceof Hero)) {
            // Isso indica um problema sério no carregamento da fase
            System.out.println("ERRO: Herói não é o primeiro elemento da fase!");
            return null; 
            }
        return (Hero) this.faseAtual.get(0);
    }
    
    public int getCameraLinha() {
        return cameraLinha;
    }

    public int getCameraColuna() {
        return cameraColuna;
    }

    public boolean ehPosicaoValida(Posicao p) {
        return cj.ehPosicaoValida(this.faseAtual, p);
    }

    public void addPersonagem(Personagem umPersonagem) {
        faseAtual.add(umPersonagem);
    }

    public void removePersonagem(Personagem umPersonagem) {
        faseAtual.remove(umPersonagem);
    }

    public Graphics getGraphicsBuffer() {
        return g2;
    }

    public void paint(Graphics gOld) {
        Graphics g = this.getBufferStrategy().getDrawGraphics();
        /*Criamos um contexto gráfico*/
        g2 = g.create(getInsets().left, getInsets().top, getWidth() - getInsets().right, getHeight() - getInsets().top);
        /**
         * ***********Desenha cenário de fundo*************
         */
        for (int i = 0; i < Consts.RES; i++) {
            for (int j = 0; j < Consts.RES; j++) {
                int mapaLinha = cameraLinha + i;
                int mapaColuna = cameraColuna + j;

                if (mapaLinha < Consts.MUNDO_ALTURA && mapaColuna < Consts.MUNDO_LARGURA) {
                    try {
                        Image newImage = Toolkit.getDefaultToolkit().getImage(
                                new java.io.File(".").getCanonicalPath() + Consts.PATH + "bricks.png");
                        g2.drawImage(newImage,
                                j * Consts.CELL_SIDE, i * Consts.CELL_SIDE,
                                Consts.CELL_SIDE, Consts.CELL_SIDE, null);
                    } catch (IOException ex) {
                        Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        if (!this.faseAtual.isEmpty()) {
            this.cj.desenhaTudo(faseAtual);
            if (!this.faseAtual.isEmpty()) {
            this.cj.desenhaTudo(faseAtual);
            
            // Pega o status do jogo (aqui é a mágica)
            String status = this.cj.processaTudo(faseAtual);
            
            // Age de acordo com o status
            switch (status) {
                case "HERO_DIED":
                    this.vidas--;
                    if (this.vidas <= 0) {
                        this.gameOver();
                    } else {
                        this.reiniciarFase();
                    }
                    break;
                case "NEXT_LEVEL":
                    this.proximaFase();
                    break;
                case "GAME_RUNNING":
                    // Não faz nada, o jogo continua
                    break;
                }
            }
        }

        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
    }

    private void atualizaCamera() {
        int linha = getHero().getPosicao().getLinha();
        int coluna = getHero().getPosicao().getColuna();

        cameraLinha = Math.max(0, Math.min(linha - Consts.RES / 2, Consts.MUNDO_ALTURA - Consts.RES));
        cameraColuna = Math.max(0, Math.min(coluna - Consts.RES / 2, Consts.MUNDO_LARGURA - Consts.RES));
    }

    public void go() {
        TimerTask task = new TimerTask() {
            public void run() {
                repaint();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, Consts.PERIOD);
    }
    
    public void keyPressed(KeyEvent e) {
        try {
            if (teclasPressionadas.contains(e.getKeyCode()))
                    return;

            teclasPressionadas.add(e.getKeyCode());
            
            if (e.getKeyCode() == KeyEvent.VK_T) {
                this.faseAtual.clear();
                ArrayList<Personagem> novaFase = new ArrayList<Personagem>();

                /*Cria faseAtual adiciona personagens*/
                Hero novoHeroi = new Hero("Robbo.png", 10, 10);
                //hero.setPosicao(10, 10);
                novaFase.add(novoHeroi);
                this.atualizaCamera();

                ZigueZague zz = new ZigueZague("bomba.png", 0, 0);
                novaFase.add(zz);

                Esfera es = new Esfera("esfera.png", 4, 4);
                novaFase.add(es);

                faseAtual = novaFase;
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                getHero().moveUp();
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                getHero().moveDown();
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                getHero().moveLeft();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                getHero().moveRight();
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                File tanque = new File("POO.dat");
                tanque.createNewFile();
                FileOutputStream canoOut = new FileOutputStream(tanque);
                ObjectOutputStream serializador = new ObjectOutputStream(canoOut);
                serializador.writeObject(faseAtual);
            } else if (e.getKeyCode() == KeyEvent.VK_L) {
                File tanque = new File("POO.dat");
                FileInputStream canoOut = new FileInputStream(tanque);
                ObjectInputStream serializador = new ObjectInputStream(canoOut);
                faseAtual = (ArrayList<Personagem>)serializador.readObject();
            } else if (e.getKeyCode() == KeyEvent.VK_K) {
            // CHEAT: Salva uma Caveira para teste de Drag-and-Drop
            try (   FileOutputStream fos = new FileOutputStream("caveira.gz");
                    GZIPOutputStream gzos = new GZIPOutputStream(fos);
                    ObjectOutputStream oos = new ObjectOutputStream(gzos)) {

                Caveira c = new Caveira("caveira.png", 0, 0); // Posição não importa
                oos.writeObject(c);
                System.out.println("Arquivo 'caveira.gz' criado para teste!");

            } catch (Exception ex) {
                System.err.println("Erro ao criar arquivo de teste: " + ex.getMessage());
            }
        }

            this.atualizaCamera();
            this.setTitle("-> Cell: " + (getHero().getPosicao().getLinha()) + ", " + (getHero().getPosicao().getColuna()));

            //repaint(); /*invoca o paint imediatamente, sem aguardar o refresh*/
        } catch (Exception ee) {

        }
    }
    public void keyReleased(KeyEvent e) {
        teclasPressionadas.remove(e.getKeyCode());        
    }    

    public void mousePressed(MouseEvent e) {
        /* Clique do mouse desligado*/
        int x = e.getX();
        int y = e.getY();

        this.setTitle("X: " + x + ", Y: " + y
                + " -> Cell: " + (y / Consts.CELL_SIDE) + ", " + (x / Consts.CELL_SIDE));

        this.getHero().getPosicao().setPosicao(y / Consts.CELL_SIDE, x / Consts.CELL_SIDE);

        repaint();
    }

    public void reiniciarFase() {
        System.out.println("Voce morreu! Vidas restantes: " + this.vidas);
        this.faseAtual.clear(); // Limpa a fase antiga
        this.faseAtual = gFase.carregarFase(this.nivelAtual); // Recarrega o *mesmo* nível
        this.atualizaCamera();
    }
    
    public void proximaFase() {
        this.nivelAtual++;
        this.pontuacao += 100; // Ganha pontos por passar de fase
        System.out.println("Passou de fase! Pontos: " + this.pontuacao);

        if (this.nivelAtual > 5) { // Opcional do Req 3 [cite: 42]
            this.fimDeJogoVitoria();
        } else {
            this.faseAtual.clear();
            this.faseAtual = gFase.carregarFase(this.nivelAtual);
            this.atualizaCamera();
        }
    }
    
    public void gameOver() {
        System.out.println("GAME OVER!");
        // (Opcional: Mostrar uma tela de Game Over)
        
        // Reinicia o jogo do zero
        this.nivelAtual = 1;
        this.vidas = 3;
        this.pontuacao = 0;
        this.faseAtual.clear();
        this.faseAtual = gFase.carregarFase(this.nivelAtual);
        this.atualizaCamera();
    }
    
    public void fimDeJogoVitoria() {
        System.out.println("PARABENS, VOCE ZEROU O JOGO!");
        System.out.println("Criado por: [Seu Nome Aqui]"); // Requisito 3 (Opcional) [cite: 42]
        
        // (Opcional: Travar o jogo aqui ou voltar ao menu)
        
        // Por enquanto, vamos só reiniciar o jogo
        this.nivelAtual = 1;
        this.vidas = 3;
        this.pontuacao = 0;
        this.faseAtual.clear();
        this.faseAtual = gFase.carregarFase(this.nivelAtual);
        this.atualizaCamera();
    }
    

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("POO2023-1 - Skooter");
        setAlwaysOnTop(true);
        setAutoRequestFocus(false);
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 561, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    // ---------------------------------------------------------------- //
    // MÉTODOS DO REQUISITO 6 (DRAG-AND-DROP)
    // ---------------------------------------------------------------- //

    @Override
    public void drop(DropTargetDropEvent e) {
        try {
            // Aceita o "drop"
            e.acceptDrop(DnDConstants.ACTION_COPY);
            
            // Pega os dados que foram soltos
            Transferable transferable = e.getTransferable();
            
            // Verifica se são arquivos
            if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                
                // Pega a posição do mouse (relativa ao painel do jogo)
                Point dropPoint = e.getLocation();

                // Converte a lista de dados para uma Lista de Arquivos
                List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                
                // Processa cada arquivo solto
                for (File file : files) {
                    System.out.println("Arquivo solto: " + file.getName());
                    
                    // Verifica se é um .gz (como sugere o PDF e o GZIPInputStream)
                    if (file.getName().toLowerCase().endsWith(".gz")) {
                        adicionarPersonagemDoArquivo(file, dropPoint);
                    }
                }
            }
            e.dropComplete(true);
        } catch (Exception ex) {
            System.err.println("Erro no Drop: " + ex.getMessage());
            ex.printStackTrace();
            e.dropComplete(false);
        }
    }

    private void adicionarPersonagemDoArquivo(File file, Point dropPoint) {
        try (   FileInputStream fis = new FileInputStream(file);
                GZIPInputStream gzis = new GZIPInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(gzis)) {

            // Lê e desserializa o objeto Personagem do arquivo
            Personagem pNovo = (Personagem) ois.readObject();

            // Calcula a posição no *GRID* (levando em conta a câmera)
            // (Esta é a lógica correta que também devemos aplicar ao mousePressed)
            int dropLinha = (dropPoint.y / Consts.CELL_SIDE) + getCameraLinha();
            int dropColuna = (dropPoint.x / Consts.CELL_SIDE) + getCameraColuna();

            // Define a nova posição do personagem
            pNovo.setPosicao(dropLinha, dropColuna);
            
            // Adiciona o personagem à fase atual
            this.faseAtual.add(pNovo);
            System.out.println("Adicionado: " + pNovo.getClass().getSimpleName() + " em " + dropLinha + "," + dropColuna);

        } catch (Exception ex) {
            System.err.println("Falha ao ler objeto do arquivo: " + ex.getMessage());
        }
    }

    // Métodos obrigatórios do DropTargetListener que não usaremos, mas precisam existir.
    @Override
    public void dragEnter(DropTargetDragEvent e) {
         // Aceita apenas arquivos
        if (e.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            e.acceptDrag(DnDConstants.ACTION_COPY);
        } else {
            e.rejectDrag();
        }
    }
    @Override
    public void dragOver(DropTargetDragEvent e) { } // Não precisa
    @Override
    public void dropActionChanged(DropTargetDragEvent e) { } // Não precisa
    @Override
    public void dragExit(DropTargetEvent e) { } // Não precisa
    
    // ---------------------------------------------------------------- //
    // FIM DOS MÉTODOS DE DRAG-AND-DROP
    // ---------------------------------------------------------------- //
}

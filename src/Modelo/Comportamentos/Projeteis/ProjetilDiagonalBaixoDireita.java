package Modelo.Comportamentos.Projeteis;

import java.io.Serializable;

// Projétil que se move na diagonal, para BAIXO e DIREITA. Usado no ataque em V.
public class ProjetilDiagonalBaixoDireita extends Projetil implements Serializable {
    private static final long serialVersionUID = 1L;
            
    public ProjetilDiagonalBaixoDireita(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }

    @Override
    public boolean move() {
        // Tenta mover para baixo. Se falhar (bateu na borda), retorna false.
        if (!super.moveDown())
            return false;
        
        // Tenta mover para a direita. Se falhar (bateu na borda), retorna false.
        if (!super.moveRight())
            return false;
            
        return true; // Moveu com sucesso
    }
}


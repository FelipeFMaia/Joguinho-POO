package Modelo.Comportamentos.Projeteis;

import java.io.Serializable;

// Projétil que se move para BAIXO.
public class ProjetilBaixo extends Projetil implements Serializable {
    private static final long serialVersionUID = 1L;
            
    public ProjetilBaixo(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }

    @Override
    public boolean move() {
        // Tenta mover para baixo. Se falhar (bateu na borda), retorna false.
        return super.moveDown();
    }
}

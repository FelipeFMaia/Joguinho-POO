package Modelo;

import Auxiliar.Desenho;

/**
 *
 * @author Jose F Rodrigues-Jr
 */
public class Esfera extends Estatico{
    public Esfera(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.bTransponivel = false;
    }

    public void autoDesenho() {
        super.autoDesenho();
    }    
}


package Modelo;

import Auxiliar.Desenho;
import java.util.Random;
import java.io.Serializable;

public class BichinhoVaiVemVertical extends Animado implements Serializable, Mortal {
    boolean bUp;
    int contadorDeFrames;
    public BichinhoVaiVemVertical(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        contadorDeFrames = 0;
        this.bTransponivel = false;        
        bUp = true;        
    }

    public void autoDesenho(){
        if(contadorDeFrames == 5){
            contadorDeFrames = 0;
            if(bUp)
                this.moveUp();
            else
                this.moveDown();
            bUp = !bUp;            
        }
        contadorDeFrames++;
        super.autoDesenho();
    }  
}

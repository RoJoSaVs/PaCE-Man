import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Clase CyanGhost con extencion a Ghost
 * crea el fantasma Cyan, agrega las imagenes y el recorrido que el va a hacer en el mapa
 */
public class CyanGhost extends Ghost {

    public CyanGhost(int x, int y,PacBoard pb, int ghostDelay){
        super(x,y,pb,ghostDelay);
    }
    /**
     * metodo loanImagenes
     * @return las imagenes que se le asignan al fantasma en cada posicion
     */
    @Override
    public void loadImages(){
        ghostR = new Image[2];
        ghostL = new Image[2];
        ghostU = new Image[2];
        ghostD = new Image[2];
        try {
            ghostR[0] = ImageIO.read(this.getClass().getResource("resources/images/ghost/cyan/1.png"));
            ghostR[1] = ImageIO.read(this.getClass().getResource("resources/images/ghost/cyan/3.png"));
            ghostL[0] = ImageHelper.flipHor(ImageIO.read(this.getClass().getResource("resources/images/ghost/cyan/1.png")));
            ghostL[1] = ImageHelper.flipHor(ImageIO.read(this.getClass().getResource("resources/images/ghost/cyan/3.png")));
            ghostU[0] = ImageIO.read(this.getClass().getResource("resources/images/ghost/cyan/4.png"));
            ghostU[1] = ImageIO.read(this.getClass().getResource("resources/images/ghost/cyan/5.png"));
            ghostD[0] = ImageIO.read(this.getClass().getResource("resources/images/ghost/cyan/6.png"));
            ghostD[1] = ImageIO.read(this.getClass().getResource("resources/images/ghost/cyan/7.png"));
        }catch(IOException e){
            System.err.println("Cannot Read Images !");
        }
    }

    moveType lastCMove;
    moveType pendMove = moveType.UP;

    @Override
    public moveType getMoveAI(){
        if(isPending){
            if(isStuck){
                if(pendMove == moveType.UP){
                    pendMove = moveType.DOWN;
                }else if(pendMove == moveType.DOWN){
                    pendMove = moveType.UP;
                }
                return pendMove;
            }else{
                return pendMove;
            }
        }
        if(isDead) {
            return baseReturner.getMove(logicalPosition.x,logicalPosition.y, parentBoard.ghostBase.x,parentBoard.ghostBase.y);
        }else {
            ArrayList<moveType> pm = getPossibleMoves();
            int i = ThreadLocalRandom.current().nextInt(pm.size());
            lastCMove = pm.get(i);
            return lastCMove;
        }
    }
}

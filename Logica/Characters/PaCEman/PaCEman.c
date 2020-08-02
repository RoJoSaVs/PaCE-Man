#include "PaCEman.h"
/*EN CASO DE SER NECESARIO ESTABLECER POR SEPARADO EL VALOR PARA LOS PAC-DOTS */

struct PaCEman createPaCEman(){
    struct PaCEman Paceman = {{.posX = 23, .posY = 14}, 2, 0, false};
    return Paceman;
}
/**
 * Cambia los valores en la cual se encuentra PaCEman
 * @param paceman
 * @param posx
 * @param posy
*/
struct PaCEman editPos(struct PaCEman paceman, int posx, int posy){
    paceman.position.posX = posx;
    paceman.position.posY = posy;
    return paceman;
}

/**
 * Actualiza los puntos y las vidas que tiene PaCEman
 * @param paceman
 * @param pacDots
*/
struct PaCEman pointsVerifier(struct PaCEman paceman, int pacDots){
    if ((paceman.pacDots + pacDots) >= 1000){
        paceman.lives = paceman.lives + 1;
        paceman.pacDots = (paceman.pacDots + pacDots) - 1000;
        return paceman;
    }
    else{
        paceman.pacDots = (paceman.pacDots + pacDots);
        return paceman;
    }
}

/**
 * Cambia el modo en el que se encuentra PaCEMan
 * @param paceman
 * @param chasingMode (ataque = true) (huir = false)
*/
struct PaCEman editChasingMode(struct PaCEman paceman, bool chasingMode){
    paceman.chasing = chasingMode;
    return paceman;
}

/*int main(int argc, char *argv[]){
    struct PaCEman myPaCEMan = createPaCEman();

    printf("%d\n", myPaCEMan.position.posX);
    printf("%d\n", myPaCEMan.position.posY);
    printf("%d\n", myPaCEMan.lives);
    printf("%d\n", myPaCEMan.pacDots);
    printf("%s\n", myPaCEMan.chasing ? "true" : "false");
    myPaCEMan = editChasingMode(myPaCEMan, true);
    myPaCEMan = editPos(myPaCEMan, 10, 10);
    myPaCEMan = pointsVerifier(myPaCEMan, 1200);
    printf("%d\n", myPaCEMan.position.posX);
    printf("%d\n", myPaCEMan.position.posY);
    printf("%d\n", myPaCEMan.lives);
    printf("%d\n", myPaCEMan.pacDots);
    printf("%s", myPaCEMan.chasing ? "true" : "false");
    return 0;
}/**/
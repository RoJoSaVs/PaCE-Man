#include "Ghost.h"



/**
 * Crea el struct que define a Blinky
*/
struct Ghost createBlinky(){
    struct Ghost Blinky = { {.posX = 25, .posY = 11,}, true, 5, 'R' };
    return Blinky;
}
/**
 * Crea el struct que define a Inky
*/
struct Ghost createInky(){
    struct Ghost Inky = { {.posX = 17, .posY = 9,}, true, 5, 'B' };
    return Inky;
}
/**
 * Crea el struct que define a Pinky
*/
struct Ghost createPinky(){
    struct Ghost Pinky = { {.posX = 6, .posY = 8,}, true, 5, 'P' };
    return Pinky;
}
/**
 * Crea el struct que define a Clyde
*/
struct Ghost createClyde(){
    struct Ghost Clyde = { {.posX = 9, .posY = 13,}, true, 5, 'O' };
    return Clyde;
}
/**
 *  Modifica la velocidad de un fantasma
 * @param ghost
 * @param speed (max = 10)
*/
struct Ghost editSpeed(struct Ghost ghost, int speed){
    if ((speed > 0) && (speed < 10)){
        ghost.speed = speed;
        return ghost;
    }
    else
    {
        printf("Valor de velocidad no valido\n");
        return ghost;
    }
    
}
/**
 * Cambia el modo en el que se encuentra el fantasma
 * @param ghost
 * @param chasingMode (ataque = true) (huir = false)
*/
struct Ghost editMode(struct Ghost ghost, bool chasingMode){
    ghost.chasing = chasingMode;
    return ghost;
}

/**
 * @param ghostPosX posicion del fantasma en la columna
 * @param ghostPosY posicion del fantasma en la fila
 * @param paceManX posicion de paceman en la columna
 * @param paceManY posicion de paceman en la fila
*/
void BlinkyAlgorithm(int ghostPosX, int ghostPosY, int paceManX, int paceManY, int counter){
    //direction = {direccion en columnas, direccion en filas}
    
}

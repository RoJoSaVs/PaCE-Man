#include "Ghost.h"



/**
 * Crea el struct que define a Blinky
*/
struct Ghost createBlinky(){
    struct Ghost Blinky = { {.posX = 11, .posY = 14,}, true, 5, 'R' };
    return Blinky;
}
/**
 * Crea el struct que define a Inky
*/
struct Ghost createInky(){
    struct Ghost Inky = { {.posX = 11, .posY = 14,}, true, 5, 'B' };
    return Inky;
}
/**
 * Crea el struct que define a Pinky
*/
struct Ghost createPinky(){
    struct Ghost Pinky = { {.posX = 11, .posY = 14,}, true, 5, 'P' };
    return Pinky;
}
/**
 * Crea el struct que define a Clyde
*/
struct Ghost createClyde(){
    struct Ghost Clyde = { {.posX = 11, .posY = 14,}, true, 5, 'O' };
    return Clyde;
}
/**
 *  Modifica la velocidad de un fantasma
 * @param ghost
 * @param speed (max = 10)
*/
struct Ghost editSpeed(struct Ghost ghost, int speed){
    if ((speed > 0) && (speed < 11)){
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
struct Coords move[20];
int tablero[10][10] = {
    {1,1,1,1,1,1,1,1,1,1},
    {0,0,0,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0}};
/**
 * @param ghostPosX posicion del fantasma en la columna
 * @param ghostPosY posicion del fantasma en la fila
 * @param paceManX posicion de paceman en la columna
 * @param paceManY posicion de paceman en la fila
*/
void BlinkyAlgorithm(int ghostPosX, int ghostPosY, int paceManX, int paceManY, int counter){
    //direction = {direccion en columnas, direccion en filas}
    
}



// int main(){
//     BlinkyAlgorithm(0, 0, 0, 9, 0);
//     for(int i = 0; i < 10; i++){
//         printf("%d%d\n", move[i].posX, move[i].posY);
//     }
//     //printf("%d", 10);
//     return 0;
// }
    /*struct Ghost Blinky = createBlinky();
    printf("%d\n", Blinky.speed);
    printf("%s\n", Blinky.chasing ? "true" : "false");
    Blinky = editSpeed(Blinky, 10);
    Blinky = editMode(Blinky, false);
    printf("%d\n", Blinky.speed);
    printf("%s", Blinky.chasing ? "true" : "false");

}/**/
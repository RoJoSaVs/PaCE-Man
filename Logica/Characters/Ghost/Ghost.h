#ifndef GHOST_H
#define GHOST_H
#include <stdio.h>
#include <stdbool.h>
#include "../PowerUps/Coords.h"

struct Ghost{
/*Caracteristicas definidas para los Fantasmas (Blinky), (Inky), (Pinky) y (Clyde)*/
    struct Coords position;
    bool chasing;
    int speed;
    char colorId;
    /**Cada letra denota el color del fantasma
     * R = RED Blinky
     * B = BLUE Inky
     * P = PINK Pinky
     * O = ORANGE Clyde
     * */
};

/*Funciones de creacion de los fantasmas*/
    struct Ghost createBlinky();
    struct Ghost createInky();
    struct Ghost createPinky();
    struct Ghost createClyde();

/*Funciones de modificacion de parametros*/
struct Ghost editSpeed(struct Ghost ghost, int speed);
struct Ghost editMode(struct Ghost ghost, bool chasingMode);
/* Algoritmos de busqueda a PaCEMan*/
    void BlinkyAlgorithm(int ghostPosX, int ghostPosY, int paceManX, int paceManY, int counter);
    void InkyAlgorithm(int posX, int posY, int paceManX, int paceManY);
    void ClydeAlgorithm(int posX, int posY, int paceManX, int paceManY);
    void PinkyAlgorithm(int posX, int posY, int paceManX, int paceManY);
#endif //GHOST_H
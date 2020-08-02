#ifndef POWERUPS_H
#define POWERUPS_H
#include <stdio.h>
#include <stdbool.h>
#include "Coords.h"

struct PowerUps{
    struct Coords position;
    int pointsValue;
    bool chaseMode;
    char nameId;
    /**
     * "P" = POWER PELLET -> chaseMode = True en PaCE-Man y da 50 puntos
     * "F" = FRUTA -> puntos definidos por el usuario
    */
    //Max Pac-Dots = 240
    //Max Power-Pellets = 4
};
   /*Funciones para la creacion de los powerups en la matriz de juego*/
   struct PowerUps PowerPellet(int posx, int posy);
   struct PowerUps pointFruit(int points);

#endif //POWERUPS_H
#ifndef PACEMAN_H
#define PACEMAN_H
#include <stdio.h>
#include <stdbool.h>
#include "../PowerUps/Coords.h"


/*Caracteristicas definidas para el Pac-Man*/
struct PaCEman{
    struct Coords position;
    int lives;
    int pacDots;
    bool chasing;
    /*struct Coords direccion;
     * Vectores de movimientos direccion[X, Y]
     * direccion[0, 0] ->No se mueve
     * direccion[1, 0] ->Se mueve hacia la izquierda
     * direccion[-1, 0] -> Se mueve hacia la derecha
     * direccion[0, 1] -> Se mueve hacia arriba
     * direccion[0, -1] -> Se mueve hacia abajo*/
};


/*Metodos para establecer los valores de pacman*/
    extern struct PaCEman createPaCEman();
    struct PaCEman editPos(struct PaCEman paceman, int posx, int posy);
    struct PaCEman pointsVerifier(struct PaCEman paceman, int pacDots);
    struct PaCEman editChasingMode(struct PaCEman paceman, bool chasingMode);

#endif //PACEMAN_H
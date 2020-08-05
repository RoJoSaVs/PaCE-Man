#include "PowerUps.h"

/**
 * Crea una PowerPill en la posicion deseada esta brinda 
 * la capacidad a paceman de comerse a los fantasmas
 * @param posx
 * @param posY
*/
struct PowerUps PowerPellet(int posx, int posy){
    struct PowerUps powerPill = {{.posX = posx, .posY =posy}, 5, true, 'P'};
    return powerPill;
}

/**
 * Crea una fruta de puntaje deseado
 * @param points Puntos que vale la fruta creada
*/
struct PowerUps pointFruit(int points){
    struct PowerUps fruit = {{.posX = 17, .posY = 14}, points, false, 'F'};
    return fruit;
}

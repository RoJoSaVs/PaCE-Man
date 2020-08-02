#include <string.h>
#include "Logic.h"
#include "../sockets/server/Server.h"

//gcc Logic.c ../sockets/server/Server.c -o Logic -lws2_32

/**
 * Crea un powerUp o una fruta para PaCEman y genera los 
 * fantasmas cuando el usuario lo desea
 * @param ptr -> Contiene la instruccion hecha por el usuario
 */ 
void createFunction(char *ptr){
    char totalData[64];
    if (strcmp(ptr, "G") == 0){
        ptr = strtok(NULL, " ");
        
        if (strcmp(ptr, "B") == 0){
            ghost = createBlinky();
            sprintf(totalData, "%d %d %d %d %c ", ghost.chasing, ghost.position.posX, 
                    ghost.position.posY, ghost.speed, ghost.colorId);
            printf("%s", totalData);
            printf("Blinky\n");

        }

        else if (strcmp(ptr, "I") == 0){
            ghost = createInky();
            sprintf(totalData, "%d %d %d %d %c ", ghost.chasing, ghost.position.posX, 
                    ghost.position.posY, ghost.speed, ghost.colorId);
            printf("%s", totalData);
            printf("Inky\n");
        }

        else if (strcmp(ptr, "P") == 0){
            ghost = createPinky();
            sprintf(totalData, "%d %d %d %d %c ", ghost.chasing, ghost.position.posX, 
                    ghost.position.posY, ghost.speed, ghost.colorId);
            printf("%s", totalData);
            printf("Pinky\n");
        }
        
        else if (strcmp(ptr, "C") == 0){
            ghost = createClyde();
            sprintf(totalData, "%d %d %d %d %c ", ghost.chasing, ghost.position.posX, 
                    ghost.position.posY, ghost.speed, ghost.colorId);
            printf("%s", totalData);
            printf("Clyde\n");
        }
        
        else{
            printf("El fantasma no existe\n");
        }
        printf("Ghost function to set a ghost in server an client\n");

    }

    else if (strcmp(ptr, "F") == 0){
        ptr = strtok(NULL, " ");
        powerUp = pointFruit(atoi(ptr));
        sprintf(totalData, "%d %d %d %d %c ", powerUp.chaseMode, powerUp.position.posX, 
                    powerUp.position.posY, powerUp.pointsValue, powerUp.nameId);
        printf("%s", totalData);
        printf("Fruit\n");
        
    }

    else if (strcmp(ptr, "P") == 0){
        int row, column;
        ptr = strtok(NULL, " ");
        row = atoi(ptr);
        ptr = strtok(NULL, " ");
        column = atoi(ptr);

        if (((column == 0) && (row == 0)) || (boardGame[row][column] == 1)){
            printf("No son coordenadas validas\n");
        }
        else{
            powerUp = PowerPellet(row, column);
            sprintf(totalData, "%d %d %d %d %c ", powerUp.chaseMode, powerUp.position.posX, 
                    powerUp.position.posY, powerUp.pointsValue, powerUp.nameId);
            printf("%s", totalData);
            printf("PowerPill\n");
        }
    }

    else{
        printf("%s", ptr);
        printf(" no se puede crear\n");
    }

    char *message_to_client=totalData;
    //char message_to_client[]="hola desde el server, desde el archivo call_Server";
    printf("\n mensaje del cliente: %s", send_to_client(message_to_client,512));
}


/**
 * Cambia el atributo de la velocidad para un fantasma
 * @param ptr -> Contiene la instruccion hecha por el usuario
 */
void setSpeedFunction(char *ptr){
    int speed;
    char totalData[64];

    if (strcmp(ptr, "B") == 0){
        ghost = createBlinky();
        ptr = strtok(NULL, " ");
        speed = atoi(ptr);
        ghost = editSpeed(ghost, speed);
        sprintf(totalData, "%d %d %d %d %c ", ghost.chasing, ghost.position.posX, 
                    ghost.position.posY, ghost.speed, ghost.colorId);
            printf("%s", totalData);
        printf("Blinky\n");
    }

    else if (strcmp(ptr, "I") == 0){
        ghost = createInky();
        ptr = strtok(NULL, " ");
        speed = atoi(ptr);
        ghost = editSpeed(ghost, speed);
        sprintf(totalData, "%d %d %d %d %c ", ghost.chasing, ghost.position.posX, 
                    ghost.position.posY, ghost.speed, ghost.colorId);
            printf("%s", totalData);
        printf("Inky\n");
    }
    else if (strcmp(ptr, "P") == 0){
        ghost = createPinky();
        ptr = strtok(NULL, " ");
        speed = atoi(ptr);
        ghost = editSpeed(ghost, speed);
        sprintf(totalData, "%d %d %d %d %c ", ghost.chasing, ghost.position.posX, 
                    ghost.position.posY, ghost.speed, ghost.colorId);
            printf("%s", totalData);
        printf("Pinky\n");
    }
    else if (strcmp(ptr, "C") == 0){
        ghost = createClyde();
        ptr = strtok(NULL, " ");
        speed = atoi(ptr);
        ghost = editSpeed(ghost, speed);
        sprintf(totalData, "%d %d %d %d %c ", ghost.chasing, ghost.position.posX, 
                    ghost.position.posY, ghost.speed, ghost.colorId);
            printf("%s", totalData);
        printf("Clyde\n");
    }
    else{
        printf("El fantasma no existe\n");
    }
}


/**
 * Funcion principal que se encarga de estar leyendo la entrada de 
 * comandos en la consola
*/
void readLine(){
    int init_size;
    char *ptr;

    while(isRunning){
        printf("\n Ingrese el comando: ");
        gets(word);
        init_size = strlen(word);
        ptr = strtok(word, " ");
        if (strcmp(ptr, "create") == 0){
            ptr = strtok(NULL, " ");
            createFunction(ptr);
        }

        else if (strcmp(ptr, "setSpeed") == 0){
            ptr = strtok(NULL, " ");
            setSpeedFunction(ptr);
        }

        else if (strcmp(ptr, "exit") == 0){
            isRunning = false;
        }

        else{
            printf("Comando no valido!!\n");
        }
    }
    printf("Termine\n");
}



    
int main(){
    readLine();
    // int xData = 10;
    // int yData = 15;
    // int zData = 1;
    // int a = 5;
    // int b = 11;

    // char totalData[64];
    // sprintf(totalData, "%d %d %d %d %d ", a, b, xData, yData, zData);
    // printf("%s", totalData);
    return 0;
}
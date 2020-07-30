//gcc Call_Server.c Server.c -o Call_Server -lws2_32

#include <windows.h>
#include <winsock2.h>
#include <ws2tcpip.h>
#include <stdlib.h>
#include <stdio.h>
#include "Server.h"

int main(){
  char message_to_client[]="hola desde el server, desde el archivo call_Server";
  printf("\n mensaje del cliente: %s\n", send_to_client(message_to_client));
  Sleep(12000);
  return 0;  
}


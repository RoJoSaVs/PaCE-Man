//ejecutar en terminal con:
//gcc Server.c -o Server -lws2_32
//referencia : https://docs.microsoft.com/en-us/windows/win32/winsock/complete-server-code


#undef UNICODE

#define _WIN32_WINNT 0x0501

#define WIN32_LEAN_AND_MEAN

#include <windows.h>
#include <winsock2.h>
#include <ws2tcpip.h>
#include <stdlib.h>
#include <stdio.h>



// Need to link with Ws2_32.lib
#pragma comment (lib, "Ws2_32.lib")
// #pragma comment (lib, "Mswsock.lib")


/**
 * Funcion que envía mensajes al cliente
 * @param message_to_client-> mensaje a enviar
 * @param size_of_message-> longitud del mensaje a enviar
 * @param DEFAULT_BUFLEN-> longitud de la cadena del mensaje a recibir
 * @param DEFAULT_PORT-> puerto al que se conectarán los sockets
 * @param error_size-> Longitud del mensaje de error
 * @return mensaje del cliente, o mensaje de error
*/

char* send_to_client(char* message_to_client,int size_of_message,int DEFAULT_BUFLEN, const char* DEFAULT_PORT,int error_size)
{
    //variables del socket

    WSADATA wsaData;
    int iResult;

    SOCKET ListenSocket = INVALID_SOCKET;
    SOCKET ClientSocket = INVALID_SOCKET;

    struct addrinfo *result = NULL;
    struct addrinfo hints;

    int iSendResult;
    char recvbuf[DEFAULT_BUFLEN];
    int recvbuflen = DEFAULT_BUFLEN;

    //variables del mensaje

    char respuesta[size_of_message];

    char *error= malloc(error_size);
    strcpy(error,"error");

    char *desde_cliente= malloc(DEFAULT_BUFLEN);
    
    
    
    // Initialize Winsock
    iResult = WSAStartup(MAKEWORD(2,2), &wsaData);
    if (iResult != 0) {
        printf("WSAStartup failed with error: %d\n", iResult);
        return error;
    }

    ZeroMemory(&hints, sizeof(hints));
    hints.ai_family = AF_INET;
    hints.ai_socktype = SOCK_STREAM;
    hints.ai_protocol = IPPROTO_TCP;
    hints.ai_flags = AI_PASSIVE;

    // resuelve la direccion y el puerto de los sockets
    iResult = getaddrinfo(NULL, DEFAULT_PORT, &hints, &result);
    if ( iResult != 0 ) {
        printf("getaddrinfo failed with error: %d\n", iResult);
        WSACleanup();
        return error;
    }

    // crea un socket para conectarse con el cliente
    ListenSocket = socket(result->ai_family, result->ai_socktype, result->ai_protocol);
    if (ListenSocket == INVALID_SOCKET) {
        printf("socket failed with error: %ld\n", WSAGetLastError());
        freeaddrinfo(result);
        WSACleanup();
        return error;
    }

    // Configura el TCP del socket para que escuche
    iResult = bind( ListenSocket, result->ai_addr, (int)result->ai_addrlen);
    if (iResult == SOCKET_ERROR) {
        printf("bind failed with error: %d\n", WSAGetLastError());
        freeaddrinfo(result);
        closesocket(ListenSocket);
        WSACleanup();
        return error;
    }

    freeaddrinfo(result);

    iResult = listen(ListenSocket, SOMAXCONN);
    if (iResult == SOCKET_ERROR) {
        printf("listen failed with error: %d\n", WSAGetLastError());
        closesocket(ListenSocket);
        WSACleanup();
        return error;
    }

    // Acepta la conexión de un cliente
    ClientSocket = accept(ListenSocket, NULL, NULL);
    if (ClientSocket == INVALID_SOCKET) {
        printf("accept failed with error: %d\n", WSAGetLastError());
        closesocket(ListenSocket);
        WSACleanup();
        return error;
    }

    // No se necesita más la conexión al socket, por lo que la cierra
    closesocket(ListenSocket);

    // Recibe lo que el cliente envie hasta que se cierre la conexión
    do {

        iResult = recv(ClientSocket, recvbuf, recvbuflen, 0);
        strcpy(desde_cliente,recvbuf);
        if (iResult > 0) {
            //printf("Bytes received: %d\n", iResult);

        // Envía el mensaje hacia el cliente
            strcpy(respuesta,message_to_client);
            iSendResult = send( ClientSocket, respuesta, sizeof(respuesta), 0 );
            if (iSendResult == SOCKET_ERROR) {
                printf("send failed with error: %d\n", WSAGetLastError());
                closesocket(ClientSocket);
                WSACleanup();
                return error;
            }
            //printf("Bytes sent: %d\n", iSendResult);
        }
        else if (iResult == 0)
            printf("Connection closing...\n");
        else  {
            printf("recv failed with error: %d\n", WSAGetLastError());
            closesocket(ClientSocket);
            WSACleanup();
            return error;
        }

    } while (iResult > 0);

    // Termina la conexión
    iResult = shutdown(ClientSocket, SD_SEND);
    if (iResult == SOCKET_ERROR) {
        printf("shutdown failed with error: %d\n", WSAGetLastError());
        closesocket(ClientSocket);
        WSACleanup();
        return error;
    }

    // Limpia el socket
    closesocket(ClientSocket);
    WSACleanup();

    return desde_cliente;
}
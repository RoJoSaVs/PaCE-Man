//referencia http://www2.ic.uff.br/~michael/kr1999/2-application/2_06-sockettcp.htm
//https://www.codejava.net/java-se/networking/java-socket-client-examples-tcp-ip


import java.io.*;
import java.net.*;

public class Client extends JPanel{
    int port=8080;

    /**
    * Función que envía mensaje al server
    * @param message es el mensaje que se enviará al server
    * @return mensaje del server
    * @throws Exception en caso de que no se pueda conectar con el server
    */
    public String send_to_server(String message) throws Exception {
        try {

            //el string que contendrá lo que el server envíe
            String modifiedSentence;

            //abre socket

            Socket clientSocket = new Socket("localhost", port);

            //envía al socket el mensaje

            OutputStream output = clientSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(message);

            //lee el mensaje de vuelta del socket

            BufferedReader inFromServer =
                    new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            modifiedSentence = inFromServer.readLine();

            //cierra la conexión 
            clientSocket.close();

            //retorna el mensaje del server
            return modifiedSentence;
        }
        catch (IOException exception){
            //en caso de no poder realizar la conexion, devuelve el mensaje de error
            return "1234567890123456789";// es el símbolo de error de nuestro programa
        }
    }

}

//referencia http://www2.ic.uff.br/~michael/kr1999/2-application/2_06-sockettcp.htm
//https://www.codejava.net/java-se/networking/java-socket-client-examples-tcp-ip

import java.io.*;
import java.net.*;

public class Client extends JPanel{
    int port=8080;

    public String send_to_server(String message) throws Exception {
        try {
            String modifiedSentence;

            Socket clientSocket = new Socket("localhost", port);

            OutputStream output = clientSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(message);


            BufferedReader inFromServer =
                    new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


            modifiedSentence = inFromServer.readLine();

            clientSocket.close();

            return modifiedSentence;
        }
        catch (IOException exception){
            return "1234567890123456789";//w es el símbolo de error de nuestro programa
        }
    }

}

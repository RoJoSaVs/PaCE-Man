

import javax.swing.*;
import java.io.*;
import java.net.*;

public class Client extends JPanel {
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
            return "w";//w es el símbolo de error de nuestro programa
        }
    }

}

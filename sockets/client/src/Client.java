//referencia http://www2.ic.uff.br/~michael/kr1999/2-application/2_06-sockettcp.htm
import java.io.*;
import java.net.*;

public class Client {
    int port=8080;

    public String send_to_server(String message) throws Exception{
        String modifiedSentence;

        BufferedReader inFromUser =
                new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = new Socket("localhost", port);

        DataOutputStream outToServer =
                new DataOutputStream(clientSocket.getOutputStream());

        outToServer.writeBytes("p"+ '\n');
        outToServer.writeBytes(message+ '\n');

        BufferedReader inFromServer =
                new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


        modifiedSentence = inFromServer.readLine();

        clientSocket.close();

        return modifiedSentence;
    }

    public static void main(String[] args) throws IOException {

    }
}
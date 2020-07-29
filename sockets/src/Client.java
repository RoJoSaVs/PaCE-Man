//referencia http://www2.ic.uff.br/~michael/kr1999/2-application/2_06-sockettcp.htm
import java.io.*;
import java.net.*;
class Client {

    public static void main(String argv[]) throws Exception
    {
        String sentence;
        String modifiedSentence;

        BufferedReader inFromUser =
                new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = new Socket("localhost", 8080);

        DataOutputStream outToServer =
                new DataOutputStream(clientSocket.getOutputStream());

        sentence = "prueba";

        outToServer.writeBytes("hola desde cliente"+ '\n');

        BufferedReader inFromServer =
                new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


        modifiedSentence = inFromServer.readLine();

        System.out.println("FROM SERVER: " + modifiedSentence);

        clientSocket.close();

    }
}
//referencia http://www2.ic.uff.br/~michael/kr1999/2-application/2_06-sockettcp.htm
import java.io.*;
import java.net.*;

public class Client {
    int port=8080;

    public String send_to_server(String message) throws Exception{
        String modifiedSentence;

        //BufferedReader inFromUser =
         //       new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = new Socket("localhost", port);

        DataOutputStream outToServer =
                new DataOutputStream(clientSocket.getOutputStream());


        outToServer.writeBytes(message+ '\n');
        Thread.sleep(100);

        /*
        outToServer.writeUTF("Hello from the other side!");
        outToServer.flush(); // send the message
        outToServer.close(); // close the output stream when we're done.
        */

        /*OutputStream outstream = clientSocket.getOutputStream();
        PrintWriter out = new PrintWriter(outstream);

        String toSend = "String to send";

        out.print(toSend ); */

        BufferedReader inFromServer =
                new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


        modifiedSentence = inFromServer.readLine();

        clientSocket.close();

        return modifiedSentence;
    }

    public static void main(String[] args) throws IOException {

    }
}
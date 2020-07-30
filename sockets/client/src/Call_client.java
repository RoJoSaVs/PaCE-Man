
public class Call_client {
    public static void main(String args[]) throws Exception {
        Client client=new Client();
        System.out.println(client.send_to_server("mensaje de prueba para el servidor"));

    }
}

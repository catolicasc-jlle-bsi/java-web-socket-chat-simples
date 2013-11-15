package Boot;

import ChatClient.Chat;
import ChatServer.Server;

public class Initializer {

    public static void main(String[] args) {

        int port = 2000;
        String host = "localhost";

        try {
            new Server(port);
            new Chat(host, port);
            new Chat(host, port);
            new Chat(host, port);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}

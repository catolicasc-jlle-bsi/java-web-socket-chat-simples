package ChatServer;

import ChatServer.Client;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable {

    public ServerSocket serverSocket;
    public int port;
    public int clientCount;
    public Server chatServer;
    private final ArrayList clientArr;
    private final Thread thread;

    public ClientHandler(Server chatServer, int port) {
        
        this.chatServer = chatServer;
        this.port = port;
        
        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            System.err.println(e);
            System.exit(0);
        }
        
        clientCount = 0;
        
        try {
            chatServer.newTitle(serverSocket.getInetAddress().getLocalHost().getHostName(), port, clientCount);
        } catch (Exception e) {
        }
        
        clientArr = new ArrayList();
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                chatServer.showMessage(socket.getInetAddress() + " conectou!");
                clientArr.add(new Client(socket, this));
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    public synchronized void broadcast(String msg) {
        chatServer.showMessage(msg);
        for (int i = 0; i < clientArr.size(); i++) {
            Client client = (Client) clientArr.get(i);
            client.sendMessage(msg);
        }
    }

    public synchronized void killClient(Client c) {
        String client = c.socket.getInetAddress().toString();
        
        try {
            c.in.close();
            c.out.close();
            c.socket.close();
            for (int i = 0; i < clientArr.size(); i++) {
                if (((Client) clientArr.get(i)).equals(c)) {
                    clientArr.remove(i);
                }
            }
            clientArr.trimToSize();
            clientCount--;
            chatServer.newTitle(serverSocket.getInetAddress().getLocalHost().getHostName(), port, clientCount);
        } catch (Exception e) {
            System.err.println(e);
        }
        broadcast(client + " desconectou!");
    }
}
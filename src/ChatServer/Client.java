package ChatServer;

import java.io.*;
import java.net.*;

public class Client extends Thread {

    public Socket socket;
    public ClientHandler ch;
    public BufferedReader in;
    public PrintWriter out;
    private String host;

    public Client(Socket socket, ClientHandler ch) {

        this.socket = socket;
        this.ch = ch;
        ch.clientCount++;
      
        try {
            ch.chatServer.newTitle(ch.serverSocket.getInetAddress().getLocalHost().getHostName(), ch.port, ch.clientCount);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            host = socket.getInetAddress().getHostName();
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Bem-vindo " + ch.serverSocket.getInetAddress().getLocalHost().getHostName());
        } catch (Exception e) {
        }
        
        this.start();
    }

    public void sendMessage(String msg) {
        try {
            out.println(msg);
        } catch (Exception e) {
            ch.killClient(this);
        }
    }

    @Override
    public void run() {
        String inStream = " ";
        
        try {
            while ((inStream = in.readLine()) != null) {
                ch.broadcast(socket.getInetAddress() + ": " + inStream);
                inStream = " ";
            }
            out.close();
            in.close();
            socket.close();
        } catch (Exception e) {
        }
        ch.killClient(this);
    }
}
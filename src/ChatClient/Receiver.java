package ChatClient;

import java.net.*;
import java.io.*;

public class Receiver implements Runnable {

    private BufferedReader in;
    private String host;
    private final Chat chat;

    public Receiver(Chat chat, Socket theSocket) {

        this.chat = chat;
        try {
            in = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));
            chat.setTitle(theSocket.getInetAddress().getHostName()
                    + ":" + theSocket.getPort());
        } catch (Exception e) {
        }
    }

    @Override
    public void run() {

        try {
            String inStream;
            while ((inStream = in.readLine()) != null) {
                chat.setIncomingMessage(inStream);
            }
        } catch (Exception e) {
        }
    }

    public void closeInStream() {

        try {
            if (in != null) {
                in.close();
            }
        } catch (Exception e) {
        }
    }
}

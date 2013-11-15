package ChatServer;

import ChatServer.ClientHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Server extends JFrame {

    private JTextArea messageArea = new JTextArea();
    private JScrollPane jsp = new JScrollPane(messageArea);
    private final ClientHandler ch;

    public Server(int port) {
        ch = new ClientHandler(this, port);
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        jsp = new JScrollPane(messageArea);

        Container con = getContentPane();
        con.setLayout(new BorderLayout());
        con.add(jsp, "Center");
        addWindowListener(closeWindow);
        setSize(600, 300); 
        setVisible(true);
    }

    WindowAdapter closeWindow = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    };

    public void newTitle(String host, int port, int clientCount) {
        setTitle("Chat em " + host + " , na porta " + port + " com " + clientCount + " usuários conectados");
    }

    public void showMessage(String msg) {
        messageArea.append(msg + "\n");
    }
}
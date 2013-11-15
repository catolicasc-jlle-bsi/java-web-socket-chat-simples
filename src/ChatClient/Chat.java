package ChatClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Chat extends JFrame implements ActionListener {

    private final String host;
    private final int port;
    private Socket theSocket;
    private final Thread receiveactivity;

    private final JTextField writeField = new JTextField(46);
    private final JTextArea messageArea = new JTextArea();
    private final JScrollPane jsp = new JScrollPane(messageArea);
    private final JPanel up = new JPanel(), down = new JPanel();
    private final JButton sendButton = new JButton();
    private final Receiver receiver;
    private final Sender sender;

    public Chat(String host, int port) throws IOException {

        this.host = host;
        this.port = port;

        Container con = getContentPane();

        up.setLayout(new FlowLayout(FlowLayout.RIGHT));
        up.setBackground(Color.lightGray);

        down.setLayout(new FlowLayout(FlowLayout.RIGHT));
        down.setBackground(Color.lightGray);
        down.add(writeField);
        down.add(sendButton);

        messageArea.setEditable(false);

        writeField.setText(" ");
        writeField.requestFocus();

        sendButton.setText(">");

        sendButton.addActionListener(this);
        writeField.addActionListener(this);
        
        con.add(down, BorderLayout.SOUTH);
        con.add(up, BorderLayout.NORTH);

        con.add(jsp, BorderLayout.CENTER);

        setSize(600, 300);
        setVisible(true);

        try {
            theSocket = new Socket(host, port);
        } catch (Exception e) {
            System.err.println(e);
        }
        
        sender = new Sender(theSocket);
        receiver = new Receiver(this, theSocket);
        receiveactivity = new Thread(receiver);
        receiveactivity.start();
    }

    public void clearWriteField() {
        writeField.setText("");
    }

    public void setIncomingMessage(String s) {
        messageArea.append("\n" + s);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == writeField || ae.getSource() == sendButton) {
            try {
                sender.sendAway(writeField.getText().trim());
                clearWriteField();
            } catch (Exception e) {
            }
        }
    }
}
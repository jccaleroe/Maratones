package Sockets.rps;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import java.io.*;
import java.net.*;

public class Client {
    private JFrame frame;
    private ArrayList<JButton> buttonList;
    private PrintWriter writer;
    private BufferedReader reader;
    private Socket sock;
    private boolean turn = false;

    public void go() {
        frame = new JFrame();
        JPanel panel = new JPanel();
        buttonList = new ArrayList<JButton>();
        JButton rock = new JButton("ROCK");
        rock.setName("ROCK");
        buttonList.add(rock);
        rock.addActionListener(new ButtonListener(rock));
        panel.add(rock);
        JButton paper = new JButton("PAPER");
        paper.setName("PAPER");
        buttonList.add(paper);
        paper.addActionListener(new ButtonListener(paper));
        panel.add(paper);
        JButton scissors = new JButton("SCISSORS");
        scissors.setName("SCISSORS");
        buttonList.add(scissors);
        scissors.addActionListener(new ButtonListener(scissors));
        panel.add(scissors);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(300, 200);
        frame.setVisible(true);
        setUpNetworking();
        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();
    }

    class ButtonListener implements ActionListener {
        JButton aux;

        public ButtonListener(JButton x) {
            aux = x;
        }

        public void actionPerformed(ActionEvent e) {
            if (turn) {
                turn = false;
                writer.println(aux.getName());
                writer.flush();
            }
        }
    }

    private void setUpNetworking() {
        try {
            sock = new Socket("localhost", 6300);
            InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(streamReader);
            writer = new PrintWriter(sock.getOutputStream());
            System.out.println("networking established");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Problem in setUpNetworking");
        }
    }

    class IncomingReader implements Runnable {
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    if (message.equals("SET"))
                        turn = true;
                    else {
                        JOptionPane.showMessageDialog(frame, message);
                    }
                }
            } catch (IOException ex) {
                System.out.println("Problem in IncomingReader");
            }
        }
    }

    public static void main(String[] args) {
        new Client().go();
    }
}
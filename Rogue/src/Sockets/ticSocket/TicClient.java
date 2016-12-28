package ticSocket;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import java.io.*;
import java.net.*;

public class TicClient {

    private JFrame frame;
    private ArrayList<JButton> buttonList;
    private String[] results = new String[9];
    private String OX;
    private boolean[] used = new boolean[9];
    private boolean inTurn = false;
    private PrintWriter writer;
    private BufferedReader reader;
    private Socket sock;

    public void go() {
        frame = new JFrame();
        GridLayout grid = new GridLayout(3, 3);
        grid.setVgap(1);
        grid.setHgap(2);
        JPanel panel = new JPanel(grid);
        buttonList = new ArrayList<JButton>();
        results = new String[9];
        for (int i = 0; i < 9; i++) {
            results[i] = "";
            used[i] = false;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton button = new JButton();
                Font bigFont = new Font("serif", Font.BOLD, 28);
                button.setFont(bigFont);
                buttonList.add(button);
                button.addActionListener(new ButtonListener(button));
                panel.add(button);
            }
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(300, 300);
        frame.setVisible(true);
        setUpNetworking();
        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();
    }

    class ButtonListener implements ActionListener {
        int position;
        JButton aux;

        public ButtonListener(JButton x) {
            aux = x;
            position = buttonList.indexOf(x);
        }

        public void actionPerformed(ActionEvent e) {
            if (!(used[position]) && inTurn) {
                results[position] = OX;
                used[position] = true;
                String tmp = String.valueOf(position);
                writer.println(OX + " " + tmp);
                writer.flush();
            }
        }
    }

    public void resetGame(String tmp) {
        JOptionPane.showMessageDialog(frame, tmp);
        for (int i = 0; i < 9; i++) {
            results[i] = "";
            used[i] = false;
            buttonList.get(i).setText("");
        }
    }

    private void setUpNetworking() {
        try {
            sock = new Socket("localhost", 7200);
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
            String[] move;
            int tmp;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println("client read " + message);
                    move = message.split(" ");
                    if (move[2].equals("set")) {
                        inTurn = Boolean.parseBoolean(move[0]);
                        OX = move[1];
                    } else {
                        inTurn = Boolean.parseBoolean(move[0]);
                        tmp = Integer.parseInt(move[2]);
                        buttonList.get(tmp).setText(move[1]);
                        results[tmp] = move[1];
                        used[tmp] = true;
                    }
                }
            } catch (IOException ex) {
                System.out.println("Problem in IncomingReader");
            }
        }
    }

    public String[] getResults() {
        return results;
    }

    public boolean[] getUsed() {
        return used;
    }
}
package ticSocket;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class TicServer {
    private ArrayList<PrintWriter> clientOutputStreams;
    private boolean[] inTurn = {true, false};
    private int turn = 0;
    private boolean nextTurn = false;

    public void go() {
        clientOutputStreams = new ArrayList<PrintWriter>();
        try {
            ServerSocket server = new ServerSocket(6200);
            PrintWriter writer;
            Socket client;
            for (int i = 0; i < 2; i++) {
                client = server.accept();
                writer = new PrintWriter(client.getOutputStream());
                clientOutputStreams.add(writer);
                Thread thread = new Thread(new ClientHandler(client));
                thread.start();
                System.out.println("Establish connection");
            }
            server.close();
            setGame();

        } catch (IOException e) {
            System.out.println("Problem in go(server)");
            e.printStackTrace();
        }
    }

    public class ClientHandler implements Runnable {
        BufferedReader reader;

        public ClientHandler(Socket socket) {
            try {
                InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
                reader = new BufferedReader(isReader);
            } catch (IOException e) {
                System.out.println("Problem in ClientHandler constructor");
            }
        }

        public void run() {
            String move;
            try {
                while ((move = reader.readLine()) != null) {
                    System.out.println("read " + move);
                    tellEveryone(move);
                }
            } catch (Exception ex) {
                System.out.println("Problem in run (server)");
            }
        }
    }

    public void tellEveryone(String move) {
        String[] message;
        String tmp;
        turn = (turn + 1) % 2;
        nextTurn = inTurn[turn];
        for (PrintWriter aux : clientOutputStreams) {
            try {
                message = move.split(" ");
                tmp = String.valueOf(nextTurn);
                aux.println(tmp + " " + message[0] + " " + message[1]);
                aux.flush();
                turn = (turn + 1) % 2;
                nextTurn = inTurn[turn];
            } catch (Exception ex) {
                System.out.println("Problem in tellEveryone");
            }
        }
    }

    public void setGame() {
        String tmp;
        boolean aux = true;
        String option = "X";
        for (PrintWriter a : clientOutputStreams) {
            tmp = String.valueOf(aux);
            a.println(tmp + " " + option + " " + "set");
            a.flush();
            aux = false;
            option = "O";
        }
    }

    public static void main(String[] args) {
        new TicServer().go();
    }
}
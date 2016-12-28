package Sockets.rps;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    private ArrayList<PrintWriter> clientOutputStreams;
    private ArrayList<String> options;

    public void go() {
        clientOutputStreams = new ArrayList<PrintWriter>();
        try {
            ServerSocket server = new ServerSocket(6300);
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
            System.out.println("Problem in go (server)");
            e.printStackTrace();
        }
    }

    public class ClientHandler implements Runnable {
        BufferedReader reader;
        String option;

        public ClientHandler(Socket socket) {
            try {
                InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
                reader = new BufferedReader(isReader);
            } catch (IOException e) {
                System.out.println("Problem in ClientHandler constructor");
            }
        }

        public void run() {
            try {
                while ((option = reader.readLine()) != null) {
                    options.add(option);
                    if (options.size() == 2)
                        checkWinner();
                }
            } catch (Exception ex) {
                System.out.println("Problem in run (server)");
            }
        }
    }

    public void setGame() {
        options = new ArrayList<String>();
        for (PrintWriter a : clientOutputStreams) {
            a.println("SET");
            a.flush();
        }
    }

    public void checkWinner() {
        String tmp = options.get(0);
        String tmpB = options.get(1);
        if (tmp.equals("ROCK") && tmpB.equals("SCISSORS"))
            tellEveryone(tmp + " ES EL GANADOR");
        else if (tmpB.equals("ROCK") && tmp.equals("SCISSORS"))
            tellEveryone(tmpB + " ES EL GANADOR");
        else if (tmp.equals("SCISSORS") && tmpB.equals("PAPER"))
            tellEveryone(tmp + " ES EL GANADOR");
        else if (tmpB.equals("SCISSORS") && tmp.equals("PAPER"))
            tellEveryone(tmpB + " ES EL GANADOR");
        else if (tmp.equals("PAPER") && tmpB.equals("ROCK"))
            tellEveryone(tmp + " ES EL GANADOR");
        else if (tmpB.equals("PAPER") && tmp.equals("ROCK"))
            tellEveryone(tmpB + " ES EL GANADOR");
        else
            tellEveryone("EMPATE");
        setGame();
    }

    public void tellEveryone(String move) {
        for (PrintWriter aux : clientOutputStreams) {
            try {
                aux.println(move);
                aux.flush();
            } catch (Exception ex) {
                System.out.println("Problem in tellEveryone");
            }
        }
    }

    public static void main(String[] args) {
        new Server().go();
    }
}
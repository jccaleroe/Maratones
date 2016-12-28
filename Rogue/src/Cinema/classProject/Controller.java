package Cinema.classProject;


import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;

public class Controller {
    public static Controller a = new Controller();
    private static MyDatabase dataBase = MyDatabase.getMyDatabase();
    private static GUI view = new GUI();
    private long time;

    public static void writeFile(ArrayList<String> lines, String fileName) {
        File file = new File(fileName);
        try {
            BufferedWriter wr = new BufferedWriter(new FileWriter(file));
            for (String string : lines) {
                wr.append(string);
                wr.newLine();
            }
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeClients() {
        ArrayList<String> tmp3 = new ArrayList<String>();
        String tmp2;
        for (Client tmp : dataBase.getClients()) {
            tmp2 = tmp.toString();
            tmp2 += "\n";
            tmp2 += tmp.getCard().toString();
            tmp3.add(tmp2);
        }
        writeFile(tmp3, "Client.txt");
    }

    public static boolean modifyFunction(String name, String nName, String hour, int price) {
        for (Function a : dataBase.getFunctions()) {
            if (a.getMovie().getName().equals(name)) {
                a.getMovie().setName(nName);
                a.setDate(hour);
                a.setPrice(price);
                return true;
            }
        }
        return false;
    }

    public static boolean checkClientID(String id, int code, String nID,
                                        int nCode, String email, int nBalance, JFrame f) {
        for (Client tmp : dataBase.getClients()) {
            if (tmp.getIdNumber().equals(id) && tmp.getCard().getCode() == code) {
                tmp.setIdNumber(nID);
                tmp.getCard().setCode(nCode);
                int aux = tmp.getCard().getBalance() + nBalance;
                tmp.getCard().setBalance(aux);
                tmp.setEmail(email);
                JOptionPane.showMessageDialog(f, nID + "se ha actualizado correctamente");
                return true;
            }
        }
        JOptionPane.showMessageDialog(f, "Los datos no son validos");
        return false;
    }

    public static boolean checkClientID(String a, int b, JFrame frame) {
        for (Client tmp : dataBase.getClients()) {
            if (tmp.getIdNumber().equals(a) && tmp.getCard().getCode() == b) {
                String tmp2 = tmp.toString();
                tmp2 += "\n";
                tmp2 += tmp.getCard().toString();
                JOptionPane.showMessageDialog(frame, tmp2);
                return true;
            }
        }
        JOptionPane.showMessageDialog(frame, "Los datos no son validos");
        return false;
    }

    public static boolean checkAdminPassword(String a, JFrame b) {
        for (Administrator tmp : dataBase.getAdministrators()) {
            try {
                int aux = Integer.parseInt(a);
                if (tmp.getCode() == aux) {
                    return true;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(b, "Password incorrect");
            }
        }
        JOptionPane.showMessageDialog(b, "Password incorrect");
        return false;
    }

    public static boolean checkService(String action, String function, String id, int code,
                                       String chair) {
        for (Client c : dataBase.getClients()) {
            if (c.getIdNumber().equals(id) && c.getCard().getCode() == code) {
                for (Function a : dataBase.getFunctions()) {
                    if (a.getMovie().getName().equals(function)) {
                        String[] tmp;
                        int row;
                        int col;
                        if (action.equals("RESERVE")) {
                            tmp = chair.split(",");
                            row = Integer.parseInt(tmp[0]);
                            col = Integer.parseInt(tmp[1]);
                            if (!(a.getCinema().getChairs()[row][col].getReserved()) &&
                                    !(a.getCinema().getChairs()[row][col].getOcupated())) {
                                a.getCinema().getChairs()[row][col].setReserved(true);
                                return true;
                            }
                            System.out.println("Silla reservada");
                            return false;
                        }
                        if (action.equals("BUY")) {
                            tmp = chair.split(",");
                            row = Integer.parseInt(tmp[0]);
                            col = Integer.parseInt(tmp[1]);
                            if (!(a.getCinema().getChairs()[row][col].getOcupated())
                                    && c.getCard().getBalance() >= a.getPrice()) {
                                int p = c.getCard().getBalance() - a.getPrice();
                                c.getCard().setBalance(p);
                                a.getCinema().getChairs()[row][col].setOcupated(true);
                                return true;
                            }
                            return false;
                        }
                    }
                }
                return false;
            }
        }
        return false;
    }

    public static boolean createClient(String name, String surname,
                                       String id, int code, String email, String birthday, int balance) {
        Card card = new Card(balance, code);
        Client client = new Client(name, surname, birthday, email, id, card);
        dataBase.getClients().add(client);
        return true;
    }

    public static String printClient(String a, ArrayList<Card> b) {
        String tmp = a;
        for (Card aux : b) {
            tmp += "\n";
            tmp += aux.toString();
        }
        return tmp;
    }

    public static void printCinemas() {
        System.out.println("CINEMAS KEPLER\n");
        for (Cinema a : dataBase.getCinemas())
            System.out.println(a);
    }

    public static void printFunctions() {
        System.out.println("CINEMAS KEPLER\n");
        for (Function a : dataBase.getFunctions())
            System.out.println(a);
    }

    public static void saveData() {
        try {
            FileOutputStream fos = new FileOutputStream("data.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(dataBase);
            oos.close();
            writeClients();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        a.go();
        view.go();
    }

    public ArrayList<String> readFile(String fileName) {
        ArrayList<String> answer = new ArrayList<String>();
        File file = new File(fileName);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str;
            while ((str = br.readLine()) != null) {
                answer.add(str);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer;
    }

    public int getRandomNumber(int min, int max) {
        int ans = (int) (Math.random() * (max - min + 1)) + min;
        return ans;
    }

    public void createRandomClients() {
        ArrayList<String> names = readFile("Names.txt");
        ArrayList<String> surnames = readFile("Surnames.txt");
        ArrayList<String> birthDays = readFile("BirthDay.txt");
        Card card;
        Client client;
        for (int i = 0; i < 300; i++) {
            card = new Card(getRandomNumber(100000, 0), getRandomNumber(10000, 00000));
            client = new Client(names.get(i), surnames.get(i), birthDays.get(i), card);
            dataBase.getClients().add(client);
        }
        writeClients();
        saveData();
    }

    public void createRandomFunctions() {
        Calendar cal = Calendar.getInstance();
        time = cal.getTimeInMillis();
        if (dataBase.getTimeOfNewFunctions() - time <= 0) {
            readMovies();
            readCinemas();
            int auxb = 1000 * 60 * 60;
            time += auxb;
            dataBase.setTimeOfNewFunctions(time);
            cal.setTimeInMillis(time);
            String a = String.format("%tA, %tB %td %tr", cal, cal, cal, cal);
            ArrayList<Function> functions = new ArrayList<Function>();
            ArrayList<Cinema> cinemas = dataBase.getCinemas();
            ArrayList<Movie> movies = new ArrayList<Movie>();
            movies = dataBase.getMovies();
            int aux = 0;
            int auxd = -1;
            for (int i = 0; i < 10; i++) {
                while (aux == auxd) {
                    aux = (int) (Math.random() * 49);
                }
                auxd = aux;
                Function tmpb = new Function(a, cinemas.get(i), movies.get(aux), 10000);
                functions.add(tmpb);
            }
            dataBase.setFunctions(functions);
            saveData();
        }
    }

    public void readMovies() {
        ArrayList<String> movies = readFile("Movies.txt");
        ArrayList<Movie> tmpb = new ArrayList<Movie>();
        for (String aux : movies)
            tmpb.add(new Movie(aux));
        dataBase.setMovies(tmpb);
    }

    public void readCinemas() {
        Cinema tmp;
        ArrayList<Cinema> cinemas = new ArrayList<Cinema>();
        tmp = new Cinema("Cinema.txt");
        cinemas.add(tmp);
        tmp = new Cinema("Cinema2.txt");
        cinemas.add(tmp);
        tmp = new Cinema("Cinema3.txt");
        cinemas.add(tmp);
        tmp = new Cinema("Cinema4.txt");
        cinemas.add(tmp);
        tmp = new Cinema("Cinema5.txt");
        cinemas.add(tmp);
        tmp = new Cinema("Cinema6.txt");
        cinemas.add(tmp);
        tmp = new Cinema("Cinema7.txt");
        cinemas.add(tmp);
        tmp = new Cinema("Cinema8.txt");
        cinemas.add(tmp);
        tmp = new Cinema("Cinema9.txt");
        cinemas.add(tmp);
        tmp = new Cinema("Cinema10.txt");
        cinemas.add(tmp);
        dataBase.setCinemas(cinemas);
    }

    public void readData() {
        try {
            FileInputStream fis = new FileInputStream("data.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            dataBase = (MyDatabase) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void go() {
        readData();
        a.createRandomFunctions();
    }
}
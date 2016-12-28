package Cinema.classProject;


import java.io.*;
import java.util.ArrayList;

public class MyDatabase implements Serializable {

    private static final long serialVersionUID = 1265129534247152434L;
    private static MyDatabase a = new MyDatabase();
    private ArrayList<Cinema> cinemas = new ArrayList<Cinema>();
    private ArrayList<Client> clients = new ArrayList<Client>();
    private ArrayList<Function> functions = new ArrayList<Function>();
    private ArrayList<Movie> movies = new ArrayList<Movie>();
    private ArrayList<Administrator> administrators = new ArrayList<Administrator>();
    private long timeOfNewFunctions;

    private MyDatabase() {
        administrators = new ArrayList<Administrator>();
        Card card = new Card(100000, 00000);
        Administrator admin = new Administrator("Juan", "Camilo", "1996\11\14", card, 00000);
        administrators.add(admin);
//		boss = new Person("Jack","Candreva","1992\08\18");
        card = new Card(100000, 00001);
        admin = new Administrator("Jack", "Candreva", "1992\08\18", card, 00001);
        administrators.add(admin);
        card = new Card(100000, 00002);
        admin = new Administrator("Oliver", "Walcott", "1990\10\09", card, 00002);
        administrators.add(admin);
    }

    public static MyDatabase getMyDatabase() {
        return a;
    }

    public void readData() {
        try {
            FileInputStream fis = new FileInputStream("data.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            a = (MyDatabase) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void go() {
        readData();
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

    public MyDatabase clone() {
        return a;
    }

    public ArrayList<Cinema> getCinemas() {
        return cinemas;
    }

    public void setCinemas(ArrayList<Cinema> cinemas) {
        this.cinemas = cinemas;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    public ArrayList<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(ArrayList<Function> functions) {
        this.functions = functions;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public ArrayList<Administrator> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(ArrayList<Administrator> administrators) {
        this.administrators = administrators;
    }

    public long getTimeOfNewFunctions() {
        return timeOfNewFunctions;
    }

    public void setTimeOfNewFunctions(long t) {
        timeOfNewFunctions = t;
    }
}
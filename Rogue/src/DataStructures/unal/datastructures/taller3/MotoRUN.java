package DataStructures.unal.datastructures.taller3;

import java.util.*;

public class MotoRUN {
    private GUI gui;
    private SpaceHashTable<Integer, User> users;
    private SpaceHashTable<String, Station> stations;
    private SpaceHashTable<Long, Moto> motos;
    private SpaceHashTable<Integer, ArrayList<Loan>> loans;
    protected static MotoRUN motoRun = new MotoRUN();
    private static String arg;

    //	SingleTon (This is my dataBase)
    private MotoRUN() {
        gui = new GUI();
        users = new SpaceHashTable<>(10);
        stations = new SpaceHashTable<>(10);
        motos = new SpaceHashTable<>(10);
        loans = new SpaceHashTable<>(10);
    }

    public static void saveUsers() {
        try {
            System.out.print("Saving users ..");
            int aux = arg.lastIndexOf(".");
            StringBuilder tmp = new StringBuilder(arg.substring(0, aux));
            tmp.append("_U");
            tmp.append(arg.substring(aux));
            System.out.print(" 20% ");
            motoRun.users.save(new String(tmp));
            System.out.print("........ 100%");
            System.out.println(" se han salvado " + motoRun.users.getSize() + " usuarios");
        } catch (Exception e) {
            System.out.println("Ups your files are incorrect, please do it well");
            System.exit(1);
        }
    }

    public static void loadUsers() {
        try {
            System.out.print("Loading users ..");
            int aux = arg.lastIndexOf(".");
            StringBuilder tmp = new StringBuilder(arg.substring(0, aux));
            tmp.append("_U");
            tmp.append(arg.substring(aux));
            System.out.print(" 20% ");
            motoRun.users.load(new String(tmp));
            System.out.print("........ 100%");
            System.out.println(" se han cargado " + motoRun.users.getSize() + " usuarios");
        } catch (Exception e) {
            System.out.println("Ups your files are incorrect, please do it well");
            System.exit(1);
        }
    }

    public static void saveStations() {
        try {
            System.out.print("Saving stations ..");
            int aux = arg.lastIndexOf(".");
            StringBuilder tmp = new StringBuilder(arg.substring(0, aux));
            tmp.append("_S");
            tmp.append(arg.substring(aux));
            System.out.print(" 20% ");
            motoRun.stations.save(new String(tmp));
            System.out.print("........ 100%");
            System.out.println(" se han salvado " + motoRun.stations.getSize() + " estaciones");
        } catch (Exception e) {
            System.out.println("Ups your files are incorrect, please do it well");
            System.exit(1);
        }
    }

    public static void loadStations() {
        try {
            System.out.print("Loading stations ..");
            int aux = arg.lastIndexOf(".");
            StringBuilder tmp = new StringBuilder(arg.substring(0, aux));
            tmp.append("_S");
            tmp.append(arg.substring(aux));
            System.out.print(" 20% ");
            motoRun.stations.load(new String(tmp));
            System.out.print("........ 100%");
            System.out.println(" se han cargado " + motoRun.stations.getSize() + " estaciones");
        } catch (Exception e) {
            System.out.println("Ups your files are incorrect, please do it well");
            System.exit(1);
        }
    }

    public static void saveMotos() {
        try {
            System.out.print("Saving motos ..");
            int aux = arg.lastIndexOf(".");
            StringBuilder tmp = new StringBuilder(arg.substring(0, aux));
            tmp.append("_M");
            tmp.append(arg.substring(aux));
            System.out.print(" 20% ");
            motoRun.motos.save(new String(tmp));
            System.out.print("........ 100%");
            System.out.println(" se han salvado " + motoRun.motos.getSize() + " motos");
        } catch (Exception e) {
            System.out.println("Ups your files are incorrect, please do it well");
            System.exit(1);
        }
    }

    public static void loadMotos() {
        try {
            System.out.print("Loading Motos ..");
            int aux = arg.lastIndexOf(".");
            StringBuilder tmp = new StringBuilder(arg.substring(0, aux));
            tmp.append("_M");
            tmp.append(arg.substring(aux));
            System.out.print(" 20% ");
            motoRun.motos.load(new String(tmp));
            System.out.print("........ 100%");
            System.out.println(" se han cargado " + motoRun.motos.getSize() + " motos");
        } catch (Exception e) {
            System.out.println("Ups your files are incorrect, please do it well");
            System.exit(1);
        }
    }

    public static void saveLoans() {
        try {
            System.out.print("Saving loans ..");
            int aux = arg.lastIndexOf(".");
            StringBuilder tmp = new StringBuilder(arg.substring(0, aux));
            tmp.append("_L");
            tmp.append(arg.substring(aux));
            System.out.print(" 20% ");
            motoRun.loans.save(new String(tmp));
            System.out.print("........ 100%");
            System.out.println(" se han salvado " + motoRun.loans.getSize() + " Prestamos");
        } catch (Exception e) {
            System.out.println("Ups your files are incorrect, please do it well");
            System.exit(1);
        }
    }

    public static void loadLoans() {
        try {
            System.out.print("Loading loans ..");
            int aux = arg.lastIndexOf(".");
            StringBuilder tmp = new StringBuilder(arg.substring(0, aux));
            tmp.append("_L");
            tmp.append(arg.substring(aux));
            System.out.print(" 20% ");
            motoRun.loans.load(new String(tmp));
            System.out.print("........ 100%");
            System.out.println(" se han cargado " + motoRun.loans.getSize() + " Prestamos");
        } catch (Exception e) {
            System.out.println("Ups your files are incorrect, please do it well");
            System.exit(1);
        }
    }

    public static int checkUserLogin(int cedula, String password, Boolean flag) {
        User tmp = motoRun.users.get(cedula);
        if (tmp == null)
            return 0;
        Calendar cal = Calendar.getInstance();
        if (tmp.getClave().equals(password)) {
            if (tmp.isActive() && (cal.compareTo(tmp.getSancion()) >= 0 && (tmp.getInLoan() == null || flag)))
                return tmp.getType();
            else
                throw new IllegalArgumentException("Lo sentimos el estudiante esta sancionado o ya tiene una moto asignada");
        }
        return 0;
    }

    public static String cargar(int cedula, String estacionA, String estacionB) {
        User user = motoRun.users.get(cedula);
        Moto moto = null;

        for (Moto motor : motoRun.motos)
            if (motor.getLocation().equals(estacionA) && motor.getState().equals("DI")) {
                moto = motor;
                break;
            }

        if (moto == null)
            throw new IllegalArgumentException("No hay motos disponibles, lo siento");

        Calendar cal = Calendar.getInstance();
        String tmp = "Su moto asignada es " + moto + " Que disfrute el viaje";
        Loan loan = new Loan(cedula, moto.getBarCode(), estacionA, estacionB, cal.getTimeInMillis());
        moto.setLocation("ET");
        moto.setState("PR");
        user.setInLoan(loan);
        MotoRUN.saveLoans();
        MotoRUN.saveUsers();
        MotoRUN.saveMotos();
        MotoRUN.saveStations();
        return tmp;
    }

    public static String descargar(int cedula, String station, Boolean state) {
        User user = motoRun.users.get(cedula);

        if (user == null)
            throw new IllegalArgumentException("Cedula incorrecta, intente de nuevo");

        Loan loan = user.getInLoan();

        if (loan == null)
            throw new IllegalArgumentException("Usted no tiene prestamos en este momento, lo siento");

        Calendar cal = Calendar.getInstance();
        loan.setTimeB(cal.getTimeInMillis());
        StringBuilder builder = new StringBuilder();

        if ((loan.getTimeB() - loan.getTimeA()) > (1000 * 60 * 60)) {
            int x = 15;
            short s = user.getS15min();
            if (user.getS15min() >= 3) {
                x *= 2;
                s = 0;
                user.setS15min(s);
                builder.append("Has sido sancionado por demorte mas de 4 veces mas de 15 minutos\n");
            } else {
                s++;
                user.setS15min(s);
                builder.append("Te demoraste mas de 15 minutos," + " sanciones: " + s + ", ten mas cuidado :)\n");
            }
            user.setSancion(x);
            loan.setSancion(true);
            builder.append("Has sido sancionado por demorte mas de 60 minutos\n");
        } else if ((loan.getTimeB() - loan.getTimeA()) > (1000 * 15 * 60)) {

            short s = user.getS15min();
            if (s >= 3) {
                user.setSancion(15);
                s = 0;
                user.setS15min(s);
                builder.append("Has sido sancionado por demorte mas de 4 veces mas de 15 minutos\n");
            } else {
                s++;
                user.setS15min(s);
                builder.append("Te demoraste mas de 15 minutos," + " sanciones: " + s + ", ten mas cuidado :)\n");
            }
            loan.setSancion(true);
        }

        if (!loan.getStationB().equals(station)) {
            short s = user.getSotherS();
            if (s >= 4) {
                user.setSancion(30);
                s = 0;
                user.setSotherS(s);
                builder.append("Has sido sancionado por llegar 5 veces a la estacion incorrecta\n");
            } else {
                s++;
                user.setSotherS(s);
                builder.append("Llevaste la moto a la estacion equivacada," + " sanciones: " + s + ", ten mas cuidado :)\n");
            }
            loan.setStationB(station);
            loan.setSancion(true);
        }

        Moto moto = motoRun.motos.get(loan.getMoto());
        if (state) {
            user.setSancion(45);
            moto.setState("DA");
            moto.setLocation("TM");
            loan.setSancion(true);
            loan.setStationB("TM");
            builder.append("Has sido sancionado por da�ar la moto :(\n");
        } else {
            moto.setState("DI");
            moto.setLocation(station);
        }

        if (motoRun.loans.get(cedula) != null)
            motoRun.loans.get(cedula).add(loan);
        else {
            ArrayList<Loan> x = new ArrayList<>();
            x.add(loan);
            motoRun.loans.put(cedula, x);
        }

        user.setInLoan(null);

        MotoRUN.saveLoans();
        MotoRUN.saveUsers();
        MotoRUN.saveMotos();
        MotoRUN.saveStations();
        if (builder.length() > 1)
            return new String(builder);
        else
            return "Moto descargada sin ningun problema =D";
    }

    // Getters
    public static SpaceHashTable<Long, Moto> getMotos() {
        return motoRun.motos;
    }

    public static SpaceHashTable<Integer, User> getUsers() {
        return motoRun.users;
    }

    public static SpaceHashTable<String, Station> getStations() {
        return motoRun.stations;
    }

    public static SpaceHashTable<Integer, ArrayList<Loan>> getLoans() {
        return motoRun.loans;
    }

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Ups you dindt put an argument, please do it well");
            return;
        }
        arg = args[0];

        //Default data
        motoRun.users.put(1231, new User(1231, "Sam", "1231", "Sistemas", "sam@unal.edu.co", 1));
        motoRun.users.put(1232, new User(1232, "Guadalupe", "1232", "Sistemas", "guadalupe@unal.edu.co", 2));
        motoRun.users.put(1233, new User(1233, "Santa Clara", "1233", "Sistemas", "clarita@unal.edu.co", 3));
        motoRun.users.put(1234, new User(1234, "Bimbo", "1234", "Sistemas", "bimbo@unal.edu.co", 1));

        motoRun.stations.put("CyT", new Station("CyT", "Ciencia y Tecnologia", "Ciencia y Tecnologia", 20));
        motoRun.stations.put("C45", new Station("C45", "Calle 45", "Calle 45", 22));
        motoRun.stations.put("C26", new Station("C26", "Calle 26", "Calle 26", 15));
        motoRun.stations.put("Uri", new Station("Uri", "Uriel Gutierrez", "Uriel Gutierrez", 17));
        motoRun.stations.put("CAN", new Station("CAN", "Salida CAN", "A la Salida del CAN", 12));
        motoRun.stations.put("AGR", new Station("AGR", "Agronomia", "Agronomia", 16));
        motoRun.stations.put("TM", new Station("TM", "Taller de motos", "En patios de Mecanica", 10));
        motoRun.stations.put("ET", new Station("ET", "En transito", "Todo Unal =D", 80));

        motoRun.motos.put(new Long(312450066), new Moto(312450066, "C45", "AKT", "UID-123", "DI", 10000000));
        motoRun.motos.put(new Long(312450067), new Moto(312450067, "C45", "AKT", "UID-124", "DI", 10000000));
        motoRun.motos.put(new Long(312450068), new Moto(312450068, "C26", "AKT", "UID-125", "DI", 10000000));
        motoRun.motos.put(new Long(312450069), new Moto(312450069, "AGR", "AKT", "UID-126", "DI", 10000000));

        Calendar cal = Calendar.getInstance();
        cal.set(2014, 10, 14, 15, 40);
        Loan loan = new Loan(1234, 312450066, "CyT", "C45", cal.getTimeInMillis());
        cal.set(2014, 10, 14, 15, 50);
        loan.setTimeB(cal.getTimeInMillis());
        ArrayList<Loan> x = new ArrayList<>();
        x.add(loan);
        motoRun.loans.put(1234, x);

        cal.set(2014, 10, 14, 16, 40);
        loan = new Loan(1231, 312450067, "CyT", "C45", cal.getTimeInMillis());
        cal.set(2014, 10, 14, 16, 50);
        loan.setTimeB(cal.getTimeInMillis());
        x = new ArrayList<>();
        x.add(loan);
        motoRun.loans.put(1231, x);

        cal.set(2014, 10, 14, 14, 40);
        loan = new Loan(1232, 312450068, "AGR", "C26", cal.getTimeInMillis());
        cal.set(2014, 10, 14, 14, 50);
        loan.setTimeB(cal.getTimeInMillis());
        x = new ArrayList<>();
        x.add(loan);
        motoRun.loans.put(1232, x);

        cal.set(2014, 10, 14, 12, 40);
        loan = new Loan(1233, 312450069, "CAN", "AGR", cal.getTimeInMillis());
        cal.set(2014, 10, 14, 12, 50);
        loan.setTimeB(cal.getTimeInMillis());
        x = new ArrayList<>();
        x.add(loan);
        motoRun.loans.put(1233, x);

        cal.set(2014, 10, 15, 15, 30);
        loan = new Loan(1234, 312450066, "CyT", "C45", cal.getTimeInMillis());
        cal.set(2014, 10, 15, 15, 44);
        loan.setTimeB(cal.getTimeInMillis());
        ArrayList<Loan> xx = motoRun.loans.get(1234);
        xx.add(loan);
        motoRun.loans.put(1234, xx);

        // Here is where the program begins
        MotoRUN.loadLoans();
        MotoRUN.loadUsers();
        MotoRUN.loadMotos();
        MotoRUN.loadStations();

        motoRun.gui.go();
    }
}

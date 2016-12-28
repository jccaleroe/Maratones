package Cinema.classProject;

import java.io.*;
import java.util.ArrayList;

public class Cinema implements Serializable {

    private static final long serialVersionUID = 3451638491531836090L;
    private String name;
    private int capacity;
    private int rows;
    private int cols;
    private Chair[][] chairs;
    private ArrayList<String> list;

    public Cinema(String name, int capacity, int rows, int cols, Chair[][] chairs) {
        this.name = name;
        this.capacity = capacity;
        this.rows = rows;
        this.cols = cols;
        this.chairs = chairs;
    }

    public Cinema(String name) {
        readFile(name);
        this.name = list.get(0);
        capacity = Integer.parseInt(list.get(1));
        rows = Integer.parseInt(list.get(2));
        cols = Integer.parseInt(list.get(3));
        chairs = new Chair[rows][cols];
        char[] aux;
        int r = 0;
        int c = 0;
        for (int i = 0; i < (list.size() - 4); i++) {
            if (!(list.get(i + 4).equals(""))) {
                aux = list.get(i + 4).toCharArray();
                for (int j = 0; j < aux.length; j++) {
                    if (!(aux[j] == ' ')) {
                        chairs[r][c] = new Chair(r, c, aux[j]);
                        c++;
                    }
                }
                r++;
                c = 0;
            }
        }
    }

    public void readFile(String name) {
        list = new ArrayList<String>();
        File file = new File(name);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str;
            while ((str = br.readLine()) != null) {
                list.add(str);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        String tmp = "\n";
        char[] aux;
        int r = 0;
        int c = 0;
        for (int i = 0; i < (list.size() - 4); i++) {
            if (list.get(i + 4).length() < 3) {
                tmp += "\n";
            } else {
                aux = list.get(i + 4).toCharArray();
                for (int j = 0; j < aux.length; j++) {
                    if (aux[j] == ' ')
                        tmp += aux[j];
                    else {
                        tmp += chairs[r][c].toString();
                        c++;
                    }
                }
                tmp += "\n";
                r++;
                c = 0;
            }
        }
        return name + "\ncapacity " + capacity + "\nrows "
                + rows + "\nchairs per row " + cols + tmp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public Chair[][] getChairs() {
        return chairs;
    }
}
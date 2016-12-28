package Bonus;

import java.io.*;
import java.util.*;

public class MyFilesClass {
    public static void main(String[] args) {
        ArrayList<String> lines = readFile("entrada.txt");
        for (String string : lines) {
            System.out.println(string);
        }
        writeFile(lines, "salida.txt");
    }

    private static void writeFile(ArrayList<String> lines, String fileName) {
        BufferedWriter wr;
        File file = new File(fileName);
        if (file.exists() == false) {
            System.err.println("Archivo " + fileName + " no existe");
            return;
        }
        try {
            wr = new BufferedWriter(new FileWriter(file));
            for (String string : lines) {
                wr.append(string);
                wr.newLine();
            }
            wr.close();
        } catch (FileNotFoundException e) {
            System.err.println("Ups, un error: FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Ups, un error: IOException ");
            e.printStackTrace();
        }
    }

    private static ArrayList<String> readFile(String fileName) {
        ArrayList<String> answer = new ArrayList<String>();

        BufferedReader br = null;
        File file = new File(fileName);
        if (file.exists() == false) {
            System.err.println("Archivo " + fileName + " no existe");
            return answer;
        }
        try {
            br = new BufferedReader(new FileReader(file));
            String str;
            while ((str = br.readLine()) != null) {
                answer.add(str);
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.err.println("Ups, un error: FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Ups, un error: IOException ");
            e.printStackTrace();
        }

        return answer;
    }
}

package Cinema.utilities;

import java.io.*;
import java.util.ArrayList;

public class emailsCreator {
    public static void writeFile()
    // The teacher did this in the random_String_files class (October 15)
    {
        BufferedWriter wr;
        File file = new File("shit.txt");
        ArrayList<String> names = readFile("Surnames.txt");
        ArrayList<String> surnames = readFile("Names.txt");
        int tmp;
        String string, aux = "@gmail.com";
        try {
            wr = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < 300; i++) {
                tmp = (int) (Math.random() * 300);
                string = names.get(tmp);
                string += "_";
                string += surnames.get(tmp);
                string += aux;
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

    public static ArrayList<String> readFile(String fileName)
    // The teacher did this in the random_String_files class (October 15)
    {
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

    public static void main(String[] args) {
        emailsCreator.writeFile();
    }
}
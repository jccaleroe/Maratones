import java.io.*;

/**
 * Created by juan on 18/04/17.
 */

public class Template {

    private static void quiet(){
        try {
            String str;
            BufferedReader reader = new BufferedReader(new FileReader(new File("/home/juan/spanish.txt")));
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("spanish.txt")));
            String[] openenig = {"@", "#", "-", "_", ".", ";", ",", "\"", "$",  "%", "&", "/", "\\", "~", "|", "¬", "°", "*", "'", "+",
                    "[", "{", "¿", "¡", "<", "(", ",", ",", ",", ".", "."};
            String[] ending = {"@", "#", "-", "_", ".", ";", ",", "\"", "$",  "%", "&", "/", "\\", "~", "|", "¬", "°", "*", "'", "+",
                    "]", "}", "?", "!", ">", ")", ",", ",", ",", ".", "."};
            while ((str = reader.readLine()) != null) {
                if (Math.random() <= 0.2)
                    str = openenig[(int) (Math.random() * openenig.length)] + str;
                if (Math.random() <= 0.2)
                    str = str + ending[(int) (Math.random() * ending.length)];
                writer.append(str);
                writer.append("\n");
            }
            writer.flush();
            reader.close();
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        quiet();
    }
}

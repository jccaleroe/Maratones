import java.io.*;
import java.util.HashSet;

/**
 * Created by juan on 18/04/17.
 */

public class Template {

    private static void quiet(){
        String b = "";
        HashSet<String> hashSet = new HashSet<>();
        try {
            String str;
            BufferedReader reader = new BufferedReader(new FileReader(new File("/home/juan/Desktop/words/words.tsv")));
            while ((str = reader.readLine()) != null) {
                b = str.split("\\t")[2];
                if (!hashSet.add(b))
                    System.out.println(b);
                b = "/home/juan/Desktop/" + b;
                File file = new File(b);
                if (!file.exists())
                    System.out.println(file.getAbsolutePath());
            }
            reader.close();
        }catch (IOException e){
            System.out.println("Openeing " + b);
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        quiet();
    }
}

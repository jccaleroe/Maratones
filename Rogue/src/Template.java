import java.io.*;

/**
 * Created by juan on 18/04/17.
 */

public class Template {

    private static void quiet(){
        try {
            String str;
            BufferedReader reader = new BufferedReader(new FileReader(new File("spanishback.txt")));
            //BufferedWriter writer = new BufferedWriter(new FileWriter(new File("spanishlol.txt")));
            int max = 0;
            String b = "null";
            while ((str = reader.readLine()) != null) {
                if (str.length() > max){
                    max= str.length();
                    b = str;
                }
            }
            reader.close();
            System.out.println(b);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        quiet();
    }
}

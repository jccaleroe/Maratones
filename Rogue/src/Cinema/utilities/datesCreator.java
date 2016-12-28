package Cinema.utilities;

import java.io.*;

public class datesCreator {
    public static void writeFile() {
        BufferedWriter wr;
        File file = new File("shit.txt");
        int tmp;
        String string;
        try {
            wr = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < 300; i++) {
                tmp = getRandomNumber(1913, 1995);
                string = String.valueOf(tmp);
                tmp = getRandomNumber(1, 12);
                string += "/";
                string += String.valueOf(tmp);
                if (tmp == 1 || tmp == 3 || tmp == 5 || tmp == 7 || tmp == 8 || tmp == 10 || tmp == 12)
                    tmp = getRandomNumber(1, 31);
                else if (tmp == 2)
                    tmp = getRandomNumber(1, 28);
                else
                    tmp = getRandomNumber(1, 30);

                string += "/";
                string += String.valueOf(tmp);
                wr.append(string);
                wr.newLine();
            }
            wr.close();
        } catch (IOException e) {
            System.err.println("Ups, un error: IOException ");
            e.printStackTrace();
        }
    }

    public static int getRandomNumber(int min, int max) {
        int ans = (int) (Math.random() * (max - min + 1)) + min;
        return ans;
    }

    public static void main(String[] args) {
        datesCreator.writeFile();
    }
}
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.File;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Created by juan on 8/04/17.
 */
public class PancakeFlipper implements Comparable<PancakeFlipper>{

    static int n;
    static int k;
    int ones, depth;
    String s;


    PancakeFlipper(String a, int ones2, int depth2){
        s = a;
        ones = ones2;
        depth = depth2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PancakeFlipper that = (PancakeFlipper) o;

        return s != null ? s.equals(that.s) : that.s == null;
    }

    @Override
    public int hashCode() {
        return s != null ? s.hashCode() : 0;
    }

    @Override
    public int compareTo(PancakeFlipper o) {
        return s.compareTo(o.s);
    }

    static int search(PancakeFlipper pancakeFlipper){
        HashSet<PancakeFlipper> hs = new HashSet<>();
        PriorityQueue<PancakeFlipper> pq = new PriorityQueue<>();
        pq.add(pancakeFlipper);
        hs.add(pancakeFlipper);
        PancakeFlipper u;
        while (!pq.isEmpty()){
            u = pq.poll();
            if (u.ones == n)
                return u.depth;

        }
        return -1;
    }


    public static void main(String[] args) throws IOException{
        //BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
        BufferedReader br = new BufferedReader( new FileReader(
              new File( "/home/juan/Documents/Maratones/Rogue/src/input.txt" ) ) );
        int T, ones, k;
        String s;
        String s2[];
        T = Integer.parseInt( br.readLine() );
        for (int c = 1; c <= T; c++){
            s2 = br.readLine().split(" ");
            s = s2[0];
            k = Integer.parseInt(s2[1]);
            ones = 0;
            for (int i = 0; i < s.length(); i++)
                if (s.charAt(i) == '+')
                    ones++;
            PancakeFlipper.n = s.length();
            PancakeFlipper.k = k;
            new PancakeFlipper(s, ones, 0);
        }
    }
}

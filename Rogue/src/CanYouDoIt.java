import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class CanYouDoIt {

    static int low = 0;
    static int high = 350, mid;
    static BigInteger tmp, k, N;

    private static int find(){
        while (high >= low) {
            mid = (high + low) / 2;
            tmp = k.pow(mid);
            if (tmp.equals(N))
                return mid;
            else if(tmp.compareTo(N) > 0)
                high = mid - 1;
            else
                low = mid + 1;
        }
        return -1;
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
        int T;
        T = Integer.parseInt(br.readLine());
        for(int c = 0; c < T; c++){
            String[] s = br.readLine().split(" ");
            high = 350;
            low  = 0;
            k = new BigInteger(s[0]);
            N = new BigInteger(s[1]);
            System.out.println(find());
        }
    }
}

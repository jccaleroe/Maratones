import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

/**
 * Created by juan on 14/03/17.
 */
public class CanYouDoIt {

    static int low = 0;
    static int high = 350, mid;
    static BigInteger tmp, k, N;

    static int find(){
        while (high >= low) {
            mid = (high + low) / 2;
            tmp = k.pow(mid);
            if (tmp.equals(N))
                return mid;
            else if(tmp.compareTo(N) > 0){
                high = mid - 1;
            }
            else{
                low = mid + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
        int T;
        T = Integer.parseInt(br.readLine());
        //BigInteger aux = new BigInteger("9");
        //aux = aux.pow(106);
        //System.out.println(aux);
        //System.out.println("10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        for(int c = 0; c < T; c++){
            String[] s = br.readLine().split(" ");
            high = 350;
            low  = 0;
            switch (s[0]){
                case "2":
                    high = 336;
                    break;
                case "3":
                    high = 215;
                    break;
                case "4":
                    high = 170;
                    break;
                case "5":
                    high = 145;
                    break;
                case "6":
                    high = 130;
                    break;
                case "7":
                    high = 120;
                    break;
                case "8":
                    high = 112;
                    break;
                case "9":
                    high = 106;
                    break;
                case "10":
                    high = 100;
                    break;
            }
            k = new BigInteger(s[0]);
            N = new BigInteger(s[1]);
            System.out.println(find());
        }
    }
}

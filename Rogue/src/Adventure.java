import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by juan on 28/02/17.
 */
public class Adventure {

    static BigInteger remind = new BigInteger("1000000007");
    static BigInteger aux1 = new BigInteger("1");
    static long re =  1000000007;

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T, a, b, c;
        int[] ar = new int[3];
        Integer k;
        T = Integer.parseInt(br.readLine());
        BigInteger tmp;
        long answer, tmp1, otherCases = 0, moreK;
        for(int j = 0; j < T; j++){
            answer = 0;
            String[] s = br.readLine().split(" ");
            ar[0] = Integer.parseInt(s[0]);
            ar[1] = Integer.parseInt(s[1]);
            ar[2] = Integer.parseInt(s[2]);
            k = Integer.parseInt(s[3]);
            Arrays.sort(ar);
            a = ar[2];
            b = ar[1];
            c = ar[0];

            //System.out.println(a + " " + b + " " + c);
            BigInteger big = new BigInteger(k.toString());
            BigInteger r2 = new BigInteger(k.toString());

            if(a != 0) {
                tmp = big.pow(a);
                tmp = tmp.subtract(aux1);
                answer = tmp.remainder(remind).longValue() / (k - 1);
                answer = (answer * k) % re;
            }

            if(b != 0) {
                tmp = big.pow(b);
                tmp = tmp.subtract(aux1);
                tmp1 = tmp.remainder(remind).longValue() / (k - 1);
                tmp1 = (tmp1 * k * 2) % re;
                answer = (tmp1 + answer) % re;
            }

            if(c != 0) {
                tmp = big.pow(c);
                tmp = tmp.subtract(aux1);
                tmp1 = tmp.remainder(remind).longValue() / (k - 1);
                tmp1 = (tmp1 * k * 3) % re;
                answer = (tmp1 + answer) % re;
            }



            otherCases = ((a+1) * (b+1)) % re;
            otherCases = ( otherCases * (c+1))  % re;
            otherCases =  ( otherCases - 1 - (a+b*2+c*3))  % re;
            otherCases =  ( otherCases * k)  % re;
            answer = (answer + otherCases) % re;
            System.out.println(answer);
        }
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by juan on 28/02/17.
 */
public class Main {

    static BigInteger remind = new BigInteger("1000000007");
    static BigInteger aux1 = new BigInteger("1");
    static long re =  1000000007;

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Integer T, a, b, c;
        int[] ar = new int[3];
        Integer k, k1;
        T = Integer.parseInt(br.readLine());
        BigInteger tmp;
        long answer, tmp1, otherCases = 0;
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
            BigInteger K = new BigInteger(k.toString());
            BigInteger r2 = new BigInteger("0");
            BigInteger moreK = new BigInteger("0");
            BigInteger others = new BigInteger("1");

            if(a != 0) {
                tmp = K.pow(a);
                tmp = tmp.subtract(aux1);
                tmp = tmp.divide(new BigInteger(String.valueOf(k-1)));
                tmp = tmp.multiply(K);
                r2 = r2.add(tmp);
                moreK = moreK.add(new BigInteger(a.toString()));
            }

            if(b != 0) {
                tmp = K.pow(b);
                tmp = tmp.subtract(aux1);
                tmp = tmp.divide(new BigInteger(String.valueOf(k-1)));
                tmp = tmp.multiply(K);
                tmp = tmp.multiply(new BigInteger(String.valueOf(2)));
                r2 = r2.add(tmp);
                moreK = moreK.add(new BigInteger(String.valueOf(b*2)));
            }

            if(c != 0) {
                tmp = K.pow(c);
                tmp = tmp.subtract(aux1);
                tmp = tmp.divide(new BigInteger(String.valueOf(k-1)));
                tmp = tmp.multiply(K);
                tmp = tmp.multiply(new BigInteger(String.valueOf(3)));
                moreK = moreK.add(new BigInteger(String.valueOf(c*3)));
                r2 = r2.add(tmp);
            }

            others = others.multiply(new BigInteger(String.valueOf(c+1)));
            others = others.multiply(new BigInteger(String.valueOf(b+1)));
            others = others.multiply(new BigInteger(String.valueOf(a+1)));
            others = others.subtract(aux1);
            others = others.subtract(moreK);
            others = others.multiply(K);

            r2 = r2.add(others);

            System.out.println(r2);
            //System.out.println(r2.remainder(remind));
        }
    }
}

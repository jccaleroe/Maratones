import java.io.*;
import java.util.HashSet;
import java.util.PriorityQueue;

public class PrimePath implements Comparable<PrimePath> {

    static int destiny, da, db, dc, dd;
    static PrimePath PrimePath;
    int depth;
    int number, differentNumbers;

    PrimePath ( String code ){
        number = 1000 * ( code.charAt(0) - '0' ) + 100 * ( code.charAt(1) - '0' ) +
                10 * ( code.charAt(2) - '0' ) + code.charAt(3) - '0';
        destiny = 1000 * ( code.charAt(5) - '0' ) + 100 * ( code.charAt(6) - '0' ) +
                10 * ( code.charAt(7) - '0' ) + code.charAt(8) - '0';
        depth = 0;
        int n = destiny;
        dd = n % 10;
        n /= 10;
        dc = n % 10;
        n /= 10;
        db = n % 10;
        n /= 10;
        da = n;
    }

    PrimePath ( int number, int depth ){
        this.number = number;
        this.depth = depth;
        int a, b, c, d, n;
        n = number;
        d = n % 10;
        n /= 10;
        c = n % 10;
        n /= 10;
        b = n % 10;
        n /= 10;
        a = n;
        differentNumbers = (a == da ? 0 : 1) + (b == db ? 0 : 1) + (c == dc ? 0 : 1) + (d == dd ? 0 : 1);
    }

    @Override
    public int compareTo(PrimePath o) {
        return depth + differentNumbers - ( o.depth + differentNumbers );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrimePath PrimePath = (PrimePath) o;

        return number == PrimePath.number;
    }

    @Override
    public int hashCode() {
        return number;
    }

    static boolean isPrime (int n ){
        int r = (int) Math.sqrt( n );
        for (int i = 2; i <= r; i++){
            if ( n % i == 0 )
                return false;
        }
        return true;
    }

    static int search ( PrimePath source ){
        if ( source.number == destiny ) return 0;
        PriorityQueue<PrimePath> queue = new PriorityQueue<>();
        HashSet<PrimePath> seen = new HashSet<>();
        queue.add(source);
        seen.add(source);
        PrimePath u, child;
        int a, b, c, d, prime, n;
        while ( !queue.isEmpty() ){
            u = queue.poll();
            n = u.number;
            d = n % 10;
            n /= 10;
            c = n % 10;
            n /= 10;
            b = n % 10;
            n /= 10;
            a = n;
            for ( int i = 0; i < 10; i++){
                if ( i != a && i != 0){
                    prime = u.number - ( a * 1000 ) + 1000 * i;
                    if ( isPrime(prime) ) {
                        if ( prime == destiny )
                            return u.depth + 1;
                        child = new PrimePath( prime, u.depth + 1);
                        if (seen.add(child))
                            queue.add(child);
                    }
                }
                if ( i != b ){
                    prime = u.number - ( b * 100 ) + 100 * i;
                    if ( isPrime(prime) ) {
                        if ( prime == destiny )
                            return u.depth + 1;
                        child = new PrimePath( prime, u.depth + 1);
                        if (seen.add(child))
                            queue.add(child);
                    }
                }
                if ( i != c ){
                    prime = u.number - ( c * 10 ) + 10 * i;
                    if ( isPrime(prime) ) {
                        if ( prime == destiny )
                            return u.depth + 1;
                        child = new PrimePath( prime, u.depth + 1);
                        if (seen.add(child))
                            queue.add(child);
                    }
                }
                if ( i != d ){
                    prime = u.number - d + i;
                    if ( isPrime(prime) ) {
                        if ( prime == destiny )
                            return u.depth + 1;
                        child = new PrimePath( prime, u.depth + 1);
                        if (seen.add(child))
                            queue.add(child);
                    }
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
        int T;
        //BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
        BufferedReader reader = new BufferedReader( new FileReader(
                new File( "/home/juan/Documents/Maratones/Rogue/src/input.txt" ) ) );
        T = Integer.parseInt( reader.readLine() );
        while (T > 0){
            T--;
            int r = PrimePath.search( new PrimePath( reader.readLine() ) );
            if ( r == -1 )
                System.out.println( "Impossible" );
            else
                System.out.println(r);
        }
    }

}

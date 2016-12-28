package Bonus;

public class SquareRoot {

    final private double EPS = 10E-16;

    public double fabs(double x) {
        if (x >= 0) {
            return x;
        }
        return -x;
    }

    public double find_square_root(double n, double low, double high) {
        if (n < 0 || high - low < 0) {
            return -1;
        }

        if (fabs(1 - n) < EPS) {
            return 1;
        }

        double mid = (low + high) / 2.0;
        double mid_square = mid * mid;
        double delta = fabs(mid_square - n);

        if (delta <= EPS) {
            return mid;
        }

        if (mid_square > n) {
            return find_square_root(n, low, mid);
        } else {
            return find_square_root(n, mid, high);
        }
    }

    public double find_square_root(double x) {
        if (fabs(x) < EPS) {
            return 0;
        }
        if (x < 0) {
            return -1;
        }

        return find_square_root(x, 0, x + 1);
    }

    public static void main(String[] args) {

        SquareRoot origin = new SquareRoot();
        double r = origin.find_square_root(2);
        System.out.println(r);
    }

}

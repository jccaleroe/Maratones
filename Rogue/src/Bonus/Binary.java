package Bonus;

public class Binary {

    public void ToBinary(int x) {
        int binary = 0;
        int aux = 1;
        while (x > 0) {
            if (x % 2 == 1) {
                binary += aux;
            }
            aux *= 10;
            x /= 2;
        }
        System.out.println(binary + " in binary");
    }

    public void ToBaseThree(int x) {
        int Three = 0;
        int aux = 1;
        while (x > 0) {
            Three += (x % 3) * aux;
            aux *= 10;
            x /= 3;
        }
        System.out.println(Three + " in base three");
    }

    public void ToBaseFour(int x) {
        int Four = 0;
        int aux = 1;
        while (x > 0) {
            Four += (x % 4) * aux;
            aux *= 10;
            x /= 4;
        }
        System.out.println(Four + " in base four");
    }

    public void ToBaseSix(int x) {
        int Six = 0;
        int aux = 1;
        while (x > 0) {
            Six += (x % 6) * aux;
            aux *= 10;
            x /= 6;
        }
        System.out.println(Six + " in base six");
    }

    public void BinaryToDecimal(int x) {
        int tmp = x;
        boolean isBinary = true;
        while (tmp > 0) {
            if (tmp % 10 > 1) {
                isBinary = false;
                break;
            }
            tmp /= 10;
        }
        if (isBinary) {
            int exponent = 0;
            int aux = 0;
            while (x > 0) {
                tmp = x % 10;
                if (tmp == 1) {
                    tmp *= exponential(2, exponent);
                }
                exponent++;
                aux += tmp;
                x /= 10;
            }
            System.out.println(aux + " from binary to decimal");
        }
    }

    public int exponential(int a, int x) {
        if (x == 0) {
            a = 1;
            return a;
        }
        int aux = a;
        for (int i = 1; i < x; i++) {
            a *= aux;
        }
        return a;
    }

    public static void main(String[] args) {

        Binary a = new Binary();
        a.ToBinary(2);
        a.ToBaseThree(3);
        a.ToBaseFour(4);
        a.ToBaseSix(6);
        a.BinaryToDecimal(1010);
    }
}
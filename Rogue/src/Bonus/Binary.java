package Bonus;

public class Binary {

    public void ToBinary(int x) {
        int binary = 0;
        int aux = 1;
        while (x > 0) {
            if (x % 2 == 1)
                binary += aux;
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
                if (tmp == 1)
                    tmp *= Math.pow(2, exponent);
                exponent++;
                aux += tmp;
                x /= 10;
            }
            System.out.println(aux + " from binary to decimal");
        }}}
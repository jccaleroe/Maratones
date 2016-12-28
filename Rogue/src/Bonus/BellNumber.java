package Bonus;

import java.util.*;

public class BellNumber {

    public int combinatorio(int n, int k) {
        if (n < 0 || k < 0) {
            return -1;
        }

        if (k == 0) {
            return 1;
        }

        if (k == 1) {
            return n;
        }

        if (n == 0 || n < k) {
            return 0;
        }

        if (n == k) {
            return 1;
        }

        return combinatorio(n - 1, k - 1) + combinatorio(n - 1, k);

    }

    public int sumatory(int k, int n) {

        if (k == n - 1) //finalizacion
        {
            return combinatorio(n - 1, k) * getBellNumber(k);
        }

        int answer = 0;

        answer = combinatorio(n - 1, k) * getBellNumber(k);
        answer = answer + sumatory(k + 1, n);

        return answer;

    }

    public int getBellNumber(int n) {
        if (n < 0) //error
        {
            return -1;
        }

        if (n <= 1)//caso base
        {
            return 1;
        }

        return sumatory(0, n);

    }

    public int sumBellNumber(int m) {
        int aux = 0;
        for (int i = 0; i <= m; i++) {
            aux += getBellNumber(i);
        }
        return aux;
    }

    public int fibonacci(int n) {
        if (n == 0) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public void isInBellSecuence(int[] arr) {
        TreeMap<Integer, Integer> list = new TreeMap<Integer, Integer>();
        ArrayList<Integer> p = new ArrayList<Integer>();
        for (int i : arr) {
            if (list.containsKey(i))
                list.put(i, (list.get(i) + 1));
            else {
                p.add(i);
                list.put(i, 1);
            }
        }
        int tmp;
        ArrayList<Integer> a = new ArrayList<Integer>();
        for (int j = 0; ; j++) {
            tmp = getBellNumber(j);
            if (tmp > list.lastKey())
                break;
            a.add(tmp);
        }
        Collections.sort(p);
        System.out.println(p);
        for (int i = p.size() - 1; i >= 0; i--) {
            for (int n = 0; n < a.size(); n++) {
                if (p.get(i) == a.get(n)) {
                    System.out.println(p.get(i) + " " + list.get(p.get(i)));
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        int n = 4;
        BellNumber Temple = new BellNumber();
        int b = Temple.getBellNumber(n);
        //		int f = Temple.fibonacci(0);
        System.out.println("El numero de Bell es: " + b);
        //		b = Temple.sumBellNumber(n);
        //		System.out.println("La sumatoria del numero de Bell es: " + b);
        //		System.out.println("El numero de Fibonacci es: " + f);
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 52, 15, 76, 203, 2, 2, 15, 1};
        Temple.isInBellSecuence(arr);
    }
}
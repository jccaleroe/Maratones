package DataStructures;

import DataStructures.unal.datastructures.CircularWithHeader;

class UNPeriodic<T> extends CircularWithHeader<T> {

    public static void main(String[] args) {
        UNPeriodic<Character> x = new UNPeriodic<>();
        x.add(0, 'Y');
        x.add(0, 'C');
        x.add(0, 'A');
        x.add(0, 'D');
        x.add(0, 'X');

        UNPeriodic<Character> y = new UNPeriodic<>();
        y.add(0, 'I');
        y.add(1, 'N');
        y.add(2, 'F');
        y.add(3, 'O');
        y.add(4, 'R');
        y.add(5, 'M');
        y.add(6, 'A');
        y.add(7, 'C');
        y.add(8, 'I');
        y.add(9, 'O');
        y.add(10, 'N');

        System.out.println(x);
        System.out.println(y);

        x.protosohariouz(y);

        System.out.println(x);
    }

    public void protosohariouz(CircularWithHeader<T> otra) {
        for (int i = 0, j = 1; i < otra.size(); i++, j += 2)
            add(min(size, j), otra.get(i));
    }

    public int min(int a, int b) {
        return (a < b ? a : b);
    }
}

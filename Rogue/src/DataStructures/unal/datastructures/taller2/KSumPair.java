package DataStructures.unal.datastructures.taller2;

import DataStructures.unal.datastructures.ArrayLinearList;
import DataStructures.unal.datastructures.Chain;
import DataStructures.unal.datastructures.LinearList;


public class KSumPair {

    public static int solve(LinearList<Integer> A, LinearList<Integer> B, int k) {
        Dracula max;
        MaxHeap<Dracula> heap = new MaxHeap<>();
        ArrayLinearList<Integer> noOnes = new ArrayLinearList<>();
        int i = 0;
        int j = 0;
        int size = A.size();
        int aux;
        heap.put(new Dracula(0, 0, A.get(0) + B.get(0)));

        for (int n = 0; n < k; n++) {
            max = heap.removeMax();
            i = max.i;
            j = max.j;
            if (i < size - 1) {
                aux = A.get(i + 1) + B.get(j);
                if (noOnes.indexOf(aux) == -1) {
                    heap.put(new Dracula(i + 1, j, aux));
                    noOnes.add(0, aux);
                }
            }
            if (j < size - 1) {
                aux = A.get(i) + B.get(j + 1);
                if (noOnes.indexOf(aux) == -1) {
                    heap.put(new Dracula(i, j + 1, aux));
                    noOnes.add(0, aux);
                }
            }
        }
        max = heap.removeMax();
        return max.sum;
    }

    public static void main(String args[]) {
        ArrayLinearList<Integer> A = new ArrayLinearList<>();
        Chain<Integer> B = new Chain<>();
        A.add(0, 2);
        A.add(0, 4);
        A.add(0, 13);
        B.add(0, 1);
        B.add(0, 8);
        B.add(0, 15);
        System.out.println("KSum: " + KSumPair.solve(A, B, 3));
    }
}

class Dracula implements Comparable<Dracula> {
    public int i;
    public int j;
    public int sum;

    public Dracula(int ii, int jj, int summ) {
        i = ii;
        j = jj;
        sum = summ;
    }

    @Override
    public int compareTo(Dracula hijo) {
        return sum - hijo.sum;
    }

    @Override
    public String toString() {
        return "[" + i + ", " + j + ", " + sum + "]";
    }
}
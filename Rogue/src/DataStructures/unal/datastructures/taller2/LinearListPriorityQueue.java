package DataStructures.unal.datastructures.taller2;

import DataStructures.unal.datastructures.ArrayLinearList;
import DataStructures.unal.datastructures.Chain;
import DataStructures.unal.datastructures.LinearList;


public class LinearListPriorityQueue<T extends Comparable<? super T>> implements MaxPriorityQueue<T> {

    LinearList<T> heap;
    int size;

    LinearListPriorityQueue(int capacity) {
        heap = new ArrayLinearList<T>(capacity + 1);
        heap.add(0, null);
        size = 0;
    }

    LinearListPriorityQueue() {
        heap = new Chain<T>();
        heap.add(0, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void notSwap(int a, int b) {
        replace(a, heap.get(b));
    }

    public T replace(int a, T b) {
        if (a >= size) {
            heap.add(a, b);
            return null;
        }

        T tmp = heap.remove(a);
        heap.add(a, b);
        return tmp;
    }

    public void put(T me) {
        int child = ++size;

        while (child != 1 && heap.get(child / 2).compareTo(me) < 0) {
            notSwap(child, child / 2);
            child /= 2;
        }

        replace(child, me);
    }

    public T getMax() {
        if (size == 0)
            return null;
        return heap.get(1);
    }

    public T removeMax() {
        if (size == 0)
            return null;

        T removed = heap.get(1);
        T last = heap.get(size--);

        int cn = 1, child = 2;
        while (child <= size) {
            if (child < size && heap.get(child).compareTo(heap.get(child + 1)) < 0)
                child++;
            if (heap.get(child).compareTo(last) < 0)
                break;

            notSwap(cn, child);
            cn = child;
            child *= 2;
        }
        replace(cn, last);

        return removed;
    }

    public void initializeArrayList(LinearList<T> theList) {
        size = theList.size();
        heap = new ArrayLinearList<T>(size + 1);
        heap.add(0, null);
        for (int i = 1; i <= size; i++)
            heap.add(i, theList.get(i - 1));

        for (int root = size / 2; root > 0; root--) {
            T last = heap.get(root);
            int cn = root, child = root * 2;

            while (child <= size) {
                if (child < size && heap.get(child).compareTo(heap.get(child + 1)) < 0)
                    child++;
                if (heap.get(child).compareTo(last) < 0)
                    break;

                notSwap(cn, child);
                cn = child;
                child *= 2;
            }
            replace(cn, last);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        if (size > 0) {
            builder.append(heap.get(1));
            for (int i = 2; i <= size; i++) {
                builder.append(", " + heap.get(i));
            }
        }
        builder.append("]");
        return new String(builder);
    }

    public static void test(MaxPriorityQueue<Integer> theList) {
        long time = System.currentTimeMillis();
        for (int i = 1; i <= 10000; i++) {
            theList.put(i * 4);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("El timepo de add fue " + time + " milisegundos\n");

        time = System.currentTimeMillis();
        for (int i = 1; i <= 5000; i++) {
            theList.getMax();
            theList.removeMax();
        }
        time = System.currentTimeMillis() - time;
        System.out.println("El timepo de remove y get fue " + time + " milisegundos\n");
    }

    public static void main(String[] args) {
        LinearListPriorityQueue<Integer> will = new LinearListPriorityQueue<>();
        LinearListPriorityQueue<Integer> dignity = new LinearListPriorityQueue<>(10);
        MaxHeap<Integer> energy = new MaxHeap<>();

        System.out.println("Timepo de Chain");
        LinearListPriorityQueue.test(will);
        System.out.println("Timepo de Array");
        LinearListPriorityQueue.test(dignity);
        System.out.println("Timepo de heap");
        LinearListPriorityQueue.test(energy);
    }
}
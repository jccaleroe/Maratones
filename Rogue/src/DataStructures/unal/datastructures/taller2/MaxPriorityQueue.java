package DataStructures.unal.datastructures.taller2;

interface MaxPriorityQueue<T extends Comparable<? super T>> {
    boolean isEmpty();

    int size();

    T getMax();

    void put(T theObject);

    T removeMax();
}

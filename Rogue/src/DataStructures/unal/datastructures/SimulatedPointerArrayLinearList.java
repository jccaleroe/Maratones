package DataStructures.unal.datastructures;

import java.io.*;
import java.util.*;


public class SimulatedPointerArrayLinearList<T>
        implements LinearListImproved<T>, Iterable<T> {

    int firstNode;
    int[] next;
    int size;
    T[] element;

    @SuppressWarnings("unchecked")
    public SimulatedPointerArrayLinearList(int initialCapacity) {
        if (initialCapacity < 1)
            throw new IllegalArgumentException
                    ("initialCapacity must be >= 1");
        next = new int[initialCapacity];
        firstNode = -1;
        size = 0;
        element = (T[]) (new Object[initialCapacity]);
    }

    public SimulatedPointerArrayLinearList() {
        this(10);
    }

    @Override
    public void save(String file) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
            os.writeInt(size);
            for (T i : this)
                os.writeObject(i);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Save done");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void load(String file) {
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
            int n = is.readInt();
            for (int i = 0; i < n; i++)
                add(i, (T) is.readObject());
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void sort() {
        T[] tmp = (T[]) new Object[size];
        int aux = 0;
        for (T j : this)
            tmp[aux++] = j;

        Arrays.sort(tmp);
        element = tmp;
        firstNode = 0;
        int i;
        for (i = 0; i < size - 1; i++)
            next[i] = i + 1;
        next[i] = -1;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void sort(Comparator<T> c) {
        T[] tmp = (T[]) new Object[size];
        int aux = 0;
        for (T j : this)
            tmp[aux++] = j;

        Arrays.sort(tmp, 0, size, c);
        element = tmp;
        firstNode = 0;
        int i;
        for (i = 0; i < size - 1; i++)
            next[i] = i + 1;
        next[i] = -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException
                    ("index = " + index + "  size = " + size);
    }

    @Override
    public int indexOf(T me) {
        int aux = firstNode;
        for (int i = 0; i < size; i++) {
            if (element[aux].equals(me))
                return i;
            aux = next[aux];
        }

        return -1;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        int aux = firstNode;
        T tmp;

        if (index == 0) {
            tmp = element[firstNode];
            element[firstNode] = null;
            firstNode = next[firstNode];
            size--;
            return tmp;
        }

        for (int i = 0; i < index - 1; i++)
            aux = next[aux];

        tmp = element[next[aux]];
        element[next[aux]] = null;
        next[aux] = next[next[aux]];
        size--;

        return tmp;
    }

    public T get(int index) {
        checkIndex(index);
        int aux = firstNode;
        for (int i = 0; i < index; i++)
            aux = next[aux];
        return element[aux];
    }

    @SuppressWarnings("unchecked")
    @Override
    public void add(int index, T me) {

        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException
                    ("index = " + index + "size =" + size);

        if (size == element.length) {
            T[] old = element;
            int[] moreOld = next;
            element = (T[]) new Object[size * 2];
            next = new int[2 * size];
            System.arraycopy(old, 0, element, 0, size);
            System.arraycopy(moreOld, 0, next, 0, size);
        }

        if (index == 0) {
            for (int i = 0; i < element.length; i++) {
                if (element[i] == null) {
                    next[i] = firstNode;
                    firstNode = i;
                    element[i] = me;
                    size++;
                    break;
                }
            }
        } else {
            int aux = firstNode;

            for (int j = 0; j < index - 1; j++) {
                aux = next[aux];
            }

            for (int i = 0; i < element.length; i++)
                if (element[i] == null) {
                    next[i] = next[aux];
                    next[aux] = i;
                    element[i] = me;
                    size++;
                    break;
                }
        }
    }

    @Override
    public String toString() {
        StringBuilder semilla = new StringBuilder("[");
        for (T x : this)
            semilla.append(Objects.toString(x) + ", ");
        if (size > 0)
            semilla.setLength(semilla.length() - 2);
        semilla.append(" ]");
        return new String(semilla);
    }

    @Override
    public Iterator<T> iterator() {
        return new SimulatedPointerArrayListIterator<T>();
    }


    class SimulatedPointerArrayListIterator<E> implements Iterator<E> {

        int nextIndex;

        public SimulatedPointerArrayListIterator() {
            nextIndex = 0;
        }

        public boolean hasNext() {
            return nextIndex < size();
        }

        @SuppressWarnings("unchecked")
        public E next() {
            return (E) get(nextIndex++);
        }

        public void remove() {
            throw new UnsupportedOperationException
                    ("remove not supported");
        }
    }


    @SuppressWarnings("unchecked")
    public void addBeforeElement(T ele1, T ele2) {
        int aux = firstNode;
        int pos = -1;
        int posBefore = 0;

        for (T x : this) {
            if (x.equals(ele1)) {
                pos = aux;
                break;
            }
            posBefore = aux;
            aux = next[aux];
        }

        if (size == element.length) {
            T[] old = element;
            int[] old2 = next;
            element = (T[]) new Object[2 * size];
            next = new int[2 * size];
            System.arraycopy(old, 0, element, 0, size);
            System.arraycopy(old2, 0, next, 0, size);
        }

        for (int i = 0; i < element.length; i++) {
            if (element[i] == null) {
                element[i] = ele2;
                next[i] = pos;
                if (pos == firstNode)
                    firstNode = i;
                else
                    next[posBefore] = i;
                size++;
                break;
            }
        }
    }

    public static void main(String[] args) {

        SimulatedPointerArrayLinearList<Integer> arbol = new SimulatedPointerArrayLinearList<>();
        for (int i = 0; i < 10; i++) {
            arbol.add(0, i * 2);
        }
        arbol.add(10, 40);
        System.out.println(arbol + "\n");

        System.out.println(arbol.get(10));
        System.out.println(arbol.remove(0));
        System.out.println(arbol.indexOf(4));
        arbol.remove(9);
        System.out.println(arbol + "\n");

        arbol.save("DataStructures/arbol.txt");
        SimulatedPointerArrayLinearList<Integer> backUp = new SimulatedPointerArrayLinearList<>();
        backUp.load("DataStructures/arbol.txt");
        System.out.println("Baxk up : \n" + backUp);
        backUp.sort();
        System.out.println("Baxk up sorted : \n" + backUp);
        System.out.print("\n");
        System.out.println("Arbol : \n" + arbol);
        arbol.addBeforeElement(14, 43);
        System.out.println(arbol.indexOf(43));
    }
}
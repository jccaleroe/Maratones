package DataStructures.unal.datastructures.taller3;

import java.io.*;
import java.util.*;

public class SpaceHashTable<K extends Comparable<? super K>, E> implements Iterable<E> {

    private DataSpace<K, E>[] spaceTable;
    private int spaceDivisor;
    private int spaceSize;
    private Boolean[] spaceUsed;
    private int removed;

    @SuppressWarnings("unchecked")
    public SpaceHashTable(int capacity) {
        spaceTable = new DataSpace[capacity];
        spaceUsed = new Boolean[capacity];
        Arrays.fill(spaceUsed, true);
        spaceDivisor = capacity;
        spaceSize = 0;
        removed = 0;
    }

    public Boolean isEmpty() {
        return spaceSize == 0;
    }

    public int getSize() {
        return spaceSize;
    }

    public int search(K theKey) {
        int bucket = Math.abs(theKey.hashCode()) % spaceDivisor;
        int i = bucket;
        do {
            if (spaceUsed[i] || (spaceTable[i] != null && spaceTable[i].key.equals(theKey)))
                return i;
            i = (i + 1) % spaceDivisor;
        } while (i != bucket);

        return i;
    }

    public E get(K theKey) {
        int bucket = search(theKey);

        if (spaceUsed[bucket] || spaceTable[bucket] == null)
            return null;

        if (spaceTable[bucket].key.equals(theKey))
            return spaceTable[bucket].element;

        return null;
    }

    public E remove(K theKey) {
        int bucket = search(theKey);

        if (spaceUsed[bucket] || spaceTable[bucket] == null)
            return null;

        if (spaceTable[bucket].key.equals(theKey)) {
            E tmp = spaceTable[bucket].element;
            spaceTable[bucket] = null;
            spaceSize--;
            removed++;
            if (removed >= (spaceDivisor * 0.35))
                //ReHash but do not duplicate size
                reHash(false);
            return tmp;
        }
        return null;
    }

    public E put(K theKey, E theE) {
        int bucket = search(theKey);

        if (spaceUsed[bucket] || spaceTable[bucket] == null) {
            spaceUsed[bucket] = false;
            spaceTable[bucket] = new DataSpace<>(theKey, theE);
            spaceSize++;
            return null;
        }

        if (spaceTable[bucket].key.equals(theKey)) {
            E tmp = spaceTable[bucket].element;
            spaceTable[bucket] = new DataSpace<>(theKey, theE);
            return tmp;
        }

        //Else it's full HD then duplicate size
        reHash(true);
        put(theKey, theE);
        return null;
    }

    @SuppressWarnings("unchecked")
    public void reHash(boolean flag) {
        DataSpace<K, E>[] old = spaceTable;
        if (flag)
            spaceDivisor *= 2;
        spaceTable = new DataSpace[spaceDivisor];
        spaceUsed = new Boolean[spaceDivisor];
        Arrays.fill(spaceUsed, true);
        spaceSize = 0;
        removed = 0;
        //Do Re hash
        for (DataSpace<K, E> x : old)
            if (x != null)
                put(x.key, x.element);
    }

    @SuppressWarnings("unchecked")
    public void load(String file) {
        try {
            FileInputStream fs = new FileInputStream(file);
            ObjectInputStream is = new ObjectInputStream(fs);
            int size = is.readInt();
            DataSpace<K, E> tmp;
            for (int i = 0; i < size; i++) {
                tmp = (DataSpace<K, E>) is.readObject();
                put(tmp.key, tmp.element);
            }
            is.close();
        } catch (FileNotFoundException e) {
            System.out.print("The file is empty or does not exists, we will make one for you ");
        } catch (Exception b) {
            System.out.print("ups un error ");
        }
    }

    public void save(String file) {
        try {
            FileOutputStream fs = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeInt(spaceSize);
            for (DataSpace<K, E> x : spaceTable) {
                if (x != null)
                    os.writeObject(x);
            }
            os.close();
        } catch (Exception b) {
            System.out.print("ups un error ");
        }
    }

    public Iterator<E> iterator() {
        return new SpaceIterator();
    }

    class SpaceIterator implements Iterator<E> {
        int i;

        SpaceIterator() {
            i = 0;
        }

        @Override
        public boolean hasNext() {
            while (i < spaceDivisor && spaceTable[i] == null)
                i++;
            return i < spaceDivisor;
        }

        @Override
        public E next() {
            return spaceTable[i++].element;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove not supported");
        }
    }

    // CopyRight: Joan
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (DataSpace<K, E> x : spaceTable)
            if (x != null)
                s.append(x);

        if (spaceSize > 0)
            s.setLength(s.length() - 2); // remove last ", "

        s.append("\n");

        return new String(s);
    }
}

class DataSpace<K extends Comparable<? super K>, E> implements Serializable {

    private static final long serialVersionUID = -7060725683909757566L;
    K key;
    E element;

    public DataSpace(K spaceKey, E spaceElement) {
        key = spaceKey;
        element = spaceElement;
    }

    @Override
    public String toString() {
        return element.toString() + "\n";
    }
}

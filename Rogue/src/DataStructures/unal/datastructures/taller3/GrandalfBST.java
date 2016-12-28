package DataStructures.unal.datastructures.taller3;

import java.io.*;
import java.util.*;

import DataStructures.unal.datastructures.ArrayQueue;

public class GrandalfBST<K extends Comparable<? super K>, E> implements Iterable<E> {

    Bilbo<K, E> tiaRoot;
    protected int size;

    public GrandalfBST() {
        tiaRoot = null;
        size = 0;
    }

    public Iterator<E> iterator() {
        return new GrandalfIterator();
    }


    private class GrandalfIterator implements Iterator<E> {
        Bilbo<K, E> nextNode;
        ArrayQueue<Bilbo<K, E>> p;

        @SuppressWarnings({"unchecked", "rawtypes"})
        public GrandalfIterator() {
            nextNode = tiaRoot;
            p = new ArrayQueue(size);
        }

        public boolean hasNext() {
            return nextNode != null;
        }

        public E next() {
            if (nextNode != null) {
                if (nextNode.leftChild != null)
                    p.put(nextNode.leftChild);
                if (nextNode.rightChild != null)
                    p.put(nextNode.rightChild);
                E tmp = nextNode.element;
                nextNode = p.remove();
                return tmp;
            }
            return null;
        }

        /**
         * unsupported method
         */
        public void remove() {
            throw new UnsupportedOperationException
                    ("remove not supported");
        }
    }

    public E get(K laura) {
        Bilbo<K, E> aragor = tiaRoot;
        while (aragor != null) {
            if (laura.compareTo(aragor.key) < 0)
                aragor = aragor.leftChild;
            else if (laura.compareTo(aragor.key) > 0)
                aragor = aragor.rightChild;
            else
                return aragor.element;
        }
        return null;
    }

    public E put(K tatiana, E maria) {
        Bilbo<K, E> nova = tiaRoot, tePienso = null;

        while (nova != null) {
            tePienso = nova;
            if (tatiana.compareTo(nova.key) < 0)
                nova = nova.leftChild;
            else if (tatiana.compareTo(nova.key) > 0)
                nova = nova.rightChild;
            else {
                E ave = nova.element;
                nova.element = maria;
                return ave;
            }
        }
        Bilbo<K, E> sams = new Bilbo<>(tatiana, maria, null, null);
        if (tePienso == null)
            tiaRoot = sams;
        else if (tatiana.compareTo(tePienso.key) < 0)
            tePienso.leftChild = sams;
        else
            tePienso.rightChild = sams;
        size++;
        return null;
    }

    public E remove(K theKey) {
        Bilbo<K, E> novel = tiaRoot, tePienso = null;

        while (novel != null && !novel.key.equals(theKey)) {
            tePienso = novel;
            if (theKey.compareTo(novel.key) < 0)
                novel = novel.leftChild;
            else
                novel = novel.rightChild;
        }

        if (novel == null)
            return null;

        E removed = novel.element;
        size--;
        if (novel.leftChild != null && novel.rightChild != null) {
            Bilbo<K, E> s = novel.leftChild, ps = novel;

            while (s.rightChild != null) {
                ps = s;
                s = s.rightChild;
            }

            novel.element = s.element;
            novel.key = s.key;
            novel = s;
            tePienso = ps;
        }

        Bilbo<K, E> c;
        if (novel.leftChild == null)
            c = novel.rightChild;
        else
            c = novel.leftChild;

        if (novel == tiaRoot)
            tiaRoot = c;
        else if (tePienso.leftChild == novel)
            tePienso.leftChild = c;
        else
            tePienso.rightChild = c;
        return removed;
    }

    public static <K, E> void inOrderOutput(Bilbo<K, E> horse) {
        if (horse != null) {
            inOrderOutput(horse.leftChild);
            System.out.println(horse);
            inOrderOutput(horse.rightChild);
        }
    }

    public void ascend() {
        inOrderOutput(tiaRoot);
    }

    public static <K, E> int myHeight(Bilbo<K, E> warrior) {
        int hLeft = 0, hRight = 0;
        if (warrior.leftChild != null)
            hLeft = myHeight(warrior.leftChild);
        if (warrior.rightChild != null)
            hRight = myHeight(warrior.rightChild);
        if (warrior.leftChild == null && warrior.rightChild == null)
            return 1;
        if (hLeft > hRight)
            return ++hLeft;
        else
            return ++hRight;
    }

    public int height() {
        return myHeight(tiaRoot);
    }

    public static <K, E> void postOrderSave(Bilbo<K, E> warrior, ObjectOutputStream os) throws IOException {
        if (warrior != null) {
            postOrderSave(warrior.leftChild, os);
            postOrderSave(warrior.rightChild, os);
            os.writeObject(warrior);
        }
    }

    public void save(String file) {
        try {
            FileOutputStream fs = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeInt(size);
            postOrderSave(tiaRoot, os);
        } catch (Exception e) {
            System.out.print("ups un error ");
        }
    }

    @SuppressWarnings("unchecked")
    public void load(String file) {
        try {
            FileInputStream fs = new FileInputStream(file);
            ObjectInputStream is = new ObjectInputStream(fs);
            int size = is.readInt();
            Bilbo<K, E> aux = new Bilbo<K, E>();
            for (int i = 0; i < size; i++) {
                aux = (Bilbo<K, E>) is.readObject();
                put(aux.key, aux.element);
            }
            is.close();
        } catch (FileNotFoundException e) {
            System.out.print("The file is empty or does not exists, we will make one for you ");
        } catch (Exception b) {
            System.out.print("ups un error ");
        }
    }
}

class Bilbo<T, D> implements Serializable {

    private static final long serialVersionUID = 4324452843374548075L;
    Bilbo<T, D> leftChild;
    Bilbo<T, D> rightChild;
    D element;
    T key;

    public Bilbo() {
        this(null, null, null, null);
    }

    public Bilbo(T theKey, D d, Bilbo<T, D> lc, Bilbo<T, D> rc) {
        leftChild = lc;
        rightChild = rc;
        key = theKey;
        element = d;
    }

    @Override
    public String toString() {
        return "[Key: " + key + ", E: " + element + "]";
    }
}
package DataStructures;

public class Grandalf<K extends Comparable<? super K>, E> {

    Bilbo<K, E> tiaRoot;

    public Grandalf() {
        tiaRoot = null;
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

    public static void main(String[] args) {
        Grandalf<Integer, Integer> frodo = new Grandalf<>();
        java.util.Random r = new java.util.Random();
        frodo.put(5000, 0);
        for (int i = 1; i <= 1024; i++) {
            frodo.put(r.nextInt(10000), i);
        }

        frodo.ascend();

        System.out.println(frodo.height());

        System.out.println(frodo.tiaRoot);
    }
}

class Bilbo<T, D> {
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
package DataStructures.unal.datastructures;

import java.util.*;

import DataStructures.unal.applications.MyStudent;


public class ArrayLinearListTaller1<T> extends ArrayLinearList<T> {

    public ArrayLinearListTaller1() {
        super();
    }

    public ArrayLinearListTaller1(int capacity) {
        super(capacity);
    }

    @SuppressWarnings("unchecked")
    public T maxElement() {
        T[] tmp = (T[]) new Object[size];
        int j = 0;
        for (T i : this)
            tmp[j++] = i;
        Arrays.sort(tmp);
        return tmp[size - 1];
    }

    @SuppressWarnings("unchecked")
    public T minElement() {
        T[] tmp = (T[]) new Object[size];
        int j = 0;
        for (T i : this)
            tmp[j++] = i;
        Arrays.sort(tmp);
        return tmp[0];
    }

    public T replace(int index, T theElement) {
        checkIndex(index);
        T removedElement = remove(index);
        add(index, theElement);
        return removedElement;
    }

    public void reverse() {
        for (int i = 0; i < size; i++)
            add(0, remove(i));
    }

    public boolean compare(LinearList<T> otro) {
        if (size == otro.size()) {
            for (int i = 0; i < size; i++)
                if (!get(i).equals(otro.get(i)))
                    return false;
            return true;
        } else return false;
    }

    public void swap(int i, int j) {
        checkIndex(i);
        checkIndex(j);
        T temp = element[i];
        element[i] = element[j];
        element[j] = temp;
    }

    public void shuffle() {
        Random r = new Random(new Date().getTime());
        for (int i = 0; i < size; i++)
            swap(i, r.nextInt(size));
        for (int i = 0; i < size; i++)
            swap(i, r.nextInt(size));
    }

    void checkIndex(int index, LinearList<T> otra) {
        if (index < 0 || index >= otra.size())
            throw new IndexOutOfBoundsException(index + "no esta en la Linear List otra");
    }

    public void listCopy(int index1, LinearList<T> otra, int index2, int size) {
        checkIndex(index1);
        checkIndex(index2, otra);
        checkIndex(index2 + size - 1, otra);
        for (int i = 0; i < size; i++) {
            add(index1 + i, otra.get(index2 + i));
        }
    }

    public void rotate(int pos) {
        if (pos < 0)
            for (int i = pos; i < 0; i++)
                add(size - 1, remove(0));
        else for (int i = 0; i < pos; i++)
            add(0, remove(size - 1));
    }

    public void eliminarRepetidos() {
        ArrayLinearListTaller1<T> tmp = new ArrayLinearListTaller1<>();
        int aux = 0;
        for (int i = 0; i < size; i++) {
            if (tmp.indexOf(get(i)) == -1) {
                tmp.add(aux, get(i));
                aux++;
            } else {
                remove(i);
                i--;
            }
        }
    }

    public void frecuenciaDeElementos() {
        ArrayLinearListTaller1<T> tmp = new ArrayLinearListTaller1<>();
        ArrayLinearListTaller1<Integer> tmp2 = new ArrayLinearListTaller1<>();
        int aux = 0;
        int aux2 = 0;
        for (T x : this)
            if (tmp.indexOf(x) == -1) {
                tmp.add(aux, x);
                aux++;
            }
        for (int i = 0; i < tmp.size(); i++)
            tmp2.add(i, 0);

        for (T x : this) {
            aux2 = tmp.indexOf(x);
            tmp2.add(aux2, tmp2.remove(aux2) + 1);
        }

        for (int i = 0; i < tmp.size(); i++)
            System.out.println(" El elemento " + tmp.get(i) + " Esta " + tmp2.get(i) + " veces en la lista");
    }

    public ArrayLinearListTaller1<T> SubList(int start, int size) {
        checkIndex(start);
        checkIndex(size + start);
        ArrayLinearListTaller1<T> tmp = new ArrayLinearListTaller1<>(size);
        int aux = 0;
        for (int i = start; i < start + size; i++) {
            tmp.add(aux, get(i));
            aux++;
        }
        return tmp;
    }


    public static void main(String[] args) {
        ArrayLinearListTaller1<Integer> smart = new ArrayLinearListTaller1<>();
        for (int i = 0; i < 10; i++)
            smart.add(i, i * 2);
        Chain<Integer> smarter = new Chain<>();
        for (int i = 0; i < 10; i++)
            smarter.add(i, i * 2);
        System.out.println(smart);
        System.out.println(smarter);
        System.out.println(smart.maxElement());
        System.out.println(smart.minElement());
        System.out.println(smart.compare(smarter));
        smart.reverse();
        System.out.println(smart);
        smart.listCopy(3, smarter, 6, 3);
        smart.rotate(-3);
        smart.shuffle();
        smart.replace(0, 20);
        System.out.println(smart);
        smart.frecuenciaDeElementos();
        smart.eliminarRepetidos();
        System.out.println("Sin repetidos");
        smart.frecuenciaDeElementos();
        System.out.println(smart);

        ArrayLinearListTaller1<String> smart2 = new ArrayLinearListTaller1<>();
        smart2.add(0, "ABC");
        smart2.add(0, "DEF");
        smart2.add(0, "GHI");
        smart2.add(0, "JJJ");
        smart2.add(0, "KKK");
        smart2.add(0, "LLL");
        smart2.add(0, "3-0");
        smart2.add(0, "GOOOOL");
        smart2.add(0, "GOOOOL");
        smart2.add(0, "GOOOOL");
        Chain<String> smarter2 = new Chain<>();
        smarter2.add(0, "ABCD");
        smarter2.add(0, "DEFT");
        smarter2.add(0, "GHIG");
        smarter2.add(0, "JJJH");
        smarter2.add(0, "KKKK");
        smarter2.add(0, "LLLK");
        smarter2.add(0, "PPPK");
        smarter2.add(0, "OOOK");
        smarter2.add(0, "EEE");
        smarter2.add(0, "GOOOOL");
        System.out.println(smart2);
        System.out.println(smarter2);
        System.out.println(smart2.maxElement());
        System.out.println(smart2.minElement());
        System.out.println(smart2.compare(smarter2));
        smart2.reverse();
        System.out.println(smart2);
        smart2.listCopy(3, smarter2, 4, 3);
        smart2.rotate(-3);
        smart2.shuffle();
        smart2.replace(0, "nooo");
        System.out.println(smart2);
        smart2.frecuenciaDeElementos();
        smart2.eliminarRepetidos();
        System.out.println("Sin repetidos");
        smart2.frecuenciaDeElementos();
        System.out.println(smart2);

        ArrayLinearListTaller1<MyStudent> smart3 = new ArrayLinearListTaller1<>();
        smart3.add(0, new MyStudent("Juan", "Caleros", 1));
        smart3.add(0, new MyStudent("Juano", "Caleror", 2));
        smart3.add(0, new MyStudent("Juane", "Calerot", 3));
        smart3.add(0, new MyStudent("Juanr", "Sierra", 4));
        smart3.add(0, new MyStudent("Juant", "Sierrae", 5));
        smart3.add(0, new MyStudent("Juany", "Sierry", 4));
        smart3.add(0, new MyStudent("Juanu", "Sierrto", 7));
        smart3.add(0, new MyStudent("Juani", "Sanchexto", 8));
        smart3.add(0, new MyStudent("Juanp", "Sanck", 7));
        smart3.add(0, new MyStudent("Juand", "eeeeeeee", 8));

        ArrayLinearListTaller1<MyStudent> smarter3 = new ArrayLinearListTaller1<>();
        smarter3.add(0, new MyStudent("al", "Caleros", 1));
        smarter3.add(0, new MyStudent("ale", "Caleror", 10));
        smarter3.add(0, new MyStudent("alelu", "Calerot", 11));
        smarter3.add(0, new MyStudent("aleluya", "Sierra", 12));
        smarter3.add(0, new MyStudent("kaka", "Sierrae", 13));
        smarter3.add(0, new MyStudent("Juany", "Sierry", 15));
        smarter3.add(0, new MyStudent("Juanu", "Sierrto", 15));
        smarter3.add(0, new MyStudent("Juani", "Sanchexto", 18));
        smarter3.add(0, new MyStudent("Juanp", "Sanck", 8));
        smarter3.add(0, new MyStudent("Juand", "eeeeeeee", 9));
        System.out.println(smart3);
        System.out.println(smarter3);
        System.out.println(smart3.maxElement());
        System.out.println(smart3.minElement());
        System.out.println(smart3.compare(smarter3));
        smart3.reverse();
        System.out.println(smart3);
        smart3.listCopy(3, smarter3, 4, 3);
        smart3.rotate(-3);
        smart3.shuffle();
        System.out.println(smart3);
        smart3.frecuenciaDeElementos();
        smart3.eliminarRepetidos();
        System.out.println("Sin repetidos");
        smart3.frecuenciaDeElementos();
        System.out.println(smart3);
    }
}

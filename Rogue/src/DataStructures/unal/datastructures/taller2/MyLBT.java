package DataStructures.unal.datastructures.taller2;

import java.util.*;

import DataStructures.unal.datastructures.ArrayLinearList;
import DataStructures.unal.datastructures.ArrayQueue;
import DataStructures.unal.datastructures.ArrayStack;
import DataStructures.unal.datastructures.LinkedQueue;

public class MyLBT<T> extends LinkedBinaryTree<T> {

    public T brother(T x) {
        ArrayQueue<BinaryTreeNode<T>> perro = new ArrayQueue<>();
        BinaryTreeNode<T> aguila = root;

        if (aguila.getElement().equals(x))
            return null;

        while (aguila != null) {

            if (aguila.getLeftChild() != null) {
                perro.put(aguila.getLeftChild());
                if (aguila.getLeftChild().getElement().equals(x))
                    return aguila.getRightChild() == null ? null : aguila.getRightChild().getElement();
            }

            if (aguila.getRightChild() != null) {
                perro.put(aguila.getRightChild());
                if (aguila.getRightChild().getElement().equals(x))
                    return aguila.getLeftChild() == null ? null : aguila.getLeftChild().getElement();
            }
            aguila = perro.remove();
        }
        return null;
    }

    public BinaryTree<T> getSubTree(int a) {
        ArrayQueue<BinaryTreeNode<T>> perro = new ArrayQueue<>();
        BinaryTreeNode<T> aguila = root;

        if (a >= size())
            throw new IllegalArgumentException(":(");

        for (int i = 0; i < a; i++) {

            if (aguila.getLeftChild() != null)
                perro.put(aguila.getLeftChild());

            if (aguila.getRightChild() != null)
                perro.put(aguila.getRightChild());

            aguila = perro.remove();
        }

        LinkedBinaryTree<T> tmp = new LinkedBinaryTree<T>();
        tmp.root = aguila;
        return (BinaryTree<T>) tmp;
    }

    public T replace(int a, T e) {
        BinaryTreeNode<T> aguila = ((LinkedBinaryTree<T>) getSubTree(a)).root;
        T tmp = aguila.getElement();
        aguila.setElement(e);
        return tmp;
    }

    public void swap(int a, int b) {
        int aux = size();
        if (a >= aux || b >= aux)
            throw new IllegalArgumentException(":(");
        T tmp = getSubTree(a).root();
        tmp = replace(b, tmp);
        replace(a, tmp);
    }

    public void shuffle() {
        Random random = new Random(new Date().getTime());
        int aux = size();
        for (int i = 0; i < aux; i++)
            swap(i, random.nextInt(aux));
        for (int i = 0; i < aux; i++)
            swap(i, random.nextInt(aux));
    }

    public void preOrderOutput() {
        ArrayLinearList<BinaryTreeNode<T>> perro = new ArrayLinearList<>();
        BinaryTreeNode<T> aguila = root;
        int count = 0;
        while (aguila != null) {
            System.out.print(aguila.getElement() + " ");

            if (aguila.getLeftChild() != null) {
                perro.add(perro.size(), aguila.getLeftChild());

            }

            if (aguila.getRightChild() != null) {
                perro.add(count, aguila.getRightChild());
                if (perro.size() > count)
                    count++;
            }

            if (perro.size() == 0)
                aguila = null;

            else {
                aguila = perro.remove(perro.size() - 1);
                if (perro.size() < count)
                    count--;
            }
        }
    }

    public void postOrderOutput() {
        ArrayLinearList<T> castor = new ArrayLinearList<>();
        ArrayStack<BinaryTreeNode<T>> ray = new ArrayStack<>();
        BinaryTreeNode<T> dog = root;
        int count = 0;
        while (count < size()) {
            castor.add(count, dog.element);
            if (dog.leftChild != null) {
                ray.push(dog.leftChild);
            }
            if (dog.rightChild != null) {
                ray.push(dog.rightChild);
            }
            if (ray.isEmpty())
                dog = null;
            else
                dog = ray.pop();
            count++;
        }
        for (int i = castor.size() - 1; i >= 0; i--) {
            System.out.print(castor.get(i) + " ");
        }
    }

    public void inOrderOutput() {
        ArrayLinearList<BinaryTreeNode<T>> perro = new ArrayLinearList<>();
        ArrayLinearList<T> delfin = new ArrayLinearList<>();
        ArrayLinearList<Boolean> spider = new ArrayLinearList<>();
        BinaryTreeNode<T> aguila = root;
        int count = 0;
        int i = 0;
        perro.add(0, aguila);
        delfin.add(0, aguila.getElement());
        spider.add(0, false);
        while (aguila != null) {

            for (i = 0; i < spider.size();
                 i++) {
                if (!spider.get(i)) {
                    break;
                }
            }

            if (i == spider.size()) {
                break;
            }

            if (aguila.getLeftChild() != null) {
                perro.add(0, aguila.getLeftChild());
                delfin.add(i, aguila.getLeftChild().getElement());
                spider.add(i, false);
                i++;
                count++;

            }

            if (aguila.getRightChild() != null) {
                perro.add(count, aguila.getRightChild());
                spider.add(i + 1, false);
                delfin.add(i + 1, aguila.getRightChild().getElement());
            }

            // replace
            spider.remove(i);
            spider.add(i, true);

            if (perro.size() == 0)
                aguila = null;

            else {
                aguila = perro.remove(0);
                if (count != 0)
                    count--;
            }
        }

        for (T x : delfin)
            System.out.print(x + " ");
    }


    public int scope(BinaryTreeNode<T> perro) {
        int right = 0, left = 0;
        if (perro.leftChild != null)
            left = scope(perro.leftChild);
        if (perro.rightChild != null)
            right = scope(perro.rightChild);
        if (perro.leftChild == null && perro.rightChild == null)
            return 1;
        return (left >= right) ? left + 1 : right + 1;
    }

    public int diameter() {
        LinkedQueue<BinaryTreeNode<T>> q = new LinkedQueue<>();
        BinaryTreeNode<T> p = root;
        MaxHeap<Integer> life = new MaxHeap<>();
        int right = 0, left = 0;
        while (p != null) {
            if (p.leftChild != null) {
                q.put(p.leftChild);
                left = scope(p.leftChild);
            }
            if (p.rightChild != null) {
                q.put(p.rightChild);
                right = scope(p.rightChild);
            }

            life.put(right + left + 1);
            p = q.remove();
        }
        return life.removeMax();
    }

    public static void main(String[] args) {
        MyLBT<Integer> x = new MyLBT<>();
        MyLBT<Integer> y = new MyLBT<>();
        MyLBT<Integer> z = new MyLBT<>();
        MyLBT<Integer> h = new MyLBT<>();
        MyLBT<Integer> f = new MyLBT<>();
        MyLBT<Integer> g = new MyLBT<>();
        MyLBT<Integer> aux = new MyLBT<>();

        x.makeTree(1, x, x);
        y.makeTree(2, y, y);
        x.makeTree(3, x, y);
        z.makeTree(4, z, z);
        h.makeTree(5, h, h);
        z.makeTree(6, z, h);
        y.makeTree(7, x, z);
        f.makeTree(8, aux, aux);
        g.makeTree(9, aux, aux);
        x.makeTree(10, f, g);
        h.makeTree(11, aux, aux);
        z.makeTree(12, x, h);
        x.makeTree(13, y, z);
        y.makeTree(14, aux, aux);
        h.makeTree(15, aux, aux);
        z.makeTree(16, y, h);
        y.makeTree(17, aux, aux);
        h.makeTree(18, aux, aux);
        f.makeTree(19, y, h);
        y.makeTree(20, z, f);
        z.makeTree(21, x, y); // mark
        y.makeTree(22, aux, aux);
        h.makeTree(23, aux, aux);
        x.makeTree(24, y, h);
        y.makeTree(25, aux, aux);
        h.makeTree(26, aux, aux);
        f.makeTree(27, y, h);
        x.makeTree(28, x, f);
        y.makeTree(29, aux, aux);
        h.makeTree(30, aux, aux);
        f.makeTree(31, y, h);
        y.makeTree(32, aux, aux);
        h.makeTree(33, aux, aux);
        g.makeTree(34, y, h);
        f.makeTree(35, f, g);
        g.makeTree(36, x, f); // mark
        y.makeTree(37, z, g); // mark

        System.out.println("Integer Arbol\n");
        System.out.println("Pre, in, pos and level order :");
        y.preOrderOutput();
        System.out.println();
        y.inOrderOutput();
        System.out.println();
        y.postOrderOutput();
        System.out.println();
        y.levelOrderOutput();

        System.out.println("\n\nThe diameter of y is: " + y.diameter());
        System.out.println("The brother of 1 is :" + y.brother(1));
        System.out.println("The brother of 4 is :" + y.brother(4));
        y.shuffle();
        System.out.println("\ny shuffle:\n");
        System.out.println("Pre, in, pos and level order :");
        y.preOrderOutput();
        System.out.println();
        y.inOrderOutput();
        System.out.println();
        y.postOrderOutput();
        System.out.println();
        y.levelOrderOutput();

        System.out.println("\n\nSecond configuration (Character or String arbol)\n");

        MyLBT<Character> xx = new MyLBT<>();
        MyLBT<Character> yy = new MyLBT<>();
        MyLBT<Character> zz = new MyLBT<>();
        MyLBT<Character> hh = new MyLBT<>();
        MyLBT<Character> ff = new MyLBT<>();
        MyLBT<Character> gg = new MyLBT<>();
        MyLBT<Character> auxx = new MyLBT<>();

        yy.makeTree('A', auxx, auxx);
        hh.makeTree('I', yy, auxx);
        yy.makeTree('N', auxx, auxx);
        xx.makeTree('S', hh, yy);
        yy.makeTree('O', auxx, auxx);
        hh.makeTree('S', auxx, auxx);
        yy.makeTree('P', yy, hh);
        yy.makeTree('E', xx, yy);
        hh.makeTree('O', auxx, auxx);
        xx.makeTree('L', hh, yy);
        yy.makeTree('E', auxx, auxx);
        hh.makeTree('R', auxx, auxx);
        yy.makeTree('A', yy, hh); // mark
        yy.makeTree('C', yy, xx);
        hh.makeTree('O', auxx, auxx);
        yy.makeTree('M', hh, yy); // mark
        xx.makeTree('I', auxx, auxx);
        hh.makeTree('L', auxx, auxx);
        xx.makeTree('A', xx, hh);
        xx.makeTree('C', xx, yy); // mark
        hh.makeTree('N', auxx, auxx);
        hh.makeTree('U', hh, xx);
        zz.makeTree('A', auxx, auxx);
        zz.makeTree('J', hh, zz);

        System.out.println("Pre, in, pos and level order :");
        zz.preOrderOutput();
        System.out.println();
        zz.inOrderOutput();
        System.out.println();
        zz.postOrderOutput();
        System.out.println();
        zz.levelOrderOutput();
        System.out.println("\n");

        System.out.println("The diameter of zz is: " + zz.diameter());
        System.out.println("The brother of L is :" + zz.brother('L'));
        System.out.println("The brother of J is :" + zz.brother('J'));

        zz.shuffle();
        System.out.println("\nz shuffle:\n");
        System.out.println("Pre, in, pos and level order :");
        zz.preOrderOutput();
        System.out.println();
        zz.inOrderOutput();
        System.out.println();
        zz.postOrderOutput();
        System.out.println();
        zz.levelOrderOutput();
        System.out.println();
    }
}

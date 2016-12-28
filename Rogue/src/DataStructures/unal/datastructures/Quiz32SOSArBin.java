package DataStructures.unal.datastructures;

public class Quiz32SOSArBin<T extends Comparable<? super T>> extends LinkedBinaryTree<T> {

    public T max() {
        T tmp = root();
        ArrayQueue<BinaryTreeNode<T>> q = new ArrayQueue<>();
        BinaryTreeNode<T> t = root;
        while (t != null) {
            if (tmp.compareTo(t.element) < 0) {
                tmp = t.element;
            }

            if (t.leftChild != null)
                q.put(t.leftChild);
            if (t.rightChild != null)
                q.put(t.rightChild);
            t = (BinaryTreeNode<T>) q.remove();
        }
        return tmp;
    }

    public T min() {
        T tmp = root();
        ArrayQueue<BinaryTreeNode<T>> q = new ArrayQueue<>();
        BinaryTreeNode<T> t = root;
        while (t != null) {
            if (tmp.compareTo(t.element) > 0)
                tmp = t.element;

            if (t.leftChild != null)
                q.put(t.leftChild);
            if (t.rightChild != null)
                q.put(t.rightChild);
            t = (BinaryTreeNode<T>) q.remove();
        }
        return tmp;
    }

    public T father(T me) {
        ArrayQueue<BinaryTreeNode<T>> q = new ArrayQueue<>();
        BinaryTreeNode<T> t = root;

        while (t != null) {
            if (t.leftChild != null)
                if (me.equals(t.leftChild.element))
                    return t.element;

            if (t.rightChild != null)
                if (me.equals(t.rightChild.element))
                    return t.element;

            if (t.leftChild != null)
                q.put(t.leftChild);
            if (t.rightChild != null)
                q.put(t.rightChild);

            t = (BinaryTreeNode<T>) q.remove();
        }
        return null;
    }

    public T grandPa(T me) {
        return father(father(me));
    }

    public static <T> void swap(BinaryTreeNode<T> me) {
        BinaryTreeNode<T> tmp = me.rightChild;
        me.setRightChild(me.getLeftChild());
        me.setLeftChild(tmp);
    }

    public void mirror() {
        try {
            preOrder(Quiz32SOSArBin.class.getMethod("swap", BinaryTreeNode.class));
        } catch (Exception e) {
            System.out.println(":(");
        }
    }

    public boolean compare(LinkedBinaryTree<T> sos) {
        ArrayQueue<BinaryTreeNode<T>> q = new ArrayQueue<BinaryTreeNode<T>>();
        BinaryTreeNode<T> t = root;
        BinaryTreeNode<T> l = sos.root;
        while (t != null) {
            if (!t.element.equals(l.element))
                return false;

            if (t.leftChild != null && l.leftChild != null) {
                q.put(t.leftChild);
                q.put(l.leftChild);
            } else if (!(t.leftChild == null && l.leftChild == null))
                return false;

            if (t.rightChild != null && l.rightChild != null) {
                q.put(t.rightChild);
                q.put(l.rightChild);
            } else if (!(t.rightChild == null && l.rightChild == null))
                return false;

            t = q.remove();
            l = q.remove();
        }
        return true;
    }

    public Chain<T> toChain() {
        ArrayQueue<BinaryTreeNode<T>> q = new ArrayQueue<>();
        Chain<T> l = new Chain<T>();
        BinaryTreeNode<T> t = root;
        while (t != null) {
            l.add(l.size(), t.element);

            if (t.leftChild != null)
                q.put(t.leftChild);
            if (t.rightChild != null)
                q.put(t.rightChild);
            t = q.remove();
        }
        return l;
    }

    public void frecuenciaDeElementos() {
        ArrayQueue<BinaryTreeNode<T>> q = new ArrayQueue<>();
        Chain<T> l = new Chain<T>();
        Chain<Integer> m = new Chain<Integer>();
        BinaryTreeNode<T> t = root;
        int index;
        while (t != null) {
            index = l.indexOf(t.element);
            if (index == -1) {
                l.add(l.size(), t.element);
                m.add(m.size(), 1);
            } else {
                m.add(index, m.remove(index) + 1);
            }

            if (t.leftChild != null)
                q.put(t.leftChild);
            if (t.rightChild != null)
                q.put(t.rightChild);
            t = (BinaryTreeNode<T>) q.remove();
        }
        for (int i = 0; i < l.size(); i++) {
            System.out.println("El elemento " + l.get(i) + " Esta " + m.get(i) + " veces en la lista");
        }
    }

    //enlaza en la pos (1 izq ,2 der) al nodo me el arbol entrante sos2. y si me tienen un hijo en pos, se agragra en la primera hoja de sos.
    public void enlaceAHoja(LinkedBinaryTree<T> sos2, T me2, int pos) {
        ArrayQueue<BinaryTreeNode<T>> q = new ArrayQueue<BinaryTreeNode<T>>();
        ArrayQueue<BinaryTreeNode<T>> m = new ArrayQueue<BinaryTreeNode<T>>();
        BinaryTreeNode<T> t = root;
        BinaryTreeNode<T> l = sos2.root;
        BinaryTreeNode<T> evano;
        while (t != null) {
            if (t.getElement() == me2) {
                if (pos == 1) {
                    if (t.leftChild != null) {
                        evano = t.leftChild;
                        while (l != null) {
                            if (l.leftChild == null && l.rightChild == null) {
                                l.setLeftChild(evano);
                                break;
                            }
                            if (l.leftChild != null)
                                m.put(l.leftChild);
                            if (l.rightChild != null)
                                m.put(l.rightChild);
                            l = m.remove();
                        }
                    }
                    t.setLeftChild(sos2.root);
                    break;
                }
                if (pos == 2) {
                    if (t.rightChild != null) {
                        evano = t.getRightChild();
                        while (l != null) {
                            if (l.leftChild == null && l.rightChild == null) {
                                l.setLeftChild(evano);
                                break;
                            }
                            if (l.leftChild != null)
                                m.put(l.leftChild);
                            if (l.rightChild != null)
                                m.put(l.rightChild);
                            l = m.remove();
                        }
                    }
                    t.setRightChild(sos2.root);
                    break;
                }
            }
            if (t.leftChild != null)
                q.put(t.leftChild);
            if (t.rightChild != null)
                q.put(t.rightChild);
            t = q.remove();
        }
    }

    public void sort() {
        ArrayQueue<BinaryTreeNode<T>> q = new ArrayQueue<BinaryTreeNode<T>>();
        Chain<T> m = toChain();
        T tmp;
        int index;
        for (int i = 0; i < m.size(); i++) {
            tmp = m.get(i);
            index = i;
            for (int j = i; j < m.size(); j++) {
                if (m.get(j).compareTo(tmp) < 0) {
                    tmp = m.get(j);
                    index = j;
                }
            }
            m.add(index, m.get(i));
            m.remove(index + 1);
            m.add(i, tmp);
            m.remove(i + 1);
        }
        BinaryTreeNode<T> t = root;

        while (t != null) {
            t.setElement(m.remove(0));

            if (t.leftChild != null)
                q.put(t.leftChild);
            if (t.rightChild != null)
                q.put(t.rightChild);

            t = q.remove();
        }
    }

    public static void main(String[] args) {
        Quiz32SOSArBin<Integer> a = new Quiz32SOSArBin<>();
        Quiz32SOSArBin<Integer> x = new Quiz32SOSArBin<>();
        Quiz32SOSArBin<Integer> y = new Quiz32SOSArBin<>();
        Quiz32SOSArBin<Integer> z = new Quiz32SOSArBin<>();
        Quiz32SOSArBin<Integer> h = new Quiz32SOSArBin<>();

        z.makeTree(6, a, a);
        y.makeTree(15, a, z);
        z.makeTree(9, a, a);
        x.makeTree(7, z, y);
        z.makeTree(5, x, a);
        x.makeTree(10, a, a);
        y.makeTree(1, a, x);
        x.makeTree(3, z, y);

        h.makeTree(10, a, a);
        z.makeTree(8, a, a);
        y.makeTree(9, a, h);
        z.makeTree(7, a, z);
        y.makeTree(5, y, a);
        y.makeTree(6, y, z);

        z.makeTree(6, y, y);

        System.out.println("x in pre, in, por, level order");
        x.preOrderOutput();
        System.out.println();
        x.inOrderOutput();
        System.out.println();
        x.postOrderOutput();
        System.out.println();
        x.levelOrderOutput();

        System.out.println("\n\ny in pre, in, por, level order");
        y.preOrderOutput();
        System.out.println();
        y.inOrderOutput();
        System.out.println();
        y.postOrderOutput();
        System.out.println();
        y.levelOrderOutput();

        System.out.println("\n\nx max = " + x.max());
        System.out.println("y max = " + y.max());

        System.out.println("\nx min = " + x.min());
        System.out.println("y min = " + y.min());

        System.out.println("\nfather of 10 in x = " + x.father(10));
        System.out.println("father of 8 in y = " + y.father(8));

        System.out.println("\nGrand pa of 10 in x = " + x.grandPa(10));
        System.out.println("Grand pa of 10 in y = " + y.grandPa(10));

        y.mirror();
        System.out.println("\ny mirror");
        y.preOrderOutput();
        System.out.println();
        y.inOrderOutput();
        System.out.println();
        y.postOrderOutput();
        System.out.println("\ny level with mirror");
        y.levelOrderOutput();

        System.out.println("\n\nz = y? " + z.compare(y));
        System.out.println("x = x? " + x.compare(x));

        System.out.println("\ny in Chiain: " + y.toChain());

        System.out.println("\nz level order");
        z.levelOrderOutput();
        System.out.println("\n\nz frecuences");
        z.frecuenciaDeElementos();

        System.out.println("\ny level order");
        y.levelOrderOutput();
        System.out.println("\ny sorted in level orda");
        y.sort();
        y.levelOrderOutput();
        System.out.println("\ny sorted in pre order");
        y.inOrderOutput();

        System.out.println("\n\nx level order");
        x.levelOrderOutput();
        x.sort();
        System.out.println("\nx sorted in level orda");
        x.levelOrderOutput();
        System.out.println("\nx sorted in pre order");
        x.inOrderOutput();

        x.enlaceAHoja(y, 3, 1);
        System.out.println("\n\nx concat y in element 3 to left");
        x.levelOrderOutput();

        System.out.println("\n\nThe end :)");
    }
}

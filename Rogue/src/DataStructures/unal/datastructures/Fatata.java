package DataStructures.unal.datastructures;

class Fatata<T> extends LinkedBinaryTree<T> {

    static int c; // yes!

    public boolean isFull() {
        return size() == Math.pow(2.0, height()) - 1;
    }

    public static <T> void h(BinaryTreeNode<T> t) {
        if (t.leftChild == null && t.rightChild == null)
            c++;
    }

    public static <T> void s(BinaryTreeNode<T> t) {
        count += (Integer) t.element;
    }

    public int numLeaves() {
        c = 0;
        try {
            preOrder(Fatata.class.getMethod("h", BinaryTreeNode.class));
        } catch (Exception e) {
            System.out.println(e);
        }
        return c;
    }

    public static <T> void noArm(BinaryTreeNode<T> tree) {
        if ((tree.getLeftChild() == null && tree.getRightChild() != null) || (tree.getLeftChild() != null && tree.getRightChild() == null))
            count++;

    }

    public int numNoArm() {
        count = 0;
        try {
            preOrder(Fatata.class.getMethod("noArm", BinaryTreeNode.class));
        } catch (Exception e) {
            System.out.println(e);
        }
        return count;
    }

    public int suma() {
        count = 0;
        try {
            preOrder(Fatata.class.getMethod("s", BinaryTreeNode.class));
        } catch (Exception e) {
            System.out.println(e);
        }
        return count;
    }

    public static void main(String[] args) {
        Fatata<Integer> a = new Fatata<>();
        Fatata<Integer> x = new Fatata<>();
        Fatata<Integer> y = new Fatata<>();
        Fatata<Integer> z = new Fatata<>();

        z.makeTree(6, a, a);
        y.makeTree(15, a, z);
        z.makeTree(9, a, a);
        x.makeTree(7, z, y);
        z.makeTree(5, x, a);
        x.makeTree(10, a, a);
        y.makeTree(1, a, x);
        x.makeTree(3, z, y);

        z.makeTree(6, a, a);

        z.makeTree(4, a, a);
        y.makeTree(5, a, a);
//      y.makeTree( 6, y, z );

        x.preOrderOutput();
        System.out.println();
        x.inOrderOutput();
        System.out.println();
        x.postOrderOutput();
        System.out.println();
        x.levelOrderOutput();
        System.out.println();

        System.out.println(y.isFull());

        System.out.println(y.numLeaves());

        System.out.println(x.numLeaves());

        System.out.println(y.suma());

        System.out.println("sumandoooo ya! " + x.suma());

        System.out.println(x.numLeaves()); // please

//      System.out.println( x.numNoArm( ) ); // please
        System.out.println(" \n" + y.numNoArm()); // please

    }
}

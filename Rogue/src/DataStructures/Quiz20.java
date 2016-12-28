package DataStructures;

import DataStructures.unal.datastructures.LinkedBinaryTree;

class Quiz20 {
    public static void main(String[] args) {
        LinkedBinaryTree<Character> a = new LinkedBinaryTree<>();
        LinkedBinaryTree<Character> x = new LinkedBinaryTree<>();
        LinkedBinaryTree<Character> y = new LinkedBinaryTree<>();
        LinkedBinaryTree<Character> z = new LinkedBinaryTree<>();
        LinkedBinaryTree<Character> w = new LinkedBinaryTree<>();

        x.makeTree('M', a, a);
        y.makeTree('W', x, a);
        x.makeTree('X', a, y);
        z.makeTree('Y', a, a);
        y.makeTree('Z', z, x);
        z.makeTree('E', a, y);
        x.makeTree('H', a, a);
        y.makeTree('D', x, a);
        x.makeTree('B', y, z);
        z.makeTree('I', a, a);
        y.makeTree('G', a, z);
        z.makeTree('F', a, a);
        w.makeTree('C', z, y);
        z.makeTree('A', x, w);

        z.preOrderOutput();
        System.out.println();
        z.inOrderOutput();
        System.out.println();
        z.postOrderOutput();
        System.out.println();
        z.levelOrderOutput();
        System.out.println();

    }

}

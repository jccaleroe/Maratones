package DataStructures;

import DataStructures.unal.datastructures.LinkedBinaryTree;

public class QuizPalFace {

    public static void main(String[] args) {
        LinkedBinaryTree<Character> x = new LinkedBinaryTree<>();
        LinkedBinaryTree<Character> y = new LinkedBinaryTree<>();
        LinkedBinaryTree<Character> z = new LinkedBinaryTree<>();
        LinkedBinaryTree<Character> h = new LinkedBinaryTree<>();
        LinkedBinaryTree<Character> f = new LinkedBinaryTree<>();
        LinkedBinaryTree<Character> g = new LinkedBinaryTree<>();
        LinkedBinaryTree<Character> aux = new LinkedBinaryTree<>();

        x.makeTree('A', x, x);
        y.makeTree('R', y, y);
        x.makeTree('O', x, y);

        z.makeTree('A', z, z);
        h.makeTree('N', h, h);
        z.makeTree('E', z, h);

        y.makeTree('I', x, z);

        f.makeTree('D', aux, aux);
        g.makeTree('A', aux, aux);
        x.makeTree('S', f, g);
        h.makeTree('P', aux, aux);
        z.makeTree('L', x, h);

        x.makeTree('N', y, z);

        y.makeTree('I', aux, aux);
        h.makeTree('N', aux, aux);
        z.makeTree('O', y, h);

        y.makeTree('O', aux, aux);
        h.makeTree('S', aux, aux);
        f.makeTree('C', y, h);

        y.makeTree('C', z, f);

        z.makeTree('U', x, y); // mark

        y.makeTree('A', aux, aux);
        h.makeTree('F', aux, aux);
        x.makeTree('A', y, h);

        y.makeTree('O', aux, aux);
        h.makeTree('N', aux, aux);
        f.makeTree('L', y, h);

        x.makeTree('A', x, f);

        y.makeTree('S', aux, aux);
        h.makeTree('E', aux, aux);
        f.makeTree('E', y, h);


        y.makeTree('C', aux, aux);
        h.makeTree('A', aux, aux);
        g.makeTree('R', y, h);

        f.makeTree('M', f, g);

        g.makeTree('A', x, f); // mark

        y.makeTree('J', z, g); // mark

        y.preOrderOutput();
        System.out.println();
        y.inOrderOutput();
        System.out.println();
        y.postOrderOutput();
        System.out.println();
        y.levelOrderOutput();
        System.out.println("\n\nSecond configuration\n");

        y.makeTree('A', aux, aux);
        h.makeTree('I', y, aux);
        y.makeTree('N', aux, aux);
        x.makeTree('S', h, y);

        y.makeTree('O', aux, aux);
        h.makeTree('S', aux, aux);
        y.makeTree('P', y, h);

        y.makeTree('E', x, y);

        h.makeTree('O', aux, aux);
        x.makeTree('L', h, y);

        y.makeTree('E', aux, aux);
        h.makeTree('R', aux, aux);
        y.makeTree('A', y, h); // mark

        y.makeTree('C', y, x);

        h.makeTree('O', aux, aux);
        y.makeTree('M', h, y); // mark

        x.makeTree('I', aux, aux);
        h.makeTree('L', aux, aux);
        x.makeTree('A', x, h);

        x.makeTree('C', x, y); // mark

        h.makeTree('N', aux, aux);
        h.makeTree('U', h, x);

        z.makeTree('A', aux, aux);
        z.makeTree('J', h, z);

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

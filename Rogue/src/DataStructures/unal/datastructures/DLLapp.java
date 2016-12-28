package DataStructures.unal.datastructures;

public class DLLapp {
    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        DoubleLinkedList<Integer> next = new DoubleLinkedList<>();
        for (int i = 0; i < 1000; i++)
            next.add(i, i * 2);
        next.add(100, 4);
        next.add(700, 40);
        System.out.println(next.indexOf(8));
        System.out.println(next.get(1000));
        System.out.println(next.size());
        for (int i = 0; i < 1002; i++)
            next.remove(0);
        System.out.println("\n" + next + "\n");
        long end = System.currentTimeMillis();
        ;
        System.out.println("\nEl tiempo con DoubleChain fue : " + (end - start) + " ms\n");

        long start2 = System.currentTimeMillis();
        Chain<Integer> next2 = new Chain<>();
        for (int i = 0; i < 1000; i++)
            next2.add(i, i * 2);
        next2.add(100, 4);
        next2.add(700, 40);
        System.out.println(next2.indexOf(8));
        System.out.println(next2.get(1000));
        System.out.println(next2.size());
        for (int i = 0; i < 1002; i++)
            next2.remove(0);
        System.out.println("\n" + next2 + "\n");
        long end2 = System.currentTimeMillis();
        System.out.println("\nEl tiempo con Chain fue : " + (end2 - start2) + " ms\n");


    }
}

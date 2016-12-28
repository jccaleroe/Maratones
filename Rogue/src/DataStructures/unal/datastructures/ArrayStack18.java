package DataStructures.unal.datastructures;

import java.util.*;

public class ArrayStack18<T> extends ArrayStack<T> {

    public void clean() {
        while (!isEmpty()) pop();
    }

    public void topGoToBottom() {
        ArrayStack18<T> tmp = new ArrayStack18<T>();
        T removed = pop();
        while (!isEmpty())
            tmp.push(pop());
        push(removed);
        while (!tmp.isEmpty())
            push(tmp.pop());
    }

    public void bottomGoToTop() {
        ArrayStack18<T> tmp = new ArrayStack18<T>();
        while (!isEmpty())
            tmp.push(pop());
        T removed = tmp.pop();
        while (!tmp.isEmpty())
            push(tmp.pop());
        push(removed);
    }

    public T peekBottom() {
        if (isEmpty())
            throw new EmptyStackException();
        return stack[top - (top)];
    }

    public void concat(LinearList<T> otra) {
        for (int i = 0; i < otra.size(); i++) {
            push(otra.get(i));
        }
    }

    public static void main(String[] args) {

        ArrayStack18<Integer> samu = new ArrayStack18<Integer>();

        for (int i = 0; i < 1000; i += 44)
            samu.push(i);

        System.out.println("top go to bottom");
        System.out.println(samu + "\n");
        samu.topGoToBottom();
        System.out.println(samu + "\n");

        System.out.println("bottom go to top");
        System.out.println(samu + "\n");
        samu.bottomGoToTop();
        System.out.println(samu + "\n");

        System.out.println("peek bottom");
        System.out.println(samu.peekBottom());

        ArrayLinearList<Integer> invasion = new ArrayLinearList<>();
        for (int i = 0; i < 15; i++)
            invasion.add(0, i + i % 2);

        System.out.println("concat");
        System.out.println("otra: " + invasion + "\n");
        System.out.println("this: " + samu + "\n");
        samu.concat(invasion);
        System.out.println(samu);

        System.out.println("\n" + "clean");
        samu.clean();
        System.out.println(samu);

    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        boolean jiji = false;
        for (T x : stack)
            if (x != null) {
                jiji = true;
                s.append(x + ", ");
            }
        if (jiji == true) s.setLength(s.length() - 2);
        s.append("]");
        return new String(s);
    }
}

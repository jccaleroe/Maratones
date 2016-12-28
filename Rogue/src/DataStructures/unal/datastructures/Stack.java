package DataStructures.unal.datastructures;

public interface Stack<T> {
    boolean isEmpty();

    T peek();

    void push(T theObject);

    T pop();
}

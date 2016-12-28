package DataStructures.unal.datastructures;

public class DoubleChainNode<T> {

    T element;
    DoubleChainNode<T> next;
    DoubleChainNode<T> previous;

    DoubleChainNode() {
        this(null, null, null);
    }

    DoubleChainNode(T element) {
        this(element, null, null);

    }

    DoubleChainNode(T element, DoubleChainNode<T> next) {
        this(element, next, null);
    }

    DoubleChainNode(DoubleChainNode<T> next, DoubleChainNode<T> previous) {
        this(null, next, previous);
    }

    DoubleChainNode(T element, DoubleChainNode<T> next, DoubleChainNode<T> previous) {
        this.element = element;
        this.next = next;
        this.previous = previous;
    }

}

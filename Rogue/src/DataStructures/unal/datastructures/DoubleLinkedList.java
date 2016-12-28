package DataStructures.unal.datastructures;

import java.util.*;

public class DoubleLinkedList<T> implements LinearList<T>, Iterable<T> {

    DoubleChainNode<T> lastNode;
    DoubleChainNode<T> firstNode;
    int size = 0;

    public DoubleLinkedList() {
        firstNode = null;
        lastNode = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException
                    ("index = " + index + "size = " + size);
    }

    public T get(int index) {
        checkIndex(index);
        T tmp;
        DoubleChainNode<T> p = firstNode;
        if (index < size / 2) {
            for (int i = 0; i < index; i++)
                p = p.next;
            tmp = p.element;
        } else {
            p = lastNode;
            for (int i = size - 1; i > index; i--)
                p = p.previous;
            tmp = p.element;
        }
        return tmp;
    }

    public int indexOf(T theElement) {
        DoubleChainNode<T> currentNode = firstNode;
        int index = 0;
        while (currentNode != null &&
                !currentNode.element.equals(theElement)) {
            currentNode = currentNode.next;
            index++;
        }
        if (currentNode == null) {
            return -1;
        } else {
            return index;
        }
    }

    public T remove(int index) {
        checkIndex(index);
        T removedElement;
        if (index == 0) {
            removedElement = firstNode.element;
            firstNode = firstNode.next;
            if (firstNode != null)
                firstNode.previous = null;
        } else if (index == size - 1) {
            removedElement = lastNode.element;
            lastNode = lastNode.previous;
            lastNode.next = null;
        } else {
            if (index < size / 2) {
                DoubleChainNode<T> p = firstNode;
                for (int i = 0; i < index; i++)
                    p = p.next;
                removedElement = p.element;
                p.next.previous = p.previous;
                p.previous.next = p.next;

            } else {
                DoubleChainNode<T> p = lastNode;
                for (int i = size - 1; i > index; i--)
                    p = p.previous;
                removedElement = p.element;
                p.next.previous = p.previous;
                p.previous.next = p.next;
            }
        }
        size--;
        return removedElement;

    }

    public void add(int index, T theElement) {
        if (index < 0 || index > size)
            // invalid list position
            throw new IndexOutOfBoundsException
                    ("index = " + index + "  size = " + size);

        if (index == 0) {
            // insert at front
            firstNode = new DoubleChainNode<T>(theElement, firstNode, null);
            //      	 firstNode.next.previous = firstNode;
            if (firstNode.next == null)
                lastNode = firstNode;
            else
                firstNode.next.previous = firstNode;
        } else {
            if (index < size / 2) {
                DoubleChainNode<T> p = firstNode;
                for (int i = 0; i < index; i++)
                    p = p.next;

                p.previous.next = new DoubleChainNode<T>(theElement, p, p.previous);
                p.previous = p.previous.next;
            } else {
                DoubleChainNode<T> p = lastNode;
                if (index == size) {
                    p.next = new DoubleChainNode<T>(theElement, null, p);
                    lastNode = p.next;
                } else {
                    for (int i = size - 1; i > index; i--)
                        p = p.previous;
                    p.previous.next = new DoubleChainNode<T>(theElement, p, p.previous);
                    p.previous = p.previous.next;
                }
            }
        }
        size++;
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for (T x : this)
            s.append(Objects.toString(x) + ", ");
        if (size > 0)
            s.setLength(s.length() - 2);
        s.append("]");
        return new String(s);
    }


    public Iterator<T> iterator() {
        return new DoubleChainIterator();
    }

    private class DoubleChainIterator implements Iterator<T> {
        DoubleChainNode<T> nextNode;

        public DoubleChainIterator() {
            nextNode = firstNode;
        }

        public boolean hasNext() {
            return nextNode != null;
        }

        public T next() {
            T elementToReturn = nextNode.element;
            nextNode = nextNode.next;
            return elementToReturn;
        }

        public void remove() {
            throw new UnsupportedOperationException
                    ("remove not supported");
        }
    }

    public static void main(String[] args) {
        DoubleLinkedList<Integer> novice = new DoubleLinkedList<>();
        novice.add(0, 1);
        novice.add(1, 2);
        novice.add(2, 3);
        novice.add(1, 4);
        novice.add(4, 5);
        novice.add(4, 6);
        novice.add(4, 4);
        System.out.println(novice);
        System.out.println(novice.indexOf(6));
        System.out.println(novice.get(2));
        novice.remove(4);
        //		System.out.println(novice);
        novice.remove(0);
        //		System.out.println(novice);
        //		novice.remove(0);
        System.out.println(novice);
    }
}

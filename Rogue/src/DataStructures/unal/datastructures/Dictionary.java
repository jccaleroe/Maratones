package DataStructures.unal.datastructures;

public interface Dictionary<K extends Comparable<? super K>, E> {
    E get(K key);

    E put(K key, E theElement);

    E remove(K key);
}

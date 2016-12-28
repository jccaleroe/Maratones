/**
 * extending interface linear list
 */

package DataStructures.unal.datastructures;

import java.util.*;

public interface LinearListImproved<T> extends LinearList<T> {
    void save(String fn);

    void load(String fn);

    void sort();

    void sort(Comparator<T> c);
}

package util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Generic list backed by a simple growable array.
 *
 * @param <E> generic type
 * @author
 */
public class List<E> implements Iterable<E> {

    private E[] objects; // E is the name for the generic type
    private int size;

    private static final int CAPACITY = 4;
    private static final int NOT_FOUND = -1;

    /**
     * Constructs an empty list with initial capacity 4.
     */
    @SuppressWarnings("unchecked")
    public List() { // new an array type-casted to E with a capacity of 4.
        objects = (E[]) new Object[CAPACITY];
        size = 0;
    }

    /**
     * Finds the index of e in the list.
     *
     * @param e object to search
     * @return index if found, -1 otherwise
     */
    private int find(E e) { // return -1 if not found
        if (e == null) return NOT_FOUND;
        for (int i = 0; i < size; i++) {
            if (objects[i].equals(e)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Grows the backing array by 4.
     */
    @SuppressWarnings("unchecked")
    private void grow() { // grow the size of the array by 4
        E[] newArray = (E[]) new Object[objects.length + CAPACITY];
        for (int i = 0; i < size; i++) {
            newArray[i] = objects[i];
        }
        objects = newArray;
    }

    /**
     * Checks whether the list contains e.
     *
     * @param e object
     * @return true if present
     */
    public boolean contains(E e) {
        return find(e) != NOT_FOUND;
    }

    /**
     * Adds e to the end of the list.
     *
     * @param e object to add
     */
    public void add(E e) {
        if (e == null) return;
        if (size == objects.length) {
            grow();
        }
        objects[size] = e;
        size++;
    }

    /**
     * Removes the first occurrence of e, if present.
     *
     * @param e object to remove
     */
    public void remove(E e) {
        int index = find(e);
        if (index == NOT_FOUND) return;

        // shift left to fill the gap
        for (int i = index; i < size - 1; i++) {
            objects[i] = objects[i + 1];
        }
        objects[size - 1] = null; // avoid loitering
        size--;
    }

    /**
     * Checks if the list is empty.
     *
     * @return true if empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns number of elements.
     *
     * @return size
     */
    public int size() {
        return size;
    }

    /**
     * Returns an iterator for foreach traversal.
     *
     * @return iterator
     */
    @Override
    public Iterator<E> iterator() { // traversing the list using for each
        return new ListIterator();
    }

    /**
     * Returns the object at index.
     *
     * @param index position
     * @return object at index
     * @throws IndexOutOfBoundsException if invalid index
     */
    public E get(int index) { // return the object at the index
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index);
        }
        return objects[index];
    }

    /**
     * Sets the object at index to e.
     *
     * @param index position
     * @param e new object
     * @throws IndexOutOfBoundsException if invalid index
     */
    public void set(int index, E e) { // put object e at the index
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index);
        }
        objects[index] = e;
    }

    /**
     * Returns index of object e or -1.
     *
     * @param e object
     * @return index or -1
     */
    public int indexOf(E e) { // return index of object e, or return -1
        return find(e);
    }

    // private inner class for the iterator to work properly
    private class ListIterator implements Iterator<E> {

        int current = 0; // current index when traversing the list (array)

        @Override
        public boolean hasNext() { // if it’s empty or at the end of the array
            return current < size;
        }

        @Override
        public E next() { // return the next object in the list
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return objects[current++];
        }
    }
}

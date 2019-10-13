package ru.otus.hw02;


import java.util.*;

public class DIYarrayList<T> implements List<T> {
    private Object[] array;
    private int capacity = 0;
    private final int INIT_SIZE = 10;
    private int collectionCopySize;

    public DIYarrayList() {
        this.array = new Object[INIT_SIZE];
    }

    public DIYarrayList(int setSize) {
        this.array = new Object[setSize];
    }

    public DIYarrayList (DIYarrayList<T> list) {
        this.array = list.toArray();
        collectionCopySize = array.length;
    }

    public int arraySize() {
        return array.length;
    }

    private Object[] changeArraySize() {
        return Arrays.copyOf(array, arraySize() * 2);
    }

    @Override
    public int size() {
        return collectionCopySize > 0 ? collectionCopySize : capacity;
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, capacity);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(T t) {
        boolean result = false;
        if (t != null) {
            if (capacity >= arraySize())
                array = changeArraySize();
            array[capacity] = t;
            capacity++;
            result = true;
        }
        return result;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }


    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException();
        return (T) array[index];
    }

    @Override
    public T set(int index, T element) {
        Object prevElement = array[index];
        array[index] = element;
        capacity = index + 1;
        return (T) prevElement;
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListIterator<T>() {
            private int currentIndex = -1;

            @Override
            public boolean hasNext() {
                return currentIndex < capacity;
            }

            @Override
            public T next() {
                if (currentIndex < size()) {
                    int tmp = currentIndex + 1;
                    currentIndex++;
                    return (T) array[tmp];
                }
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public T previous() {
                return null;
            }

            @Override
            public int nextIndex() {
                return 0;
            }

            @Override
            public int previousIndex() {
                return 0;
            }

            @Override
            public void remove() {

            }

            @Override
            public void set(T t) {
                array[currentIndex] = t;
//                currentIndex++;
            }

            @Override
            public void add(T t) {

            }
        };
//        return listIterator;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }


    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }


}






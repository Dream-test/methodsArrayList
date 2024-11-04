package com.edu;

import java.util.Arrays;
import java.util.Iterator;

public class DefaultCustomArrayList<E> implements CustomArrayList<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    E elementsData[];
    static int sizeData;

    @SuppressWarnings("unchecked")
    public DefaultCustomArrayList() {
       elementsData = (E[]) new Object[DEFAULT_CAPACITY];
       sizeData = 0;
    }

    @Override
    public boolean add(E element) {
        int minSize = sizeData + 1;
        checkAvailableSize(minSize);
        elementsData[sizeData] = element;
        sizeData++;
        return true;
    }

    private void checkAvailableSize(int minSize) {
        if (minSize - elementsData.length > 0) {
            grow(minSize);
        }
    }

    private void grow(int minSize) {
        int oldSize = elementsData.length;
        int newSize = oldSize +(oldSize >> 1);
        if  (newSize - MAX_ARRAY_SIZE > 0) {
            newSize = maxVolume(minSize);
        }

        elementsData = Arrays.copyOf(elementsData, newSize);
    }

    private int maxVolume(int minSize) {
        if ( minSize < 0) {
            throw new OutOfMemoryError("Limit arrays elements");
        }
        return (minSize > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    @Override
    public boolean remove(E element) {
        for (int i = 0; i < sizeData; i++) {
            if (elementsData[i].equals(element)) {
                for (int j = i + 1; j < sizeData; j++) {
                  elementsData[i] = elementsData[j];
                }
                sizeData--;
                int minSize = sizeData;
                cut(minSize);
                return true;
            }
        }
        return false;
    }
    
    private void cut(int minSize) {
        int optimumSize = elementsData.length - elementsData.length/2;
        if (minSize < optimumSize ) {
            elementsData = Arrays.copyOf(elementsData, optimumSize);
        }
    }

    private void remove(int index) {
    }

    @Override
    public E get(int index) {
        return elementsData[index];
    }

    @Override
    public int size() {
        return sizeData;
    }

    @Override
    public boolean isEmpty() {
        if (sizeData > 0) {
            return false;
        }
        return true;
    }

    @Override
    public void clear() {
    }

    @Override
    public boolean contains(E element) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }
}
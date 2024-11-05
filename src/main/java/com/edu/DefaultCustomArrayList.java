package com.edu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

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
            newSize = maxSize(minSize);
        }

        elementsData = Arrays.copyOf(elementsData, newSize);
    }

    private int maxSize(int minSize) {
        if ( minSize < 0) {
            throw new OutOfMemoryError("Limit arrays elements");
        }
        return (minSize > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    @Override
    public boolean remove(E element) {
        for (int i = 0; i < sizeData; i++) {
            if (elementsData[i].equals(element)) {
                remove(i);
                return true;
            }
        }
        return false;
    }
    
    private void cut(int minSize) {
        int optimumSize = elementsData.length - elementsData.length/3;
        if (minSize < optimumSize ) {
            elementsData = Arrays.copyOf(elementsData, optimumSize);
        }
    }

    private void remove(int index) {
        if (index >= sizeData) {
            throw new IndexOutOfBoundsException("Index hasn't data element");
        }
        System.arraycopy(elementsData, index + 1, elementsData, index, sizeData - index - 1);
        /* for (int i = index; i < (sizeData - 1); i++) {
            elementsData[i] = elementsData[i + 1];
        } */
        elementsData[sizeData--] = null;
        cut(sizeData);
    }

    @Override
    public E get(int index) {
        if (index >= sizeData) {
            throw new IndexOutOfBoundsException("Index hasn't data element");
        }
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

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
         elementsData = (E[]) new Object[DEFAULT_CAPACITY];
         sizeData = 0;
    }

    @Override
    public boolean contains(E element) {
        for (int i = 0; i < sizeData; i++) {
            if (element.equals(elementsData[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomIterator();
    }

    private class CustomIterator implements Iterator<E> {
        private int dataIndex = 0;
        private int lastReturnIndex = -1;

        @Override
        public boolean hasNext() {
            return dataIndex < sizeData;
        }

        @Override
        public E next() {
            if (dataIndex >= sizeData) {
                throw new NoSuchElementException("Index out of elementsData index interval");
            }
            lastReturnIndex = dataIndex;
            return elementsData[dataIndex++];
        }

        @Override
        public void remove() {
            if (lastReturnIndex < 0) {
                throw new IllegalStateException("remove() called before next(), or called twice.");
            }
            System.arraycopy(elementsData, lastReturnIndex + 1, elementsData, lastReturnIndex, sizeData - lastReturnIndex - 1);
            elementsData[sizeData--] = null;
            cut(sizeData);
            dataIndex = lastReturnIndex;
            lastReturnIndex = -1;
        }

    }

    @Override
    public int listSize() {
        return elementsData.length;
    }
}
package com.edu;

import java.util.Arrays;
import java.util.Iterator;

public class DefaultCustomArrayList<E> implements CustomArrayList<E> {
    private static final int DEFAULT_VOLUME = 10;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    static Object elementsData[];
    static int countData;

    public DefaultCustomArrayList() {
       elementsData = new Object[DEFAULT_VOLUME];
       countData = 0;
    }

    @Override
    public boolean add(E element) {
        int minVolume = countData + 1;
        checkAvailableVolume(minVolume);
        elementsData[countData] = element;

        return true;
    }

    private void checkAvailableVolume(int minVolume) {
        if (minVolume - elementsData.length > 0) {
            grow(minVolume);
        }
    }

    private void grow(int minVolume) {
        int oldVolume = elementsData.length;
        int newVolume = oldVolume +(oldVolume >> 1);
        if  (newVolume - MAX_ARRAY_SIZE > 0) {
            newVolume = maxVolume(minVolume);
        }

        elementsData = Arrays.copyOf(elementsData, newVolume);
    }

    private int maxVolume(int minVolume) {
        if ( minVolume < 0) {
            throw new OutOfMemoryError("Limit arrays elements");
        }
        return (minVolume > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    @Override
    public boolean remove(E element) {
        return false;
    }

    private void remove(int index) {
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
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
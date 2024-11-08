package com.edu;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DefaultCustomArrayList<E> implements CustomArrayList<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    E[] elementsData;
    private int sizeData;

    @SuppressWarnings("unchecked")
    public DefaultCustomArrayList() {
       elementsData = (E[]) new Object[DEFAULT_CAPACITY]; //создает массив с типом E и начальным размером
       sizeData = 0;
    }

    @Override
    public boolean add(E element) {
        if (element == null) {
            throw new NullPointerException("Element cannot be null");
        }
        int requiredSize = sizeData + 1; // устанавливает необходимый минимум массива для размещения данных
        if (!canAdd(requiredSize)) { // проверяет выход на максимальный размер массива, при превышении возвращает false
            return false;
        }
        if (!hasAvailableSize(requiredSize)) { // проверяет наличие необходимого для записи данных места в буфере
            grow(requiredSize); // увеличивает длину массива если проверка вернула false
        }
        elementsData[sizeData] = element; // добавляет элемент в массив
        sizeData++; // увеличивает счетчик данных
        return true; // возвращает true при успешной записи элемента в массив
    }

    private boolean canAdd(int requiredSize) { //проверяет не достиг ли необходимый минимум массива максимально возможного значения
        return requiredSize < Integer.MAX_VALUE;
    }

    private boolean hasAvailableSize(int requiredSize) { // проверяет наличие необходимого для записи данных места в буфере
        return requiredSize < elementsData.length;
    }

    private void grow(int requiredSize) { // увеличивает длину массива до следующей границы буфера либо до максимальной длины при ее достижении
        int oldSize = elementsData.length;
        int newSize = Math.min((oldSize + (oldSize >>1)), MAX_ARRAY_SIZE);
        /* int newSize = oldSize +(oldSize >> 1);
        if  (newSize - MAX_ARRAY_SIZE > 0) {
            newSize = maxSize(requiredSize);
        } */
        if (requiredSize > newSize) {
            newSize = maxSize(requiredSize);
        }

        elementsData = Arrays.copyOf(elementsData, newSize);
    }

    private int maxSize(int requiredSize) {
        if ( requiredSize < 0) {
            throw new OutOfMemoryError("Limit arrays elements");
        }
        System.out.println("Warning: Required size exceeds MAX_ARRAY_SIZE. Setting to Integer.MAX_VALUE.");
        return (requiredSize > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    @Override
    public boolean remove(E element) {
        for (int i = 0; i < sizeData; i++) {
            if (elementsData[i].equals(element)) {
                removeAtIndex(i);
                return true;
            }
        }
        return false;
    }
    
    private void cut() {
        int optimumSize = elementsData.length - elementsData.length/3;
        if (sizeData < optimumSize ) {
            elementsData = Arrays.copyOf(elementsData, optimumSize);
        }
    }

    private void removeAtIndex(int index) {
        if (index >= sizeData) {
            throw new IndexOutOfBoundsException("Index hasn't data element");
        }
        System.arraycopy(elementsData, index + 1, elementsData, index, sizeData - index - 1);
        elementsData[sizeData--] = null;
        cut();
    }

    @Override
    public E get(int index) {
        if (index >= sizeData) {
            throw new IndexOutOfBoundsException("Index hasn't data element");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("Wrong Index < 0");
        }
        return elementsData[index];
    }

    @Override
    public int size() {
        return sizeData;
    }

    @Override
    public boolean isEmpty() {
        return sizeData <= 0;
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
            cut();
            dataIndex = lastReturnIndex;
            lastReturnIndex = -1;
        }

    }

    @Override
    public int listSize() {
        return elementsData.length;
    }
}
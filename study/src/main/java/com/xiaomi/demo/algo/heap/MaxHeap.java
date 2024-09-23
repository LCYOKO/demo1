package com.xiaomi.demo.algo.heap;

/**
 * @author Admin
 */
public class MaxHeap<E extends Comparable<E>> {

    private final Object[] data;
    private int size;
    private final int capacity;

    public MaxHeap(int capacity) {
        data = new Object[capacity + 1];
        size = 0;
        this.capacity = capacity;
    }

    public MaxHeap(E[] arr) {
        int n = arr.length;
        data = new Object[n];
        capacity = n;
        for (int i = 0; i < n; i++) {
            data[i + 1] = arr[i];
        }
        size = n;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public void add(E item) {
        if (isFull()) {
            throw new IllegalArgumentException("Heap is full.");
        }
        data[size + 1] = item;
        size++;
        heapfiy();
    }

    public E peek() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Heap is empty.");
        }
        return (E) data[0];
    }

    public E poll() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Heap is empty.");
        }
        E ret = (E) data[0];
        swap(0, size - 1);
        size--;
        adjust(size, 0);
        return ret;
    }

    private void swap(int i, int j) {
        Object temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    private void heapfiy() {
        for (int i = size / 2 - 1; i >= 0; i--) {
            adjust(size, i);
        }
    }

    private void adjust(int n, int i) {
        //左节点
        int left = i * 2 + 1;
        Object val = data[i];
        while (left < n) {
            if (left + 1 < n && ((Comparable<E>) data[left + 1]).compareTo((E) data[left]) > 0) {
                left++;
            }
            if (((Comparable<E>) data[left]).compareTo((E) val) >= 0) {
                break;
            }
            data[i] = data[left];
            i = left;
            left = i * 2 + 1;
        }
        data[i] = val;
    }
}
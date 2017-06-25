import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

/**
 * Created by Alex on 25.06.17.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size = 0;
    private int capacity = 1;

    public RandomizedQueue() {
        // construct an empty randomized queue
        queue = (Item[]) new Object[capacity];
    }

    public boolean isEmpty() {
        // is the queue empty?
        return size == 0;
    }

    public int size() {
        // return the number of items on the queue
        return size;
    }

    public void enqueue(Item item) {
        // add the item
        if (item == null) {
            throw new java.lang.IllegalArgumentException("Element must be not null!");
        }
        if (size == capacity) {
            resize(2 * capacity);
        }
        queue[size++] = item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int n = 0;
        for (Item i : queue) {
            if (i != null) {
                copy[n++] = i;
            }
        }
        this.capacity = capacity;
        this.queue = copy;
    }

    public Item dequeue() {
        // remove and return a random item
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Queue is empty");
        }
        int rand = StdRandom.uniform(size);
        Item item = queue[rand];
        queue[rand] = null;
        size--;
        if (size > 0 && capacity > size / 4) {
            resize(capacity / 2);
        }
        return item;
    }

    public Item sample() {
        // return (but do not remove) a random item
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Queue is empty");
        }
        return queue[StdRandom.uniform(size)];
    }

    public Iterator<Item> iterator() {
        // return an independent iterator over items in random order
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        private int[] shuffledQueue;
        private int position = 0;

        public QueueIterator() {
            shuffledQueue = StdRandom.permutation(size);
        }

        public boolean hasNext() {
            return shuffledQueue.length > position;
        }

        public Item next() {
            if (shuffledQueue.length < position) {
                throw new java.util.NoSuchElementException("No more elements in queue!");
            }
            return queue[position++];
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException("Method not supported!");
        }
    }

    public static void main(String[] args) {
        // unit testing (optional)
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        queue.enqueue(10);
        queue.enqueue(50);
        System.out.println(queue.dequeue());
        for (int i : queue) {
            System.out.println(i);
        }
        System.out.println(queue.dequeue());
        queue.enqueue(100);
        System.out.println(queue.dequeue());
    }
}

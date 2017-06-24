import java.util.Iterator;

/**
 * Created by Alex on 24.06.17.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node first = null;
    private Node last = null;
    private int size;

    public Deque() {
        // construct an empty deque
        size = 0;
    }

    public static void main(String[] args) {
        // unit testing (optional)
        Deque<String> deque = new Deque<String>();
        deque.addFirst("test1");
        deque.addLast("test2 ");
        System.out.println(deque.size());
        for (String i : deque) {
            System.out.println(i);
        }
    }

    public boolean isEmpty() {
        // is the deque empty?
        return first == null && last == null;
    }

    public int size() {
        // return the number of items on the deque
        return size;
    }

    private void validate(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException("Can not add null element!");
        }
    }

    public void addFirst(Item item) {
        // add the item to the front
        validate(item);
        Node newNode = new Node(item);
        newNode.next = first;
        if (first == null) {
            last = newNode;
        } else {
            first.previous = newNode;
        }
        first = newNode;
        size++;
    }

    public void addLast(Item item) {
        // add the item to the end
        validate(item);
        Node newNode = new Node(item);
        newNode.previous = last;
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    public Item removeFirst() {
        // remove and return the item from the front
        if (first == null) {
            throw new java.util.NoSuchElementException("Deque is empty!");
        }
        Node n = first;
        first = n.next;
        n.next = null;
        if (first == null) {
            last = null;
        } else {
            first.previous = null;
        }
        size--;
        return n.item;
    }

    public Item removeLast() {
        // remove and return the item from the end
        if (last == null) {
            throw new java.util.NoSuchElementException("Deque is empty!");
        }
        Node n = last;
        last = n.previous;
        n.previous = null;
        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }
        size--;
        return n.item;
    }

    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return new DequeIterator();
    }

    private class Node {
        Item item;
        Node next;
        Node previous;

        public Node(Item item) {
            this.item = item;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (current == null) {
                throw new java.util.NoSuchElementException("No more elements in deque!");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException("Remove unsupported in iterator!");
        }
    }
}

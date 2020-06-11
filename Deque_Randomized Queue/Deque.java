import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int n;

    // helper linked list class
    private class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    // construct an empty deque
    public Deque() {
        first = last = null;
        n = 0;
        assert check();
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null || last == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Item null");
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        first.prev = null;
        n++;
        if (null == oldfirst) last = first;
        else
            oldfirst.prev = first;

        assert check();

    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Item null");
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        n++;
        if (null == oldlast) first = last;
        else
            oldlast.next = last;
        assert check();
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) last = null;
        else
            first.prev = null;
        assert check();
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = last.item;
        last = last.prev;
        n--;
        if (isEmpty()) first = null;
        else
            last.next = null;
        assert check();
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> dequeue = new Deque<String>();
        StdOut.println("(" + dequeue.size() + " left on queue)");
        StdOut.println("isEmpty: " + dequeue.isEmpty());

        dequeue.addFirst("1");
        StdOut.println("(" + dequeue.size() + " left on queue)");
        StdOut.println("isEmpty: " + dequeue.isEmpty());
        StdOut.print(dequeue.removeFirst() + " ");
        StdOut.println("(" + dequeue.size() + " left on queue)");
        StdOut.println("isEmpty: " + dequeue.isEmpty());
        dequeue.addFirst("1");
        StdOut.println("(" + dequeue.size() + " left on queue)");
        StdOut.println("isEmpty: " + dequeue.isEmpty());
        StdOut.print(dequeue.removeLast() + " ");
        StdOut.println("(" + dequeue.size() + " left on queue)");
        StdOut.println("isEmpty: " + dequeue.isEmpty());
        dequeue.addFirst("1");
        dequeue.addFirst("1");
        StdOut.print(dequeue.removeFirst() + " ");
        StdOut.print(dequeue.removeLast() + " ");
        dequeue.addFirst("1");
        dequeue.addFirst("1");
        StdOut.print(dequeue.removeLast() + " ");
        StdOut.print(dequeue.removeFirst() + " ");


        dequeue.addLast("1");
        StdOut.println("(" + dequeue.size() + " left on queue)");
        StdOut.println("isEmpty: " + dequeue.isEmpty());
        StdOut.print(dequeue.removeFirst() + " ");
        StdOut.println("(" + dequeue.size() + " left on queue)");
        StdOut.println("isEmpty: " + dequeue.isEmpty());
        dequeue.addLast("1");
        StdOut.println("(" + dequeue.size() + " left on queue)");
        StdOut.println("isEmpty: " + dequeue.isEmpty());
        StdOut.print(dequeue.removeLast() + " ");
        StdOut.println("(" + dequeue.size() + " left on queue)");
        StdOut.println("isEmpty: " + dequeue.isEmpty());
        dequeue.addLast("1");
        dequeue.addLast("1");
        StdOut.print(dequeue.removeFirst() + " ");
        StdOut.print(dequeue.removeLast() + " ");
        dequeue.addLast("1");
        dequeue.addLast("1");
        StdOut.print(dequeue.removeLast() + " ");
        StdOut.print(dequeue.removeFirst() + " ");


        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                dequeue.addFirst(item);
                dequeue.addLast(item);
            } else if (!dequeue.isEmpty()) {
                StdOut.print(dequeue.removeFirst() + " ");
                StdOut.print(dequeue.removeLast() + " ");
            }
        }
        StdOut.println("(" + dequeue.size() + " left on queue)");
        if (dequeue.size() < 3 || dequeue.isEmpty()) {
            dequeue.addLast("-");
            dequeue.addFirst("-");
            dequeue.addLast("-");
        }
        StdOut.print(dequeue.removeFirst() + " ");
        StdOut.print(dequeue.removeLast() + " ");
        Iterator<String> iterator = dequeue.iterator();
        while (iterator.hasNext())
            System.out.print(iterator.next() + " ");
    }

    // check internal invariants
    private boolean check() {
        if (n < 0) {
            return false;
        } else if (n == 0) {
            if (first != null) return false;
            if (last != null) return false;
        } else if (n == 1) {
            if (first == null || last == null) return false;
            if (first != last) return false;
            if (first.next != null) return false;
        } else {
            if (first == null || last == null) return false;
            if (first == last) return false;
            if (first.next == null) return false;
            if (last.next != null) return false;

            // check internal consistency of instance variable n
            int numberOfNodes = 0;
            for (Node<Item> x = first; x != null && numberOfNodes <= n; x = x.next) {
                numberOfNodes++;
            }
            if (numberOfNodes != n) return false;

            // check internal consistency of instance variable last
            Node<Item> lastNode = first;
            while (lastNode.next != null) {
                lastNode = lastNode.next;
            }
            if (last != lastNode) return false;
            // check internal consistency of instance variable first
            Node<Item> firstNode = last;
            while (firstNode.prev != null) {
                firstNode = firstNode.prev;
            }
            if (first != firstNode) return false;
        }

        return true;
    }
}

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int n;
    private int capa;
    private int shuffle_cnt = 0;


    // construct an empty randomized queue
    public RandomizedQueue() {
        arr = (Item[]) new Object[1];
        n = 0;
        capa = 1;

    }

    /*
        private RandomizedQueue(int size) {
            arr = (Item[]) new Object[size];
            n = 0;
            capa = size;

        }
    */
    private void resize(int size) {
        Item[] copy = (Item[]) new Object[size];
        for (int i = 0; i < size(); i++)
            copy[i] = arr[i];
        arr = copy;
        capa = size;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Item null");
        if (size() == capa) resize(2 * capa);
        arr[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");

        int int_random = StdRandom.uniform(size());

        Item randitem = arr[int_random];
        arr[int_random] = arr[--n];
        arr[n] = null;
        if (size() > 0 && size() == capa / 4) resize(capa / 4);
        return randitem;
    }

    /*
        public Item dequeue(int idx) {
            return arr[idx];
        }

        public void replace(int idx, Item item) {
            if (isEmpty()) throw new NoSuchElementException("Queue underflow");
            arr[idx] = item;
        }
    */
    private void shuffle() {
        shuffle_cnt = 0;
        //StdOut.printf("\nSize:%d \n", size());
        for (int i = size() - 1; i > 0; i--) {
            int j = StdRandom.uniform(i);

            Item t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
            //if (arr[i] == null || arr[j] == null || t == null)
            //  StdOut.printf("[%d] %s [%d] %s %s,", j, arr[j], i, arr[i], t);
        }

    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        if (shuffle_cnt >= size()) shuffle();
        return arr[shuffle_cnt++];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new LinkedIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class LinkedIterator implements Iterator<Item> {
        private int current = 0;
        final private int array_max_idx = size();
        private int[] array = new int[array_max_idx];
        //Array<Node<Item>> int_Array = new Array(Node<Item>.class, array_max_idx);

        LinkedIterator() {
            //if (array == null) StdOut.printf("New failed");
            //Node<Item> p = first;
            int idx = array_max_idx;
            while (idx-- > 0) {
                array[idx] = idx;
            }
            StdRandom.shuffle(array);
        }

        public boolean hasNext() {
            return current < size();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            //if (int_random >= size()) StdOut.printf("Idx Errr: %d ", int_random);
            //array[int_random] = 0;

            return arr[array[current++]];
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rqueue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                rqueue.enqueue(item);
            } else if (!rqueue.isEmpty()) {
                StdOut.print(rqueue.dequeue() + " ");
            }
        }
        StdOut.println("(" + rqueue.size() + " left on queue)");
        rqueue.enqueue("item");

        StdOut.print(rqueue.sample() + " ");
        StdOut.print(rqueue.dequeue() + " ");
        StdOut.print(rqueue.isEmpty() + " ");
        rqueue.enqueue("item1");
        rqueue.enqueue("item2");
        int i = rqueue.size();
        StdOut.print("\n Sample (" + rqueue.size() + " left on queue)" + ": ");
        while (i > 0) {
            i--;
            StdOut.print(rqueue.sample() + " ,");
        }

        StdOut.print("\n Iterator (" + rqueue.size() + " left on queue)" + ": ");
        Iterator<String> iterator = rqueue.iterator();
        while (iterator.hasNext())
            System.out.print(iterator.next() + " ");
        StdOut.print("\n Sample (" + rqueue.size() + " left on queue)" + ": ");
        i = rqueue.size();
        while (i > 0) {
            i--;
            StdOut.print(rqueue.sample() + " ,");
        }
        StdOut.print("\n Iterator (" + rqueue.size() + " left on queue)" + ": ");
        Iterator<String> iterator2 = rqueue.iterator();
        while (iterator2.hasNext())
            System.out.print(iterator2.next() + " ");
        StdOut.println("\n (" + rqueue.size() + " left on queue)");

    }


}


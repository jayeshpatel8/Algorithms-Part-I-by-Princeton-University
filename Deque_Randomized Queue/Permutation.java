import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k;
        try {
            k = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            k = 0;
        }

        RandomizedQueue<String> rqueue = new RandomizedQueue<String>();
        //Deque<String> q;
        //String[] arr = new String[k];

        int i = 0;
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (k > 0) {
                if (i < k)
                    rqueue.enqueue(s);
                else {
                    int j = StdRandom.uniform(i);

                    if (j < k) {
                        rqueue.dequeue();
                        rqueue.enqueue(s);
                    }
                }
                i++;
            }
        }
        while (k-- > 0 && rqueue.isEmpty() == false) {
            StdOut.printf("%s\n", rqueue.dequeue());
        }
    }
}

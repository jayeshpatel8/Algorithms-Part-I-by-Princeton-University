import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    final private int trials;
    final private int size;
    final private double[] openSite;
    private double m = 0.0, s = 0.0;

    // perform independent trials on an n-by-n grid

    public PercolationStats(int n, int trials) {
        if (n <= 0) throw new IllegalArgumentException(" n <=0 ");
        if (trials <= 0) throw new IllegalArgumentException(" trials <=0 ");
        this.trials = trials;
        size = n;
        openSite = new double[trials];
        while (trials-- != 0) {
            Percolation p = new Percolation(n);

            while (p.percolates() == false) {
                int row = StdRandom.uniform(n) + 1, col = StdRandom.uniform(n) + 1;
                while (p.isOpen(row, col)) {
                    row = StdRandom.uniform(n) + 1;
                    col = StdRandom.uniform(n) + 1;
                }
                p.open(row, col);
            }
            //StdOut.printf(" [numberOfOpenSites() : %d \n", p.numberOfOpenSites());
            openSite[trials] = (double) p.numberOfOpenSites() / (size * size);
            //StdOut.print(openSite[trials]);
            p = null;
        }
    }


/*
    public PercolationStats(int n, int trials) {
        if (n <= 0) throw new IllegalArgumentException(" n <=0 ");
        if (trials <= 0) throw new IllegalArgumentException(" trials <=0 ");
        n = 3;
        trials = 400;
        this.trials = trials;
        size = n;
        openSite = new double[trials];
        while (trials-- != 0) {
            Percolation p = new Percolation(n);
            int i = 0;
            while (i < 7) {
                int row = StdRandom.uniform(n) + 1, col = StdRandom.uniform(n) + 1;
                while (p.isOpen(row, col)) {
                    row = StdRandom.uniform(n) + 1;
                    col = StdRandom.uniform(n) + 1;
                }

                p.open(row, col);
                i++;

            }
            if (p.percolates() == false) {
                StdOut.printf("Failed at trial %d %d %d\n", trials, i, n);
                for (int j = 1; j <= size; j++) {
                    for (int k = 1; k <= size; k++) {
                        StdOut.printf("%s ", p.isOpen(j, k));
                    }
                    StdOut.printf("\n");
                }
                p.pt();
                StdOut.printf("p.percolates() : %s \n", p.percolates());
            }

            //StdOut.printf(" [numberOfOpenSites() : %d \n", p.numberOfOpenSites());
            openSite[trials] = (double) p.numberOfOpenSites() / (size * size);
            //StdOut.print(openSite[trials]);
            p = null;
        }
    }

 */

    // sample mean of percolation threshold
    public double mean() {
        if (m == 0.0)
            m = StdStats.mean(openSite);
        return m;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (s == 0.0) {
            double[] std = new double[trials];
            for (int i = 0; i < trials; i++) {
                std[i] = (openSite[i] - m);//* (openSite[i] - m);
            }

            s = StdStats.stddev(std);
        }
        return s;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(trials));

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats p = new PercolationStats(n, T);
        StdOut.printf("mean                    = %f\n", p.mean());
        StdOut.printf("stddev                  = %f\n", p.stddev());
        double l = p.confidenceLo();
        double h = p.confidenceHi();
        StdOut.printf("95%% confidence interval = [%f, %f]\n", l, h);
    }

}

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    //Performance requirements.  The constructor must take time proportional to n2;
// all instance methods must take constant time plus a constant number of calls to union() and find().
    // creates n-by-n grid, with all sites initially blocked
    private byte[][] site;
    private int noOfOpenSite;
    private WeightedQuickUnionUF uf;
    final private int size;
    private boolean percolateflag;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException(" n <=0 ");
        // create n x n site , all are blocked initiall
        site = new byte[n][n];
        uf = new WeightedQuickUnionUF(n * n);
        size = n;
        percolateflag = false;
        noOfOpenSite = 0;
    }

    private boolean isvalid(int row, int col) {
        return (row >= 1 && row <= size && col >= 1 && col <= size);
    }

    private boolean getSite(int row, int col) {
        return ((site[row - 1][col - 1] & 0x1) == 0x1);
    }

    private void openSite(int row, int col) {
        site[row - 1][col - 1] |= 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isvalid(row, col)) {
            if (getSite(row, col) == false) {
                int csite = (row - 1) * size + col - 1;
                byte status = 1;
                if (row == 1) {
                    status |= 0x2;
                } else if (row == size) {
                    status |= 0x4;
                }

                if (isvalid(row - 1, col) && getSite(row - 1, col)) {
                    int root = uf.find((row - 2) * size + col - 1);
                    int r = root / size, c = root % size;
                    status |= site[r][c];
                    uf.union(root, csite);
                }
                if (isvalid(row + 1, col) && getSite(row + 1, col)) {
                    int root = uf.find((row) * size + col - 1);
                    int r = root / size, c = root % size;
                    status |= site[r][c];
                    uf.union(root, csite);
                }
                if (isvalid(row, col - 1) && getSite(row, col - 1)) {
                    int root = uf.find((row - 1) * size + col - 2);
                    int r = root / size, c = root % size;
                    status |= site[r][c];
                    uf.union(root, csite);
                }
                if (isvalid(row, col + 1) && getSite(row, col + 1)) {
                    int root = uf.find((row - 1) * size + col);
                    int r = root / size, c = root % size;
                    status |= site[r][c];
                    uf.union(root, csite);
                }
                int root = uf.find(csite);
                int r = root / size, c = root % size;
                site[r][c] |= status;
                site[row - 1][col - 1] |= site[r][c];
                if ((site[r][c] & 0x7) == 0x7) percolateflag = true;
                //StdOut.printf("[%d,%d]= %d\n", r + 1, c + 1, site[r][c]);
                noOfOpenSite++;
            }

        } else
            throw new IllegalArgumentException(" outside its prescribed range ");
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (isvalid(row, col)) {
            return getSite(row, col);
        } else
            throw new IllegalArgumentException(" outside its prescribed range ");
    }

    private boolean isMyOpen(int row, int col) {
        if (isvalid(row, col)) {
            return getSite(row, col);
        }
        return false;

    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (isOpen(row, col)) {
            int csite = (row - 1) * size + col - 1;
            int root = uf.find(csite);
            int r = root / size, c = root % size;
            return ((site[r][c] & 0x3) == 0x3);
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return noOfOpenSite;
    }

    public boolean percolates() {
        if (size == 1) return noOfOpenSite == 1;
        return percolateflag;
    }

/*
    public void test(int i, int j) {
        //int i = 1, j = 1;
        boolean t;
        open(i, j);
        t = percolates();
        StdOut.printf(" [%d %d] isFull: %s ,NOS: %d, P: %s \n", i, j, isFull(i, j), numberOfOpenSites(), t);
    }

 */

    // test client (optional)
    public static void main(String[] args) {
/*
        Percolation p = new Percolation(1);
        p.test(1, 1);


         p = new Percolation(3);
        assert (p.isFull(1, 1) == false);
        //p.test(1, 1);
        //p.test(1, 3);
        p.test(3, 1);
        //p.test(2, 1);
        p.test(3, 2);
        p.test(3, 3);
        p.test(2, 2);
        p.test(2, 3);
        p.test(1, 3);
        p.test(1, 1);

 */
/*
        int trials = 200000;
        int n = 3;
        int row = 1, col = 1;
        while (trials-- >= 0) {

            Percolation p = new Percolation(n);
            int i = 0;
            while (i < 4) {
                row = StdRandom.uniform(n) + 1;
                col = StdRandom.uniform(n) + 1;
                while (row == 1 || p.isOpen(row, col) /*|| p.isNOpen(row, col)*//*) {
                    row = StdRandom.uniform(n) + 1;
                    col = StdRandom.uniform(n) + 1;

                }

                p.open(row, col);
                i++;
                //StdOut.printf("trails: %d, i:%d > T\n", i, trials);
            }
            //StdOut.printf("trails: %d > T\n", trials);
            // check result
            if (p.percolates() || p.isFull(2, 1)) {
                StdOut.printf("Failed at trial %d %d %d\n", trials, i, n);
                for (int j = 1; j <= n; j++) {
                    for (int k = 1; k <= n; k++) {
                        StdOut.printf("%s ", p.isOpen(j, k));
                    }
                    StdOut.printf("\n");
                }
                p.pt();
                StdOut.printf("percolates() : %s ,isFull(%d , %d): %s\n", p.percolates(), 2, 1, p.isFull(2, 1));
            }
            p = null;
        }
        */
    }

}

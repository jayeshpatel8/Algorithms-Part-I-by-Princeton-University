import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private final int n;
    private final int[][] tile;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        tile = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tile[i][j] = tiles[i][j];
    }

    // string representation of this board
    public String toString() {
        String str = "";
        str = Integer.toString(dimension()) + '\n';
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                str += " " + tile[i][j];
            }
            str += "\n";
        }
        return str;
    }


    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int ham = 0, cnt = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tile[i][j] != cnt++)
                    ham++;
            }
        }
        // if (tile[n - 1][n - 1] == 0)
        ham--;
        return ham;
    }


    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int man = 0, cnt = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tile[i][j] != cnt) {
                    if (tile[i][j] != 0) {
                        int r = (tile[i][j] - 1) / n;
                        if (i > r)
                            man += (i - r);
                        else
                            man += (r - i);
                        int c = (tile[i][j] - 1) % n;
                        if (j > c)
                            man += ((j - c));
                        else
                            man += c - j;
                        //StdOut.printf("[%d]:%d ", tile[i][j], man);
                    }
                }
                cnt++;
            }
        }
        return man;
    }


    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (!(y instanceof Board)) {
            return false;
        }
        if (this.getClass() != y.getClass()) return false;
        Board b = (Board) y;
        if (b.dimension() != n) return false;
        return (Arrays.deepEquals(this.tile, b.tile));
    }


    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> a = new ArrayList<>();
        int i = 0, j = 0;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (tile[i][j] == 0) {
                    int k = i - 1;
                    if (k >= 0) {
                        int t = tile[i][j];
                        tile[i][j] = tile[k][j];
                        tile[k][j] = t;
                        a.add(new Board(tile));
                        tile[k][j] = tile[i][j];
                        tile[i][j] = t;
                    }
                    k = j - 1;
                    if (k >= 0) {
                        int t = tile[i][j];
                        tile[i][j] = tile[i][k];
                        tile[i][k] = t;
                        a.add(new Board(tile));
                        tile[i][k] = tile[i][j];
                        tile[i][j] = t;
                    }
                    k = i + 1;
                    if (k < n) {
                        int t = tile[i][j];
                        tile[i][j] = tile[k][j];
                        tile[k][j] = t;
                        a.add(new Board(tile));
                        tile[k][j] = tile[i][j];
                        tile[i][j] = t;
                    }
                    k = j + 1;
                    if (k < n) {
                        int t = tile[i][j];
                        tile[i][j] = tile[i][k];
                        tile[i][k] = t;
                        a.add(new Board(tile));
                        tile[i][k] = tile[i][j];
                        tile[i][j] = t;
                    }
                    return a;

                }
            }
        }
        return a;
    }

    private int[][] copy(int[][] blocks) {
        int[][] copy = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                copy[i][j] = blocks[i][j];
            }
        }
        return copy;
    }

    private int[][] swap(int i1, int j1, int i2, int j2) {
        int[][] copy = copy(tile);
        int temp = copy[i1][j1];
        copy[i1][j1] = copy[i2][j2];
        copy[i2][j2] = temp;
        return copy;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinTiles = copy(this.tile);

        if (twinTiles[0][0] != 0 && twinTiles[0][1] != 0)
            return new Board(swap(0, 0, 0, 1));
        else
            return new Board(swap(1, 0, 1, 1));
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] foo = new int[][]{
                new int[]{8, 1, 3},
                new int[]{4, 0, 2},
                new int[]{7, 6, 5},
        };
        int[][] foo2 = new int[][]{
                new int[]{1, 2, 3},
                new int[]{4, 5, 6},
                new int[]{7, 8, 0},
        };
        Board b = new Board(foo);
        StdOut.println(b.toString());
        StdOut.printf("dimension = %d \n", b.dimension());
        StdOut.printf("hamming = %d \n", b.hamming());
        StdOut.printf("manhattan = %d \n", b.manhattan());
        StdOut.printf("isGoal = %s \n", b.isGoal());
        for (Board n : b.neighbors())
            StdOut.println(n.toString());

        Board b2 = new Board(foo2);
        StdOut.printf("equals = %s \n", b.equals(b2));
        StdOut.println(b2.toString());
        StdOut.printf("dimension = %d \n", b2.dimension());
        StdOut.printf("hamming = %d \n", b2.hamming());
        StdOut.printf("manhattan = %d \n", b2.manhattan());
        StdOut.printf("isGoal = %s \n", b2.isGoal());
        StdOut.printf("equals = %s \n", b2.equals(b2));
        StdOut.printf("neighbors : \n");
        for (Board n : b2.neighbors())
            StdOut.println(n.toString());

        StdOut.printf("twin : \n%s \n", b2.twin().toString());
    }

}

/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        double ret;
        if (x == that.x) {
            if (y == that.y) {
                ret = Double.NEGATIVE_INFINITY;
            } else
                ret = Double.POSITIVE_INFINITY;
        } else if (y == that.y) {
            ret = 0;
        } else
            ret = (((double) that.y - y) / (that.x - x));
        // StdOut.printf("-SlopeTo-: (%d,%d) - (%d,%d) : %f \n", x, y, that.x, that.y, ret);
        return ret;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        int result;
        if (y > that.y) {
            result = 1;
        } else if (y == that.y) {
            if (x > that.x) {
                result = 1;
            } else if (x < that.x) {
                result = -1;
            } else result = 0;

        } else {
            result = -1;
        }
        //StdOut.printf(" CompareTo res== %d, %s -> %s\n", result, toString(), that.toString());
        return result;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point ap, Point bp) {
            double a = slopeTo(ap);
            double b = slopeTo(bp);
            //StdOut.printf("SlopeOrder.compare() : %f %f -> ", a, b);
            if (a < b) return -1;
            if (a > b) return 1;
            return 0;
        }
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    private static void checkslop(int x1, int y1, int x2, int y2, int x3, int y3) {
        Point p1 = new Point(x1, y1), p2 = new Point(x2, y2), p3 = new Point(x3, y3);

        StdOut.printf("SlopTo: (%d,%d)->(%d,%d) = %f ,", x1, y1, x2, y2, p1.slopeTo(p2));
        StdOut.printf("SlopTo: (%d,%d)->(%d,%d) = %f \n", x1, y1, x3, y3, p1.slopeTo(p3));
        StdOut.printf("checkslop: (%d,%d)->(%d,%d)-(%d,%d) = %d \n", x1, y1, x2, y2, x3, y3, (p1.slopeOrder()).compare(p2, p3));
        StdOut.printf("checkslop: (%d,%d)->(%d,%d)-(%d,%d) = %d \n", x1, y1, x3, y3, x2, y2, (p1.slopeOrder()).compare(p3, p2));
        StdOut.printf("checkslop: (%d,%d)->(%d,%d)-(%d,%d) = %d \n", x1, y1, x2, y2, x3, y3, (p1.slopeOrder()).compare(p2, p3));
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        //int x = 10, y = 10;

        Point p1 = new Point(10, 10);

        Point p2 = new Point(10, 9);

        Point p3 = new Point(9, 9);
        Point p4 = new Point(11, 9);

        StdOut.printf("Cmp : %d %f\n", p1.compareTo(p2), p1.slopeTo(p2));
        assert (p1.compareTo(p2) == 1);
        StdOut.printf("Cmp : %d %f \n", p1.compareTo(p3), p1.slopeTo(p3));
        assert (p1.compareTo(p3) == 1);
        StdOut.printf("Cmp : %d \n", p1.compareTo(p4));
        assert (p1.compareTo(p4) == 1);

        Point p5 = new Point(10, 10);
        StdOut.printf("Cmp : %d %f\n", p1.compareTo(p5), p1.slopeTo(p5));
        assert (p1.compareTo(p5) == 0);
        Point p6 = new Point(11, 11);
        StdOut.printf("Cmp : %d %f\n", p1.compareTo(p6), p1.slopeTo(p6));
        assert (p1.compareTo(p6) == -1);

        Point p7 = new Point(10, 90);
        StdOut.printf("Cmp : %d %f\n", p1.compareTo(p7), p1.slopeTo(p7));
        Point p8 = new Point(20, 10);
        StdOut.printf("Cmp : %d %f\n", p1.compareTo(p8), p1.slopeTo(p8));
        Point p9 = new Point(10, 9);
        StdOut.printf("Cmp : %d %f\n", p1.compareTo(p9), p1.slopeTo(p9));
        Point p10 = new Point(9, 10);
        StdOut.printf("Cmp : %d %f\n", p1.compareTo(p10), p1.slopeTo(p10));
        Point p11 = new Point(25, 35);
        StdOut.printf("Cmp : %d %f\n", p1.compareTo(p11), p1.slopeTo(p11));
        Point p12 = new Point(3, 4);
        StdOut.printf("Cmp : %d %f\n", p1.compareTo(p12), p1.slopeTo(p12));

        checkslop(10, 10, 20, 20, 30, 30);
        checkslop(10, 10, 20, 19, 30, 30);
        checkslop(10, 10, 19, 20, 30, 30);
        checkslop(10, 10, 10, 20, -30, 30);
        checkslop(10, 10, 20, 20, 10, 30);
        checkslop(10, 10, 10, -20, -30, 30);
        checkslop(10, 10, 20, 20, 10, -30);
        Point p13 = new Point(0, 0);
        Point q13 = new Point(0, 0);
        StdOut.printf("%s Cmp %s : %d %d\n", p13.toString(), q13.toString(), p13.compareTo(q13), q13.compareTo(p13));
        checkslop(218, 155, 365, 317, 424, 99);
        checkslop(0, 1, 26, 344, 180, 16);
    }
}

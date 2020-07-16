import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
    private SET<Point2D> set;


    public PointSET()                               // construct an empty set of points
    {
        set = new SET<Point2D>();

    }

    public boolean isEmpty()                      // is the set empty?
    {
        return set.isEmpty();
    }

    public int size()                         // number of points in the set
    {
        return set.size();
    }

    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (p == null) throw new IllegalArgumentException();
        if (set.contains(p)) return;
        set.add(p);
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        if (p == null) throw new IllegalArgumentException();
        return set.contains(p);
    }

    public void draw()                         // draw all points to standard draw
    {
        for (Point2D point : set)
            point.draw();
    }

    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> q = new Queue<>();
        for (Point2D point : set)
            if (rect.contains(point))
                q.enqueue(point);
        return q;
    }

    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (p == null) throw new IllegalArgumentException();
        if (set.isEmpty() == true) return null;
        Point2D smallest = null;
        double d = 1;
        for (Point2D point : set)
            if (smallest == null) {
                smallest = point;
                d = point.distanceSquaredTo(p);
            } else {
                if (d > point.distanceSquaredTo(p)) {
                    d = point.distanceSquaredTo(p);
                    smallest = point;
                }
            }
        return smallest;
    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {
        /*
        PointSET ps = new PointSET();
        StdOut.printf("isEmpty: %s \n", ps.isEmpty());
        StdOut.printf("size: %d \n", ps.size());

        Point2D p = new Point2D(0.1, 0.1);
        ps.insert(p);
        Point2D near = ps.nearest(new Point2D(0.04, 0.04));
        if (near != null)
            StdOut.println(near.toString());
        StdOut.printf("contains: %s \n", ps.contains(p));
        double h = 0.01, v = 0.01;
        for (int i = 0; i < 20; i++) {

            ps.insert(new Point2D(h, v));
            h += 0.05;
            v += 0.05;
        }

        Point2D co = new Point2D(0.1351, 0.1351);
        near = ps.nearest(co);
        if (near != null)
            StdOut.println("Near : " + near.toString());
        StdOut.printf("contains Near: %s \n", ps.contains(co));
        ps.draw();
        StdOut.printf("isEmpty: %s \n", ps.isEmpty());
        StdOut.printf("size: %d \n", ps.size());
        RectHV r = new RectHV(0.01, 0.01, 0.9, 0.9);
        r.draw();
        Iterable<Point2D> it = ps.range(r);
        for (Point2D point : it) {
            Point2D np = new Point2D(point.x(), point.y() + 0.02);
            np.draw();
        }

         */

    }
}

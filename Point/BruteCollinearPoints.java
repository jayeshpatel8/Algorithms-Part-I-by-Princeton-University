import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;

public class BruteCollinearPoints {
    private int numberOfSegments = 0;
    private LineSegment[] line = null;

    private boolean validateNullPoint(Point[] p) {
        for (int i = 0; i < p.length; i++) {
            if (p[i] == null)
                return true;
        }
        return false;
    }

    private boolean CheckDuplicatePoint(Point[] p) {
        for (int i = 0; i < p.length; i++) {
            for (int j = i + 1; j < p.length; j++) {
                if (p[i].compareTo(p[j]) == 0) return true;
            }
        }
        return false;
    }

    public BruteCollinearPoints(Point[] pointsCopy)    // finds all line segments containing 4 points
    {

        if (pointsCopy == null || validateNullPoint(pointsCopy) || CheckDuplicatePoint(pointsCopy))
            throw new IllegalArgumentException(" BruteCollinearPoints Const Arg Null");
        //Throw an IllegalArgumentException :
        //  if the argument to the constructor is null,
        //  if any point in the array is null,
        //  or if the argument to the constructor contains a repeated point.
        //ArrayList<LineSegment> segmentsList = new ArrayList<LineSegment>();
        Stack<LineSegment> segmentstack = new Stack<>();
        Point[] points = Arrays.copyOf(pointsCopy, pointsCopy.length);
        Arrays.sort(points);

        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {

                for (int k = j + 1; k < points.length - 1; k++) {
                    double sl = points[i].slopeTo(points[j]);
                    double sl2 = points[i].slopeTo(points[k]);

                    if (sl != sl2) continue;

                    for (int l = k + 1; l < points.length; l++) {
                        double sl3 = points[i].slopeTo(points[l]);

                        if (sl != sl3) continue;
                        LineSegment tempLineSegment = new LineSegment(points[i], points[l]);
                        segmentstack.push(tempLineSegment);
                    }
                }
            }
        }
        //line = segmentsList.toArray(new LineSegment[segmentsList.size()]);
        int size = segmentstack.size();
        line = new LineSegment[size];
        for (int i = 0; i < size; i++) line[i] = segmentstack.pop();
    }

    public int numberOfSegments()        // the number of line segments//
    {
        return line.length;
    }

    public LineSegment[] segments()                // the line segments
    {
        return Arrays.copyOf(line, numberOfSegments());
    }
/*
    private static Point[] addPoint(int n, Point arr[], Point x) {
        int i;

        // create a new array of size n+1
        Point newarr[] = new Point[n + 1];

        if (arr != null) {
            for (i = 0; i < n; i++)
                newarr[i] = arr[i];
        }
        newarr[n] = x;

        return newarr;
    }

    public static void main(String[] args) {
        Point[] p = null;
        int i = 0;
        p = addPoint(i++, p, new Point(10, 10));
        p = addPoint(i++, p, new Point(20, 20));
        p = addPoint(i++, p, new Point(30, 30));
        p = addPoint(i++, p, new Point(40, 40));
        p = addPoint(i++, p, new Point(11, 20));
        BruteCollinearPoints b = new BruteCollinearPoints(p);
        assert (b.numberOfSegments() == 1);
        StdOut.printf(" numberOfSegments : %d %s\n", b.numberOfSegments(), (b.segments())[0].toString());
        p = null;
        b = null;
        i = 0;
        p = addPoint(i++, p, new Point(10, 10));
        p = addPoint(i++, p, new Point(20, 30));
        p = addPoint(i++, p, new Point(30, 40));
        p = addPoint(i++, p, new Point(40, 50));
        p = addPoint(i++, p, new Point(11, 60));
        b = new BruteCollinearPoints(p);
        assert (b.numberOfSegments() == 0);
        assert (b.segments() == null);
        //StdOut.printf(" numberOfSegments : %d %s\n", b.numberOfSegments(), if(b.segments())[0].toString());
        p = null;
        b = null;
        i = 0;
        p = addPoint(i++, p, new Point(10, 10));
        p = addPoint(i++, p, new Point(20, 30));
        p = addPoint(i++, p, new Point(30, 40));
        p = addPoint(i++, p, new Point(30, 30));
        p = addPoint(i++, p, new Point(11, 60));
        b = new BruteCollinearPoints(p);
        assert (b.numberOfSegments() == 0);
        assert (b.segments() == null);

        p = null;
        b = null;
        i = 0;
        p = addPoint(i++, p, new Point(10, 10));
        p = addPoint(i++, p, new Point(20, 20));
        p = addPoint(i++, p, new Point(30, 30));
        p = addPoint(i++, p, new Point(40, 40));
        p = addPoint(i++, p, new Point(25, 35));
        p = addPoint(i++, p, new Point(5, 55));
        p = addPoint(i++, p, new Point(-5, 65));
        //p = addPoint(i++, p, new Point(65, 75));
        b = new BruteCollinearPoints(p);
        assert (b.numberOfSegments() == 2);
        StdOut.printf(" numberOfSegments : %d ", b.numberOfSegments());
        for (i = 0; i < b.numberOfSegments(); i++)
            StdOut.printf(" Segments[%d] %s ,", i, (b.segments())[i].toString());
    }

 */
}

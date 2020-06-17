import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] lines = null;

    public FastCollinearPoints(Point[] pointsCopy)     // finds all line segments containing 4 or more points
    {
        if (pointsCopy == null || validateNullPoint(pointsCopy) || CheckDuplicatePoint(pointsCopy))
            throw new IllegalArgumentException(" BruteCollinearPoints Const Arg Null");
        Stack<LineSegment> segmentstack = new Stack<>();
        int len = pointsCopy.length;
        Point[] points = Arrays.copyOf(pointsCopy, pointsCopy.length);
        Arrays.sort(points);
        Point[] clonePoints = points.clone();
        for (int i = 0; i < len; i++) {
            Arrays.sort(clonePoints);
            Arrays.sort(clonePoints, points[i].slopeOrder());

            for (int j = 1; j < len; ) {
                double slop = points[i].slopeTo(clonePoints[j]);
                int start = j, dup = 1;
                while (++j < len) {
                    if (points[i].slopeTo(clonePoints[j]) == slop) {
                        dup++;
                    } else
                        break;
                }
                if ((dup >= 3) && (clonePoints[start].compareTo(points[i]) > 0)) {
                    //segmentsList.add(new LineSegment(points[i], clonePoints[j - 1]));
                    LineSegment t = new LineSegment(points[i], clonePoints[j - 1]);
                    segmentstack.push(t);
                    // StdOut.printf("dup=%d,  %s : %s - %s \n", dup, clonePoints[start].toString(), points[i].toString(), clonePoints[j - 1].toString());
                }
            }
        }
        //lines = segmentsList.toArray(new LineSegment[segmentsList.size()]);
        int size = segmentstack.size();
        lines = new LineSegment[size];
        for (int i = 0; i < size; i++) lines[i] = segmentstack.pop();
    }

    private boolean validateNullPoint(Point[] p) {
        for (int i = 0; i < p.length; i++) {
            if (p[i] == null)
                return true;
        }
        return false;
    }

    public int numberOfSegments()        // the number of line segments
    {
        return lines.length;
    }

    public LineSegment[] segments()                // the line segments
    {
        return Arrays.copyOf(lines, numberOfSegments());
    }

    private boolean CheckDuplicatePoint(Point[] p) {
        for (int i = 0; i < p.length; i++) {
            for (int j = i + 1; j < p.length; j++) {
                if (p[i].compareTo(p[j]) == 0) return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        // read the n points from a file
        StdOut.println(args[0]);
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        //BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        StdOut.printf(" numberOfSegments : %d \n", collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

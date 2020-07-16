import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class KdTree {

    private Node root;

    private int size = 0;

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) {
            root = Node.create(p, 0, null);
            size++;
        } else if (!contains(p)) {
            insert(root, p, root.rect, true);
            size++;
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return false;
        return contains(root, p, true);
    }

    public void draw() {
        draw(root, new RectHV(0, 0, 1, 1));
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        List<Point2D> result = new LinkedList<>();
        range(this.root, rect, result);
        return result;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        return nearest(p, root.point, root);
    }


    private Point2D nearest(Point2D p, Point2D NPoint, Node n) {

        if (n == null) {
            return NPoint;
        }
        if (n.point.distanceSquaredTo(p) < NPoint.distanceSquaredTo(p))
            NPoint = n.point;

        //  StdOut.printf("%s \n", n.point.toString());
        double dis;
        if (n.nodeLevel % 2 == 0) {
            if (p.x() > n.point.x()) { // check right sub-tree
                //StdOut.printf("xr ");
                NPoint = nearest(p, NPoint, n.right);

                if (n.left != null && (NPoint.distanceSquaredTo(p) > n.left.rect.distanceSquaredTo(p)))
                    NPoint = nearest(p, NPoint, n.left);

            } else { // check left sub-tree
                //StdOut.printf("xl ");
                NPoint = nearest(p, NPoint, n.left);

                if (n.right != null && (NPoint.distanceSquaredTo(p) > n.right.rect.distanceSquaredTo(p)))
                    NPoint = nearest(p, NPoint, n.right);
            }
        } else {
            if (p.y() > n.point.y()) { // check up sub-tree
                ////StdOut.printf("yr ");
                NPoint = nearest(p, NPoint, n.right);

                if (n.left != null && (NPoint.distanceSquaredTo(p) > n.left.rect.distanceSquaredTo(p)))
                    NPoint = nearest(p, NPoint, n.left);

            } else { // check left sub-tree
                //StdOut.printf("xl ");
                NPoint = nearest(p, NPoint, n.left);

                if (n.right != null && (NPoint.distanceSquaredTo(p) > n.right.rect.distanceSquaredTo(p)))
                    NPoint = nearest(p, NPoint, n.right);
            }
        }
        return NPoint;
    }

    private void range(Node n, RectHV rect, List<Point2D> acc) {
        if (n == null) {
            return;
        }

        if (rect.contains(n.point)) {
            acc.add(n.point);
        }

        if (n.nodeLevel % 2 == 0) { //Vertical segment
            // the vertical line intersects with query rectangle
            if (rect.xmin() <= n.point.x() && n.point.x() <= rect.xmax()) {
                range(n.left, rect, acc);
                range(n.right, rect, acc);
            } else if (rect.xmin() > n.point.x()) { // Search right
                range(n.right, rect, acc);
            } else { // Search left
                range(n.left, rect, acc);
            }
        } else { // Horizontal segment
            // the horizontal line intersects with query rectangle
            if (rect.ymin() <= n.point.y() && n.point.y() <= rect.ymax()) {
                range(n.left, rect, acc);
                range(n.right, rect, acc);
            } else if (rect.ymin() > n.point.y()) { // Search up
                range(n.right, rect, acc);
            } else { // Search down
                range(n.left, rect, acc);
            }
        }
    }

    private void draw(Node n, RectHV rectHV) {
        if (n == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        n.point.draw();

        StdDraw.setPenRadius(0.001);
        if (n.nodeLevel % 2 == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(n.point.x(), rectHV.ymin(), n.point.x(), rectHV.ymax());
            draw(n.left, new RectHV(rectHV.xmin(), rectHV.ymin(), n.point.x(), rectHV.ymax()));
            draw(n.right, new RectHV(n.point.x(), rectHV.ymin(), rectHV.xmax(), rectHV.ymax()));
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(rectHV.xmin(), n.point.y(), rectHV.xmax(), n.point.y());
            draw(n.left, new RectHV(rectHV.xmin(), rectHV.ymin(), rectHV.xmax(), n.point.y()));
            draw(n.right, new RectHV(rectHV.xmin(), n.point.y(), rectHV.xmax(), rectHV.ymax()));
        }
    }

    private boolean contains(Node root, Point2D p, boolean useX) {
        Comparator<Point2D> comp = useX ? Point2D.X_ORDER : Point2D.Y_ORDER;
        if (root.point.equals(p)) return true;
        if (comp.compare(root.point, p) > 0) {
            if (root.left == null) {
                return false;
            } else {
                return contains(root.left, p, !useX);
            }
        } else if (root.right == null) {
            return false;
        } else {
            return contains(root.right, p, !useX);
        }
    }


    private void insert(Node root, Point2D p, RectHV rect, boolean useX) {
        Comparator<Point2D> comp = useX ? Point2D.X_ORDER : Point2D.Y_ORDER;
        // if the point to be inserted is smaller, go left
        RectHV r;
        if (comp.compare(root.point, p) > 0) {
            if (root.left == null) {
                if (useX)
                    r = new RectHV(rect.xmin(), rect.ymin(), root.point.x(), rect.ymax());
                else
                    r = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), root.point.y());
                root.left = Node.create(p, root.nodeLevel + 1, r);
            } else {
                insert(root.left, p, root.left.rect, !useX);
            }
        } else if (root.right == null) {
            if (useX)
                r = new RectHV(root.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
            else
                r = new RectHV(rect.xmin(), root.point.y(), rect.xmax(), rect.ymax());
            root.right = Node.create(p, root.nodeLevel + 1, r);
        } else {
            insert(root.right, p, root.right.rect, !useX);
        }
    }

    /**
     * Represents a Node in a Kd tree
     */
    private static class Node {

        private final Point2D point;
        private Node left;
        private Node right;
        private final int nodeLevel;
        private RectHV rect;

        private Node(Point2D data, int nodeLevel, RectHV rect) {
            this.point = data;
            this.left = left;
            this.right = right;
            this.nodeLevel = nodeLevel;
            if (rect == null) {
                rect = new RectHV(0, 0, 1, 1);
            }
            this.rect = rect;
        }

        public static Node create(Point2D data, int nodeLevel, RectHV rect) {
            return new Node(data, nodeLevel, rect);
        }
    }
/*
    public static void main(String[] args) {

        // initialize the two data structures with point from file
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }
        //Point2D query1 = new Point2D(0.27, 0.46);
        Point2D query1 = new Point2D(0.277, 0.405);
        Point2D near = kdtree.nearest(query1);
        StdOut.printf("nearest %s \n", near.toString());
        StdOut.printf("distanceSquaredTo %f \n", query1.distanceSquaredTo(near));
        // process nearest neighbor queries
        StdDraw.enableDoubleBuffering();
        while (true) {

            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);

            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            brute.draw();

            // draw in red the nearest neighbor (using brute-force algorithm)
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            brute.nearest(query).draw();
            StdDraw.setPenRadius(0.02);

            // draw in blue the nearest neighbor (using kd-tree algorithm)
            StdDraw.setPenColor(StdDraw.BLUE);
            kdtree.nearest(query).draw();
            StdDraw.show();
            StdDraw.pause(40);
            break;
        }
    }
 */
}


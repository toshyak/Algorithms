import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;

/**
 * Created by Alex on 23.07.17.
 */
public class KdTree {
    private Node root = null;
    private int size = 0;
    private static boolean VERT = true;
    private static boolean HOR = false;

    public KdTree() {
        // construct an empty set of points
    }

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree


        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }

    public boolean isEmpty() {
        // is the set empty?
        return (root == null);
    }

    public int size() {
        // number of points in the set
        return size;
    }

    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException();
        if (contains(p)) return;
        root = put(root, p, 0, 0, 1, 1, VERT);
        size++;
    }

    private Node put(Node n, Point2D p, double xmin, double ymin, double xmax, double ymax, boolean orient) {
        if (n == null) return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
        if (orient) {
            if (n.p.x() <= p.x())
                n.rt = put(n.rt, p, n.p.x(), n.rect.ymin(), n.rect.xmax(), n.rect.ymax(), HOR);
            else n.lb = put(n.lb, p, n.rect.xmin(), n.rect.ymin(), n.p.x(), n.rect.ymax(), HOR);
        } else {
            if (n.p.y() <= p.y())
                n.rt = put(n.rt, p, n.rect.xmin(), n.p.y(), n.rect.xmax(), n.rect.ymax(), VERT);
            else n.lb = put(n.lb, p, n.rect.xmin(), n.rect.ymin(), n.rect.xmax(), n.p.y(), VERT);
        }
        return n;
    }

    public boolean contains(Point2D p) {
        // does the set contain point p?
        if (p == null) throw new IllegalArgumentException();
        return get(root, p, VERT);
    }

    private boolean get(Node n, Point2D p, boolean orient) {
        if (n == null) return false;
        if (n.p.equals(p)) return true;
        if (orient) {
            if (n.p.x() <= p.x()) return get(n.rt, p, HOR);
            else return get(n.lb, p, HOR);
        } else {
            if (n.p.y() <= p.y()) return get(n.rt, p, VERT);
            else return get(n.lb, p, VERT);
        }
    }

    public void draw() {
        // draw all points to standard draw
        draw(root, VERT);

    }

    private void draw(Node n, boolean orient) {
        if (n == null) return;
        draw(n.lb, !orient);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        n.p.draw();
        StdDraw.setPenRadius();
        if (orient) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
        }
        draw(n.rt, !orient);
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle
        if (rect == null) throw new IllegalArgumentException();
        LinkedList<Point2D> points = new LinkedList<>();
        rangeSearch(root, rect, points);
        return points;
    }

    private void rangeSearch(Node n, RectHV rect, LinkedList<Point2D> points) {
        if (n == null) return;
        if (!n.rect.intersects(rect)) return;
        if (rect.contains(n.p)) points.add(n.p);
        rangeSearch(n.lb, rect, points);
        rangeSearch(n.rt, rect, points);
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new IllegalArgumentException();
        if (size() == 0) return null;
        Node nearest = root;
        double squaredDistance = Double.MAX_VALUE;
        nearestSearch(root, p, nearest, squaredDistance, VERT);
        return nearest.p;
    }

    private void nearestSearch(Node n, Point2D p, Node nearest, double squaredDistance, boolean orient) {
        if (n == null) return;
        if (n.p.distanceSquaredTo(p) < squaredDistance) {
            nearest = n;
            squaredDistance = n.p.distanceSquaredTo(p);
        }
        if (n.rect.distanceSquaredTo(p) > squaredDistance) return;
        if ((orient && (p.x() < n.p.x())) || (!orient && (p.y() < n.p.y()))) {
            nearestSearch(n.lb, p, nearest, squaredDistance, !orient);
            nearestSearch(n.rt, p, nearest, squaredDistance, !orient);
        } else {
            nearestSearch(n.rt, p, nearest, squaredDistance, !orient);
            nearestSearch(n.lb, p, nearest, squaredDistance, !orient);
        }

    }

    public static void main(String[] args) {
        // unit testing of the methods (optional)
    }
}

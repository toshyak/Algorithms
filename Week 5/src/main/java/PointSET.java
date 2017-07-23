import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.LinkedList;
import java.util.TreeSet;

/**
 * Created by Alex on 22.07.17.
 */
public class PointSET {
    private TreeSet<Point2D> tree;

    public PointSET() {
        // construct an empty set of points
        tree = new TreeSet<>();
    }

    public boolean isEmpty() {
        return tree.isEmpty();
    }                    // is the set empty?

    public int size() {
        return tree.size();
    }                         // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        tree.add(p);
    }              // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return tree.contains(p);
    }           // does the set contain point p?

    public void draw() {
        // draw all points to standard draw
        for (Point2D p : tree) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        LinkedList<Point2D> inside = new LinkedList<>();
        for (Point2D p : tree) {
            if (rect.contains(p)) {
                inside.add(p);
            }
        }
        return inside;
    }            // all points that are inside the rectangle

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        double min = Double.MAX_VALUE;
        Point2D minPoint = null;
        for (Point2D point : tree) {
            if (p.distanceSquaredTo(point) < min) {
                min = p.distanceSquaredTo(point);
                minPoint = point;
            }
        }
        return minPoint;
    }            // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
        // unit testing of the methods (optional)
    }
}
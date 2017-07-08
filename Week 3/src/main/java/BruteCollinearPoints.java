import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Alexey on 04.07.2017.
 */
public class BruteCollinearPoints {
    private int numberOfSegments = 0;
    private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
    private ArrayList<Point> allPoints;
    private Point[] collinear = new Point[4];

    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        if (points == null) {
            throw new IllegalArgumentException("Input array cannot be null!");
        }
        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException("Points cannot be null!");
        }
        allPoints = new ArrayList<Point>(Arrays.asList(points));
        Collections.sort(allPoints);
        Point check = null;
        for (Point test : allPoints) {
            if (check == null) {
                check = test;
            } else if (check.compareTo(test) == 0) {
                throw new IllegalArgumentException("There should not be equal points in array!");
            } else {
                check = test;
            }
        }
        int p, q, r, s;
        check = null;
        for (p = 0; p < points.length - 3; p++) {
            if (points[p] == null) {
                throw new IllegalArgumentException("Points cannot be null!");
            }
            for (q = p + 1; q < points.length - 2; q++) {
                if (points[q] == null) {
                    throw new IllegalArgumentException("Points cannot be null!");
                }
                for (r = q + 1; r < points.length - 1; r++) {
                    if (points[r] == null) {
                        throw new IllegalArgumentException("Points cannot be null!");
                    }
                    for (s = r + 1; s < points.length; s++) {
                        if (points[s] == null) {
                            throw new IllegalArgumentException("Points cannot be null!");
                        }
                        if (isCollinear(points[p], points[q], points[r], points[s])) {
                            collinear[0] = points[p];
                            collinear[1] = points[q];
                            collinear[2] = points[r];
                            collinear[3] = points[s];
                            Arrays.sort(collinear);
                            segments.add(new LineSegment(collinear[0], collinear[3]));
                            numberOfSegments++;
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        // the number of line segments
        return numberOfSegments;
    }

    private boolean isCollinear(Point p, Point q, Point r, Point s) {
        return p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s);
    }

    public LineSegment[] segments() {
        // the line segments
        LineSegment[] segmentsArray = new LineSegment[numberOfSegments];
        int i = 0;
        for (LineSegment s : segments) {
            segmentsArray[i++] = s;
        }
        return segmentsArray;
    }
}
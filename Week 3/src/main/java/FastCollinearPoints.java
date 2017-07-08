import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Alex on 07.07.17.
 */
public class FastCollinearPoints {
    private int numberOfSegments = 0;
    private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        if (points == null) {
            throw new IllegalArgumentException("Input array cannot be null!");
        }
        Point[] allPoints = Arrays.copyOf(points, points.length);

        for (int i = 0; i < allPoints.length; i++) {
            Point p = points[i];
            if (p == null) throw new IllegalArgumentException("Points cannot be null!");
            Arrays.sort(allPoints);
            Arrays.sort(allPoints, p.slopeOrder());
            int min = 0;
            while (min < allPoints.length && p.compareTo(allPoints[min]) == 0) min++;
            if (min != 1) throw new IllegalArgumentException("There should not be equal points in array!");
            int max = min;
            while (min < allPoints.length) {
                while (max < allPoints.length && p.slopeTo(allPoints[min]) == p.slopeTo(allPoints[max])) max++;
                if (max - min >= 3) {
                    if (allPoints[min].compareTo(p) >= 0) {
                        segments.add(new LineSegment(p, allPoints[max - 1]));
                    }
                }
                min = max;
            }
        }
    }

    public int numberOfSegments() {
        // the number of line segments
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        // the line segments
        LineSegment[] arraySegment = new LineSegment[segments.size()];
        arraySegment = segments.toArray(arraySegment);
        return arraySegment;
    }
}

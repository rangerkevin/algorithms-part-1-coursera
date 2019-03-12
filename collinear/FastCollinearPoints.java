/* ************************************************************************
 *
 * Name: Wei Sun
 * Date: 3/9/2019
 *
 *********************************************************************** */
import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {

    private ArrayList<LineSegment> segmentList;
    private final int N;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        checkNull(points);
        N = points.length;
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        checkDup(sortedPoints);
        segmentList = new ArrayList<>();

        for (int i = 0; i < N; i++) {

            Point P = sortedPoints[i];
            Point[] pointsBySlope = sortedPoints.clone();
            Arrays.sort(pointsBySlope, P.slopeOrder());

            int j = 1;
            while (j < N) {
                ArrayList<Point> segment = new ArrayList<>();
                final double SLOPE_REFERENCE = P.slopeTo(pointsBySlope[j]);

                while (j < N && P.slopeTo(pointsBySlope[j]) == SLOPE_REFERENCE) {
                    segment.add(pointsBySlope[j++]);
                }

                // Removing outer loop duplicates here
                // Append LineSegment only if start is the smallest Point in the group
                if (segment.size() >= 3 && P.compareTo(segment.get(0)) < 0) {
                    Point start = P;
                    Point end = segment.get(segment.size() - 1);
                    segmentList.add(new LineSegment(start, end));
                } 
            }
        }
    }

    // the number of line segments    
    public int numberOfSegments() {
        return segmentList.size();
    } 

    // the line segments     
    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[segmentList.size()];
        return segmentList.toArray(segments);
    }   

    private void checkNull(Point[] points) {
        if (points == null) throw new java.lang.IllegalArgumentException("Null input.");
        if (points.length == 0) throw new java.lang.IllegalArgumentException("Empty list.");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new java.lang.IllegalArgumentException("Null point found .");
        }
    }

    private void checkDup(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (i > 0 && points[i].compareTo(points[i - 1]) == 0) throw new java.lang.IllegalArgumentException("Duplicate point found.");
        }
    }

    public static void main(String[] args) {

        // read the n points from a file
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
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }          
}

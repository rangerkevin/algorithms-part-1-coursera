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

public class BruteCollinearPoints {

    private ArrayList<LineSegment> segmentList;
    private int N;

    // finds all line segments containing 4 points 
    public BruteCollinearPoints(Point[] points) {
        
        checkNull(points);
        N = points.length;
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        checkDup(sortedPoints);
        segmentList = new ArrayList<>();

        for (int p = 0; p < N - 3; p++) {
            Point P = sortedPoints[p];
            for (int q = p + 1; q < N - 2; q++) {
                Point Q = sortedPoints[q];
                for (int r = q + 1; r < N - 1; r++) {
                    Point R = sortedPoints[r];
                    if (P.slopeTo(Q) == P.slopeTo(R)) { 
                        for (int s = r + 1; s < N; s++) {
                            Point S = sortedPoints[s];
                            if (P.slopeTo(R) == P.slopeTo(S)) {
                                segmentList.add(new LineSegment(P, S));
                            }
                        }
                    }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }           
}

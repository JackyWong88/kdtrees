
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

/**
 *
 * @author Jacky
 */
public class PointSET {
    private RedBlackBST<Point2D, Integer> tree;
    private int count = 0;
    
    public PointSET() {                              // construct an empty set of points 
        tree = new RedBlackBST<Point2D, Integer>();
    }

    public boolean isEmpty() {                     // is the set empty? 
        return count == 0;
    }

    public int size() {                        // number of points in the set 
        return tree.size();
    }

    public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
        tree.put(p, count);
        count++;
    }

    public boolean contains(Point2D p) {           // does the set contain point p? 
        return tree.contains(p);
    }

    public void draw() {                        // draw all points to standard draw 
        StdDraw.show(0);
        for (Point2D p : tree.keys()) {
            StdDraw.setXscale(0, 1);
            StdDraw.setYscale(0, 1);
            p.draw();
        }
        StdDraw.show();
    }

    public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle 
        Stack<Point2D> range = new Stack<Point2D>();
        for (Point2D p : tree.keys()) {
            if (p.x() >= rect.xmin() && p.x() <= rect.xmax() && p.y() >= rect.ymin() && p.y() <= rect.ymax()) {
                range.push(p);
            }
        }
        return range;
    }

    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 
        Point2D champion = null;
        double shortest = 2;
        for (Point2D thatp : tree.keys()) {
            double distance = p.distanceTo(thatp);
            if (distance < shortest) {
                shortest = distance;
                champion = thatp;
            }
        }
        return champion;
    }

    public static void main(String[] args) {                 // unit testing of the methods (optional) 
        PointSET set = new PointSET();
        set.insert(new Point2D(0, 1));
    }

}

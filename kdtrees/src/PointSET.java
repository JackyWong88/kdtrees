
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
    RedBlackBST<Point2D, Integer> tree;
    int count;
    
    public PointSET() {                              // construct an empty set of points 
        RedBlackBST<Point2D, Integer> tree = new RedBlackBST<Point2D, Integer>();
    }

    public boolean isEmpty() {                     // is the set empty? 
        return tree.isEmpty();
    }

    public int size() {                        // number of points in the set 
        return tree.size();
    }

    public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
        tree.put(p,count);
        count++;
    }

    public boolean contains(Point2D p) {           // does the set contain point p? 
        return tree.contains(p);
    }

    public void draw() {                        // draw all points to standard draw 
        StdDraw.show(0);
        for (Point2D p : tree.keys()) {
            StdDraw.setXscale(0,1);
            StdDraw.setYscale(0,1);
            p.draw();
        }
        StdDraw.show();
    }

    public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle 
        Stack<Point2D> range = new Stack<Point2D>();
        for (Point2D p : tree.keys()) {
            if (p.x() > rect.xmin() && p.x() < rect.xmax() && p.y() > rect.ymin() && p.y() < rect.ymax()) {
                range.push(p);
            }
        }
        return range;
    }

    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 
        Point2D champion = null;
        int shortest = 2;
        for (Point2D thatp : tree.keys()) {
            if (p.equals(thatp)) continue;
            if (p.distanceTo(thatp) < shortest) {
                champion = thatp;
            }
        }
        return champion;
    }

    public static void main(String[] args) {                 // unit testing of the methods (optional) 
        
    }

}

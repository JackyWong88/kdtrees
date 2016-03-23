
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jacky
 */
public class KdTree {
    
    public KdTree() {                              // construct an empty set of points 
        
    }

    public boolean isEmpty() {                     // is the set empty? 
        return false;
    }

    public int size() {                        // number of points in the set 
        return 0;
    }

    public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
        
    }

    public boolean contains(Point2D p) {           // does the set contain point p? 
        return false;
    }

    public void draw() {                        // draw all points to standard draw 
        
    }

    public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle 
        Stack<Point2D> range = new Stack<Point2D>();
        return range;
    }

    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 
        Point2D champion = null;
        return champion;
    }

    public static void main(String[] args) {                 // unit testing of the methods (optional) 
        
    }

}

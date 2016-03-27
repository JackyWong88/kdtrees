
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

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
    private BST<Node, Integer> tree;
    private int count = 0;
    
    public KdTree() {                              // construct an empty set of points 
        tree = new BST<Node, Integer>();
    }

    public boolean isEmpty() {                     // is the set empty? 
        return tree.isEmpty();
    }

    public int size() {                        // number of points in the set 
        return tree.size();
    }

    public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
        Node node = new Node(p, count);
        tree.put(node, count);
        count++;
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
    
    private abstract class Node{
        private boolean horizontal = true;
        Point2D p;
        
        abstract int compareTo(Point2D p);
        abstract int compareTo(RectHV rect);
        abstract Node makeNode(Point2D p, int n);
        //abstract RectHV rectCompute(RectHV parentRect, String side);
        //abstract void setPenColor();
        //abstract void lineThroughPoint(Point2D point);
        
        private Node(Point2D p, int n) {
            
        }
        
        private class YNode extends Node{
            private YNode(Point2D p, int n) {
                super(p, n);
            }
            
            int compareTo(Point2D p) {
                if (this.p.y() < p.y()) return -1;
                if (this.p.y() > p.y()) return 1;
                return 0;
            }
                    
            int compareTo(RectHV rect) {
                if (rect.ymin() > this.p.y()) return -1;
                if (rect.ymax() < this.p.y()) return 1;
                return 0;
            }
            
            Node makeNode(Point2D p, int n){
                return new XNode(p, n);
            }
        }
        
        private class XNode extends Node{
            private XNode(Point2D p, int n) {
                super(p, n);
            }
            
            int compareTo(Point2D p) {
                if (this.p.x() < p.x()) return -1;
                if (this.p.x() > p.x()) return 1;
                return 0;
            }
                    
            int compareTo(RectHV rect) {
                if (rect.xmin() > this.p.x()) return -1;
                if (rect.xmax() < this.p.x()) return 1;
                return 0;
            }
            
            Node makeNode(Point2D p, int n){
                return new YNode(p, n);
            }
        }
    }
    
    private static int binlog( int bits ) // returns 0 for bits=0
{
    int log = 0;
    if( ( bits & 0xffff0000 ) != 0 ) { bits >>>= 16; log = 16; }
    if( bits >= 256 ) { bits >>>= 8; log += 8; }
    if( bits >= 16  ) { bits >>>= 4; log += 4; }
    if( bits >= 4   ) { bits >>>= 2; log += 2; }
    return log + ( bits >>> 1 );
}

    public static void main(String[] args) {                 // unit testing of the methods (optional) 
        int log = binlog(1025);
        StdOut.println(log);
    }

}

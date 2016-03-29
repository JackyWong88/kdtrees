
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

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

    private int count = 0;
    private YNode root;

    private abstract static class Node {
        private Point2D p;
        private Node left, right;  // left and right subtrees
        private int val;
        private double xbound, ybound;

        abstract int compareTo(Point2D p);

        abstract int compareTo(RectHV rect);
        abstract int compareTo(Node that);

        abstract Node makeNode(Point2D p, int n);

        //abstract RectHV rectCompute(RectHV parentRect, String side);
        abstract void setPenColor();

        //abstract void lineThroughPoint(Point2D point);
        abstract void drawLine(Point2D point);

//        private Node(Point2D p, int val) {
//            this.p = p;
//            this.val = val;
//            left = null;
//            right = null;
//        }
    }

    private class YNode extends Node {
        private Point2D p;
        private Node left, right;  // left and right subtrees
        private int val;
        private double bound;

        private YNode(Point2D p, int val) {
            this.p = p;
            this.val = val;
            left = null;
            right = null;
        }

        @Override
        int compareTo(Point2D p) {
            if (this.p.x() < p.x()) return 1;
            else if (this.p.x() > p.x()) return -1;
            else if (this.p.y() > p.y()) return 1;
            else if (this.p.y() < p.y()) return -1;
            return 0;
        }

        @Override
        int compareTo(RectHV rect) {
            if (rect.ymin() > this.p.y()) return -1;
            if (rect.ymax() < this.p.y()) return 1;
            return 0;
        }

        @Override
        Node makeNode(Point2D p, int n) {
            return new XNode(p, n);
        }

        public int compareTo(Node that) {
            if (this.p.y() < that.p.y()) return -1;
            else if (this.p.y() > that.p.y()) return 1;
            else if (this.p.x() < that.p.x()) return -1;
            else if (this.p.x() > that.p.x()) return 1;
            return 0;
        }

        @Override
        void setPenColor() {
            StdDraw.setPenColor(StdDraw.RED);
        }

        @Override
        void drawLine(Point2D point) {
            setPenColor();
            if (compareTo(point) < 0) {
                StdDraw.line(this.p.x(), 1, this.p.x(), point.y());
            } else {
                StdDraw.line(this.p.x(), 0, this.p.x(), point.y());
            }
        }
    }

    private class XNode extends Node {
        private Point2D p;
        private Node left, right;  // left and right subtrees
        private int val;
        private double xbound;

        private XNode(Point2D p, int n) {
            this.p = p;
            this.val = val;
            left = null;
            right = null;
        }

        @Override
        int compareTo(Point2D p) {
            if (this.p.y() < p.y()) return 1;
            if (this.p.y() > p.y()) return -1;
            else if (this.p.x() < p.x()) return 1;
            else if (this.p.x() > p.x()) return -1;
            return 0;
        }

        @Override
        int compareTo(RectHV rect) {
            if (rect.xmin() > this.p.x()) return -1;
            if (rect.xmax() < this.p.x()) return 1;
            return 0;
        }

        @Override
        Node makeNode(Point2D p, int n) {
            return new YNode(p, n);
        }

        public int compareTo(Node that) {
            if (this.p.x() < that.p.x()) return -1;
            else if (this.p.x() > that.p.x()) return 1;
            else if (this.p.y() < that.p.y()) return 1;
            else if (this.p.y() > that.p.y()) return -1;
            return 0;
        }

        @Override
        void setPenColor() {
            StdDraw.setPenColor(StdDraw.BLUE);
        }

        @Override
        void drawLine(Point2D point) {
            setPenColor();
            if (compareTo(point) < 0) {
                StdDraw.line(point.x(), this.p.y(), 1, this.p.y());
            } else {
                StdDraw.line(point.x(), this.p.y(), 0, this.p.y());
            }
        }
    }

    public KdTree() {                              // construct an empty set of points 

    }

    public boolean isEmpty() {                     // is the set empty? 
        return count == 0;
    }

    public int size() {                        // number of points in the set 
        return count;
    }

    public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
        if (isEmpty()) {
            root = new YNode(p, count);
            count++;
        } else {
            Node node = root;
            Node parent = null;
            while (node != null) {
                int cmp = node.compareTo(p);
                //StdOut.println("Comparing " + node.p + " and " + p + "results : " + cmp);
                if (cmp < 0) {
                    parent = node;
                    node = node.left;
                } else if (cmp > 0) {
                    parent = node;
                    node = node.right;
                } else return;
            }
            int cmp = parent.compareTo(p);
            if (cmp < 0) {
                parent.left = parent.makeNode(p, count);
                count++;
                return;
            } else {
                parent.right = parent.makeNode(p, count);
                count++;
                return;
            }
        }
    }

    private Node insert(Node x, Point2D key, int val) {
        //if (x == null) return new Node(key, val);
        int cmp = key.compareTo(x.p);
        if (cmp < 0) x.left = insert(x.left, key, val);
        else if (cmp > 0) x.right = insert(x.right, key, val);
        else x.val = val;
        return x;
    }

    public boolean contains(Point2D p) {           // does the set contain point p? 
        Node node = root;
        while (node != null) {
            int cmp = node.compareTo(p);
            if (cmp < 0) node = node.left;
            else if (cmp > 0) node = node.right;
            else {
                //StdOut.println(node.p + " is equal to " + p);
                return true;
            }
        }
        return false;
    }

    public void draw() {                        // draw all points to standard draw 
        if (root == null) return;
        Stack<Node> stack = new Stack<Node>();
        Node node = root;
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
        
        while (stack.size() > 0) {
            node = stack.pop();
            if (node.right != null) {
                node = node.right;
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }
        }
//        if (isEmpty()) return;
//        Node max = root;
//        while (max.right != null) {
//            max = max.right;
//        }
//        Node min = root;
//        while (min.left != null) {
//            min = min.left;
//        }
//        draw(root, min, max, null);
    }

    private void draw(Node x, Node lo, Node hi, Node parent) {
        if (x == null) return;
        int cmplo = x.compareTo(lo);
        int cmphi = x.compareTo(hi);
        if (cmplo < 0) draw(x.left, lo, hi, x);
        if (cmplo <= 0 && cmphi >= 0) {
//            StdOut.print("Drawing: ");
//            StdOut.println(x.p);
            x.p.draw();
            if (parent != null) x.drawLine(parent.p);
            else {
                x.setPenColor();
                StdDraw.line(x.p.x(), 0, x.p.x(), 1);
            }
        }
        if (cmphi > 0) draw(x.right, lo, hi, x);
    }

    public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle 
        Stack<Point2D> range = new Stack<Point2D>();
        return range;
    }

    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 
        Point2D champion = null;
        return champion;
    }

    private static int binlog(int bits) // returns 0 for bits=0
    {
        int log = 0;
        if ((bits & 0xffff0000) != 0) {
            bits >>>= 16;
            log = 16;
        }
        if (bits >= 256) {
            bits >>>= 8;
            log += 8;
        }
        if (bits >= 16) {
            bits >>>= 4;
            log += 4;
        }
        if (bits >= 4) {
            bits >>>= 2;
            log += 2;
        }
        return log + (bits >>> 1);
    }

    public static void main(String[] args) {                 // unit testing of the methods (optional) 
        int log = binlog(1025);
        StdOut.println(log);
    }

}

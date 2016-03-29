
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
        abstract void drawLine();

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
        private double upbound, lowbound;
        private double lbound, rbound;

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
            XNode node = new XNode(p, n);
            int cmp = compareTo(p);
            if (cmp < 0) {
                node.rbound = this.p.x();
                node.lbound = lbound;
            } else if (cmp > 0) {
                node.lbound = this.p.x();
                node.rbound = rbound;
            } else {
                node.lbound = this.p.x();
                node.rbound = this.p.x();
            }
            node.upbound = upbound;
            node.lowbound = lowbound;
            return node;
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

        void drawLine() {
            setPenColor();
            StdDraw.line(this.p.x(), lowbound, this.p.x(), upbound);
        }
    }

    private class XNode extends Node {
        private Point2D p;
        private Node left, right;  // left and right subtrees
        private int val;
        private double lbound, rbound;
        private double upbound, lowbound;

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
            YNode node = new YNode(p, n);
            int cmp = compareTo(p);
            if (cmp < 0) {
                node.upbound = this.p.y();
                node.lowbound = lowbound;
            } else if (cmp > 0) {
                node.lowbound = this.p.y();
                node.upbound = upbound;
            } else {
                node.lowbound = this.p.y();
                node.upbound = this.p.y();
            }
            node.rbound = rbound;
            node.lbound = lbound;
            return node;
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

        void drawLine() {
            setPenColor();
            StdDraw.line(lbound, this.p.y(), rbound, this.p.y());
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
            root.lbound = 0.0;
            root.rbound = 1.0;
            root.upbound = 1.0;
            root.lowbound = 0.0;
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
            node.drawLine();
            if (node.right != null) {
                node = node.right;
                node.drawLine();
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

//    private void draw(Node x, Node lo, Node hi, Node parent) {
//        if (x == null) return;
//        int cmplo = x.compareTo(lo);
//        int cmphi = x.compareTo(hi);
//        if (cmplo < 0) draw(x.left, lo, hi, x);
//        if (cmplo <= 0 && cmphi >= 0) {
////            StdOut.print("Drawing: ");
////            StdOut.println(x.p);
//            x.p.draw();
//            if (parent != null) x.drawLine(parent.p);
//            else {
//                x.setPenColor();
//                StdDraw.line(x.p.x(), 0, x.p.x(), 1);
//            }
//        }
//        if (cmphi > 0) draw(x.right, lo, hi, x);
//    }

    public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle 
        Stack<Point2D> range = new Stack<Point2D>();
        return range;
    }

    //find the point furthest down the tree and then recursively check its distance compared to the dividing lines.
    //if a distance to a dividing line is shorter than the point, search the other side of that line (the other subtree)
    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 
        Point2D champion = null;
        return champion;
    }

    public static void main(String[] args) {                 // unit testing of the methods (optional) 
    }

}

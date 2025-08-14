// AVLBasicExercise.java
// Beginner exercise: minimal AVL with insert, search, height, validation.

public class AVLBasicExercise {

    /* -------- Node -------- */
    private static class Node {
        int key;
        int height;          // height of this node (leaf = 1)
        Node left, right;

        Node(int k) {
            key = k;
            height = 1;
        }
    }

    private Node root;

    /* -------- Public APIs -------- */

    // Insert a key (BST insert + rebalance). Time: O(log n), Space: O(log n)
    public void insert(int key) { root = insert(root, key); }

    // Search a key. Time: O(log n)
    public boolean search(int key) {
        Node cur = root;
        while (cur != null) {
            if (key == cur.key) return true;
            cur = (key < cur.key) ? cur.left : cur.right;
        }
        return false;
    }

    // Height of the whole tree. Empty tree => 0
    public int height() { return height(root); }

    // Validate AVL property: for every node, |balance| <= 1
    public boolean isValidAVL() { return checkAVL(root) != -1; }

    /* -------- Internal helpers -------- */

    private Node insert(Node node, int key) {
        if (node == null) return new Node(key);

        if (key < node.key) node.left = insert(node.left, key);
        else if (key > node.key) node.right = insert(node.right, key);
        else return node; // ignore duplicates

        updateHeight(node);
        return rebalance(node);
    }

    private int height(Node n) { return (n == null) ? 0 : n.height; }

    private int balanceFactor(Node n) { return height(n.left) - height(n.right); }

    private void updateHeight(Node n) { n.height = Math.max(height(n.left), height(n.right)) + 1; }

    // Rebalance with the 4 AVL cases
    private Node rebalance(Node n) {
        int bf = balanceFactor(n);

        // LL
        if (bf > 1 && balanceFactor(n.left) >= 0) return rotateRight(n);
        // LR
        if (bf > 1 && balanceFactor(n.left) < 0) {
            n.left = rotateLeft(n.left);
            return rotateRight(n);
        }
        // RR
        if (bf < -1 && balanceFactor(n.right) <= 0) return rotateLeft(n);
        // RL
        if (bf < -1 && balanceFactor(n.right) > 0) {
            n.right = rotateRight(n.right);
            return rotateLeft(n);
        }
        return n; // already balanced
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);
        return y;
    }

    // Returns height if subtree is valid AVL; otherwise -1
    private int checkAVL(Node n) {
        if (n == null) return 0;
        int lh = checkAVL(n.left);  if (lh == -1) return -1;
        int rh = checkAVL(n.right); if (rh == -1) return -1;
        if (Math.abs(lh - rh) > 1) return -1;
        return Math.max(lh, rh) + 1;
    }

    /* -------- Simple printer (optional) -------- */
    // In-order print with balance factors
    public void printInOrder() { printInOrder(root); System.out.println(); }
    private void printInOrder(Node n) {
        if (n == null) return;
        printInOrder(n.left);
        System.out.print(n.key + "(" + balanceFactor(n) + ") ");
        printInOrder(n.right);
    }

    /* -------- Demo main --------
       Run with args: java AVLBasicExercise 10 20 30 40
       or without args to see a default demo.
    --------------------------------------------- */
    public static void main(String[] args) {
        AVLBasicExercise avl = new AVLBasicExercise();

        if (args.length > 0) {
            for (String s : args) {
                try { avl.insert(Integer.parseInt(s)); } catch (NumberFormatException ignored) {}
            }
        } else {
            int[] demo = {10, 20, 30, 40, 50, 25, 5};
            for (int x : demo) avl.insert(x);
        }

        System.out.println("In-order with BF:");
        avl.printInOrder();
        System.out.println("Height: " + avl.height());
        System.out.println("Valid AVL: " + avl.isValidAVL());
        System.out.println("Search 25: " + avl.search(25));
        System.out.println("Search 99: " + avl.search(99));
    }
}

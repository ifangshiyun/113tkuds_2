// AVLDeleteExercise.java
// 中級練習 3：實作 AVL 刪除（葉節點、單子節點、雙子節點）

import java.util.*;

public class AVLDeleteExercise {

    /* ---------- Node ---------- */
    private static class Node {
        int key;
        int height; // 葉節點高度 = 1
        Node left, right;
        Node(int k) { key = k; height = 1; }
    }

    private Node root;

    /* ---------- Public APIs ---------- */

    // 插入：標準 BST + AVL 再平衡
    public void insert(int key) { root = insert(root, key); }

    // 搜尋
    public boolean search(int key) {
        Node cur = root;
        while (cur != null) {
            if (key == cur.key) return true;
            cur = key < cur.key ? cur.left : cur.right;
        }
        return false;
    }

    // 刪除：處理 3 種情況並自下而上再平衡
    public void delete(int key) { root = delete(root, key); }

    // 取得整棵樹高度（空樹 = 0）
    public int height() { return h(root); }

    // 檢查是否為有效 AVL（所有節點 |bf| <= 1）
    public boolean isValidAVL() { return checkAVL(root) != -1; }

    // 走訪與列印工具
    public List<Integer> inorder() { List<Integer> out = new ArrayList<>(); inorder(root, out); return out; }
    public List<Integer> preorder() { List<Integer> out = new ArrayList<>(); preorder(root, out); return out; }
    public List<Integer> postorder() { List<Integer> out = new ArrayList<>(); postorder(root, out); return out; }
    public List<List<Integer>> levelOrder() {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        Queue<Node> q = new ArrayDeque<>();
        q.add(root);
        while (!q.isEmpty()) {
            int sz = q.size();
            List<Integer> level = new ArrayList<>(sz);
            for (int i = 0; i < sz; i++) {
                Node n = q.poll();
                level.add(n.key);
                if (n.left != null) q.add(n.left);
                if (n.right != null) q.add(n.right);
            }
            res.add(level);
        }
        return res;
    }
    public void printInOrderWithBF() { printInOrderWithBF(root); System.out.println(); }

    /* ---------- Internal: Insert / Delete / Rebalance ---------- */

    private Node insert(Node n, int key) {
        if (n == null) return new Node(key);
        if (key < n.key) n.left = insert(n.left, key);
        else if (key > n.key) n.right = insert(n.right, key);
        else return n; // 忽略重複
        updateHeight(n);
        return rebalance(n);
    }

    private Node delete(Node n, int key) {
        if (n == null) return null;

        if (key < n.key) {
            n.left = delete(n.left, key);
        } else if (key > n.key) {
            n.right = delete(n.right, key);
        } else {
            // 命中要刪除的節點：分三種情況
            // 1) 葉節點或 2) 只有一個子節點
            if (n.left == null || n.right == null) {
                n = (n.left != null) ? n.left : n.right; // 可能為 null（葉）
            } else {
                // 3) 兩個子節點：用「中序後繼」替代
                Node succ = minNode(n.right);
                n.key = succ.key;
                n.right = delete(n.right, succ.key);
            }
        }

        if (n == null) return null; // 刪成空子樹

        updateHeight(n);
        return rebalance(n);
    }

    private Node minNode(Node n) {
        while (n.left != null) n = n.left;
        return n;
    }

    /* ---------- AVL Helpers ---------- */

    private static int h(Node n) { return n == null ? 0 : n.height; }
    private static int bf(Node n) { return n == null ? 0 : h(n.left) - h(n.right); }
    private static void updateHeight(Node n) { n.height = Math.max(h(n.left), h(n.right)) + 1; }

    private Node rebalance(Node n) {
        int b = bf(n);
        // LL
        if (b > 1 && bf(n.left) >= 0) return rotateRight(n);
        // LR
        if (b > 1 && bf(n.left) < 0) { n.left = rotateLeft(n.left); return rotateRight(n); }
        // RR
        if (b < -1 && bf(n.right) <= 0) return rotateLeft(n);
        // RL
        if (b < -1 && bf(n.right) > 0) { n.right = rotateRight(n.right); return rotateLeft(n); }
        return n;
    }

    private Node rotateRight(Node y) {
        Node x = y.left, T2 = x.right;
        x.right = y; y.left = T2;
        updateHeight(y); updateHeight(x);
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right, T2 = y.left;
        y.left = x; x.right = T2;
        updateHeight(x); updateHeight(y);
        return y;
    }

    // 回傳高度；若非 AVL 回 -1
    private int checkAVL(Node n) {
        if (n == null) return 0;
        int lh = checkAVL(n.left);  if (lh == -1) return -1;
        int rh = checkAVL(n.right); if (rh == -1) return -1;
        if (Math.abs(lh - rh) > 1) return -1;
        return Math.max(lh, rh) + 1;
    }

    /* ---------- Traversal helpers ---------- */
    private void inorder(Node n, List<Integer> out) {
        if (n == null) return;
        inorder(n.left, out); out.add(n.key); inorder(n.right, out);
    }
    private void preorder(Node n, List<Integer> out) {
        if (n == null) return;
        out.add(n.key); preorder(n.left, out); preorder(n.right, out);
    }
    private void postorder(Node n, List<Integer> out) {
        if (n == null) return;
        postorder(n.left, out); postorder(n.right, out); out.add(n.key);
    }
    private void printInOrderWithBF(Node n) {
        if (n == null) return;
        printInOrderWithBF(n.left);
        System.out.print(n.key + "(bf=" + bf(n) + ",h=" + h(n) + ") ");
        printInOrderWithBF(n.right);
    }

    /* ---------- Demo main：展示三種刪除情況 ---------- */
    public static void main(String[] args) {
        AVLDeleteExercise avl = new AVLDeleteExercise();

        // 製造一棵樹
        int[] arr = { 30, 20, 40, 10, 25, 35, 50, 5, 15, 45, 60 };
        for (int x : arr) avl.insert(x);

        System.out.println("Initial (inorder): " + avl.inorder());
        System.out.println("Initial (level):   " + avl.levelOrder());
        System.out.println("Height=" + avl.height() + ", ValidAVL=" + avl.isValidAVL());
        System.out.println();

        // 1) 刪除葉節點：15
        System.out.println("Delete leaf 15");
        avl.delete(15);
        System.out.println("Inorder: " + avl.inorder());
        System.out.println("Level:   " + avl.levelOrder());
        System.out.println("Height=" + avl.height() + ", ValidAVL=" + avl.isValidAVL());
        System.out.println();

        // 2) 刪除只有一個子節點：45（假設它是 50 的左子，且無右子）
        System.out.println("Delete single-child 45");
        avl.delete(45);
        System.out.println("Inorder: " + avl.inorder());
        System.out.println("Level:   " + avl.levelOrder());
        System.out.println("Height=" + avl.height() + ", ValidAVL=" + avl.isValidAVL());
        System.out.println();

        // 3) 刪除有兩個子節點：20（左右皆有）
        System.out.println("Delete two-children 20");
        avl.delete(20);
        System.out.println("Inorder: " + avl.inorder());
        System.out.println("Level:   " + avl.levelOrder());
        System.out.println("Height=" + avl.height() + ", ValidAVL=" + avl.isValidAVL());
        System.out.println();

        // 額外測試：連續刪除導致多次旋轉
        System.out.println("Delete chain: 5, 10, 25");
        avl.delete(5);
        avl.delete(10);
        avl.delete(25);
        System.out.println("Inorder: " + avl.inorder());
        System.out.println("Level:   " + avl.levelOrder());
        System.out.println("Height=" + avl.height() + ", ValidAVL=" + avl.isValidAVL());
    }
}

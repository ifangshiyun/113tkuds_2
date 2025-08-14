// AVLRangeQueryExercise.java
// 練習 4：實作範圍查詢 rangeQuery(min, max)

import java.util.*;

public class AVLRangeQueryExercise {

    /* -------- Node -------- */
    private static class Node {
        int key;
        int height;        // 葉節點高度 = 1
        Node left, right;
        Node(int k) { key = k; height = 1; }
    }

    private Node root;

    /* -------- Public APIs -------- */

    // 插入：BST + AVL 再平衡（忽略重複）
    public void insert(int key) { root = insert(root, key); }

    // 範圍查詢：回傳 [min, max] 區間內的所有鍵（遞增序）
    // 以中序遍歷並利用 BST 性質剪枝：當 key < min 時不需走左；當 key > max 時不需走右
    public List<Integer> rangeQuery(int min, int max) {
        List<Integer> out = new ArrayList<>();
        rangeInorder(root, min, max, out);
        return out;
    }

    // 便利工具：中序輸出（含平衡因子），便於自我檢查
    public void printInOrder() { printInOrder(root); System.out.println(); }

    /* -------- Internal: Insert / Rebalance -------- */

    private Node insert(Node n, int key) {
        if (n == null) return new Node(key);
        if (key < n.key) n.left = insert(n.left, key);
        else if (key > n.key) n.right = insert(n.right, key);
        else return n; // 忽略重複

        updateHeight(n);
        return rebalance(n);
    }

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

    /* -------- Range Inorder with Pruning -------- */

    private void rangeInorder(Node n, int min, int max, List<Integer> out) {
        if (n == null) return;

        // 若當前節點值大於等於下界，左子樹可能有解
        if (n.key > min) {
            rangeInorder(n.left, min, max, out);
        } else if (n.key == min) {
            // 等於下界時仍可走左，但為最佳化可省略（左側皆 < min）
        }

        // 中間：若位於範圍則收集
        if (n.key >= min && n.key <= max) out.add(n.key);

        // 若當前節點值小於等於上界，右子樹可能有解
        if (n.key < max) {
            rangeInorder(n.right, min, max, out);
        } else if (n.key == max) {
            // 等於上界時仍可走右，但為最佳化可省略（右側皆 > max）
        }
    }

    /* -------- Debug Helpers -------- */

    private void printInOrder(Node n) {
        if (n == null) return;
        printInOrder(n.left);
        System.out.print(n.key + "(bf=" + bf(n) + ",h=" + h(n) + ") ");
        printInOrder(n.right);
    }

    /* -------- Demo main --------
       預設插入一批數字並示範 rangeQuery
       可用指令參數傳入：第一個為 min，第二個為 max（可只帶一個或不帶）
    -------------------------------- */
    public static void main(String[] args) {
        AVLRangeQueryExercise avl = new AVLRangeQueryExercise();

        int[] data = { 20, 10, 30, 5, 15, 25, 40, 13, 17, 35, 45, 27, 33 };
        for (int x : data) avl.insert(x);

        System.out.print("In-order: ");
        avl.printInOrder();

        int min = 12, max = 34; // 預設範圍
        if (args.length >= 1) {
            try { min = Integer.parseInt(args[0]); } catch (NumberFormatException ignored) {}
        }
        if (args.length >= 2) {
            try { max = Integer.parseInt(args[1]); } catch (NumberFormatException ignored) {}
        }
        if (min > max) { int t = min; min = max; max = t; }

        List<Integer> ans = avl.rangeQuery(min, max);
        System.out.println("Range [" + min + ", " + max + "] -> " + ans);
        System.out.println("k = " + ans.size());
    }
}

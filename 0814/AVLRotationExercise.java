// AVLRotationExercise.java
// 練習 2：實作四種旋轉（左旋、右旋、左右旋、右左旋）與測試案例

import java.util.*;

public class AVLRotationExercise {

    /* ---------- 節點定義 ---------- */
    static class Node {
        int key;
        int height;      // 葉節點高度 = 1
        Node left, right;

        Node(int k) {
            key = k;
            height = 1;
        }
    }

    /* ---------- 基本工具 ---------- */

    // 取得節點高度（null 視為 0）
    static int h(Node n) {
        return (n == null) ? 0 : n.height;
    }

    // 更新節點高度 = max(左高, 右高) + 1
    static void updateHeight(Node n) {
        if (n != null) {
            n.height = Math.max(h(n.left), h(n.right)) + 1;
        }
    }

    // 平衡因子 = 左高 - 右高
    static int bf(Node n) {
        return (n == null) ? 0 : h(n.left) - h(n.right);
    }

    // 簡單中序列印（含平衡因子）
    static void printInOrder(Node n) {
        if (n == null) return;
        printInOrder(n.left);
        System.out.print(n.key + "(h=" + h(n) + ",bf=" + bf(n) + ") ");
        printInOrder(n.right);
    }

    // 層序列印
    static void printLevelOrder(Node root) {
        if (root == null) {
            System.out.println("[]");
            return;
        }
        List<List<String>> levels = new ArrayList<>();
        Queue<Node> q = new ArrayDeque<>();
        q.add(root);
        while (!q.isEmpty()) {
            int sz = q.size();
            List<String> level = new ArrayList<>(sz);
            for (int i = 0; i < sz; i++) {
                Node n = q.poll();
                level.add(n.key + "(h=" + h(n) + ",bf=" + bf(n) + ")");
                if (n.left != null) q.add(n.left);
                if (n.right != null) q.add(n.right);
            }
            levels.add(level);
        }
        System.out.println(levels);
    }

    /* ---------- 四種旋轉 ---------- */

    // 右旋（Right Rotation, LL 修正）
    //        y                 x
    //       / \               / \
    //      x   T3   -->     T1  y
    //     / \                   / \
    //    T1 T2                 T2 T3
    static Node rotateRight(Node y) {
        if (y == null || y.left == null) return y; // 邊界：不可旋
        Node x = y.left;
        Node T2 = x.right;

        // 執行旋轉
        x.right = y;
        y.left = T2;

        // 自底向上更新高度
        updateHeight(y);
        updateHeight(x);
        return x; // 新根
    }

    // 左旋（Left Rotation, RR 修正）
    //      x                    y
    //     / \                  / \
    //    T1  y     -->        x  T3
    //       / \              / \
    //      T2 T3            T1 T2
    static Node rotateLeft(Node x) {
        if (x == null || x.right == null) return x; // 邊界：不可旋
        Node y = x.right;
        Node T2 = y.left;

        // 執行旋轉
        y.left = x;
        x.right = T2;

        // 自底向上更新高度
        updateHeight(x);
        updateHeight(y);
        return y; // 新根
    }

    // 左右旋（Left-Right, LR 修正）：先對左子樹左旋，再對自己右旋
    static Node rotateLeftRight(Node z) {
        if (z == null || z.left == null) return z;
        z.left = rotateLeft(z.left);
        return rotateRight(z);
    }

    // 右左旋（Right-Left, RL 修正）：先對右子樹右旋，再對自己左旋
    static Node rotateRightLeft(Node z) {
        if (z == null || z.right == null) return z;
        z.right = rotateRight(z.right);
        return rotateLeft(z);
    }

    /* ---------- 測試案例建樹（手動建立，方便觀察旋轉前後） ---------- */

    // 產生 RR 不平衡（根 x 的右-右）：
    //    10
    //      \
    //       20
    //         \
    //          30
    static Node buildRR() {
        Node n10 = new Node(10);
        Node n20 = new Node(20);
        Node n30 = new Node(30);
        n10.right = n20;
        n20.right = n30;
        updateHeight(n30);
        updateHeight(n20);
        updateHeight(n10);
        return n10;
    }

    // 產生 LL 不平衡（根 z 的左-左）：
    //      30
    //     /
    //   20
    //  /
    // 10
    static Node buildLL() {
        Node n30 = new Node(30);
        Node n20 = new Node(20);
        Node n10 = new Node(10);
        n30.left = n20;
        n20.left = n10;
        updateHeight(n10);
        updateHeight(n20);
        updateHeight(n30);
        return n30;
    }

    // 產生 LR 不平衡（左-右）：
    //      30
    //     /
    //   10
    //     \
    //      20
    static Node buildLR() {
        Node n30 = new Node(30);
        Node n10 = new Node(10);
        Node n20 = new Node(20);
        n30.left = n10;
        n10.right = n20;
        updateHeight(n20);
        updateHeight(n10);
        updateHeight(n30);
        return n30;
    }

    // 產生 RL 不平衡（右-左）：
    //   10
    //     \
    //     30
    //    /
    //   20
    static Node buildRL() {
        Node n10 = new Node(10);
        Node n30 = new Node(30);
        Node n20 = new Node(20);
        n10.right = n30;
        n30.left = n20;
        updateHeight(n20);
        updateHeight(n30);
        updateHeight(n10);
        return n10;
    }

    /* ---------- 驗證工具 ---------- */

    // 驗證整棵樹所有節點 |bf| <= 1
    static boolean isAVL(Node n) {
        return checkAVL(n) != -1;
    }
    // 回傳高度；若不是 AVL，回傳 -1
    static int checkAVL(Node n) {
        if (n == null) return 0;
        int lh = checkAVL(n.left);  if (lh == -1) return -1;
        int rh = checkAVL(n.right); if (rh == -1) return -1;
        if (Math.abs(lh - rh) > 1) return -1;
        return Math.max(lh, rh) + 1;
    }

    /* ---------- 主程式：逐一測試四種旋轉與邊界 ---------- */
    public static void main(String[] args) {
        // RR -> 左旋
        System.out.println("=== RR case -> rotateLeft ===");
        Node rr = buildRR();
        System.out.print("Before (level): "); printLevelOrder(rr);
        System.out.print("Before (inorder): "); printInOrder(rr); System.out.println();
        rr = rotateLeft(rr);
        System.out.print("After  (level): "); printLevelOrder(rr);
        System.out.print("After  (inorder): "); printInOrder(rr); System.out.println();
        System.out.println("AVL valid: " + isAVL(rr));
        System.out.println();

        // LL -> 右旋
        System.out.println("=== LL case -> rotateRight ===");
        Node ll = buildLL();
        System.out.print("Before (level): "); printLevelOrder(ll);
        System.out.print("Before (inorder): "); printInOrder(ll); System.out.println();
        ll = rotateRight(ll);
        System.out.print("After  (level): "); printLevelOrder(ll);
        System.out.print("After  (inorder): "); printInOrder(ll); System.out.println();
        System.out.println("AVL valid: " + isAVL(ll));
        System.out.println();

        // LR -> 左右旋
        System.out.println("=== LR case -> rotateLeftRight ===");
        Node lr = buildLR();
        System.out.print("Before (level): "); printLevelOrder(lr);
        System.out.print("Before (inorder): "); printInOrder(lr); System.out.println();
        lr = rotateLeftRight(lr);
        System.out.print("After  (level): "); printLevelOrder(lr);
        System.out.print("After  (inorder): "); printInOrder(lr); System.out.println();
        System.out.println("AVL valid: " + isAVL(lr));
        System.out.println();

        // RL -> 右左旋
        System.out.println("=== RL case -> rotateRightLeft ===");
        Node rl = buildRL();
        System.out.print("Before (level): "); printLevelOrder(rl);
        System.out.print("Before (inorder): "); printInOrder(rl); System.out.println();
        rl = rotateRightLeft(rl);
        System.out.print("After  (level): "); printLevelOrder(rl);
        System.out.print("After  (inorder): "); printInOrder(rl); System.out.println();
        System.out.println("AVL valid: " + isAVL(rl));
        System.out.println();

        // 邊界測試：對 null 與單側子樹旋轉
        System.out.println("=== Edge cases ===");
        Node edgeNull = null;
        edgeNull = rotateLeft(edgeNull);   // 不應 NPE
        edgeNull = rotateRight(edgeNull);  // 不應 NPE
        System.out.println("Rotate on null OK.");

        Node single = new Node(42);
        single = rotateLeft(single);   // 右子樹為 null，應回傳原樹
        single = rotateRight(single);  // 左子樹為 null，應回傳原樹
        System.out.println("Rotate on single-node OK. Height=" + h(single) + ", bf=" + bf(single));
    }
}

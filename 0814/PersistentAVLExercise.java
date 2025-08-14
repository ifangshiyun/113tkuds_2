// PersistentAVLExercise.java
// 練習 6：持久化 AVL 樹（不可變節點 + 路徑複製 + 多版本查詢）

import java.util.*;

public class PersistentAVLExercise {

    /* ========== 不可變節點定義 (immutable) ========== */
    private static final class Node {
        final int key;
        final int height;     // 葉節點高度 = 1
        final Node left;
        final Node right;

        private Node(int key, Node left, Node right) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.height = Math.max(h(left), h(right)) + 1; // 高度在建構時計算
        }
    }

    /* ========== 版本根節點列表：version 0 為空樹 ========== */
    private final List<Node> versions = new ArrayList<>();

    public PersistentAVLExercise() {
        versions.add(null); // version 0: empty tree
    }

    /* ========== 公開 API ========== */

    /** 取得目前最新版本編號（0-based）。*/
    public int latestVersion() { return versions.size() - 1; }

    /** 在指定版本插入 key，回傳新的版本編號。若為重複鍵，仍建立新版本（共享相同結構）。 */
    public int insertAtVersion(int version, int key) {
        checkVersion(version);
        Node newRoot = insert(versions.get(version), key);
        versions.add(newRoot);
        return latestVersion();
    }

    /** 查詢：在指定版本是否包含 key。*/
    public boolean search(int version, int key) {
        checkVersion(version);
        Node cur = versions.get(version);
        while (cur != null) {
            if (key == cur.key) return true;
            cur = key < cur.key ? cur.left : cur.right;
        }
        return false;
    }

    /** 中序輸出（遞增序）。*/
    public List<Integer> inorder(int version) {
        checkVersion(version);
        List<Integer> out = new ArrayList<>();
        inorder(versions.get(version), out);
        return out;
    }

    /** 指定版本的樹高（空樹 = 0）。*/
    public int height(int version) {
        checkVersion(version);
        return h(versions.get(version));
    }

    /** 指定版本是否為有效 AVL。*/
    public boolean isValidAVL(int version) {
        checkVersion(version);
        return checkAVL(versions.get(version)) != -1;
    }

    /** 版本數（含 0 號空樹）。*/
    public int versionCount() { return versions.size(); }

    /* ========== 內部：AVL Insert（不可變 + 路徑複製） ========== */

    private static int h(Node n) { return (n == null) ? 0 : n.height; }
    private static int bf(Node n) { return (n == null) ? 0 : h(n.left) - h(n.right); }

    // 工廠：建立新節點（不可變），自動計算高度
    private static Node node(int key, Node left, Node right) {
        return new Node(key, left, right);
    }

    // Insert：回傳新子樹根（僅沿路徑建立新節點；其他子樹共享）
    private Node insert(Node root, int key) {
        if (root == null) return node(key, null, null);

        if (key < root.key) {
            Node newLeft = insert(root.left, key);
            if (newLeft == root.left) {
                // key 已存在於左子樹且未改變：仍需建立新節點以維持不可變版本（共享左右子參考）
                return rebalance(node(root.key, root.left, root.right));
            }
            return rebalance(node(root.key, newLeft, root.right));
        } else if (key > root.key) {
            Node newRight = insert(root.right, key);
            if (newRight == root.right) {
                return rebalance(node(root.key, root.left, root.right));
            }
            return rebalance(node(root.key, root.left, newRight));
        } else {
            // 重複鍵：結構不變，為保持「每次操作產生新版本」語意，複製當前節點（共享子樹）
            return node(root.key, root.left, root.right);
        }
    }

    /* ========== 旋轉與再平衡（均建立新節點，重用未變之子樹） ========== */

    private Node rebalance(Node n) {
        int b = bf(n);
        if (b > 1) {
            if (bf(n.left) >= 0) {             // LL
                return rotateRight(n);
            } else {                            // LR
                return rotateRight(node(n.key, rotateLeft(n.left), n.right));
            }
        } else if (b < -1) {
            if (bf(n.right) <= 0) {            // RR
                return rotateLeft(n);
            } else {                            // RL
                return rotateLeft(node(n.key, n.left, rotateRight(n.right)));
            }
        }
        return n; // 已平衡
    }

    // 不可變右旋：回傳新根
    //       y                 x
    //      / \               / \
    //     x   T3   =>      T1   y
    //    / \                   / \
    //   T1 T2                 T2 T3
    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        Node newY = node(y.key, T2, y.right); // y 的新形態
        return node(x.key, x.left, newY);
    }

    // 不可變左旋：回傳新根
    //     x                    y
    //    / \                  / \
    //   T1  y     =>         x  T3
    //      / \              / \
    //     T2 T3            T1 T2
    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        Node newX = node(x.key, x.left, T2); // x 的新形態
        return node(y.key, newX, y.right);
    }

    /* ========== 驗證/走訪工具 ========== */

    // 回傳高度；若不是 AVL 回 -1
    private int checkAVL(Node n) {
        if (n == null) return 0;
        int lh = checkAVL(n.left);  if (lh == -1) return -1;
        int rh = checkAVL(n.right); if (rh == -1) return -1;
        if (Math.abs(lh - rh) > 1) return -1;
        return Math.max(lh, rh) + 1;
    }

    private void inorder(Node n, List<Integer> out) {
        if (n == null) return;
        inorder(n.left, out);
        out.add(n.key);
        inorder(n.right, out);
    }

    private void checkVersion(int v) {
        if (v < 0 || v >= versions.size()) {
            throw new IllegalArgumentException("Invalid version: " + v);
        }
    }

    /* ========== Demo main：展示版本化插入與歷史查詢 ========== */
    public static void main(String[] args) {
        PersistentAVLExercise p = new PersistentAVLExercise();

        // v0: 空
        int v0 = 0;

        // 在 v0 之上插入 20 -> v1
        int v1 = p.insertAtVersion(v0, 20);
        // 在 v1 之上插入 10 -> v2
        int v2 = p.insertAtVersion(v1, 10);
        // 在 v2 之上插入 30 -> v3
        int v3 = p.insertAtVersion(v2, 30);
        // 在 v3 之上插入 25 -> v4
        int v4 = p.insertAtVersion(v3, 25);
        // 在 v4 之上插入 40 -> v5
        int v5 = p.insertAtVersion(v4, 40);
        // 在 v2 之上另開分支插入 5 -> v6（不同分支）
        int v6 = p.insertAtVersion(v2, 5);

        System.out.println("Total versions: " + p.versionCount()); // 7 (0..6)

        // 各版本中序、樹高與 AVL 驗證
        for (int v : new int[]{v0, v1, v2, v3, v4, v5, v6}) {
            System.out.println("v" + v + " inorder = " + p.inorder(v)
                    + "  height=" + p.height(v)
                    + "  validAVL=" + p.isValidAVL(v));
        }

        // 歷史版本查詢不受新版本影響
        System.out.println("Search 25 in v3 (expect false): " + p.search(v3, 25));
        System.out.println("Search 25 in v4 (expect true):  " + p.search(v4, 25));

        // 重複插入：仍產生新版本（但共享舊結構）
        int v7 = p.insertAtVersion(v5, 40); // duplicate
        System.out.println("v" + v7 + " inorder (duplicate insert) = " + p.inorder(v7));
        System.out.println("v" + v7 + " height=" + p.height(v7) + "  validAVL=" + p.isValidAVL(v7));

        // 額外：展示另一鏈的發展
        int v8 = p.insertAtVersion(v6, 15);
        int v9 = p.insertAtVersion(v8, 13);
        System.out.println("v" + v9 + " inorder = " + p.inorder(v9));
    }
}

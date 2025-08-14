// AVLLeaderboardSystem.java
// 練習 5：AVL 樹應用 - 排行榜系統（維護子樹 size 以支援 rank/select）

import java.util.*;

public class AVLLeaderboardSystem {

    /* --------- Node 定義（含 height、size） --------- */
    private static class Node {
        String id;
        int score;
        int height;   // 葉 = 1
        int size;     // 此節點為根的子樹大小（節點數）
        Node left, right;

        Node(String id, int score) {
            this.id = id;
            this.score = score;
            this.height = 1;
            this.size = 1;
        }
    }

    private Node root;
    // 以 id 快速查到當前分數（用於 update、rank）：O(1)
    private final Map<String, Integer> scoreById = new HashMap<>();

    /* ===================== 公開 API ===================== */

    // 新增玩家；若 id 已存在則回傳 false
    public boolean addPlayer(String id, int score) {
        if (scoreById.containsKey(id)) return false;
        root = insert(root, id, score);
        scoreById.put(id, score);
        return true;
    }

    // 更新玩家分數；不存在回傳 false
    public boolean updatePlayer(String id, int newScore) {
        Integer old = scoreById.get(id);
        if (old == null) return false;
        // 先刪舊 key（以 id + 舊分數確保正確刪除）
        root = delete(root, id, old);
        // 插入新 key
        root = insert(root, id, newScore);
        scoreById.put(id, newScore);
        return true;
    }

    // 查詢玩家名次（1 為第一名）；不存在回傳 -1
    public int getPlayerRank(String id) {
        Integer s = scoreById.get(id);
        if (s == null) return -1;
        return rank(root, id, s);
    }

    // 查詢玩家當前分數；不存在回傳 null
    public Integer getPlayerScore(String id) {
        return scoreById.get(id);
    }

    // 回傳前 K 名 (id, score)（不足 K 則回傳全部）
    public List<PlayerScore> topK(int k) {
        List<PlayerScore> out = new ArrayList<>();
        inOrderCollect(root, out, k);
        return out;
    }

    // 目前玩家總數
    public int size() { return sz(root); }

    // 觀察用：中序列印（從高分到低分，含 size/bf）
    public void printInOrder() {
        printInOrder(root);
        System.out.println();
    }

    /* =============== 內部：AVL + Order Statistic =============== */

    // 比較器：分數高者「較小」，使其排在左側（即中序由高到低）
    // 若分數相同，以 id 的字典序
    private static int cmp(String id1, int s1, String id2, int s2) {
        int c = Integer.compare(s2, s1); // 分數：高的在前（即 s1 > s2 => c < 0）
        if (c != 0) return c;
        return id1.compareTo(id2);
    }

    private static int ht(Node n) { return n == null ? 0 : n.height; }
    private static int sz(Node n) { return n == null ? 0 : n.size; }
    private static int bf(Node n) { return n == null ? 0 : ht(n.left) - ht(n.right); }

    private static void refresh(Node n) {
        if (n != null) {
            n.height = Math.max(ht(n.left), ht(n.right)) + 1;
            n.size   = sz(n.left) + sz(n.right) + 1;
        }
    }

    private Node rotateRight(Node y) {
        Node x = y.left, T2 = x.right;
        x.right = y; y.left = T2;
        refresh(y); refresh(x);
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right, T2 = y.left;
        y.left = x; x.right = T2;
        refresh(x); refresh(y);
        return y;
    }

    private Node rebalance(Node n) {
        int b = bf(n);
        if (b > 1 && bf(n.left) >= 0) return rotateRight(n);          // LL
        if (b > 1 && bf(n.left) < 0)  { n.left = rotateLeft(n.left); return rotateRight(n); } // LR
        if (b < -1 && bf(n.right) <= 0) return rotateLeft(n);         // RR
        if (b < -1 && bf(n.right) > 0) { n.right = rotateRight(n.right); return rotateLeft(n); } // RL
        return n;
    }

    private Node insert(Node n, String id, int score) {
        if (n == null) return new Node(id, score);
        int c = cmp(id, score, n.id, n.score);
        if (c < 0) n.left = insert(n.left, id, score);
        else if (c > 0) n.right = insert(n.right, id, score);
        else {
            // 理論上不會發生（id 唯一），但若同 id 同分數則直接覆寫分數（冪等）
            n.score = score;
            return n;
        }
        refresh(n);
        return rebalance(n);
    }

    private Node delete(Node n, String id, int score) {
        if (n == null) return null;
        int c = cmp(id, score, n.id, n.score);
        if (c < 0) {
            n.left = delete(n.left, id, score);
        } else if (c > 0) {
            n.right = delete(n.right, id, score);
        } else {
            // 命中目標
            if (n.left == null || n.right == null) {
                n = (n.left != null) ? n.left : n.right;
            } else {
                // 以中序後繼（右子樹最左）替換
                Node succ = minNode(n.right);
                n.id = succ.id;
                n.score = succ.score;
                n.right = delete(n.right, succ.id, succ.score);
            }
        }
        if (n == null) return null;
        refresh(n);
        return rebalance(n);
    }

    private static Node minNode(Node n) {
        while (n.left != null) n = n.left;
        return n;
    }

    // rank：1-based。樹的中序即為「高分→低分」。
    private int rank(Node n, String id, int score) {
        if (n == null) return 0; // 不存在時外層會轉成 -1
        int c = cmp(id, score, n.id, n.score);
        if (c < 0) {
            return rank(n.left, id, score);
        } else if (c > 0) {
            return sz(n.left) + 1 + rank(n.right, id, score);
        } else {
            return sz(n.left) + 1;
        }
    }

    // 依序收集前 k 名（中序：左-root-右；此比較器下左子樹是較高分）
    private void inOrderCollect(Node n, List<PlayerScore> out, int k) {
        if (n == null || out.size() >= k) return;
        inOrderCollect(n.left, out, k);
        if (out.size() < k) out.add(new PlayerScore(n.id, n.score));
        inOrderCollect(n.right, out, k);
    }

    private void printInOrder(Node n) {
        if (n == null) return;
        printInOrder(n.left);
        System.out.print("[" + n.id + ":" + n.score + " h=" + ht(n) + " sz=" + sz(n) + " bf=" + bf(n) + "] ");
        printInOrder(n.right);
    }

    /* --------- 資料模型：回傳前 K 名使用 --------- */
    public static class PlayerScore {
        public final String id;
        public final int score;
        public PlayerScore(String id, int score) { this.id = id; this.score = score; }
        @Override public String toString() { return "(" + id + ", " + score + ")"; }
    }

    /* ===================== Demo main ===================== */
    public static void main(String[] args) {
        AVLLeaderboardSystem board = new AVLLeaderboardSystem();

        // 新增一些玩家
        board.addPlayer("alice", 1200);
        board.addPlayer("bob", 1500);
        board.addPlayer("carol", 1500);
        board.addPlayer("dave", 900);
        board.addPlayer("erin", 1300);
        board.addPlayer("frank", 1100);

        System.out.println("== Initial leaderboard (in-order, hi->lo) ==");
        board.printInOrder();

        System.out.println("\nSize: " + board.size());
        System.out.println("Rank of bob:   " + board.getPlayerRank("bob"));   // 1 or 2（與 carol 同分，由 id 字典序決定）
        System.out.println("Rank of carol: " + board.getPlayerRank("carol"));
        System.out.println("Top 3: " + board.topK(3));

        // 更新分數
        System.out.println("\n== Update alice -> 1600 ==");
        board.updatePlayer("alice", 1600);
        board.printInOrder();
        System.out.println();
        System.out.println("Rank of alice: " + board.getPlayerRank("alice"));
        System.out.println("Top 3: " + board.topK(3));

        // 再次更新（降分）
        System.out.println("\n== Update bob -> 1000 ==");
        board.updatePlayer("bob", 1000);
        board.printInOrder();
        System.out.println();
        System.out.println("Rank of bob:   " + board.getPlayerRank("bob"));
        System.out.println("Top 4: " + board.topK(4));

        // 查詢不存在
        System.out.println("\nRank of unknown: " + board.getPlayerRank("unknown")); // -1
    }
}

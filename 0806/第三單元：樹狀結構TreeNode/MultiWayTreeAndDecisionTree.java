import java.util.*;

public class MultiWayTreeAndDecisionTree {

    static class MultiNode {
        String value;
        List<MultiNode> children;

        MultiNode(String value) {
            this.value = value;
            this.children = new ArrayList<>();
        }

        void addChild(MultiNode child) {
            this.children.add(child);
        }
    }

    public static void main(String[] args) {
        // 測試一：建立多路樹
        MultiNode root = new MultiNode("A");
        MultiNode b = new MultiNode("B");
        MultiNode c = new MultiNode("C");
        MultiNode d = new MultiNode("D");
        MultiNode e = new MultiNode("E");
        MultiNode f = new MultiNode("F");

        root.addChild(b);
        root.addChild(c);
        root.addChild(d);
        b.addChild(e);
        b.addChild(f);

        System.out.println("1️⃣ DFS 深度優先：");
        dfs(root);

        System.out.println("\n2️⃣ BFS 廣度優先：");
        bfs(root);

        System.out.println("\n3️⃣ 每個節點的度數：");
        printDegrees(root);

        System.out.println("\n4️⃣ 樹的高度：" + getHeight(root));

        // 測試二：決策樹 - 猜數字遊戲
        System.out.println("\n🎮 模擬猜數字決策樹");
        MultiNode gameRoot = buildGuessGameTree();
        dfs(gameRoot);
    }

    // DFS 深度優先走訪
    public static void dfs(MultiNode node) {
        if (node == null) return;
        System.out.print(node.value + " ");
        for (MultiNode child : node.children) {
            dfs(child);
        }
    }

    // BFS 廣度優先走訪
    public static void bfs(MultiNode root) {
        if (root == null) return;
        Queue<MultiNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            MultiNode current = queue.poll();
            System.out.print(current.value + " ");
            queue.addAll(current.children);
        }
    }

    // 計算高度
    public static int getHeight(MultiNode node) {
        if (node == null) return 0;
        int max = 0;
        for (MultiNode child : node.children) {
            max = Math.max(max, getHeight(child));
        }
        return max + 1;
    }

    // 列出每個節點的度（子節點數）
    public static void printDegrees(MultiNode node) {
        if (node == null) return;
        System.out.println("節點 " + node.value + " 的度數：" + node.children.size());
        for (MultiNode child : node.children) {
            printDegrees(child);
        }
    }

    // 模擬簡單決策樹：猜數字
    public static MultiNode buildGuessGameTree() {
        MultiNode root = new MultiNode("開始遊戲");
        MultiNode lessThan5 = new MultiNode("小於 5？");

        MultiNode guess1 = new MultiNode("是 1 嗎？");
        MultiNode guess2 = new MultiNode("是 2 嗎？");
        MultiNode guess3 = new MultiNode("是 3 嗎？");
        MultiNode guess4 = new MultiNode("是 4 嗎？");

        lessThan5.addChild(guess1);
        lessThan5.addChild(guess2);
        lessThan5.addChild(guess3);
        lessThan5.addChild(guess4);

        MultiNode moreThan5 = new MultiNode("大於等於 5？");

        MultiNode guess5 = new MultiNode("是 5 嗎？");
        MultiNode guess6 = new MultiNode("是 6 嗎？");
        MultiNode guess7 = new MultiNode("是 7 嗎？");

        moreThan5.addChild(guess5);
        moreThan5.addChild(guess6);
        moreThan5.addChild(guess7);

        root.addChild(lessThan5);
        root.addChild(moreThan5);

        return root;
    }
}

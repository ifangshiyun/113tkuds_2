import java.util.ArrayList;

public class BSTRangeQuerySystem {
    static class TreeNode {
        int data;
        TreeNode left;
        TreeNode right;

        public TreeNode(int data) {
            this.data = data;
        }
    }

    // ✅ 範圍查詢方法（遞迴中序走訪 + 範圍剪枝）
    public static void rangeQuery(TreeNode node, int min, int max, ArrayList<Integer> result) {
        if (node == null) return;

        // 左子樹遞迴（若有可能小於 max）
        if (node.data > min) {
            rangeQuery(node.left, min, max, result);
        }

        // 符合範圍的節點加入結果
        if (node.data >= min && node.data <= max) {
            result.add(node.data);
        }

        // 右子樹遞迴（若有可能大於 min）
        if (node.data < max) {
            rangeQuery(node.right, min, max, result);
        }
    }

    public static void main(String[] args) {
        // ✅ 建立 BST：[20, 10, 30, 5, 15, 25, 35]
        TreeNode root = new TreeNode(20);
        root.left = new TreeNode(10);
        root.right = new TreeNode(30);
        root.left.left = new TreeNode(5);
        root.left.right = new TreeNode(15);
        root.right.left = new TreeNode(25);
        root.right.right = new TreeNode(35);

        // ✅ 查詢區間 [12, 27]
        ArrayList<Integer> result = new ArrayList<>();
        rangeQuery(root, 12, 27, result);

        // ✅ 輸出結果
        System.out.println("區間 [12, 27] 節點值: " + result);
    }
}
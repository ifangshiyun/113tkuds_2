public class BSTKthElement {
    static class TreeNode {
        int data;
        TreeNode left;
        TreeNode right;

        public TreeNode(int data) {
            this.data = data;
        }
    }

    // 內部輔助類別，用於記錄目前走訪狀態
    static class Counter {
        int count = 0;
        int result = -1;
    }

    // ✅ 主方法：找到第 k 小元素
    public static int kthSmallest(TreeNode root, int k) {
        Counter counter = new Counter();
        inOrderFind(root, k, counter);
        return counter.result;
    }

    // ✅ 中序走訪 + 提前結束邏輯
    private static void inOrderFind(TreeNode node, int k, Counter counter) {
        if (node == null || counter.count >= k) return;

        inOrderFind(node.left, k, counter);

        counter.count++;
        if (counter.count == k) {
            counter.result = node.data;
            return;
        }

        inOrderFind(node.right, k, counter);
    }

    public static void main(String[] args) {
        // 建立一棵 BST：[20, 10, 30, 5, 15, 25, 35]
        TreeNode root = new TreeNode(20);
        root.left = new TreeNode(10);
        root.right = new TreeNode(30);
        root.left.left = new TreeNode(5);
        root.left.right = new TreeNode(15);
        root.right.left = new TreeNode(25);
        root.right.right = new TreeNode(35);

        // 測試第 k 小
        for (int k = 1; k <= 7; k++) {
            System.out.println("第 " + k + " 小元素: " + kthSmallest(root, k));
        }
    }
}
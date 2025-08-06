public class BSTValidationAndRepair {
    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        // 測試用樹
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(8); // 錯誤：應該大於 10

        System.out.println("1. 是否為合法 BST：" + isValidBST(root));

        System.out.print("2. 不符合 BST 的節點：");
        findInvalidNodes(root, Integer.MIN_VALUE, Integer.MAX_VALUE);

        TreeNode wrongRoot = new TreeNode(6);
        wrongRoot.left = new TreeNode(10);  // 應該是 5，和右邊交換
        wrongRoot.right = new TreeNode(2);

        System.out.println("\n3. 修復前是否合法 BST：" + isValidBST(wrongRoot));
        recoverBST(wrongRoot);
        System.out.println("   修復後是否合法 BST：" + isValidBST(wrongRoot));

        TreeNode messyRoot = new TreeNode(3);
        messyRoot.left = new TreeNode(2);
        messyRoot.right = new TreeNode(4);
        messyRoot.left.left = new TreeNode(5);  // 錯誤

        System.out.println("4. 最少需刪除節點數：" + minDeletionsToValidBST(messyRoot));
    }

    // 1. 驗證 BST 是否有效
    public static boolean isValidBST(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean validate(TreeNode node, long min, long max) {
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        return validate(node.left, min, node.val) && validate(node.right, node.val, max);
    }

    // 2. 印出所有不合法的節點
    public static void findInvalidNodes(TreeNode node, long min, long max) {
        if (node == null) return;
        if (node.val <= min || node.val >= max)
            System.out.print(node.val + " ");
        findInvalidNodes(node.left, min, node.val);
        findInvalidNodes(node.right, node.val, max);
    }

    // 3. 修復兩個錯誤位置節點的 BST（僅支援交換兩個錯誤節點的情形）
    static TreeNode first, second, prev;

    public static void recoverBST(TreeNode root) {
        first = second = prev = null;
        inorderDetect(root);
        if (first != null && second != null) {
            int tmp = first.val;
            first.val = second.val;
            second.val = tmp;
        }
    }

    private static void inorderDetect(TreeNode node) {
        if (node == null) return;
        inorderDetect(node.left);
        if (prev != null && prev.val > node.val) {
            if (first == null) first = prev;
            second = node;
        }
        prev = node;
        inorderDetect(node.right);
    }

    // 4. 最少需移除多少個節點使其成為合法 BST
    public static int minDeletionsToValidBST(TreeNode root) {
        return countInvalidNodes(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static int countInvalidNodes(TreeNode node, long min, long max) {
        if (node == null) return 0;
        if (node.val <= min || node.val >= max) {
            return 1 + countInvalidNodes(node.left, min, max) + countInvalidNodes(node.right, min, max);
        }
        return countInvalidNodes(node.left, min, node.val) + countInvalidNodes(node.right, node.val, max);
    }
}

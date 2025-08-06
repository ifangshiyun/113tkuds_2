import java.util.*;

public class BSTConversionAndBalance {

    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int x) {
            val = x;
        }
    }

    static class DoublyListNode {
        int val;
        DoublyListNode prev, next;

        DoublyListNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        // 測試用 BST：       4
        //                 /   \
        //               2       6
        //              / \     / \
        //             1   3   5   7
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(5);
        root.right.right = new TreeNode(7);

        // 1️⃣ BST → 雙向鏈結串列（排序）
        DoublyListNode head = bstToDoublyLinkedList(root);
        System.out.print("1. 雙向鏈結串列（排序）：");
        printDoublyList(head);

        // 2️⃣ 排序陣列 → 平衡 BST
        List<Integer> sorted = new ArrayList<>();
        inOrder(root, sorted);
        TreeNode balanced = sortedArrayToBST(sorted);
        System.out.println("\n2. 排序陣列轉平衡 BST：中序為");
        printInOrder(balanced);

        // 3️⃣ 檢查是否平衡 & 平衡因子
        System.out.println("\n3. 是否平衡：" + isBalanced(balanced));
        System.out.println("   平衡因子：" + getBalanceFactor(balanced));

        // 4️⃣ 改為「大於等於總和」
        transformToGreaterSumTree(root);
        System.out.println("\n4. 節點轉換為大於等於總和：");
        printInOrder(root);
    }

    // ✅ 1. 將 BST 轉換為排序的雙向鏈結串列
    static DoublyListNode prevNode = null;
    static DoublyListNode headNode = null;

    public static DoublyListNode bstToDoublyLinkedList(TreeNode root) {
        prevNode = null;
        headNode = null;
        inOrderConvert(root);
        return headNode;
    }

    private static void inOrderConvert(TreeNode node) {
        if (node == null) return;
        inOrderConvert(node.left);

        DoublyListNode curr = new DoublyListNode(node.val);
        if (prevNode == null) {
            headNode = curr;
        } else {
            prevNode.next = curr;
            curr.prev = prevNode;
        }
        prevNode = curr;

        inOrderConvert(node.right);
    }

    public static void printDoublyList(DoublyListNode head) {
        while (head != null) {
            System.out.print(head.val);
            if (head.next != null) System.out.print(" <-> ");
            head = head.next;
        }
        System.out.println();
    }

    // ✅ 2. 排序陣列轉平衡 BST
    public static TreeNode sortedArrayToBST(List<Integer> nums) {
        return helper(nums, 0, nums.size() - 1);
    }

    private static TreeNode helper(List<Integer> nums, int left, int right) {
        if (left > right) return null;
        int mid = (left + right) / 2;
        TreeNode node = new TreeNode(nums.get(mid));
        node.left = helper(nums, left, mid - 1);
        node.right = helper(nums, mid + 1, right);
        return node;
    }

    // ✅ 3. 檢查是否平衡，並算平衡因子
    public static boolean isBalanced(TreeNode root) {
        return checkHeight(root) != -1;
    }

    private static int checkHeight(TreeNode node) {
        if (node == null) return 0;
        int lh = checkHeight(node.left);
        int rh = checkHeight(node.right);
        if (lh == -1 || rh == -1 || Math.abs(lh - rh) > 1) return -1;
        return Math.max(lh, rh) + 1;
    }

    public static int getBalanceFactor(TreeNode node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }

    private static int height(TreeNode node) {
        if (node == null) return 0;
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    // ✅ 4. 將每節點改為「所有大於等於節點值的總和」
    static int sum = 0;
    public static void transformToGreaterSumTree(TreeNode root) {
        sum = 0;
        transformReverseInOrder(root);
    }

    private static void transformReverseInOrder(TreeNode node) {
        if (node == null) return;
        transformReverseInOrder(node.right);
        sum += node.val;
        node.val = sum;
        transformReverseInOrder(node.left);
    }

    // 🔄 輔助函數：中序列印與儲存
    public static void inOrder(TreeNode root, List<Integer> res) {
        if (root == null) return;
        inOrder(root.left, res);
        res.add(root.val);
        inOrder(root.right, res);
    }

    public static void printInOrder(TreeNode root) {
        if (root == null) return;
        printInOrder(root.left);
        System.out.print(root.val + " ");
        printInOrder(root.right);
    }
}

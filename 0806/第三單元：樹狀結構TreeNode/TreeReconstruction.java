import java.util.*;

public class TreeReconstruction {
    static class TreeNode {
        int data;
        TreeNode left, right;

        TreeNode(int data) {
            this.data = data;
        }
    }

    // 根據前序 + 中序 重建二元樹
    public static TreeNode buildFromPreIn(int[] preorder, int[] inorder) {
        Map<Integer, Integer> inMap = buildIndexMap(inorder);
        return buildPreIn(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, inMap);
    }

    private static TreeNode buildPreIn(int[] pre, int preL, int preR, int[] in, int inL, int inR, Map<Integer, Integer> inMap) {
        if (preL > preR || inL > inR) return null;
        TreeNode root = new TreeNode(pre[preL]);
        int inRoot = inMap.get(root.data);
        int leftSize = inRoot - inL;

        root.left = buildPreIn(pre, preL + 1, preL + leftSize, in, inL, inRoot - 1, inMap);
        root.right = buildPreIn(pre, preL + leftSize + 1, preR, in, inRoot + 1, inR, inMap);
        return root;
    }

    // 根據後序 + 中序 重建二元樹
    public static TreeNode buildFromPostIn(int[] postorder, int[] inorder) {
        Map<Integer, Integer> inMap = buildIndexMap(inorder);
        return buildPostIn(postorder, 0, postorder.length - 1, inorder, 0, inorder.length - 1, inMap);
    }

    private static TreeNode buildPostIn(int[] post, int postL, int postR, int[] in, int inL, int inR, Map<Integer, Integer> inMap) {
        if (postL > postR || inL > inR) return null;
        TreeNode root = new TreeNode(post[postR]);
        int inRoot = inMap.get(root.data);
        int leftSize = inRoot - inL;

        root.left = buildPostIn(post, postL, postL + leftSize - 1, in, inL, inRoot - 1, inMap);
        root.right = buildPostIn(post, postL + leftSize, postR - 1, in, inRoot + 1, inR, inMap);
        return root;
    }

    // 共用：建立中序索引表
    private static Map<Integer, Integer> buildIndexMap(int[] inorder) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return map;
    }

    // 輔助：中序走訪
    public static void printInOrder(TreeNode root) {
        if (root == null) return;
        printInOrder(root.left);
        System.out.print(root.data + " ");
        printInOrder(root.right);
    }

    // 輔助：前序走訪
    public static void printPreOrder(TreeNode root) {
        if (root == null) return;
        System.out.print(root.data + " ");
        printPreOrder(root.left);
        printPreOrder(root.right);
    }

    // 輔助：後序走訪
    public static void printPostOrder(TreeNode root) {
        if (root == null) return;
        printPostOrder(root.left);
        printPostOrder(root.right);
        System.out.print(root.data + " ");
    }

    public static void main(String[] args) {
        // 範例：前序、中序、後序
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder  = {9, 3, 15, 20, 7};
        int[] postorder = {9, 15, 7, 20, 3};

        // 前序 + 中序 建樹
        TreeNode root1 = buildFromPreIn(preorder, inorder);
        System.out.println("用前序+中序建樹 → 中序:");
        printInOrder(root1);
        System.out.println("\n前序:");
        printPreOrder(root1);
        System.out.println("\n後序:");
        printPostOrder(root1);

        System.out.println("\n\n------------------");

        // 後序 + 中序 建樹
        TreeNode root2 = buildFromPostIn(postorder, inorder);
        System.out.println("用後序+中序建樹 → 中序:");
        printInOrder(root2);
        System.out.println("\n前序:");
        printPreOrder(root2);
        System.out.println("\n後序:");
        printPostOrder(root2);
    }
}
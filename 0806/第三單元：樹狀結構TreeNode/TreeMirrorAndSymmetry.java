public class TreeMirrorAndSymmetry {

    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // 判斷是否為對稱樹
    public static boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return isMirror(root.left, root.right);
    }

    private static boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        return t1.val == t2.val &&
               isMirror(t1.left, t2.right) &&
               isMirror(t1.right, t2.left);
    }

    // 轉換為鏡像樹
    public static TreeNode mirrorTree(TreeNode root) {
        if (root == null) return null;

        TreeNode temp = root.left;
        root.left = mirrorTree(root.right);
        root.right = mirrorTree(temp);
        return root;
    }

    // 判斷兩棵樹是否互為鏡像
    public static boolean areMirrors(TreeNode t1, TreeNode t2) {
        return isMirror(t1, t2);
    }

    // 判斷 t2 是否為 t1 的子樹
    public static boolean isSubtree(TreeNode t1, TreeNode t2) {
        if (t2 == null) return true;
        if (t1 == null) return false;
        if (isSameTree(t1, t2)) return true;
        return isSubtree(t1.left, t2) || isSubtree(t1.right, t2);
    }

    private static boolean isSameTree(TreeNode a, TreeNode b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.val == b.val &&
               isSameTree(a.left, b.left) &&
               isSameTree(a.right, b.right);
    }

    // 主程式測試
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.right.right = new TreeNode(3);

        System.out.println("是否為對稱樹: " + isSymmetric(root));  // true

        TreeNode t1 = new TreeNode(4);
        t1.left = new TreeNode(5);
        t1.right = new TreeNode(6);

        TreeNode t2 = new TreeNode(4);
        t2.left = new TreeNode(6);
        t2.right = new TreeNode(5);

        System.out.println("是否互為鏡像: " + areMirrors(t1, t2)); // true

        TreeNode sub = new TreeNode(2);
        sub.right = new TreeNode(3);
        System.out.println("是否為子樹: " + isSubtree(root, sub)); // true

        // 測試鏡像轉換
        TreeNode original = new TreeNode(7);
        original.left = new TreeNode(8);
        original.right = new TreeNode(9);

        TreeNode mirrored = mirrorTree(original);
        System.out.println("鏡像後 root 左右子節點: " +
            mirrored.left.val + ", " + mirrored.right.val); // 9, 8
    }
}

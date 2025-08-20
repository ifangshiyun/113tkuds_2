
import java.util.*;

// 複雜度分析：
//   建樹 O(n)，檢查 BST O(n)，檢查 AVL O(n)。
//   總時間 O(n)，空間 O(h) (遞迴深度)，h ≤ n。
public class M09_AVLValidate {
    static class Node {
        int val;
        Node left, right;
        Node(int v) { val = v; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();
        sc.close();

        Node root = buildTree(arr);

        if (!isBST(root, Long.MIN_VALUE, Long.MAX_VALUE)) {
            System.out.println("Invalid BST");
            return;
        }
        if (!isAVL(root).isValid) {
            System.out.println("Invalid AVL");
            return;
        }
        System.out.println("Valid");
    }

    // ===== 建樹：層序，-1 為 null =====
    private static Node buildTree(int[] arr) {
        if (arr.length == 0 || arr[0] == -1) return null;
        Node root = new Node(arr[0]);
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        int i = 1;
        while (!q.isEmpty() && i < arr.length) {
            Node cur = q.poll();
            if (i < arr.length && arr[i] != -1) {
                cur.left = new Node(arr[i]);
                q.add(cur.left);
            }
            i++;
            if (i < arr.length && arr[i] != -1) {
                cur.right = new Node(arr[i]);
                q.add(cur.right);
            }
            i++;
        }
        return root;
    }

    // ===== 檢查 BST：帶上下界 =====
    private static boolean isBST(Node root, long min, long max) {
        if (root == null) return true;
        if (root.val <= min || root.val >= max) return false;
        return isBST(root.left, min, root.val) &&
               isBST(root.right, root.val, max);
    }

    // ===== 檢查 AVL：回傳高度 + 是否有效 =====
    static class AVLInfo {
        int height;
        boolean isValid;
        AVLInfo(int h, boolean v) { height = h; isValid = v; }
    }

    private static AVLInfo isAVL(Node root) {
        if (root == null) return new AVLInfo(0, true);
        AVLInfo left = isAVL(root.left);
        AVLInfo right = isAVL(root.right);
        if (!left.isValid || !right.isValid) return new AVLInfo(0, false);

        if (Math.abs(left.height - right.height) > 1) {
            return new AVLInfo(0, false);
        }
        return new AVLInfo(Math.max(left.height, right.height) + 1, true);
    }
}

/* Terminal Run result
ifangelinetosani@ifangelines-MacBook-Air midterm % cd /Users/ifangelinetosani/Documents/GitHub/113tkuds_2/midterm
ifangelinetosani@ifangelines-MacBook-Air midterm % javac M09_AVLValidate.java
ifangelinetosani@ifangelines-MacBook-Air midterm % java M09_AVLValidate
7
10 5 15 3 7 12 18
Valid
 */
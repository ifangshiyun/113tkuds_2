
import java.util.*;

// 限制：n ≤ 1e4
// 複雜度分析：建樹 O(n)，搜尋 O(m)，其中 m 為實際走訪節點數 ≤ n。
// 總時間 O(n)，空間 O(h) (樹高)。
public class M08_BSTRangedSum {
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
        int L = sc.nextInt(), R = sc.nextInt();
        sc.close();

        Node root = buildTree(arr);
        int sum = rangeSum(root, L, R);
        System.out.println("Sum: " + sum);
    }

    // 建樹：層序，-1 表 null
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

    // DFS with pruning
    private static int rangeSum(Node root, int L, int R) {
        if (root == null) return 0;
        if (root.val < L) return rangeSum(root.right, L, R);
        if (root.val > R) return rangeSum(root.left, L, R);
        return root.val + rangeSum(root.left, L, R) + rangeSum(root.right, L, R);
    }
}

/* Terminal Run Result
ifangelinetosani@ifangelines-MacBook-Air midterm % cd /Users/ifangelinetosani/Documents/GitHub/113tkuds_2/midterm
ifangelinetosani@ifangelines-MacBook-Air midterm % javac M08_BSTRangedSum.java 
ifangelinetosani@ifangelines-MacBook-Air midterm % java M08_BSTRangedSum
7
10 5 15 3 7 -1 18
7 15
Sum: 32
 */
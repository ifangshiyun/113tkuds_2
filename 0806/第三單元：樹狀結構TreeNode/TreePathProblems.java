import java.util.*;

public class TreePathProblems {
    static class TreeNode {
        int data;
        TreeNode left, right;
        TreeNode(int data) {
            this.data = data;
        }
    }

    // 1. 所有根到葉的路徑
    public static List<List<Integer>> findAllPaths(TreeNode root) {
        List<List<Integer>> paths = new ArrayList<>();
        findPathsHelper(root, new ArrayList<>(), paths);
        return paths;
    }

    private static void findPathsHelper(TreeNode node, List<Integer> current, List<List<Integer>> paths) {
        if (node == null) return;

        current.add(node.data);

        // 是葉節點，加入結果
        if (node.left == null && node.right == null) {
            paths.add(new ArrayList<>(current));
        } else {
            findPathsHelper(node.left, current, paths);
            findPathsHelper(node.right, current, paths);
        }

        current.remove(current.size() - 1); // 回溯
    }

    // 2. 判斷是否存在某個和的根到葉路徑
    public static boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        if (root.left == null && root.right == null)
            return root.data == targetSum;
        return hasPathSum(root.left, targetSum - root.data) ||
               hasPathSum(root.right, targetSum - root.data);
    }

    // 3. 找出總和最大的根到葉路徑
    public static List<Integer> findMaxSumPath(TreeNode root) {
        List<Integer> maxPath = new ArrayList<>();
        findMaxPathHelper(root, new ArrayList<>(), 0, new int[]{Integer.MIN_VALUE}, maxPath);
        return maxPath;
    }

    private static void findMaxPathHelper(TreeNode node, List<Integer> current, int sum, int[] maxSum, List<Integer> maxPath) {
        if (node == null) return;

        current.add(node.data);
        sum += node.data;

        if (node.left == null && node.right == null) {
            if (sum > maxSum[0]) {
                maxSum[0] = sum;
                maxPath.clear();
                maxPath.addAll(current);
            }
        } else {
            findMaxPathHelper(node.left, current, sum, maxSum, maxPath);
            findMaxPathHelper(node.right, current, sum, maxSum, maxPath);
        }

        current.remove(current.size() - 1); // 回溯
    }

    public static void main(String[] args) {
        /*
            範例樹：
                   10
                 /    \
                5     12
               / \      \
              3   7     15
         */
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(12);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(7);
        root.right.right = new TreeNode(15);

        // 1. 所有根到葉路徑
        System.out.println("所有根到葉路徑：");
        List<List<Integer>> allPaths = findAllPaths(root);
        for (List<Integer> path : allPaths) {
            System.out.println(path);
        }

        // 2. 是否有某個總和為 22 的根到葉路徑
        System.out.println("\n是否存在和為 22 的根到葉路徑: " + hasPathSum(root, 22));  // true (10 + 5 + 7)

        // 3. 總和最大的根到葉路徑
        List<Integer> maxPath = findMaxSumPath(root);
        System.out.println("\n總和最大的根到葉路徑: " + maxPath);  // 10 + 12 + 15 = 37
    }
}

public class AVLRotations {

    // 右旋操作
    // 時間複雜度: O(1), 空間複雜度: O(1)
    public static AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // 執行旋轉
        x.right = y;
        y.left = T2;

        // 更新高度
        y.updateHeight();
        x.updateHeight();

        return x; // 新的根節點
    }

    // 左旋操作
    // 時間複雜度: O(1), 空間複雜度: O(1)
    public static AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // 執行旋轉
        y.left = x;
        x.right = T2;

        // 更新高度
        x.updateHeight();
        y.updateHeight();

        return y; // 新的根節點
    }

    // 主方法用於測試AVL樹旋轉操作
    public static void main(String[] args) {
        // 建立一個不平衡的樹 (需要右旋)
        System.out.println("===== 測試右旋 =====");
        AVLNode root1 = new AVLNode(30);
        root1.left = new AVLNode(20);
        root1.left.left = new AVLNode(10);
        root1.updateHeight();
        root1.left.updateHeight();

        System.out.println("右旋前:");
        System.out.println("根節點: " + root1.data + ", 高度: " + root1.height);
        System.out.println("平衡因子: " + root1.getBalance());

        // 執行右旋
        root1 = rightRotate(root1);

        System.out.println("右旋後:");
        System.out.println("根節點: " + root1.data + ", 高度: " + root1.height);
        System.out.println("平衡因子: " + root1.getBalance());

        // 建立一個不平衡的樹 (需要左旋)
        System.out.println("\n===== 測試左旋 =====");
        AVLNode root2 = new AVLNode(10);
        root2.right = new AVLNode(20);
        root2.right.right = new AVLNode(30);
        root2.updateHeight();
        root2.right.updateHeight();

        System.out.println("左旋前:");
        System.out.println("根節點: " + root2.data + ", 高度: " + root2.height);
        System.out.println("平衡因子: " + root2.getBalance());

        // 執行左旋
        root2 = leftRotate(root2);

        System.out.println("左旋後:");
        System.out.println("根節點: " + root2.data + ", 高度: " + root2.height);
        System.out.println("平衡因子: " + root2.getBalance());
    }
}
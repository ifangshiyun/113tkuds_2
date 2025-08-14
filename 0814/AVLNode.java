
public class AVLNode {

    int data;
    AVLNode left, right;
    int height;

    public AVLNode(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.height = 1;
    }

    // 計算平衡因子
    public int getBalance() {
        int leftHeight = (left != null) ? left.height : 0;
        int rightHeight = (right != null) ? right.height : 0;
        return leftHeight - rightHeight;
    }

    // 更新節點高度
    public void updateHeight() {
        int leftHeight = (left != null) ? left.height : 0;
        int rightHeight = (right != null) ? right.height : 0;
        this.height = Math.max(leftHeight, rightHeight) + 1;
    }

    @Override
    public String toString() {
        return "AVLNode{"
                + "data=" + data
                + ", height=" + height
                + '}';
    }

    // main 方法測試
    public static void main(String[] args) {
        AVLNode node = new AVLNode(10);
        node.left = new AVLNode(5);
        node.right = new AVLNode(15);
        node.updateHeight();
        System.out.println(node);
        System.out.println("Balance: " + node.getBalance());
    }
}
import java.util.*;

// 題 10：紅黑樹性質檢查
// 規則：
// 1. 根必為黑
// 2. 不得紅紅相鄰
// 3. 自任一節點至所有 NIL 路徑黑高度一致
//
// 複雜度：每個節點僅訪問一次 O(n)，遞迴深度 O(h)，h ≤ n。
public class M10_RBPropertiesCheck {
    static int n;
    static int[] vals;
    static char[] cols;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        vals = new int[n];
        cols = new char[n];
        for (int i = 0; i < n; i++) {
            vals[i] = sc.nextInt();
            cols[i] = sc.next().charAt(0);
        }
        sc.close();

        // 1) 根必為黑
        if (n > 0 && vals[0] != -1 && cols[0] != 'B') {
            System.out.println("RootNotBlack");
            return;
        }

        // 2) 紅紅相鄰檢查
        String redRed = checkRedRed(0);
        if (redRed != null) {
            System.out.println(redRed);
            return;
        }

        // 3) 黑高度一致
        if (!checkBlackHeight(0).valid) {
            System.out.println("BlackHeightMismatch");
            return;
        }

        System.out.println("RB Valid");
    }

    // ===== 紅紅相鄰檢查 =====
    private static String checkRedRed(int i) {
        if (i >= n || vals[i] == -1) return null;
        if (cols[i] == 'R') {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            if (left < n && vals[left] != -1 && cols[left] == 'R') {
                return "RedRedViolation at index " + left;
            }
            if (right < n && vals[right] != -1 && cols[right] == 'R') {
                return "RedRedViolation at index " + right;
            }
        }
        String res = checkRedRed(2 * i + 1);
        if (res != null) return res;
        return checkRedRed(2 * i + 2);
    }

    // ===== 黑高度檢查 =====
    static class BHInfo {
        int blackHeight;
        boolean valid;
        BHInfo(int h, boolean v) { blackHeight = h; valid = v; }
    }

    private static BHInfo checkBlackHeight(int i) {
        if (i >= n || vals[i] == -1) {
            return new BHInfo(1, true); // NIL 為黑，高度+1
        }
        BHInfo left = checkBlackHeight(2 * i + 1);
        BHInfo right = checkBlackHeight(2 * i + 2);
        if (!left.valid || !right.valid) return new BHInfo(0, false);
        if (left.blackHeight != right.blackHeight) return new BHInfo(0, false);
        int add = (cols[i] == 'B') ? 1 : 0;
        return new BHInfo(left.blackHeight + add, true);
    }
}

/* Terminal Run Result
ifangelinetosani@ifangelines-MacBook-Air 113tkuds_2 % cd /Users/ifangelinetosani/Documents/GitHub/113tkuds_2/midterm
ifangelinetosani@ifangelines-MacBook-Air midterm % javac M10_RBPropertiesCheck.java
ifangelinetosani@ifangelines-MacBook-Air midterm % java M10_RBPropertiesCheck
3
10 R 5 B 15 B
RootNotBlack
 */
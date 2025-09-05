import java.io.*;
import java.util.*;

/**
 * 題目 1 高鐵連假加班車 Two Sum
 * 讀入：
 *   第一行：n target
 *   第二行：n 個整數（座位數）
 * 輸出：
 *   兩個索引 i j（任一組解即可），無解輸出 "-1 -1"
 *
 * 作法：一次遍歷 HashMap<需要的數, 索引>，O(n) 時間 / O(n) 空間
 */
public class LC01_TwoSum_THSRHoliday {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        if (!fs.hasNext()) {
            // 沒有讀到任何輸入時的友善提示（不影響評測，平常手動執行才會看到）
            System.out.println("請輸入：\\n第一行：n target\\n第二行：n 個整數");
            return;
        }

        int n = fs.nextInt();
        long target = fs.nextLong();

        long[] a = new long[n];
        for (int i = 0; i < n; i++) a[i] = fs.nextLong();

        int[] ans = twoSum(a, target);
        System.out.println(ans[0] + " " + ans[1]);
    }

    // 回傳兩個索引；若無解回傳 {-1, -1}
    static int[] twoSum(long[] nums, long target) {
        Map<Long, Integer> need = new HashMap<>(); // key: 需要的數, val: 索引

        for (int i = 0; i < nums.length; i++) {
            long x = nums[i];
            Integer j = need.get(x);
            if (j != null) {
                return new int[]{j, i}; // 找到解
            }
            long want = target - x;
            // 只記第一次出現的索引即可（多解任一）
            need.putIfAbsent(want, i);
        }
        return new int[]{-1, -1};
    }

    // 簡單快速輸入工具
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream is) { this.in = is; }

        boolean hasNext() throws IOException {
            int c;
            while ((c = read()) != -1) {
                if (c > ' ') { ptr--; return true; }
            }
            return false;
        }

        int nextInt() throws IOException { return (int) nextLong(); }

        long nextLong() throws IOException {
            int c = skipBlanks();
            int sign = 1;
            if (c == '-') { sign = -1; c = read(); }
            long val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }

        private int skipBlanks() throws IOException {
            int c;
            while ((c = read()) <= ' ') {
                if (c == -1) return -1;
            }
            return c;
        }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }
    }
}

import java.io.*;
import java.util.*;

public class LC27_RemoveElement_Recycle {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] first = br.readLine().trim().split("\\s+");
        int n = Integer.parseInt(first[0]);
        int val = Integer.parseInt(first[1]);

        if (n == 0) {
            System.out.println(0);
            return;
        }

        String[] parts = br.readLine().trim().split("\\s+");
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = Integer.parseInt(parts[i]);

        int newLen = removeElement(nums, val);

        // 輸出新長度
        System.out.println(newLen);
        // 輸出前段結果
        for (int i = 0; i < newLen; i++) {
            System.out.print(nums[i]);
            if (i < newLen - 1) System.out.print(" ");
        }
    }

    // O(n) in-place overwrite
    static int removeElement(int[] nums, int val) {
        int write = 0;
        for (int x : nums) {
            if (x != val) {
                nums[write++] = x;
            }
        }
        return write;
    }
}

import java.io.*;
import java.util.*;

public class LC26_RemoveDuplicates_Scores {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 讀 n
        String line = br.readLine();
        if (line == null || line.trim().isEmpty()) {
            return;
        }
        int n = Integer.parseInt(line.trim());
        if (n == 0) {
            System.out.println(0);
            return;
        }

        // 讀 n 個升序整數
        String[] parts = br.readLine().trim().split("\\s+");
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(parts[i]);
        }

        int newLen = removeDuplicates(nums);

        // 輸出新長度
        System.out.println(newLen);

        // 輸出壓縮後前段結果
        for (int i = 0; i < newLen; i++) {
            System.out.print(nums[i]);
            if (i < newLen - 1) System.out.print(" ");
        }
    }

    // 雙指針：O(n) 時間，O(1) 空間
    static int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        int write = 1; // 下個可寫入位置
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[write - 1]) {
                nums[write] = nums[i];
                write++;
            }
        }
        return write;
    }
}

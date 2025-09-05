import java.io.*;
import java.util.*;

public class LC34_SearchRange_DelaySpan {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] first = br.readLine().trim().split("\\s+");
        int n = Integer.parseInt(first[0]);
        int target = Integer.parseInt(first[1]);

        int[] nums = new int[n];
        if (n > 0) {
            // read possibly spaced numbers
            int filled = 0;
            while (filled < n) {
                String line = br.readLine();
                if (line == null) break;
                String[] parts = line.trim().split("\\s+");
                for (String p : parts) {
                    if (p.isEmpty()) continue;
                    nums[filled++] = Integer.parseInt(p);
                    if (filled == n) break;
                }
            }
        }

        int[] ans = searchRange(nums, target);
        System.out.println(ans[0] + " " + ans[1]);
    }

    // O(log n) using two binary searches
    static int[] searchRange(int[] nums, int target) {
        int n = nums.length;
        int left = lowerBound(nums, target);
        if (left == n || nums[left] != target) return new int[]{-1, -1};
        int right = upperBound(nums, target) - 1;
        return new int[]{left, right};
    }

    // First index i such that nums[i] >= x (returns n if none)
    static int lowerBound(int[] a, int x) {
        int l = 0, r = a.length;
        while (l < r) {
            int m = l + (r - l) / 2;
            if (a[m] < x) l = m + 1;
            else r = m;
        }
        return l;
    }

    // First index i such that nums[i] > x (returns n if none)
    static int upperBound(int[] a, int x) {
        int l = 0, r = a.length;
        while (l < r) {
            int m = l + (r - l) / 2;
            if (a[m] <= x) l = m + 1;
            else r = m;
        }
        return l;
    }
}

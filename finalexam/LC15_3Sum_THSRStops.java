import java.io.*;
import java.util.*;

public class LC15_3Sum_THSRStops {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        // Read n integers (handle line breaks / extra spaces)
        int[] a = new int[n];
        int filled = 0;
        while (filled < n) {
            String line = br.readLine();
            if (line == null) break;
            String[] parts = line.trim().split("\\s+");
            for (String p : parts) {
                if (p.isEmpty()) continue;
                a[filled++] = Integer.parseInt(p);
                if (filled == n) break;
            }
        }

        List<int[]> ans = threeSum(a);

        StringBuilder sb = new StringBuilder();
        for (int[] t : ans) {
            sb.append(t[0]).append(' ').append(t[1]).append(' ').append(t[2]).append('\n');
        }
        System.out.print(sb.toString());
    }

    // Sort + two pointers, skip duplicates. O(n^2)
    static List<int[]> threeSum(int[] nums) {
        List<int[]> res = new ArrayList<>();
        Arrays.sort(nums);
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;    // skip same i
            if (nums[i] > 0) break;                            // early stop

            int target = -nums[i];
            int l = i + 1, r = n - 1;

            while (l < r) {
                int sum = nums[l] + nums[r];
                if (sum == target) {
                    res.add(new int[]{nums[i], nums[l], nums[r]});
                    // skip duplicates for l and r
                    int lv = nums[l], rv = nums[r];
                    while (l < r && nums[l] == lv) l++;
                    while (l < r && nums[r] == rv) r--;
                } else if (sum < target) {
                    l++;
                } else {
                    r--;
                }
            }
        }
        return res;
    }
}

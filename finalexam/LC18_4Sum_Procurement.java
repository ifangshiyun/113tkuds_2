import java.io.*;
import java.util.*;

public class LC18_4Sum_Procurement {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] first = br.readLine().trim().split("\\s+");
        int n = Integer.parseInt(first[0]);
        long target = Long.parseLong(first[1]);

        String[] parts = br.readLine().trim().split("\\s+");
        long[] nums = new long[n];
        for (int i = 0; i < n; i++) nums[i] = Long.parseLong(parts[i]);

        List<long[]> ans = fourSum(nums, target);

        StringBuilder sb = new StringBuilder();
        for (long[] quad : ans) {
            sb.append(quad[0]).append(' ')
              .append(quad[1]).append(' ')
              .append(quad[2]).append(' ')
              .append(quad[3]).append('\n');
        }
        System.out.print(sb.toString());
    }

    // O(n^3) two-pointer
    static List<long[]> fourSum(long[] nums, long target) {
        List<long[]> res = new ArrayList<>();
        Arrays.sort(nums);
        int n = nums.length;

        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue; // skip dup i
            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue; // skip dup j

                int l = j + 1, r = n - 1;
                while (l < r) {
                    long sum = nums[i] + nums[j] + nums[l] + nums[r];
                    if (sum == target) {
                        res.add(new long[]{nums[i], nums[j], nums[l], nums[r]});

                        long lv = nums[l], rv = nums[r];
                        while (l < r && nums[l] == lv) l++;
                        while (l < r && nums[r] == rv) r--;
                    } else if (sum < target) {
                        l++;
                    } else {
                        r--;
                    }
                }
            }
        }
        return res;
    }
}

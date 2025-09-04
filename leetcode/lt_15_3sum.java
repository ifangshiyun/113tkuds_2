import java.util.*;

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue; // skip duplicate a
            if (nums[i] > 0) break; // smallest is > 0 ⇒ no triplet can sum to 0

            int left = i + 1, right = n - 1, target = -nums[i];
            while (left < right) {
                int sum = nums[left] + nums[right];
                if (sum == target) {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    int lv = nums[left], rv = nums[right];
                    while (left < right && nums[left] == lv) left++;   // skip duplicate b
                    while (left < right && nums[right] == rv) right--; // skip duplicate c
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return res;
    }
}

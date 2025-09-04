class Solution {
    public int[] searchRange(int[] nums, int target) {
        int left = lowerBound(nums, target);      // first index with value >= target
        if (left == nums.length || nums[left] != target) {
            return new int[]{-1, -1};            // target not present
        }
        int right = upperBound(nums, target) - 1; // last index with value == target
        return new int[]{left, right};
    }

    // returns first index i such that nums[i] >= target (or nums.length if none)
    private int lowerBound(int[] a, int target) {
        int l = 0, r = a.length;
        while (l < r) {
            int m = l + (r - l) / 2;
            if (a[m] < target) l = m + 1;
            else r = m;
        }
        return l;
    }

    // returns first index i such that nums[i] > target (or nums.length if none)
    private int upperBound(int[] a, int target) {
        int l = 0, r = a.length;
        while (l < r) {
            int m = l + (r - l) / 2;
            if (a[m] <= target) l = m + 1;
            else r = m;
        }
        return l;
    }
}

/*
Explanation (simple):
- Array is sorted. We can binary search for the first and last positions of target in O(log n).
- lowerBound: first index where value >= target.
- upperBound: first index where value > target.
- If nums[left] != target, target doesn't exist → return [-1, -1].
- Otherwise, the range is [left, upperBound(target) - 1].
- Time: O(log n) for each bound (overall O(log n)). Space: O(1).
*/

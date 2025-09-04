class Solution {
    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (nums[m] == target) return m;

            // Determine which half is sorted
            if (nums[l] <= nums[m]) { // left half is sorted
                if (nums[l] <= target && target < nums[m]) {
                    r = m - 1; // target in left half
                } else {
                    l = m + 1; // target in right half
                }
            } else { // right half is sorted
                if (nums[m] < target && target <= nums[r]) {
                    l = m + 1; // target in right half
                } else {
                    r = m - 1; // target in left half
                }
            }
        }
        return -1; // not found
    }
}

/*
Explanation (simple):
- The array is sorted but rotated once. In any position, at least one half (left or right of mid) is still sorted.
- Binary search:
  - Compare nums[l], nums[m], nums[r] to detect which half is sorted.
  - If target falls within the sorted half's range, search that half; otherwise search the other half.
- Repeat until found or l > r.
- Time: O(log n). Space: O(1).
*/

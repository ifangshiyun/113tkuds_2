class Solution {
    public int searchInsert(int[] nums, int target) {
        int l = 0, r = nums.length; // search in [l, r)
        while (l < r) {
            int m = l + (r - l) / 2;
            if (nums[m] < target) {
                l = m + 1;       // target is to the right
            } else {
                r = m;           // target could be here or to the left
            }
        }
        return l; // first index with nums[i] >= target (insert position)
    }
}

/*
Explanation (simple):
- We need the index of target if present; otherwise the position where it should be inserted.
- Use binary search for the first index i where nums[i] >= target (a "lower_bound").
- Maintain half-open interval [l, r):
  - If nums[m] < target, discard left half (l = m + 1).
  - Else, keep left half including m (r = m).
- Loop ends when l == r; that's exactly the insertion index (and the index of target if it exists).
- Time: O(log n), Space: O(1).
*/

class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int k = 1; // position to place next unique number
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                nums[k] = nums[i];
                k++;
            }
        }
        return k;
    }
}

/*
Explanation (simple):
- The array is sorted, so duplicates appear next to each other.
- Use two pointers:
  - i scans the array from left to right.
  - k tracks the position to place the next unique value.
- Start k at 1 (the first element is always unique).
- For each i from 1..n-1:
  - If nums[i] != nums[i-1], it's a new unique value → assign nums[k] = nums[i], then k++.
- After the loop, the first k elements are the unique values in order.
- Return k as the count of unique elements.
- Time: O(n). Space: O(1), in-place.
*/

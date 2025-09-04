class Solution {
    public void nextPermutation(int[] nums) {
        int n = nums.length;
        if (n < 2) return;

        // 1) Find the first index i from the right where nums[i] < nums[i+1]
        int i = n - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) i--;

        if (i >= 0) {
            // 2) Find the first index j from the right where nums[j] > nums[i]
            int j = n - 1;
            while (nums[j] <= nums[i]) j--;
            // 3) Swap i and j
            swap(nums, i, j);
        }

        // 4) Reverse the suffix starting at i+1 to get the next smallest order
        reverse(nums, i + 1, n - 1);
    }

    private void reverse(int[] a, int l, int r) {
        while (l < r) swap(a, l++, r--);
    }

    private void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}

/*
Explanation (simple):
- We want the next lexicographically larger permutation.
- Scan from right to left to find the first "ascending" pair nums[i] < nums[i+1]. This i is the "pivot".
  - If no such i exists (array is non-increasing like [3,2,1]), reverse the whole array to get the smallest order.
- Otherwise:
  1) From the right, find the first element nums[j] > nums[i].
  2) Swap nums[i] and nums[j] to make the number just larger at position i.
  3) Reverse the suffix (i+1 ... end) to make it as small as possible.
- Runs in O(n) time and O(1) extra space, in-place.
*/

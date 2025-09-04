class Solution {
    public int removeElement(int[] nums, int val) {
        int k = 0; // next position to place a kept element
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[k] = nums[i];
                k++;
            }
        }
        return k;
    }
}

/*
Explanation (simple):
- We scan the array once. If nums[i] is not equal to val, we keep it by writing it to the next free spot at index k, then k++.
- Elements equal to val are skipped and effectively removed from the first k positions.
- After the loop, the first k elements of nums are exactly the elements not equal to val (order preserved).
- The rest of the array can be anything and does not matter.
- Time: O(n) single pass. Space: O(1) in-place.
*/

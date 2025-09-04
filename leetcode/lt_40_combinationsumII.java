import java.util.*;

class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates); // sort to group duplicates and enable pruning
        List<List<Integer>> res = new ArrayList<>();
        backtrack(candidates, target, 0, new ArrayList<>(), res);
        return res;
    }

    private void backtrack(int[] nums, int remain, int start, List<Integer> path, List<List<Integer>> res) {
        if (remain == 0) {                // found a valid combination
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) continue; // skip duplicates on the same depth
            if (nums[i] > remain) break;   // pruning since array is sorted
            path.add(nums[i]);
            backtrack(nums, remain - nums[i], i + 1, path, res); // i+1: each number used at most once
            path.remove(path.size() - 1);  // undo choice
        }
    }
}

/*
Explanation (simple):
- We need unique combinations summing to target; each candidate can be used at most once.
- Sort the array to:
  1) stop early when nums[i] > remain (pruning),
  2) skip duplicate values at the same recursion depth (i > start && nums[i] == nums[i-1]).
- Backtracking:
  - Choose nums[i], reduce remain, and move start to i+1 so we don't reuse the same element.
  - When remain == 0, record the current path.
  - If nums[i] > remain, break (further numbers are larger due to sorting).
- This ensures no duplicate combinations and uses only constant extra memory besides recursion/path.
- Time: exponential in worst case, but pruning helps; Space: O(k) recursion depth (k ≤ candidates.length).
*/

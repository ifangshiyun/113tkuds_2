import java.util.*;

class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates); // sort to enable early pruning
        List<List<Integer>> res = new ArrayList<>();
        backtrack(candidates, target, 0, new ArrayList<>(), res);
        return res;
    }

    private void backtrack(int[] cand, int remain, int start, List<Integer> path, List<List<Integer>> res) {
        if (remain == 0) {                 // found a valid combination
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i < cand.length; i++) {
            if (cand[i] > remain) break;   // pruning: numbers are positive & sorted
            path.add(cand[i]);             // choose cand[i]
            backtrack(cand, remain - cand[i], i, path, res); // i (not i+1) because reuse allowed
            path.remove(path.size() - 1);  // undo choice
        }
    }
}

/*
Explanation (simple):
- We need all unique combinations that sum to target; each number can be reused.
- Use backtracking:
  - Sort candidates to prune early when cand[i] > remain.
  - At each step, try candidates from index 'start' to avoid permutations of the same combo.
    (Using 'i' again allows unlimited reuse of the same number.)
  - When remain == 0, record the current path.
  - Backtrack by removing the last chosen number and continue exploring.
- This generates each combination once (no duplicates).
- Time: exponential in the worst case, but pruning with sorting helps.
- Space: O(target/min(candidates)) for recursion depth and path.
*/

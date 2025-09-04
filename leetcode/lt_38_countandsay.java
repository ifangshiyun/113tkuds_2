class Solution {
    public String countAndSay(int n) {
        String s = "1";                 // base case: countAndSay(1) = "1"
        for (int i = 2; i <= n; i++) {  // build from 2 up to n
            StringBuilder next = new StringBuilder();
            int count = 1;
            for (int j = 1; j <= s.length(); j++) {
                if (j < s.length() && s.charAt(j) == s.charAt(j - 1)) {
                    count++; // keep counting same digit
                } else {
                    // run ends: say "count" + "digit"
                    next.append(count).append(s.charAt(j - 1));
                    count = 1; // reset for next run
                }
            }
            s = next.toString(); // move to next term
        }
        return s;
    }
}

/*
Explanation (simple):
- The sequence is defined by "saying" the previous term: for each group of identical digits, 
  write the count followed by the digit (run-length encoding of digits).
- Start with "1". For each step up to n, scan the current string, count consecutive equal chars,
  append "<count><digit>" to build the next string.
- Example: "21" -> "1211" because there is one '2' (-> "12") then one '1' (-> "11").
- Time: O(total length up to term n). Space: O(length of current term). 
  With n ≤ 30 this is well within limits.
*/

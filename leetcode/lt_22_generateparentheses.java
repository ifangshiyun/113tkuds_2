import java.util.*;

class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        backtrack(res, new StringBuilder(), n, 0, 0);
        return res;
    }

    // open = number of '(' used, close = number of ')' used
    private void backtrack(List<String> res, StringBuilder cur, int n, int open, int close) {
        if (cur.length() == 2 * n) {
            res.add(cur.toString());
            return;
        }
        if (open < n) {
            cur.append('(');
            backtrack(res, cur, n, open + 1, close);
            cur.deleteCharAt(cur.length() - 1);
        }
        if (close < open) {
            cur.append(')');
            backtrack(res, cur, n, open, close + 1);
            cur.deleteCharAt(cur.length() - 1);
        }
    }
}

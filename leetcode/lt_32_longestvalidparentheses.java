import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int longestValidParentheses(String s) {
        int n = s.length();
        int maxLen = 0;

        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1); // sentinel: base index before a valid substring starts

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(i); // store index of '('
            } else {
                stack.pop();   // try to match a previous '('
                if (stack.isEmpty()) {
                    // no base to measure from; set new base after this ')'
                    stack.push(i);
                } else {
                    // valid substring ends at i; length is i - last_unmatched_index
                    maxLen = Math.max(maxLen, i - stack.peek());
                }
            }
        }
        return maxLen;
    }
}

/*
Explanation (simple):
- Use a stack of indices to track the start of the current valid block.
- Push -1 as a sentinel "base" index. For each '(' push its index.
- For each ')', pop one:
  - If stack becomes empty, push current index as new base (no matching '(').
  - Else, a valid substring ends at i; its length is i - stack.peek().
- Keep the maximum length seen.
- Time O(n), Space O(n).
*/

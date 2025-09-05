import java.io.*;
import java.util.*;

public class LC32_LongestValidParen_Metro {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        if (s == null || s.isEmpty()) { 
            System.out.println(0); 
            return; 
        }
        System.out.println(longestValidParentheses(s));
    }

    // Stack of indices. Base index -1.
    // Time: O(n), Space: O(n)
    static int longestValidParentheses(String s) {
        Deque<Integer> st = new ArrayDeque<>();
        st.push(-1);               // base for the first valid segment
        int best = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                st.push(i);
            } else { // c == ')'
                st.pop();
                if (st.isEmpty()) {
                    // no matching '(', set new base
                    st.push(i);
                } else {
                    best = Math.max(best, i - st.peek());
                }
            }
        }
        return best;
    }
}

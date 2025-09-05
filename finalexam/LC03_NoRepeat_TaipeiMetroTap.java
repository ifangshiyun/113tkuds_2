import java.io.*;
import java.util.*;

public class LC03_NoRepeat_TaipeiMetroTap {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        if (s == null) { System.out.println(0); return; }
        System.out.println(lengthOfLongestSubstring(s));
    }

    // Sliding window, track last seen index of each ASCII char
    // Time: O(n), Space: O(k) (k <= 256 for ASCII)
    static int lengthOfLongestSubstring(String s) {
        int[] last = new int[256];
        Arrays.fill(last, -1);

        int ans = 0, left = 0; // window [left..i]
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int ci = c & 0xFF;       // ASCII
            if (last[ci] >= left) {  // repeated inside window → move left
                left = last[ci] + 1;
            }
            last[ci] = i;            // update last seen
            ans = Math.max(ans, i - left + 1);
        }
        return ans;
    }

    /*  If you prefer a Map-based version that works for full Unicode:
    static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> last = new HashMap<>();
        int ans = 0, left = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            Integer prev = last.get(c);
            if (prev != null && prev >= left) left = prev + 1;
            last.put(c, i);
            ans = Math.max(ans, i - left + 1);
        }
        return ans;
    }
    */
}

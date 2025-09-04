import java.util.HashMap;
import java.util.Map;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) return 0;

        Map<Character, Integer> lastIndex = new HashMap<>();
        int maxLen = 0;
        int left = 0; // start of current window

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            // If we've seen c and it's inside current window, move left past its last index
            if (lastIndex.containsKey(c) && lastIndex.get(c) >= left) {
                left = lastIndex.get(c) + 1;
            }

            lastIndex.put(c, right);                 // update last seen index
            maxLen = Math.max(maxLen, right - left + 1); // update answer
        }

        return maxLen;
    }
}

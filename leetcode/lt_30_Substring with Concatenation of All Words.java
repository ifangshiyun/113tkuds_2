import java.util.*;

class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        if (s == null || words == null || words.length == 0) return res;

        int n = s.length();
        int w = words[0].length();
        int k = words.length;
        int windowLen = w * k;
        if (n < windowLen) return res;

        // Count required frequency of each word
        Map<String, Integer> need = new HashMap<>();
        for (String t : words) need.put(t, need.getOrDefault(t, 0) + 1);

        // Try each starting offset within one word length
        for (int offset = 0; offset < w; offset++) {
            int left = offset, count = 0;
            Map<String, Integer> seen = new HashMap<>();

            for (int right = offset; right + w <= n; right += w) {
                String cur = s.substring(right, right + w);

                if (need.containsKey(cur)) {
                    seen.put(cur, seen.getOrDefault(cur, 0) + 1);
                    count++;

                    // If cur is over-used, shrink from the left
                    while (seen.get(cur) > need.get(cur)) {
                        String drop = s.substring(left, left + w);
                        seen.put(drop, seen.get(drop) - 1);
                        left += w;
                        count--;
                    }

                    // If we collected k words, record answer and slide one word
                    if (count == k) {
                        res.add(left);
                        String drop = s.substring(left, left + w);
                        seen.put(drop, seen.get(drop) - 1);
                        left += w;
                        count--;
                    }
                } else {
                    // Not a valid word: reset window after this position
                    seen.clear();
                    count = 0;
                    left = right + w;
                }
            }
        }
        return res;
    }
}

/*
Explanation (simple):
- All words have the same length w. The target window size is w * k (k = number of words).
- Build a frequency map 'need' for words.
- For each offset in [0..w-1], slide a window in steps of w:
  - Read the next chunk of length w. If it's a needed word, add to 'seen'.
  - If any word is seen too many times, move 'left' forward by w until counts are valid.
  - When we have exactly k words in the window (count == k), the window start 'left' is a valid index.
    Record it, then slide one word to continue searching.
  - If a chunk is not a needed word, clear 'seen' and restart the window after it.
- Time: O(n) chunks processed (each index visited at most once per offset). Space: O(k) for maps.
*/

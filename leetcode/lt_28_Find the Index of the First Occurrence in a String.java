class Solution {
    public int strStr(String haystack, String needle) {
        if (needle.length() == 0) return 0; // (constraints say >=1, but safe)
        int[] lps = buildLPS(needle); // longest proper prefix which is also suffix

        int i = 0, j = 0; // i for haystack, j for needle
        while (i < haystack.length()) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++; j++;
                if (j == needle.length()) {
                    return i - j; // found match ending at i-1
                }
            } else {
                if (j > 0) {
                    j = lps[j - 1]; // fallback in needle
                } else {
                    i++; // move forward in haystack
                }
            }
        }
        return -1; // not found
    }

    // Build LPS (prefix function) for KMP
    private int[] buildLPS(String p) {
        int n = p.length();
        int[] lps = new int[n];
        int len = 0; // length of current longest prefix-suffix
        for (int i = 1; i < n; ) {
            if (p.charAt(i) == p.charAt(len)) {
                lps[i++] = ++len;
            } else if (len > 0) {
                len = lps[len - 1];
            } else {
                lps[i++] = 0;
            }
        }
        return lps;
    }
}

/*
Explanation (simple):
- We use KMP string matching to find the first occurrence of 'needle' in 'haystack' in O(n + m) time.
- KMP precomputes an LPS array (Longest Prefix which is also Suffix) for 'needle':
  - lps[j] tells us, after a mismatch at needle[j], where to continue in 'needle' without re-checking matched chars.
- Matching phase:
  - Move i over 'haystack' and j over 'needle'. When chars match, i++ and j++.
  - On mismatch:
    - If j > 0, set j = lps[j-1] (shift the pattern using LPS).
    - Else, i++ (advance haystack).
- When j reaches needle.length(), a full match ends at i-1, so the start index is i - j.
- If we reach the end without full match, return -1.

Complexity:
- Building LPS: O(m), Matching: O(n), total O(n + m).
- Space: O(m) for the LPS array.
*/

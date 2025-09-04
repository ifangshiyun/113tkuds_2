class Solution {
    public String longestPalindrome(String s) {
        int n = s.length();
        if (n < 2) return s;

        int start = 0, maxLen = 1;

        for (int center = 0; center < n; center++) {
            // Odd-length palindrome (single center)
            int[] odd = expand(s, center, center);
            if (odd[1] > maxLen) {
                start = odd[0];
                maxLen = odd[1];
            }

            // Even-length palindrome (between center and center+1)
            int[] even = expand(s, center, center + 1);
            if (even[1] > maxLen) {
                start = even[0];
                maxLen = even[1];
            }
        }

        return s.substring(start, start + maxLen);
    }

    // Returns {startIndex, length} of the palindrome expanded from (l, r)
    private int[] expand(String s, int l, int r) {
        int n = s.length();
        while (l >= 0 && r < n && s.charAt(l) == s.charAt(r)) {
            l--; r++;
        }
        // After the loop, l and r are one step beyond the palindrome
        int start = l + 1;
        int len = r - l - 1;
        return new int[]{start, len};
    }
}

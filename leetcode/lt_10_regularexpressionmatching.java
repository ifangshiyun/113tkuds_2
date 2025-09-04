class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];

        // Empty string matches empty pattern
        dp[0][0] = true;

        // Initialize dp for patterns like a*, a*b*, a*b*c*, ...
        for (int j = 2; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char pc = p.charAt(j - 1);

                if (pc == '.' || pc == s.charAt(i - 1)) {
                    // current chars match; inherit from diagonal
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (pc == '*') {
                    // '*' can eliminate its preceding element (zero times)
                    dp[i][j] = dp[i][j - 2];

                    // or, if preceding element matches current s char,
                    // we can "use" '*' to match one more of that element
                    char prev = p.charAt(j - 2);
                    if (prev == '.' || prev == s.charAt(i - 1)) {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                } else {
                    dp[i][j] = false;
                }
            }
        }

        return dp[m][n];
    }
}

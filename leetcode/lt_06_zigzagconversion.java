class Solution {
    public String convert(String s, int numRows) {
        int n = s.length();
        if (numRows == 1 || numRows >= n) return s;

        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) rows[i] = new StringBuilder();

        int row = 0;
        int dir = 1; // 1 = going down, -1 = going up

        for (int i = 0; i < n; i++) {
            rows[row].append(s.charAt(i));
            // Turn around at top or bottom
            if (row == 0) dir = 1;
            else if (row == numRows - 1) dir = -1;
            row += dir;
        }

        StringBuilder ans = new StringBuilder();
        for (StringBuilder sb : rows) ans.append(sb);
        return ans.toString();
    }
}

class Solution {
    public int romanToInt(String s) {
        int res = 0;
        int prev = 0; // value to the right (max seen so far)
        for (int i = s.length() - 1; i >= 0; i--) {
            int val = valueOf(s.charAt(i));
            if (val < prev) res -= val;  // subtractive case
            else {
                res += val;
                prev = val;
            }
        }
        return res;
    }

    private int valueOf(char c) {
        switch (c) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            default:  return 1000; // 'M'
        }
    }
}

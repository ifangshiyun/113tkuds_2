import java.util.*;

class Solution {
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits == null || digits.length() == 0) return res;

        String[] map = new String[]{
            "",     "",     "abc", "def", "ghi",
            "jkl",  "mno",  "pqrs","tuv","wxyz"
        };

        backtrack(digits, 0, new StringBuilder(), map, res);
        return res;
    }

    private void backtrack(String digits, int idx, StringBuilder path, String[] map, List<String> res) {
        if (idx == digits.length()) {
            res.add(path.toString());
            return;
        }
        String letters = map[digits.charAt(idx) - '0'];
        for (int i = 0; i < letters.length(); i++) {
            path.append(letters.charAt(i));
            backtrack(digits, idx + 1, path, map, res);
            path.deleteCharAt(path.length() - 1);
        }
    }
}

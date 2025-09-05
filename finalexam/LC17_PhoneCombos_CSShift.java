import java.io.*;
import java.util.*;

public class LC17_PhoneCombos_CSShift {

    private static final String[] MAP = {
        "",     // 0
        "",     // 1
        "abc",  // 2
        "def",  // 3
        "ghi",  // 4
        "jkl",  // 5
        "mno",  // 6
        "pqrs", // 7
        "tuv",  // 8
        "wxyz"  // 9
    };

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String digits = br.readLine();
        if (digits == null || digits.isEmpty()) return;

        List<String> res = letterCombinations(digits.trim());

        StringBuilder sb = new StringBuilder();
        for (String s : res) {
            sb.append(s).append("\n");
        }
        System.out.print(sb.toString());
    }

    // Backtracking
    static List<String> letterCombinations(String digits) {
        List<String> ans = new ArrayList<>();
        if (digits.isEmpty()) return ans;
        backtrack(digits, 0, new StringBuilder(), ans);
        return ans;
    }

    static void backtrack(String digits, int idx, StringBuilder path, List<String> ans) {
        if (idx == digits.length()) {
            ans.add(path.toString());
            return;
        }
        int d = digits.charAt(idx) - '0';
        String letters = MAP[d];
        for (char c : letters.toCharArray()) {
            path.append(c);
            backtrack(digits, idx + 1, path, ans);
            path.deleteCharAt(path.length() - 1);
        }
    }
}

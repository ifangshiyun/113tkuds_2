import java.io.*;
import java.util.*;

public class LC20_ValidParentheses_AlertFormat {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        if (s == null) {
            System.out.println("true"); // 空字串視為合法
            return;
        }
        System.out.println(isValid(s));
    }

    // 判斷括號是否合法
    static boolean isValid(String s) {
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');

        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            if (map.containsKey(c)) {
                // 遇到閉括號 → 檢查棧頂
                if (stack.isEmpty() || stack.peek() != map.get(c)) {
                    return false;
                }
                stack.pop();
            } else {
                // 開括號 push
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }
}

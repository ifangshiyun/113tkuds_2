import java.util.*;

public class AdvancedStringRecursion {
    public static void main(String[] args) {
        String input = "abc";
        String text = "helloabcworld";
        String pattern = "abc";

        // 1. 所有排列組合
        System.out.println("1. 所有排列組合：");
        permute("", input);

        // 2. 字串匹配（是否為子字串）
        System.out.println("\n2. 是否匹配 '" + pattern + "' in '" + text + "'：");
        System.out.println(match(text, pattern) ? "匹配成功" : "匹配失敗");

        // 3. 移除重複字元
        System.out.println("\n3. 移除重複字元：");
        System.out.println(removeDuplicates(input));

        // 4. 所有子字串組合
        System.out.println("\n4. 所有子字串組合：");
        Set<String> allSubsets = new HashSet<>();
        generateSubsets("", input, allSubsets);
        for (String s : allSubsets) {
            System.out.println(s);
        }
    }

    // 1. 遞迴產生排列組合
    public static void permute(String prefix, String remaining) {
        if (remaining.isEmpty()) {
            System.out.println(prefix);
            return;
        }

        for (int i = 0; i < remaining.length(); i++) {
            permute(prefix + remaining.charAt(i),
                    remaining.substring(0, i) + remaining.substring(i + 1));
        }
    }

    // 2. 遞迴比對 pattern 是否為 text 的子字串
    public static boolean match(String text, String pattern) {
        if (pattern.isEmpty()) return true;
        if (text.length() < pattern.length()) return false;
        if (text.startsWith(pattern)) return true;
        return match(text.substring(1), pattern);
    }

    // 3. 遞迴移除重複字元
    public static String removeDuplicates(String str) {
        return removeDuplicatesHelper(str, "", new HashSet<>());
    }

    public static String removeDuplicatesHelper(String str, String result, Set<Character> seen) {
        if (str.isEmpty()) return result;
        char c = str.charAt(0);
        if (seen.contains(c)) {
            return removeDuplicatesHelper(str.substring(1), result, seen);
        } else {
            seen.add(c);
            return removeDuplicatesHelper(str.substring(1), result + c, seen);
        }
    }

    // 4. 遞迴產生所有子字串組合（子集）
    public static void generateSubsets(String current, String remaining, Set<String> result) {
        if (remaining.isEmpty()) {
            if (!current.isEmpty()) {
                result.add(current);
            }
            return;
        }

        generateSubsets(current + remaining.charAt(0), remaining.substring(1), result); // include
        generateSubsets(current, remaining.substring(1), result); // exclude
    }
}

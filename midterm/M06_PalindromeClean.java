
import java.util.*;

// 限制：字串長度 ≤ 1e5
// 複雜度分析：
//   清洗字串 O(n)，檢測 O(n)，總時間 O(n)，空間 O(n)。
//   n = 輸入字串長度。
public class M06_PalindromeClean {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        sc.close();

        // 清洗字串 → 只留英文字母並轉小寫
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                sb.append(Character.toLowerCase(c));
            }
        }
        String cleaned = sb.toString();

        // 遞迴檢測
        boolean ok = isPalindrome(cleaned, 0, cleaned.length() - 1);
        System.out.println(ok ? "Yes" : "No");
    }

    // 遞迴判斷回文
    private static boolean isPalindrome(String s, int l, int r) {
        if (l >= r) return true;                // base case
        if (s.charAt(l) != s.charAt(r)) return false;
        return isPalindrome(s, l + 1, r - 1);   // recursive step
    }
}

/* Terminal Run Result
ifangelinetosani@ifangelines-MacBook-Air midterm % cd /Users/ifangelinetosani/Documents/GitHub/113tkuds_2/midterm
ifangelinetosani@ifangelines-MacBook-Air midterm % javac M06_PalindromeClean.java
ifangelinetosani@ifangelines-MacBook-Air midterm % java M06_PalindromeClean
A man, a plan, a canal: Panama
Yes
 */
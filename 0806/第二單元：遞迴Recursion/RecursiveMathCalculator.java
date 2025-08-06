import java.util.Scanner;

public class RecursiveMathCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 測試組合數
        System.out.println("輸入 n 和 k 計算組合數 C(n, k)：");
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        System.out.println("C(" + n + ", " + k + ") = " + combination(n, k));

        // 測試卡塔蘭數
        System.out.println("\n輸入 n 計算卡塔蘭數 Catalan(n)：");
        int catalanN = scanner.nextInt();
        System.out.println("Catalan(" + catalanN + ") = " + catalan(catalanN));

        // 測試漢諾塔步數
        System.out.println("\n輸入 n 計算漢諾塔移動步數：");
        int hanoiN = scanner.nextInt();
        System.out.println("Hanoi(" + hanoiN + ") = " + hanoiSteps(hanoiN));

        // 測試是否為回文數
        System.out.println("\n輸入一個整數判斷是否為回文數：");
        int number = scanner.nextInt();
        if (isPalindrome(Integer.toString(number))) {
            System.out.println(number + " 是回文數");
        } else {
            System.out.println(number + " 不是回文數");
        }

        scanner.close();
    }

    // 組合數 C(n, k) = C(n-1, k-1) + C(n-1, k)
    public static int combination(int n, int k) {
        if (k == 0 || k == n) return 1;
        return combination(n - 1, k - 1) + combination(n - 1, k);
    }

    // 卡塔蘭數 Catalan(n) = Σ Catalan(i) * Catalan(n - 1 - i)
    public static int catalan(int n) {
        if (n == 0) return 1;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += catalan(i) * catalan(n - 1 - i);
        }
        return sum;
    }

    // 漢諾塔移動步數：H(n) = 2 * H(n - 1) + 1
    public static int hanoiSteps(int n) {
        if (n == 1) return 1;
        return 2 * hanoiSteps(n - 1) + 1;
    }

    // 判斷是否為回文（文字遞迴判斷）
    public static boolean isPalindrome(String s) {
        if (s.length() <= 1) return true;
        if (s.charAt(0) != s.charAt(s.length() - 1)) return false;
        return isPalindrome(s.substring(1, s.length() - 1));
    }
}

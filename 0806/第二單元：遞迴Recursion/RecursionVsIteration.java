import java.util.Stack;

public class RecursionVsIteration {
    public static void main(String[] args) {
        // 測試資料
        int n = 5, k = 2;
        int[] array = {1, 2, 3, 4};
        String text = "RecursionIsCool";
        String brackets = "((a+b)*[c-d])";

        // 1. 二項式係數 C(n, k)
        System.out.println("1. 二項式係數 C(" + n + "," + k + ")");
        System.out.println("遞迴： " + binomialRecursion(n, k));
        System.out.println("迴圈： " + binomialIteration(n, k));

        // 2. 陣列乘積
        System.out.println("\n2. 陣列乘積");
        System.out.println("遞迴： " + productRecursion(array, 0));
        System.out.println("迴圈： " + productIteration(array));

        // 3. 母音數量
        System.out.println("\n3. 字串中母音數量");
        System.out.println("遞迴： " + countVowelsRecursion(text, 0));
        System.out.println("迴圈： " + countVowelsIteration(text));

        // 4. 括號匹配
        System.out.println("\n4. 括號是否匹配");
        System.out.println("遞迴： " + isBalancedRecursion(brackets, 0, 0));
        System.out.println("迴圈： " + isBalancedIteration(brackets));
    }

    // 1. 二項式係數 (n choose k)
    public static int binomialRecursion(int n, int k) {
        if (k == 0 || k == n) return 1;
        return binomialRecursion(n - 1, k - 1) + binomialRecursion(n - 1, k);
    }

    public static int binomialIteration(int n, int k) {
        int[][] C = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= Math.min(i, k); j++) {
                if (j == 0 || j == i) C[i][j] = 1;
                else C[i][j] = C[i - 1][j - 1] + C[i - 1][j];
            }
        }
        return C[n][k];
    }

    // 2. 陣列乘積
    public static int productRecursion(int[] arr, int index) {
        if (index == arr.length) return 1;
        return arr[index] * productRecursion(arr, index + 1);
    }

    public static int productIteration(int[] arr) {
        int result = 1;
        for (int num : arr) {
            result *= num;
        }
        return result;
    }

    // 3. 計算母音數
    public static int countVowelsRecursion(String str, int index) {
        if (index == str.length()) return 0;
        char c = Character.toLowerCase(str.charAt(index));
        int count = (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') ? 1 : 0;
        return count + countVowelsRecursion(str, index + 1);
    }

    public static int countVowelsIteration(String str) {
        int count = 0;
        for (char c : str.toLowerCase().toCharArray()) {
            if ("aeiou".indexOf(c) >= 0) {
                count++;
            }
        }
        return count;
    }

    // 4. 括號是否平衡（遞迴版只判斷圓括號正確）
    public static boolean isBalancedRecursion(String str, int index, int balance) {
        if (balance < 0) return false;
        if (index == str.length()) return balance == 0;

        char c = str.charAt(index);
        if (c == '(') return isBalancedRecursion(str, index + 1, balance + 1);
        if (c == ')') return isBalancedRecursion(str, index + 1, balance - 1);
        return isBalancedRecursion(str, index + 1, balance);
    }

    // 4. 括號是否平衡（迴圈＋堆疊支援所有括號）
    public static boolean isBalancedIteration(String str) {
        Stack<Character> stack = new Stack<>();
        for (char c : str.toCharArray()) {
            if ("([{".indexOf(c) != -1) {
                stack.push(c);
            } else if (")]}".indexOf(c) != -1) {
                if (stack.isEmpty()) return false;
                char top = stack.pop();
                if ((c == ')' && top != '(') ||
                    (c == ']' && top != '[') ||
                    (c == '}' && top != '{')) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}

import java.util.*;

// 限制：1 ≤ a, b ≤ 1e9；避免溢位 → 先除後乘
// 複雜度分析：
//   GCD 使用遞迴歐幾里得演算法，時間 O(log(min(a, b)))，空間 O(log(min(a, b))) (遞迴呼叫堆疊)。
//   LCM 只需一次乘除，O(1)。
//   總體複雜度：O(log(min(a, b))).
public class M05_GCD_LCM_Recursive {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long a = sc.nextLong();
        long b = sc.nextLong();
        sc.close();

        long g = gcd(a, b);
        long l = (a / g) * b;  // 避免溢位：先除後乘

        System.out.println("GCD: " + g);
        System.out.println("LCM: " + l);
    }

    // 遞迴版 Euclidean Algorithm
    private static long gcd(long x, long y) {
        if (y == 0) return x;
        return gcd(y, x % y);
    }
}

/* Terminal Result
ifangelinetosani@ifangelines-MacBook-Air midterm % cd /Users/ifangelinetosani/Documents/GitHub/113tkuds_2/midterm
ifangelinetosani@ifangelines-MacBook-Air midterm % javac M05_GCD_LCM_Recursive.java
ifangelinetosani@ifangelines-MacBook-Air midterm % java M05_GCD_LCM_Recursive
12 18
GCD: 6
LCM: 36
 */
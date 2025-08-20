import java.util.*;

// 複雜度：每筆計算 O(1)，總 O(n)，空間 O(1)
public class M04_TieredTaxSimple {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long totalTax = 0;

        for (int i = 0; i < n; i++) {
            long income = sc.nextLong();
            long tax = computeTax(income);
            System.out.println("Tax: " + tax);
            totalTax += tax;
        }
        sc.close();

        // 平均稅額需要四捨五入到整數
        long avg = Math.round((double) totalTax / n);
        System.out.println("Average: " + avg);
    }

    private static long computeTax(long income) {
        long tax = 0;

        // 1000001+
        if (income > 1_000_000) {
            tax += (income - 1_000_000) * 30 / 100;
            income = 1_000_000;
        }
        // 500001–1000000
        if (income > 500_000) {
            tax += (income - 500_000) * 20 / 100;
            income = 500_000;
        }
        // 120001–500000
        if (income > 120_000) {
            tax += (income - 120_000) * 12 / 100;
            income = 120_000;
        }
        // 0–120000
        tax += income * 5 / 100;

        return tax;
    }
}

/* Terminal Run Result 
ifangelinetosani@ifangelines-MacBook-Air 113tkuds_2 % cd /Users/ifangelinetosani/Documents/GitHub/113tkuds_2/midterm
ifangelinetosani@ifangelines-MacBook-Air midterm % javac M04_TieredTaxSimple.java
ifangelinetosani@ifangelines-MacBook-Air midterm % java M04_TieredTaxSimple
3
100000
Tax: 5000
350000
Tax: 33600
1200000
Tax: 211600
Average: 83400
 */
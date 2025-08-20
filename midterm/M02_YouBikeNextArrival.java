

import java.util.*;

// 複雜度：輸入 O(n)，查詢 O(log n)，總 O(n + log n)
public class M02_YouBikeNextArrival {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine().trim());
        int[] times = new int[n];
        for (int i = 0; i < n; i++) {
            times[i] = toMinutes(sc.nextLine().trim());
        }
        int query = toMinutes(sc.nextLine().trim());
        sc.close();

        int idx = firstGreater(times, query);
        if (idx == -1) System.out.println("No bike");
        else System.out.println(toHHMM(times[idx]));
    }

    private static int toMinutes(String s) {
        String[] parts = s.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }

    private static String toHHMM(int m) {
        return String.format("%02d:%02d", m / 60, m % 60);
    }

    private static int firstGreater(int[] a, int t) {
        int lo = 0, hi = a.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (a[mid] > t) hi = mid;
            else lo = mid + 1;
        }
        return lo == a.length ? -1 : lo;
    }
}

/* Terminal Run Result
ifangelinetosani@ifangelines-MacBook-Air 113tkuds_2 % cd /Users/ifangelinetosani/Documents/GitHub/113tkuds_2/midterm
ifangelinetosani@ifangelines-MacBook-Air midterm % javac M02_YouBikeNextArrival.java
ifangelinetosani@ifangelines-MacBook-Air midterm % java M02_YouBikeNextArrival
5
06:00
06:30
07:00
07:30
08:00
07:45
08:00
 */
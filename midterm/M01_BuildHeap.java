import java.util.*;

// 自底向上建堆 (max 或 min)
// 複雜度：O(n) 時間, O(1) 額外空間
public class M01_BuildHeap {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String type = sc.next(); // "max" 或 "min"
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();
        sc.close();

        boolean isMax = type.equalsIgnoreCase("max");
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i, isMax);
        }

        for (int i = 0; i < n; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(arr[i]);
        }
    }

    // 下沉操作
    private static void heapify(int[] a, int n, int i, boolean isMax) {
        while (true) {
            int left = 2 * i + 1, right = 2 * i + 2;
            int best = i;
            if (left < n && better(a[left], a[best], isMax)) best = left;
            if (right < n && better(a[right], a[best], isMax)) best = right;
            if (best == i) break;
            int tmp = a[i]; a[i] = a[best]; a[best] = tmp;
            i = best;
        }
    }

    private static boolean better(int x, int y, boolean isMax) {
        return isMax ? x > y : x < y;
    }
}

/* Terminal Run Result
ifangelinetosani@ifangelines-MacBook-Air 113tkuds_2 % cd /Users/ifangelinetosani/Documents/GitHub/113tkuds_2/midterm
ifangelinetosani@ifangelines-MacBook-Air midterm % javac M01_BuildHeap.java
ifangelinetosani@ifangelines-MacBook-Air midterm % java M01_BuildHeap
max
8
4 10 3 5 1 15 20 17
20 17 15 10 1 4 3 5%  
 */
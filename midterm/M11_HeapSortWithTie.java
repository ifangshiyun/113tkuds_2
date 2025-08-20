
import java.util.*;

// 複雜度分析：
//   建堆 O(n)，每次取出 O(log n)，總 O(n log n)。
//   空間 O(1) 額外（原地堆排序）。
public class M11_HeapSortWithTie {
    static class Item {
        int score;
        int idx; // 輸入順序
        Item(int s, int i) { score = s; idx = i; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Item[] arr = new Item[n];
        for (int i = 0; i < n; i++) {
            arr[i]
             = new Item(sc.nextInt(), i);
        }
        sc.close();

        // 用 Max-Heap 做堆排序，最後得到遞增序
        heapSort(arr);

        // 輸出排序後的 score
        for (int i = 0; i < n; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(arr[i].score);
        }
    }

    // ====== Heap Sort ======
    private static void heapSort(Item[] arr) {
        int n = arr.length;
        // 建立最大堆
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        // 一一取出最大元素，放到尾端
        for (int end = n - 1; end > 0; end--) {
            swap(arr, 0, end);
            heapify(arr, end, 0);
        }
    }

    // 下沉 (Max-Heap)，比較規則：score 大者優先；若分數相同，idx 小者優先
    private static void heapify(Item[] arr, int size, int i) {
        int largest = i;
        int left = 2 * i + 1, right = 2 * i + 2;

        if (left < size && better(arr[left], arr[largest])) largest = left;
        if (right < size && better(arr[right], arr[largest])) largest = right;

        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, size, largest);
        }
    }

    // 比較函數：true if a 比 b 更大（Max-Heap 用）
    private static boolean better(Item a, Item b) {
        if (a.score != b.score) return a.score > b.score; // 分數大者優先
        return a.idx < b.idx; // 分數相同 → 輸入較早者優先
    }

    private static void swap(Item[] arr, int i, int j) {
        Item tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
    }
}

/* Terminal Run Result
ifangelinetosani@ifangelines-MacBook-Air 113tkuds_2 % cd /Users/ifangelinetosani/Documents/GitHub/113tkuds_2/midterm
ifangelinetosani@ifangelines-MacBook-Air midterm % javac M11_HeapSortWithTie.java
ifangelinetosani@ifangelines-MacBook-Air midterm % java M11_HeapSortWithTie
5
90 75 90 60 80
60 75 80 90 90%   
 */
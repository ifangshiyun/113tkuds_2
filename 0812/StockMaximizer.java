// File: StockMaximizer.java
import java.util.PriorityQueue;
import java.util.Collections;

public class StockMaximizer {

    // Heap-based solution: O(n log R) where R = number of rising segments (R <= n)
    // Strategy:
    // 1) Scan prices to find every valley->peak ascending run.
    // 2) Push each run's profit (peak - valley) into a MAX heap.
    // 3) Pop the best K profits and sum them.
    public static int maxProfitHeap(int[] prices, int K) {
        if (prices == null || prices.length == 0 || K <= 0) return 0;

        // If K is huge, the best is just summing all positive deltas (equivalent to unlimited trades)
        if (K >= prices.length / 2) return sumAllPositiveDiffs(prices);

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        int n = prices.length, i = 0;
        while (i < n - 1) {
            // find valley (local minimum)
            while (i < n - 1 && prices[i] >= prices[i + 1]) i++;
            int buy = prices[i];

            // find peak (local maximum)
            while (i < n - 1 && prices[i] <= prices[i + 1]) i++;
            int sell = prices[i];

            if (sell > buy) maxHeap.offer(sell - buy);
        }

        int profit = 0;
        for (int t = 0; t < K && !maxHeap.isEmpty(); t++) {
            profit += maxHeap.poll();
        }
        return profit;
    }

    // sum of all positive day-to-day increases (unlimited transactions)
    private static int sumAllPositiveDiffs(int[] prices) {
        int sum = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) sum += prices[i] - prices[i - 1];
        }
        return sum;
    }

    // Demo with samples in the handout
    public static void main(String[] args) {
        int[] a1 = {2, 4, 1};          // K = 2 -> 2
        int[] a2 = {3, 2, 6, 5, 0, 3}; // K = 2 -> 7
        int[] a3 = {1, 2, 3, 4, 5};    // K = 2 -> 4

        System.out.println(maxProfitHeap(a1, 2)); // 2
        System.out.println(maxProfitHeap(a2, 2)); // 7
        System.out.println(maxProfitHeap(a3, 2)); // 4
    }
}

// File: SlidingWindowMedian.java
import java.util.*;

public class SlidingWindowMedian {

    // Max-heap for the smaller half
    private PriorityQueue<Integer> small =
        new PriorityQueue<>(Collections.reverseOrder());
    // Min-heap for the larger half
    private PriorityQueue<Integer> large = new PriorityQueue<>();

    // Delayed removals: value -> count
    private Map<Integer, Integer> delayed = new HashMap<>();

    // Sizes excluding elements marked for deletion
    private int smallSize = 0, largeSize = 0;

    public double[] medianSlidingWindow(int[] nums, int k) {
        if (nums == null || k <= 0) return new double[0];
        int n = nums.length;
        if (n == 0 || k > n) return new double[0];

        double[] ans = new double[n - k + 1];

        // init first window
        for (int i = 0; i < k; i++) add(nums[i]);
        ans[0] = getMedian(k);

        for (int i = k; i < n; i++) {
            add(nums[i]);
            remove(nums[i - k]);
            ans[i - k + 1] = getMedian(k);
        }
        return ans;
    }

    private void add(int num) {
        if (small.isEmpty() || num <= small.peek()) {
            small.offer(num);
            smallSize++;
        } else {
            large.offer(num);
            largeSize++;
        }
        rebalance();
    }

    private void remove(int num) {
        // Mark for lazy deletion
        delayed.put(num, delayed.getOrDefault(num, 0) + 1);

        if (!small.isEmpty() && num <= small.peek()) {
            smallSize--;
            if (num == small.peek()) prune(small);
        } else {
            largeSize--;
            if (!large.isEmpty() && num == large.peek()) prune(large);
        }
        rebalance();
    }

    private void rebalance() {
        // Keep: smallSize == largeSize or smallSize == largeSize + 1
        if (smallSize > largeSize + 1) {
            large.offer(small.poll());
            smallSize--; largeSize++;
            prune(small);
        } else if (smallSize < largeSize) {
            small.offer(large.poll());
            largeSize--; smallSize++;
            prune(large);
        }
    }

    private void prune(PriorityQueue<Integer> heap) {
        while (!heap.isEmpty()) {
            int x = heap.peek();
            Integer cnt = delayed.get(x);
            if (cnt == null || cnt == 0) break;
            heap.poll();
            if (cnt == 1) delayed.remove(x);
            else delayed.put(x, cnt - 1);
        }
    }

    private double getMedian(int k) {
        if ((k & 1) == 1) return small.peek();
        // avoid overflow
        return ((long) small.peek() + (long) large.peek()) / 2.0;
    }

    // Demo with the samples in the prompt
    public static void main(String[] args) {
        SlidingWindowMedian swm = new SlidingWindowMedian();

        int[] a1 = {1, 3, -1, -3, 5, 3, 6, 7};
        System.out.println(Arrays.toString(swm.medianSlidingWindow(a1, 3)));
        // [1.0, -1.0, -1.0, 3.0, 5.0, 6.0]

        int[] a2 = {1, 2, 3, 4};
        System.out.println(Arrays.toString(swm.medianSlidingWindow(a2, 2)));
        // [1.5, 2.5, 3.5]
    }
}

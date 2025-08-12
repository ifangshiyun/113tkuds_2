// File: MovingAverageStream.java
import java.util.*;

/** Sliding window moving average with median/min/max queries. */
public class MovingAverageStream {

    public static class MovingAverage {
        private final int k;
        private final Deque<Integer> window = new ArrayDeque<>();
        private long sum = 0;                 // running sum for average

        // Heaps for median: small = max-heap (lower half), large = min-heap (upper half)
        private final PriorityQueue<Integer> small =
                new PriorityQueue<>(Collections.reverseOrder());
        private final PriorityQueue<Integer> large = new PriorityQueue<>();
        private final Map<Integer, Integer> delayed = new HashMap<>(); // lazy deletions
        private int smallSize = 0, largeSize = 0; // sizes excluding delayed items

        // Multiset for min/max
        private final TreeMap<Integer, Integer> multiset = new TreeMap<>();

        public MovingAverage(int size) {
            if (size <= 0) throw new IllegalArgumentException("size must be >= 1");
            this.k = size;
        }

        /** Add a value and return current moving average. */
        public double next(int val) {
            window.addLast(val);
            sum += val;
            addToMedian(val);
            addToMultiset(val);

            if (window.size() > k) {
                int out = window.removeFirst();
                sum -= out;
                removeFromMedian(out);
                removeFromMultiset(out);
            }
            return sum / (double) window.size();
        }

        /** Current median of the window. Throws if window is empty. */
        public double getMedian() {
            ensureNotEmpty();
            prune(small);
            prune(large);
            int size = window.size();
            if ((size & 1) == 1) return small.peek();
            return ((long) small.peek() + (long) large.peek()) / 2.0;
        }

        /** Current minimum in the window. */
        public int getMin() {
            ensureNotEmpty();
            return multiset.firstKey();
        }

        /** Current maximum in the window. */
        public int getMax() {
            ensureNotEmpty();
            return multiset.lastKey();
        }

        /* ---------------- Internal helpers ---------------- */

        private void addToMedian(int x) {
            if (small.isEmpty() || x <= small.peek()) {
                small.offer(x);
                smallSize++;
            } else {
                large.offer(x);
                largeSize++;
            }
            rebalance();
        }

        private void removeFromMedian(int x) {
            delayed.put(x, delayed.getOrDefault(x, 0) + 1);
            // Adjust sizes depending on where x would be
            if (!small.isEmpty() && x <= small.peek()) {
                smallSize--;
                if (!small.isEmpty() && small.peek() == x) prune(small);
            } else {
                largeSize--;
                if (!large.isEmpty() && large.peek() == x) prune(large);
            }
            rebalance();
        }

        private void rebalance() {
            // small must have either the same size as large, or one more
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

        private void addToMultiset(int x) {
            multiset.put(x, multiset.getOrDefault(x, 0) + 1);
        }

        private void removeFromMultiset(int x) {
            int c = multiset.getOrDefault(x, 0);
            if (c <= 1) multiset.remove(x);
            else multiset.put(x, c - 1);
        }

        private void ensureNotEmpty() {
            if (window.isEmpty()) throw new NoSuchElementException("window is empty");
        }
    }

    /* ---------------- Demo with the prompt’s sample ---------------- */
    public static void main(String[] args) {
        MovingAverage ma = new MovingAverage(3);
        System.out.println(ma.next(1));   // 1.0
        System.out.println(ma.next(10));  // 5.5
        System.out.println(ma.next(3));   // 4.666...
        System.out.println(ma.next(5));   // 6.0
        System.out.println(ma.getMedian()); // 5.0
        System.out.println(ma.getMin());    // 3
        System.out.println(ma.getMax());    // 10
    }
}

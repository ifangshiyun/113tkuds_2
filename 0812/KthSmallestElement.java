// File: KthSmallestElement.java
import java.util.*;

public class KthSmallestElement {

    // --- Method 1: Keep a size-K Max-Heap (top = current kth smallest) ---
    // Time: O(n log k), Space: O(k)
    public static int kthSmallestUsingMaxHeap(int[] nums, int k) {
        validate(nums, k);
        // Max-heap: largest on top
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(k, (a, b) -> Integer.compare(b, a));
        for (int x : nums) {
            if (maxHeap.size() < k) {
                maxHeap.offer(x);
            } else if (x < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.offer(x);
            }
        }
        return maxHeap.peek();
    }

    // --- Method 2: Build a Min-Heap and pop K-1 times, Kth is answer ---
    // Time: O(n + k log n) if heapify from array, but with offers here: O(n log n)
    // Space: O(n)
    public static int kthSmallestUsingMinHeap(int[] nums, int k) {
        validate(nums, k);
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int x : nums) minHeap.offer(x);
        for (int i = 1; i < k; i++) minHeap.poll();
        return minHeap.peek();
    }

    private static void validate(int[] nums, int k) {
        if (nums == null || nums.length == 0) throw new IllegalArgumentException("Empty array");
        if (k < 1 || k > nums.length) throw new IllegalArgumentException("k out of range");
    }

    // --- Demo with samples ---
    public static void main(String[] args) {
        int[] a1 = {7, 10, 4, 3, 20, 15};
        int[] a2 = {1};
        int[] a3 = {3, 1, 4, 1, 5, 9, 2, 6};

        System.out.println("Method1 (MaxHeap):");
        System.out.println(kthSmallestUsingMaxHeap(a1, 3)); // 7
        System.out.println(kthSmallestUsingMaxHeap(a2, 1)); // 1
        System.out.println(kthSmallestUsingMaxHeap(a3, 4)); // 3

        System.out.println("Method2 (MinHeap):");
        System.out.println(kthSmallestUsingMinHeap(a1, 3)); // 7
        System.out.println(kthSmallestUsingMinHeap(a2, 1)); // 1
        System.out.println(kthSmallestUsingMinHeap(a3, 4)); // 3
    }
}

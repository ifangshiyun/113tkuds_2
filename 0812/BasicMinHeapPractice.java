// File: BasicMinHeapPractice.java
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class BasicMinHeapPractice {

    static class MinHeap {
        private final ArrayList<Integer> heap = new ArrayList<>();

        // Insert a value and bubble it up to keep heap order
        public void insert(int val) {
            heap.add(val);
            heapifyUp(heap.size() - 1);
        }

        // Remove and return the smallest value (root)
        public int extractMin() {
            if (heap.isEmpty()) throw new NoSuchElementException("Heap is empty");
            int min = heap.get(0);
            int last = heap.remove(heap.size() - 1);
            if (!heap.isEmpty()) {
                heap.set(0, last);
                heapifyDown(0);
            }
            return min;
        }

        // Peek the smallest value without removing
        public int getMin() {
            if (heap.isEmpty()) throw new NoSuchElementException("Heap is empty");
            return heap.get(0);
        }

        // Number of elements
        public int size() {
            return heap.size();
        }

        // Is heap empty
        public boolean isEmpty() {
            return heap.isEmpty();
        }

        // Move a node up until parent <= node
        private void heapifyUp(int i) {
            while (i > 0) {
                int p = parent(i);
                if (heap.get(p) <= heap.get(i)) break;
                swap(p, i);
                i = p;
            }
        }

        // Move a node down to the correct position
        private void heapifyDown(int i) {
            int n = heap.size();
            while (true) {
                int left = leftChild(i);
                int right = rightChild(i);
                int smallest = i;

                if (left < n && heap.get(left) < heap.get(smallest)) smallest = left;
                if (right < n && heap.get(right) < heap.get(smallest)) smallest = right;

                if (smallest == i) break;
                swap(i, smallest);
                i = smallest;
            }
        }

        // Index helpers
        private int parent(int i) { return (i - 1) / 2; }
        private int leftChild(int i) { return 2 * i + 1; }
        private int rightChild(int i) { return 2 * i + 2; }

        private void swap(int a, int b) {
            int tmp = heap.get(a);
            heap.set(a, heap.get(b));
            heap.set(b, tmp);
        }
    }

    // Demo with the sample sequence
    public static void main(String[] args) {
        MinHeap h = new MinHeap();
        int[] toInsert = {15, 10, 20, 8, 25, 5};
        for (int v : toInsert) h.insert(v);

        System.out.println("size = " + h.size());      // should be 6
        System.out.println("min  = " + h.getMin());    // should be 5

        // Expected extract order: 5, 8, 10, 15, 20, 25
        while (!h.isEmpty()) {
            System.out.print(h.extractMin());
            if (!h.isEmpty()) System.out.print(", ");
        }
        System.out.println();
    }
}

// File: PriorityQueueWithHeap.java
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class PriorityQueueWithHeap {

    static class Task {
        String name;
        int priority;
        long timestamp; // used to break ties: earlier added = higher priority when same priority

        Task(String name, int priority, long timestamp) {
            this.name = name;
            this.priority = priority;
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return "(" + name + ", priority=" + priority + ")";
        }
    }

    static class MaxHeap {
        private final ArrayList<Task> heap = new ArrayList<>();
        private long counter = 0; // increments for each added task

        public void addTask(String name, int priority) {
            heap.add(new Task(name, priority, counter++));
            heapifyUp(heap.size() - 1);
        }

        public Task executeNext() {
            if (heap.isEmpty()) throw new NoSuchElementException("No tasks to execute");
            Task top = heap.get(0);
            Task last = heap.remove(heap.size() - 1);
            if (!heap.isEmpty()) {
                heap.set(0, last);
                heapifyDown(0);
            }
            return top;
        }

        public Task peek() {
            if (heap.isEmpty()) throw new NoSuchElementException("No tasks available");
            return heap.get(0);
        }

        public void changePriority(String name, int newPriority) {
            for (int i = 0; i < heap.size(); i++) {
                if (heap.get(i).name.equals(name)) {
                    heap.get(i).priority = newPriority;
                    // After change, decide whether to bubble up or down
                    heapifyUp(i);
                    heapifyDown(i);
                    return;
                }
            }
            throw new NoSuchElementException("Task not found: " + name);
        }

        private void heapifyUp(int i) {
            while (i > 0) {
                int p = parent(i);
                if (compare(heap.get(i), heap.get(p)) <= 0) break;
                swap(i, p);
                i = p;
            }
        }

        private void heapifyDown(int i) {
            int n = heap.size();
            while (true) {
                int left = leftChild(i);
                int right = rightChild(i);
                int largest = i;

                if (left < n && compare(heap.get(left), heap.get(largest)) > 0) largest = left;
                if (right < n && compare(heap.get(right), heap.get(largest)) > 0) largest = right;

                if (largest == i) break;
                swap(i, largest);
                i = largest;
            }
        }

        // Compare tasks: higher priority wins, if tie, earlier timestamp wins
        private int compare(Task a, Task b) {
            if (a.priority != b.priority) return Integer.compare(a.priority, b.priority);
            return Long.compare(b.timestamp, a.timestamp) * -1; 
        }

        private void swap(int a, int b) {
            Task tmp = heap.get(a);
            heap.set(a, heap.get(b));
            heap.set(b, tmp);
        }

        private int parent(int i) { return (i - 1) / 2; }
        private int leftChild(int i) { return 2 * i + 1; }
        private int rightChild(int i) { return 2 * i + 2; }
    }

    // Demo
    public static void main(String[] args) {
        MaxHeap pq = new MaxHeap();
        pq.addTask("備份", 1);
        pq.addTask("緊急修復", 5);
        pq.addTask("更新", 3);

        System.out.println("Next: " + pq.peek()); // 緊急修復

        while (true) {
            try {
                System.out.println("Execute: " + pq.executeNext());
            } catch (NoSuchElementException e) {
                break;
            }
        }
    }
}

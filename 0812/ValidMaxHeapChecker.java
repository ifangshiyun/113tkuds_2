// File: ValidMaxHeapChecker.java
import java.util.Arrays;

public class ValidMaxHeapChecker {

    /** Returns true if arr represents a valid max heap. */
    public static boolean isValidMaxHeap(int[] arr) {
        return firstViolationIndex(arr) == -1;
    }

    /**
     * Returns the index of the FIRST node that violates the max-heap rule
     * (i.e., a child > its parent). If no violation, returns -1.
     * Empty or single-element arrays are valid heaps.
     */
    public static int firstViolationIndex(int[] arr) {
        if (arr == null || arr.length <= 1) return -1;

        int n = arr.length;
        // Last non-leaf index is (n - 2) / 2
        for (int parent = 0; parent <= (n - 2) / 2; parent++) {
            int left  = 2 * parent + 1;
            int right = 2 * parent + 2;

            if (left < n && arr[left] > arr[parent])  return left;   // left child breaks rule
            if (right < n && arr[right] > arr[parent]) return right; // right child breaks rule
        }
        return -1;
    }

    // Demo with the samples shown in the prompt
    public static void main(String[] args) {
        int[][] samples = {
            {100, 90, 80, 70, 60, 75, 65},
            {100, 90, 80, 95, 60, 75, 65},
            {50},
            {}
        };

        for (int[] s : samples) {
            int bad = firstViolationIndex(s);
            boolean ok = (bad == -1);
            System.out.println(Arrays.toString(s) + " -> " + ok +
                (ok ? "" : " (violate at index " + bad + ", value " + s[bad] + ")"));
        }
    }
}

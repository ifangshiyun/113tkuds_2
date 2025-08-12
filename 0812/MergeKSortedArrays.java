// File: MergeKSortedArrays.java
import java.util.*;

public class MergeKSortedArrays {

    // Node keeps (value, which array, index in that array)
    static class Node {
        int val, arrIdx, elemIdx;
        Node(int v, int a, int e) { val = v; arrIdx = a; elemIdx = e; }
    }

    // Merge K sorted arrays into one sorted array
    // Time: O(N log K), Space: O(K) for heap, O(N) for result
    public static int[] mergeKSortedArrays(int[][] arrays) {
        if (arrays == null || arrays.length == 0) return new int[0];

        // total length
        int total = 0;
        for (int[] a : arrays) total += (a == null ? 0 : a.length);
        if (total == 0) return new int[0];

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.val));
        // push first element of each non-empty array
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i] != null && arrays[i].length > 0) {
                pq.offer(new Node(arrays[i][0], i, 0));
            }
        }

        int[] result = new int[total];
        int idx = 0;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            result[idx++] = cur.val;

            int nextIdx = cur.elemIdx + 1;
            int arrI = cur.arrIdx;
            if (nextIdx < arrays[arrI].length) {
                pq.offer(new Node(arrays[arrI][nextIdx], arrI, nextIdx));
            }
        }
        return result;
    }

    // Demo with the samples in the prompt
    public static void main(String[] args) {
        int[][] s1 = {{1,4,5}, {1,3,4}, {2,6}};
        int[][] s2 = {{1,2,3}, {4,5,6}, {7,8,9}};
        int[][] s3 = {{1}, {0}};

        System.out.println(Arrays.toString(mergeKSortedArrays(s1))); // [1,1,2,3,4,4,5,6]
        System.out.println(Arrays.toString(mergeKSortedArrays(s2))); // [1,2,3,4,5,6,7,8,9]
        System.out.println(Arrays.toString(mergeKSortedArrays(s3))); // [0,1]
    }
}

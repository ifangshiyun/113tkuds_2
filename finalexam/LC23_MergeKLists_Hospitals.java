import java.io.*;
import java.util.*;

public class LC23_MergeKLists_Hospitals {

    // Definition for singly-linked list.
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int v) { val = v; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String line = br.readLine();
        if (line == null || line.trim().isEmpty()) return;
        int k = Integer.parseInt(line.trim());
        if (k == 0) return; // k=0 → empty output

        // Read k lines, each ends with -1 (may be just "-1" for empty list)
        ListNode[] lists = new ListNode[k];
        for (int i = 0; i < k; i++) {
            String row = br.readLine();
            lists[i] = parseList(row);
        }

        ListNode merged = mergeKLists(lists);

        // Print merged sequence (single line, space-separated). If empty, print nothing.
        if (merged == null) return;
        StringBuilder sb = new StringBuilder();
        while (merged != null) {
            sb.append(merged.val).append(' ');
            merged = merged.next;
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 1); // trim trailing space
        System.out.println(sb.toString());
    }

    // Parse a single line of integers ending with -1 into a linked list.
    // Example: "1 4 5 -1" -> 1->4->5 ; "-1" or empty -> null
    static ListNode parseList(String row) {
        if (row == null) return null;
        row = row.trim();
        if (row.isEmpty()) return null;

        StringTokenizer st = new StringTokenizer(row);
        ListNode dummy = new ListNode(0), cur = dummy;

        while (st.hasMoreTokens()) {
            int v = Integer.parseInt(st.nextToken());
            if (v == -1) break;
            cur.next = new ListNode(v);
            cur = cur.next;
        }
        return dummy.next;
    }

    // Merge k sorted lists with a min-heap. Time: O(N log k), Space: O(k)
    static ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.val));
        for (ListNode head : lists) {
            if (head != null) pq.offer(head);
        }
        if (pq.isEmpty()) return null;

        ListNode dummy = new ListNode(0), tail = dummy;

        while (!pq.isEmpty()) {
            ListNode node = pq.poll();
            tail.next = node;
            tail = tail.next;
            if (node.next != null) pq.offer(node.next);
        }
        return dummy.next;
    }
}

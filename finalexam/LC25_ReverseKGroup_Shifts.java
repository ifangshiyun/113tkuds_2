import java.io.*;
import java.util.*;

public class LC25_ReverseKGroup_Shifts {

    // Definition for singly-linked list
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int v) { val = v; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Read k
        String line1 = br.readLine();
        if (line1 == null || line1.trim().isEmpty()) return;
        int k = Integer.parseInt(line1.trim());

        // Read sequence (may be empty)
        String line2 = br.readLine();
        if (line2 == null || line2.trim().isEmpty()) {
            return; // no nodes
        }
        String[] parts = line2.trim().split("\\s+");
        int n = parts.length;

        // Build linked list
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int i = 0; i < n; i++) {
            cur.next = new ListNode(Integer.parseInt(parts[i]));
            cur = cur.next;
        }

        ListNode head = reverseKGroup(dummy.next, k);

        // Print result
        StringBuilder sb = new StringBuilder();
        cur = head;
        while (cur != null) {
            sb.append(cur.val).append(" ");
            cur = cur.next;
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 1);
        System.out.println(sb.toString());
    }

    // Reverse nodes in k-group
    static ListNode reverseKGroup(ListNode head, int k) {
        if (k <= 1 || head == null) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prevGroup = dummy;

        while (true) {
            // Check length of remaining list
            ListNode node = prevGroup;
            for (int i = 0; i < k && node != null; i++) {
                node = node.next;
            }
            if (node == null) break; // less than k left

            // Reverse next k nodes
            ListNode groupStart = prevGroup.next;
            ListNode cur = groupStart.next;
            for (int i = 1; i < k; i++) {
                groupStart.next = cur.next;
                cur.next = prevGroup.next;
                prevGroup.next = cur;
                cur = groupStart.next;
            }

            // Move prevGroup pointer to end of reversed segment
            prevGroup = groupStart;
        }

        return dummy.next;
    }
}

import java.io.*;
import java.util.*;

public class LC24_SwapPairs_Shifts {

    // Definition for singly-linked list
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int v) { val = v; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        if (line == null || line.trim().isEmpty()) {
            return; // empty input → nothing to print
        }

        String[] parts = line.trim().split("\\s+");
        int n = parts.length;

        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int i = 0; i < n; i++) {
            cur.next = new ListNode(Integer.parseInt(parts[i]));
            cur = cur.next;
        }

        ListNode head = swapPairs(dummy.next);

        // Print swapped list
        StringBuilder sb = new StringBuilder();
        cur = head;
        while (cur != null) {
            sb.append(cur.val).append(" ");
            cur = cur.next;
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 1); // trim last space
        System.out.println(sb.toString());
    }

    // Iterative swap in pairs
    static ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        while (prev.next != null && prev.next.next != null) {
            ListNode a = prev.next;
            ListNode b = a.next;

            // swap a and b
            a.next = b.next;
            b.next = a;
            prev.next = b;

            // move prev two steps forward
            prev = a;
        }
        return dummy.next;
    }
}

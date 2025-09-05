import java.io.*;
import java.util.*;

public class LC19_RemoveNth_Node_Clinic {

    // Definition for singly-linked list.
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int v) { val = v; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());
        String[] parts = br.readLine().trim().split("\\s+");
        int k = Integer.parseInt(br.readLine().trim());

        // Build linked list
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int i = 0; i < n; i++) {
            cur.next = new ListNode(Integer.parseInt(parts[i]));
            cur = cur.next;
        }
        ListNode head = dummy.next;

        // Remove kth from end
        head = removeNthFromEnd(head, k);

        // Print list after deletion
        StringBuilder sb = new StringBuilder();
        cur = head;
        while (cur != null) {
            sb.append(cur.val).append(" ");
            cur = cur.next;
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 1); // trim last space
        System.out.println(sb.toString());
    }

    // Fast-slow pointer, one pass
    static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode fast = dummy, slow = dummy;
        // advance fast n+1 steps
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }
        // move both until fast reaches end
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        // delete node
        slow.next = slow.next.next;
        return dummy.next;
    }
}

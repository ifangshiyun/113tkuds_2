import java.io.*;
import java.util.*;

public class LC21_MergeTwoLists_Clinics {

    // Definition for singly-linked list
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int v) { val = v; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] first = br.readLine().trim().split("\\s+");
        int n = Integer.parseInt(first[0]);
        int m = Integer.parseInt(first[1]);

        int[] arr1 = new int[n];
        int[] arr2 = new int[m];

        if (n > 0) {
            String[] line1 = br.readLine().trim().split("\\s+");
            for (int i = 0; i < n; i++) arr1[i] = Integer.parseInt(line1[i]);
        } else {
            if (m > 0) br.readLine(); // consume line for arr2
        }

        if (m > 0) {
            String[] line2 = (n > 0 ? br.readLine() : new String[]{}).trim().split("\\s+");
            for (int i = 0; i < m; i++) arr2[i] = Integer.parseInt(line2[i]);
        }

        ListNode l1 = buildList(arr1);
        ListNode l2 = buildList(arr2);

        ListNode merged = mergeTwoLists(l1, l2);

        StringBuilder sb = new StringBuilder();
        while (merged != null) {
            sb.append(merged.val).append(" ");
            merged = merged.next;
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 1); // trim trailing space
        System.out.println(sb.toString());
    }

    // Merge two sorted lists
    static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                tail.next = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                l2 = l2.next;
            }
            tail = tail.next;
        }
        if (l1 != null) tail.next = l1;
        if (l2 != null) tail.next = l2;

        return dummy.next;
    }

    // Helper: build linked list from array
    static ListNode buildList(int[] arr) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int v : arr) {
            cur.next = new ListNode(v);
            cur = cur.next;
        }
        return dummy.next;
    }
}

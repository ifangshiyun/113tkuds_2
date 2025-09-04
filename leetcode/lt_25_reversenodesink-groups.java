/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode groupPrev = dummy;

        while (true) {
            // Find the k-th node from groupPrev (end of current group)
            ListNode kth = getKth(groupPrev, k);
            if (kth == null) break; // not enough nodes left; stop
            ListNode groupNext = kth.next; // node after the group

            // Reverse the group [groupPrev.next ... kth]
            ListNode prev = groupNext;
            ListNode curr = groupPrev.next;
            while (curr != groupNext) {
                ListNode tmp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = tmp;
            }

            // Connect previous part with the reversed group
            ListNode newGroupHead = kth;           // after reverse, kth becomes group head
            ListNode newGroupTail = groupPrev.next; // original head becomes tail
            groupPrev.next = newGroupHead;
            groupPrev = newGroupTail; // move to tail to start next group
        }

        return dummy.next;
    }

    // Helper: return the k-th node ahead of 'start' (1-based from start.next)
    private ListNode getKth(ListNode start, int k) {
        ListNode node = start;
        for (int i = 0; i < k; i++) {
            node = node.next;
            if (node == null) return null;
        }
        return node;
    }
}

/*
Explanation (simple):
- We process the list in groups of size k.
- For each group, we first check there are at least k nodes left (find the k-th node from the current position).
- Then we reverse exactly those k nodes in-place:
  - Let groupNext be the node after the k-th node; we reverse pointers so the group points toward groupNext.
  - Standard in-place reverse: iterate through the k nodes, re-point next to the previous node (starting from groupNext).
- After reversing, connect:
  - The node before the group (groupPrev) should point to the new head of the group (the original k-th node).
  - The original head of the group becomes the tail; this will be the 'groupPrev' for the next iteration.
- Stop when fewer than k nodes remain; leave the rest as-is.

Complexity:
- Time O(n): each node is visited/re-linked a constant number of times.
- Space O(1): only a few pointers are used.
*/

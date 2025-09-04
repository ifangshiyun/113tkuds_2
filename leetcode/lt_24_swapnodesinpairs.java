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
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0); // helper to handle head swaps easily
        dummy.next = head;

        ListNode prev = dummy; // node before the current pair
        while (head != null && head.next != null) {
            ListNode first = head;        // 1st node of the pair
            ListNode second = head.next;  // 2nd node of the pair

            // --- swap the pair ---
            prev.next = second;       // connect prev -> second
            first.next = second.next; // connect first -> node after second
            second.next = first;      // connect second -> first (pair reversed)

            // move pointers to the next pair
            prev = first;
            head = first.next;
        }
        return dummy.next;
    }
}

/*
Explanation (simple):
- We use a dummy node so swapping at the head is easy.
- 'prev' points to the node before the current pair; 'head' is the 1st node of the pair.
- While there are at least two nodes to swap:
  1) Identify 'first' = head and 'second' = head.next.
  2) Rewire links: prev -> second, first -> second.next, second -> first.
  3) Advance: prev = first; head = first.next (start of the next pair).
- Time: O(n) because each node is visited once.
- Space: O(1) because we only use a few pointers.
*/

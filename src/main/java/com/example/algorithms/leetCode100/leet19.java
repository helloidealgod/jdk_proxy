package com.example.algorithms.leetCode100;

public class leet19 {
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode nail = new ListNode(0);
        nail.next = head;
        ListNode show = nail;
        ListNode fast = nail;
        for (int i = 0; i < n; i++) {
            if (null == fast) {
                return null;
            }
            fast = fast.next;
        }
        if (null == fast.next) {
            if (null == head.next) {
                nail.next = null;
            } else {
                show.next = show.next.next;
            }
            return nail.next;
        }
        while (null != fast && null != fast.next) {
            show = show.next;
            fast = fast.next;
        }
        show.next = show.next.next;
        return nail.next;
    }

    public static void main(String[] args) {
        ListNode head1 = new ListNode(1);
        ListNode head2 = new ListNode(2);
        //removeNthFromEnd(head1, 1);
        head1.next = head2;
        removeNthFromEnd(head1, 2);
        //removeNthFromEnd(head1, 1);
    }
}

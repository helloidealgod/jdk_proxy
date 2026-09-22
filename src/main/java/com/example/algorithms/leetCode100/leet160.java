package com.example.algorithms.leetCode100;

public class leet160 {
    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode a, b;
        a = headA;
        b = headB;
        int loop = 3;
        while (a != b && loop > 0) {
            if (null == a.next) {
                a = headB;
                loop--;
            } else {
                a = a.next;
            }
            if (null == b.next) {
                b = headA;
                loop--;
            } else {
                b = b.next;
            }
        }
        return loop > 0 ? a : null;
    }

    public static void main(String[] args) {
        int[] a = {4, 1, 8, 4, 5};
        int[] b = {5, 6, 1, 8, 4, 5};
        ListNode headA = null;
        ListNode headB = null;
        ListNode cross = null;
        for (int i = a.length - 1; i >= 0; i--) {
            ListNode temp = new ListNode(a[i]);
            if (null == headA) {
                headA = temp;
            } else {
                temp.next = headA;
                headA = temp;
            }
        }
        ListNode listNode = new ListNode(6);
        headB = new ListNode(5);
        headB.next = listNode;
        listNode.next = headA.next;

        getIntersectionNode(headA, headB);
    }
}

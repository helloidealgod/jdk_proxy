package com.example.algorithms.leetCode100;


public class leet206 {
    public static ListNode reverseList(ListNode head) {
        ListNode current = head;
        if(null == current || null == current.next){
            return current;
        }
        ListNode next = head.next;
        current.next = null;
        ListNode temp = null;
        while(null != next){
            temp = next.next;
            next.next = current;
            current = next;
            next = temp;
        }
        return current;
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        node1.next = node2;
        reverseList(node1);
    }
}

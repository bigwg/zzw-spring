package com.zzw.data.structure;

import java.util.LinkedHashMap;
import java.util.Stack;

/**
 * 二叉树弓形遍历
 *
 * @author zhaozhiwei
 * @date 2020/2/13 15:50
 */
public class BinaryTreeArchTraversal {

    public static void main(String[] args) {
        Stack<TNode> stack1 = new Stack<>();
        Stack<TNode> stack2 = new Stack<>();
        TNode nine = new TNode(9, null, null);
        TNode eight = new TNode(8, null, null);
        TNode seven = new TNode(7, null, null);
        TNode six = new TNode(6, null, null);
        TNode five = new TNode(5, null, null);
        TNode four = new TNode(4, eight, nine);
        TNode three = new TNode(3, six, seven);
        TNode two = new TNode(2, four, five);
        TNode root = new TNode(1, two, three);
        stack1.add(root);
        TNode p;
        LinkedHashMap<Integer, Integer> a = new LinkedHashMap<>();
        while (!stack1.isEmpty()) {
            while (!stack1.isEmpty()) {
                p = stack1.pop();
                System.out.println(p.getData());
                if (p.getLeft() != null) {
                    stack2.push(p.getLeft());
                }
                if (p.getRight() != null) {
                    stack2.push(p.getRight());
                }
            }
            while (!stack2.isEmpty()) {
                p = stack2.pop();
                System.out.println(p.getData());
                if (p.getRight() != null) {
                    stack1.push(p.getRight());
                }
                if (p.getLeft() != null) {
                    stack1.push(p.getLeft());
                }
            }
        }
    }

    public static class TNode {
        private int data;
        private TNode left;
        private TNode right;

        public TNode(int data, TNode left, TNode right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public int getData() {
            return data;
        }

        public TNode getLeft() {
            return left;
        }

        public TNode getRight() {
            return right;
        }
    }
}

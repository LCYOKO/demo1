package com.xiaomi.demo.net;

import org.junit.Test;
import org.w3c.dom.Node;

import java.util.Random;

/**
 * @Author：liuchiyun
 * @Date: 2021/5/10
 */
public class TestCase1 {


     @Test
    public void testSkpiList(){
        Skiplist skiplist = new Skiplist();
        skiplist.add(0);
        skiplist.add(5);
        skiplist.add(2);
        skiplist.add(1);
        skiplist.search(0);
//        skiplist.print();
        skiplist.erase(5);
        skiplist.search(2);
        skiplist.search(3);
        skiplist.search(2);
//        skiplist.print();
    }

    class Skiplist {
        Node head;
        int maxLevel;
        int curLevel;
        double limit=0.5D;
        Random random;
        public Skiplist() {
            maxLevel=10;
            curLevel=1;
            random=new Random();
            head=new Node(-1);

        }

        public boolean search(int target) {
            Node node=search(head,target);
            if(node==null){
                return false;
            }
            return  true;
        }

        public void add(int num) {
            int l=getLevel();
            while(l!=curLevel){
                if(l>curLevel){
                Node node=new Node(-1);
                node.down=head;
                head=node;
                curLevel++;
                }
                else {

                }
            }
            Node node=head;
            Node parent=null;
            while(node!=null){
                Node temp=node;
                while(temp.next!=null && temp.next.val<num){
                    temp=temp.next;
                }
                Node newNode=new Node(num);
                temp.next=newNode;
                if(parent!=null){
                    parent.down=newNode;
                }
                parent=newNode;
                node=node.down;
            }
        }

        public boolean erase(int num) {
            Node node= search(head,num);
            if(node==null ){
                return  false;
            }
            while(node!=null){
                Node temp=node;
                while(temp.next!=null &&temp.next.val<num){
                    temp=temp.next;
                }
                temp.next=temp.next.next;
                node=node.down;
            }
            return  true;
        }

        private int getLevel(){
            int cur=1;
            while(cur<maxLevel && random.nextDouble()<limit){
                cur++;
            }
            return  cur;
        }

        private Node search(Node node, int target){
            if(node==null){
                return  null;
            }
            while(node.next!=null && node.next.val<target){
                node=node.next;
            }
            if(node.next!=null && target==node.next.val){
                return node;
            }
            return search(node.down,target);
        }

        private void print(){
            Node node=head;
            while(node!=null){
                Node temp=node;
                while(temp.next!=null){
                    System.out.print(temp.next.val+" ");
                    temp=temp.next;
                }
                System.out.println();
                node=node.down;
            }
        }
        class Node{
            Node next;
            Node down;
            int val;
            Node(int val){
                this.val=val;
            }
        }
    }
}

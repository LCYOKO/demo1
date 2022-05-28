package com.xiaomi.demo.collection;

import lombok.Data;


/**
* @Author: lcy
* @Description //TODO
* @Date 15:13 2021/3/13
 *

 *
*/
@Data
public class MyQueue <E> {
    private int first=0;
    private int tail=0;
    private Object element[]=null;
    private final int DEFAULTSIZE=10;
    private int size;
    private int cap;
    public MyQueue(int cap){
         this.cap=cap+1;
    }
    private void init(){
        element=new Object[cap];
    }
   public MyQueue(){
         cap=DEFAULTSIZE+1;
    }
    public boolean isFull(){
        return first==(tail+1)%cap;
    }

    public boolean isEmpty(){
        return first==tail;
    }

    public  boolean push(E e){
        if(element==null) init();
        if(isFull()) return  false;
        element[tail]=e;
        size++;
        tail=(tail+1)%cap;
        return  true;
    }

    public E pop(){
        if (element==null) init();
        if(isEmpty()) throw  new RuntimeException("队列未空");
        E e=(E)element[first];
        size--;
        first=(first+1)%cap;
        return  e;

    }
    public int size(){
        return  this.size;
    }
}

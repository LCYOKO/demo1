package com.xiaomi.demo.net;

import io.netty.channel.epoll.EpollSocketChannel;
import org.junit.Test;
import org.springframework.data.relational.core.sql.In;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author：liuchiyun
 * @Date: 2021/5/7
 */
public class TestIO {

    @Test
    public void test() throws FileNotFoundException {
//        EpollSocketChannel

          List<List<Integer>> l=new ArrayList<>();
          ArrayList<Integer> list = new ArrayList<>();

    }


    public boolean isConvex(List<List<Integer>> points) {
        boolean flag=false;
        int n=points.size();
        if(!compl(points.get(n-1),points.get(0),points.get(1))){
            return false;
        }
        if(!compl(points.get(n-2),points.get(n-1),points.get(1))){
            return false;
        }
        for(int i=2;i<n;i++){
            if(!compl(points.get(i-2),points.get(i-1),points.get(i))){
                return false;
            }
        }
        return true;
    }

    private boolean compl(List<Integer> p1, List<Integer> p2, List<Integer> p3){
        int x1=p1.get(0)-p2.get(0),y1=p1.get(1)-p2.get(1),x2=p3.get(0)-p2.get(0),y2=p3.get(1)-p2.get(1);
        return  x1*x2+y1*y2>=0?true:false;
    }
}

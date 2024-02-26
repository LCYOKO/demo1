package com.xiaomi.demo.distributed.zk;//package com.xiaomi.demo.distributed.zk;
//
//import org.apache.curator.RetryPolicy;
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.framework.recipes.cache.*;
//import org.apache.curator.framework.recipes.locks.InterProcessMutex;
//
//import org.apache.curator.retry.ExponentialBackoffRetry;
//import org.apache.zookeeper.CreateMode;
//import org.apache.zookeeper.data.Stat;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.data.relational.core.sql.In;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
///**
// * @Author：liuchiyun
// * @Date: 2021/6/2
// */
//public class ZkTest {
//
//    private CuratorFramework client;
//
//    private String path="/test";
//    @Before
//    public void init(){
//        RetryPolicy retry = new ExponentialBackoffRetry(2000,3);
//        client = CuratorFrameworkFactory.builder()
//                .connectString("localhost:2181")
//                .sessionTimeoutMs(100000)
//                .retryPolicy(retry)
//                .connectionTimeoutMs(1000)
//                .build();
//        client.start();
//    }
//    @After
//    public void close(){
//        client.close();
//    }
//
//    @Test
//    public void test() throws Exception {
//
//    }
//
//    boolean[][] col=new boolean[9][9];
//    boolean[][] row=new boolean[9][9];
//    boolean[][] table=new boolean[9][9];
//    public void solveSudoku(char[][] board) {
//        dfs(board,0,0);
//
//    }
//
//    private boolean dfs(char board[][],int i,int j){
//        if(j==9){
//            i++;
//            j=0;
//        }
//        if(i==9){
//            return true;
//        }
//        if(board[i][j]=='.'){
//            for(int k=1;k<=9;k++){
//                if(col[j][k-1] || row[i][k-1] ||table[i/3*3+j/3][k-1]){
//                    continue;
//                }
//                col[j][k-1]=true;
//                row[i][k-1]=true;
//                table[i/3*3+j/3][k-1]=true;
//                board[i][j]=(char)(k+'0');
//                if(dfs(board,i,j+1)){
//                    return true;
//                }
//                col[j][k-1]=false;
//                row[i][k-1]=false;
//                table[i/3*3+j/3][k-1]=false;
//            }
//            return false;
//        }
//        else {
//            col[j][board[i][j]-'1']=true;
//            row[i][board[i][j]-'1']=true;
//            table[i/3*3+j/3][board[i][j]-'1']=true;
//            return dfs(board,i,j+1);
//        }
//        // return false;
//    }
//
//    private void testCreate() throws Exception {
//        client.create()
//                .creatingParentsIfNeeded()
//              .withMode(CreateMode.PERSISTENT)
//              .forPath("/test_temp1/a","123".getBytes());
//    }
//
//    private void testGet() throws Exception {
//        Stat stat = new Stat();
//        byte[] bytes = client.getData().
//                storingStatIn(stat)
//                .forPath("/test_temp1");
//        System.err.println(new String(bytes));
//        System.err.println(stat);
//        List<String> list = client.getChildren().forPath("/test_temp1");
//        System.err.println(list);
//    }
//
//    private void testSet() throws Exception {
//        client.setData()
//                .forPath("/test_temp1","456".getBytes());
//    }
//
//    private void testDel() throws Exception {
//        client.delete().deletingChildrenIfNeeded()
//                .forPath("/test_temp1");
//    }
//
//    private void registerNodeWatcher() throws Exception {
//        NodeCache nodeCache = new NodeCache(client, path);
//        nodeCache.getListenable().addListener(new NodeCacheListener() {
//            @Override
//            public void nodeChanged() throws Exception {
//               System.out.println("Node cache Change ");
//               System.out.println(nodeCache.getPath());
//               System.out.println(new String(nodeCache.getCurrentData().getData()));
//            }
//        });
//        nodeCache.start();
//    }
//
//    private void registerPathWatcher() throws Exception {
//        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, path,true);
//        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
//            @Override
//            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
//                System.out.println("path changed");
//                System.out.println(event.toString());
//            }
//        });
//        pathChildrenCache.start();
//    }
//
//    private void registerTreeWatcher() throws Exception {
//        TreeCache treeCache = new TreeCache(client,path);
//        treeCache.getListenable().addListener(new TreeCacheListener() {
//            @Override
//            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
//                System.out.println("tree changed");
//                System.out.println(event.toString());
//            }
//        });
//        treeCache.start();
//    }
//
//
//}
package com.xiaomi.demo.etcd;

import io.etcd.jetcd.*;
import io.etcd.jetcd.cluster.MemberListResponse;
import io.etcd.jetcd.election.CampaignResponse;
import io.etcd.jetcd.kv.DeleteResponse;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.kv.PutResponse;
import io.etcd.jetcd.kv.TxnResponse;
import io.etcd.jetcd.lease.LeaseGrantResponse;
import io.etcd.jetcd.lock.LockResponse;
import io.etcd.jetcd.op.Cmp;
import io.etcd.jetcd.op.CmpTarget;
import io.etcd.jetcd.op.Op;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * @Author liuchiyun
 * @Date 2024/6/17
 * https://etcd.io/docs/v3.5/tutorials/
 */
@Slf4j
public class EtcdTest {
    private Client etcdClient = Client.builder()
            .endpoints("http://localhost:2379")
//            .user(ByteSequence.from("root".getBytes(StandardCharsets.UTF_8)))
//            .password(ByteSequence.from("123".getBytes(StandardCharsets.UTF_8)))
            .build();
    private KV kvClient = etcdClient.getKVClient();


    @Test
    public void testCreate() throws ExecutionException, InterruptedException {
        ByteSequence key = ByteSequence.from("test_key".getBytes());
        ByteSequence value = ByteSequence.from("test_value".getBytes());
        CompletableFuture<PutResponse> future = kvClient.put(key, value);
        log.info("put result:{}", future.get());
    }

    @Test
    public void testUpdate() throws ExecutionException, InterruptedException {
        ByteSequence key = ByteSequence.from("test_key".getBytes());
        ByteSequence value = ByteSequence.from("test_value1231".getBytes());
        CompletableFuture<PutResponse> future = kvClient.put(key, value);
        log.info("put result:{}", future.get());
    }

    @Test
    public void testGetData() throws ExecutionException, InterruptedException {
        ByteSequence key = ByteSequence.from("test_key".getBytes());
        CompletableFuture<GetResponse> future = kvClient.get(key);
        log.info("get result:{}", future.get());
    }

    @Test
    public void testPrefix() throws ExecutionException, InterruptedException {
        ByteSequence key = ByteSequence.from("test".getBytes());
        GetOption getOption = GetOption.newBuilder().withPrefix(ByteSequence.EMPTY).build();
        CompletableFuture<GetResponse> future = kvClient.get(key, getOption);
        log.info("get result:{}", future.get());
    }

    @Test
    public void testDelete() throws ExecutionException, InterruptedException {
        ByteSequence key = ByteSequence.from("test".getBytes());
        CompletableFuture<DeleteResponse> future = kvClient.delete(key);
        log.info("get result:{}", future.get());
    }

    @Test
    public void testWatch() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Watch watchClient = etcdClient.getWatchClient();
        watchClient.watch(of("test"), response -> {
            log.info("watch result:{}", response);
            countDownLatch.countDown();
        });
        countDownLatch.await();
    }

    @Test
    public void testTransaction() throws ExecutionException, InterruptedException {
        Txn txn = kvClient.txn();
        CompletableFuture<TxnResponse> future = txn.If(new Cmp(of("test"), Cmp.Op.EQUAL, CmpTarget.value(of("123"))))
                .Then(Op.put(of("test"), of("1234"), PutOption.DEFAULT))
                .commit();
        log.info("txt  result:{}", future.get());
    }

    @Test
    public void testLease() throws ExecutionException, InterruptedException {
        Lease leaseClient = etcdClient.getLeaseClient();
        CompletableFuture<LeaseGrantResponse> grant = leaseClient.grant(10);
        LeaseGrantResponse leaseGrantResponse = grant.get();
        log.info("grant result:{}", leaseGrantResponse);

        ByteSequence key = ByteSequence.from("test_key1".getBytes());
        ByteSequence value = ByteSequence.from("test_value1".getBytes());
        PutOption putOption = PutOption.newBuilder().withLeaseId(leaseGrantResponse.getID()).build();
        CompletableFuture<PutResponse> future = kvClient.put(key, value, putOption);
        log.info("put result:{}", future.get());
    }

    @Test
    public void testLock() throws ExecutionException, InterruptedException {
        Lock lockClient = etcdClient.getLockClient();
        Lease leaseClient = etcdClient.getLeaseClient();
        CompletableFuture<LeaseGrantResponse> grant = leaseClient.grant(10);
        LeaseGrantResponse leaseGrantResponse = grant.get();
        CompletableFuture<LockResponse> lock = lockClient.lock(of("lock"), leaseGrantResponse.getID());
        log.info("lock result:{}", lock.get());
    }

    @Test
    public void testLeader() throws ExecutionException, InterruptedException {
        Lease leaseClient = etcdClient.getLeaseClient();
        CompletableFuture<LeaseGrantResponse> grant = leaseClient.grant(10000);
        LeaseGrantResponse leaseGrantResponse = grant.get();
        Election electionClient = etcdClient.getElectionClient();
        ByteSequence sequence = ByteSequence.from("test_leader".getBytes(StandardCharsets.UTF_8));
        ByteSequence proposal = ByteSequence.from("one".getBytes(StandardCharsets.UTF_8));
        CompletableFuture<CampaignResponse> completableFuture = electionClient.campaign(sequence, leaseGrantResponse.getID(), proposal);
        log.info("leader result:{}", completableFuture.get());

    }

    @Test
    public void testCluster() throws ExecutionException, InterruptedException {
        Cluster clusterClient = etcdClient.getClusterClient();
        CompletableFuture<MemberListResponse> listMember = clusterClient.listMember();
        log.info("cluster result:{}", listMember.get());
    }

    private ByteSequence of(String str) {
        return ByteSequence.from(str.getBytes());
    }
}

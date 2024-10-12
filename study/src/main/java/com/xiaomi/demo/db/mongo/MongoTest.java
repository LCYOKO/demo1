package com.xiaomi.demo.db.mongo;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.junit.Test;

import java.io.Serializable;

/**
 * @Author liuchiyun
 * @Date 2024/6/17
 */
@Slf4j
public class MongoTest {
    private final String connectStr = "mongodb://localhost:27017";
    private final MongoClient mongoClient = MongoClients.create(connectStr);
    private final MongoDatabase userDb = mongoClient.getDatabase("users");


    @Test
    public void testDb() {
        MongoIterable<String> names = mongoClient.listDatabaseNames();
        names.forEach(name -> log.info("dbName:{}", name));
    }

    @Test
    public void testFind() {
        Document filter = new Document();
        filter.put("name", "lisi");
        userDb.getCollection("users").find(filter).forEach(user -> log.info("user:{}", user));
    }

    @Test
    public void testUpdate() {
        Document filter = new Document("name", "lisi");
        userDb.getCollection("users").find(filter).forEach(user -> log.info("user:{}", user));

        userDb.getCollection("users").updateOne(filter, new Document("$set", new Document("email", "lisi@qq.com")));

        userDb.getCollection("users").find(filter).forEach(user -> log.info("user:{}", user));
    }

    public void testDelete() {

    }


    @Data
    public static class User implements Serializable {
        private String name;
        private String email;
        private String password;
    }

}

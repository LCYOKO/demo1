package com.xiaomi.demo.db.hbase;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.apache.hadoop.hbase.CellBuilderType.DEEP_COPY;

/**
 * @Author: liuchiyun
 * @Date: 2023/11/9
 */
@Slf4j
public class HbaseTest {
    private Connection connection;

    @Before
    public void createConnection() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "localhost:2181");
        connection = ConnectionFactory.createConnection(configuration);
    }

    @Test
    public void testCreateNameSpace() throws IOException {
        Admin admin = connection.getAdmin();
        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create("test2").build();
        admin.createNamespace(namespaceDescriptor);
    }

    @Test
    @Ignore
    public void testDeleteNs() throws IOException {
        Admin admin = connection.getAdmin();
        admin.deleteNamespace("test2");
    }

    @Test
    public void testListNs() throws IOException {
        Admin admin = connection.getAdmin();
        NamespaceDescriptor[] listNamespaceDescriptors = admin.listNamespaceDescriptors();
        log.info("list:{}", Arrays.toString(listNamespaceDescriptors));
    }

    @Test
    public void testCreateTable() throws IOException {
        Admin admin = connection.getAdmin();
        TableDescriptor descriptor = TableDescriptorBuilder.newBuilder(TableName.valueOf("test1:users"))
                .setColumnFamily(ColumnFamilyDescriptorBuilder.of("info"))
                .build();
        admin.createTable(descriptor);
    }

    @Test
    public void testDescribeTable() throws IOException {
        Admin admin = connection.getAdmin();
        TableName tableName = TableName.valueOf("test1:users");
        TableDescriptor tableDescriptor = admin.getDescriptor(tableName);
        log.info("table:{}", tableDescriptor);
    }

    @Test
    @Ignore
    public void testDeleteTables() throws IOException {
        Admin admin = connection.getAdmin();
        TableName tableName = TableName.valueOf("test1:users");
        //删除前需要先禁用
        admin.disableTable(tableName);
        admin.deleteTable(tableName);
    }

    @Test
    public void testUpdateTable() throws IOException {
        Admin admin = connection.getAdmin();
        TableName tableName = TableName.valueOf("test1:users");
        TableDescriptor tableDescriptor = admin.getDescriptor(tableName);
        log.info("TableDescriptor:{}", tableDescriptor);

        admin.addColumnFamily(tableName, ColumnFamilyDescriptorBuilder.of("test"));
        tableDescriptor = admin.getDescriptor(tableName);
        log.info("TableDescriptor:{}", tableDescriptor);

        byte[] bytes = ColumnFamilyDescriptorBuilder.of("test").getName();
        admin.deleteColumnFamily(tableName, bytes);

        tableDescriptor = admin.getDescriptor(tableName);
        log.info("TableDescriptor:{}", tableDescriptor);
    }

    @Test
    public void testGetTables() throws IOException {
        Admin admin = connection.getAdmin();
        log.info("tables:{}", admin.listTableDescriptors());
    }

    @Test
    public void testPut() throws IOException {
        Table table = connection.getTable(TableName.valueOf("test1:users"));
        Put put = new Put("row1".getBytes(StandardCharsets.UTF_8));
        Cell cell1 = CellBuilderFactory.create(DEEP_COPY)
                .setRow("row1".getBytes(StandardCharsets.UTF_8))
                .setFamily("info".getBytes(StandardCharsets.UTF_8))
                .setQualifier("name".getBytes(StandardCharsets.UTF_8))
                .setType(Cell.Type.Put)
                .setValue("xiaomi".getBytes(StandardCharsets.UTF_8))
                .build();

        Cell cell2 = CellBuilderFactory.create(DEEP_COPY)
                .setRow("row1".getBytes(StandardCharsets.UTF_8))
                .setFamily("info".getBytes(StandardCharsets.UTF_8))
                .setQualifier("age".getBytes(StandardCharsets.UTF_8))
                .setType(Cell.Type.Put)
                .setValue("16".getBytes(StandardCharsets.UTF_8))
                .build();
        put.add(cell1);
        put.add(cell2);
        table.put(put);
    }

    @Test
    public void testGet() throws IOException {
        Table table = connection.getTable(TableName.valueOf("test1:users"));
        Get get = new Get("row1".getBytes(StandardCharsets.UTF_8));
        Result result = table.get(get);
        log.info("result:{}", result.getColumnCells("info".getBytes(StandardCharsets.UTF_8), "name".getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    public void testDelete() throws IOException {
        Table table = connection.getTable(TableName.valueOf("test1:users"));
        Delete delete = new Delete("row1".getBytes(StandardCharsets.UTF_8));
        table.delete(delete);
    }
}

## 小米物流 bms系统,多线程批量并发导入，发生的死锁
-- 创建测试表
CREATE TABLE `t_user_test`
(
    `id`   int(11) unsigned NOT NULL AUTO_INCREMENT,
    `no`   int(11)     DEFAULT NULL,
    `name` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`id`),
    unique key (`no`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC;
-- 测试数据
insert into t_user_test ( `no`, `name`)
values (1,  'test'),
       (2,  'test'),
       (3,  'test' ),
       (5,  'test'),
       (30,  'test');

# session1
set autocommit = 0;

begin;

insert into t_user_test(no, name) values (6, 'analysis') on duplicate key update name='analytics';

# session2
set autocommit = 0;

begin;

insert into t_user_test(no, name) values (7, 'analysis') on duplicate key update name='analytics';

# session3
set autocommit = 0;

begin;

insert into t_user_test(no, name) values (8, 'analysis') on duplicate key update name='analytics';


# 首先insert on duplicate key 这条sql的语义是：
# 如果insert中的对应键值在数据库中没有找到对应的唯一索引记录，即进行插入；
# 如果对表中唯一索引记录冲突，便进行更新，能够很轻松的达到一种效果： 有则直接更新，无则插入。
# 而我们业务中的sql是自增主键id，这样一来冲突的只有可能是 phone这个唯一索引了。
#首先，在RR的事务隔离级别下，insert on duplicate key这个sql与普通insert只插入意向锁和记录锁不同，
# insert on duplicate key sql如果没有找到对应的会在唯一键上插入gap lock和插入意向锁（如果有对应记录则会获取next key lock，next key lock 比gap lock多了一个边缘的记录锁）
#gap lock即间隙锁，假设目前表中唯一键的数据有以下几个，1，5，10。
# 那么insert的key如果是4,在1-5之间，则获取的gap lock的区间就是（1，5）；
# 如果插入的数据是15，则在10-正无穷之间，因此gap lock的区间就是（10，正无穷），这个gap lock。
# 插入意向锁也是类似于gap lock的一种，生效的范围也一致，只是对应锁上相同范围或者有交集的。
# 横轴为已持有，纵轴为后续申请，是否互斥或兼容
# 兼容性	       插入意向锁 	行锁	    gap lock
# 插入意向锁	    兼容  	    互斥	     互斥
# 行锁     	    兼容	        互斥	     兼容
# gaplock	    兼容	        兼容	     兼容
# 因此可以看到，在持有gap lock时，在插入的时候如果申请插入意向锁，便会需要等待，而insert on duplicate key的sql在执行时一般就是gap lock和插入意向锁。那么造成死锁的问题就定位到了，肯定是同一时间多个insert事务到来，并且所插入的记录对应的唯一键范围基本一致，所拥有的gap lock和插入意向锁的范围有交集，便可以出现共同持有锁反而造成死锁的问题。
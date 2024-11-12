## 老虎证券实际业务中发现的
# 横轴为已持有，纵轴为后续申请，是否互斥或兼容
# 兼容性	       插入意向锁 	行锁	    gap lock
# 插入意向锁	    兼容  	    互斥	     互斥
# 行锁     	    兼容	        互斥	     兼容
# gaplock	    兼容	        兼容	     兼容

CREATE TABLE `asset_funds`
(
    `id`       int(11)     NOT NULL AUTO_INCREMENT,
    account_id bigint      not null,
    `currency` varchar(10) NOT NULL,
    `type`     varchar(10) NOT NULL,
    `amount`   decimal(20, 10) DEFAULT '0.0000000000',
    PRIMARY KEY (`id`),
    UNIQUE KEY `af_uk_act` (account_id,`currency`, `type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

insert into asset_funds(account_id, currency, type, amount)
values (1, 'BTC', 'AVAILABLE', 0)
     , (2, 'ETH', 'AVAILABLE', 0);



# session1
set autocommit =0;

start transaction;
select * from asset_funds where account_id=1 for update;
insert into asset_funds(account_id, currency, type, amount)
values (1, 'ETH', 'AVAILABLE', 0.0000000001);


# session2
set autocommit = 0;
start transaction;
select * from asset_funds where account_id=2 for update;
insert into asset_funds(account_id, currency, type, amount)
values (2, 'BTH', 'AVAILABLE', 0.0000000001);


# 查看锁状态
select
    ENGINE_LOCK_ID,
    ENGINE_TRANSACTION_ID,
    EVENT_ID,
    OBJECT_NAME,
    INDEX_NAME,
    OBJECT_INSTANCE_BEGIN,
    LOCK_TYPE,
    LOCK_MODE,
    LOCK_STATUS,
    LOCK_DATA
from performance_schema.data_locks;

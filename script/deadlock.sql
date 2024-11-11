## 老虎证券实际业务中发现的

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
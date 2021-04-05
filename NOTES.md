# MySQL
    AUTO_INCREMENT=n  # 指定一个自增的初始值
## 索引
    UNIQUE KEY `u_uid_gid` (`user_id`, `goods_id USING BTREE);  # 唯一索引，组合索引，物理上使用B-Tree。
### 物理类型
#### B-Tree (大部分)
#### Hash
### 逻辑类型
#### 普通索引
INDEX / KEY，可以为NULL，可以重复。

    CREATE INDEX index_id ON tb_student(id);
#### 唯一索引
UNIQUE，可以为NULL，除NULL值不可重复。
#### 主键索引
PRIMARY KEY，不可为NULL，不可重复。
#### 空间索引
SPATIAL，只可用于MylSAM引擎，主要用于地理空间数据，必须声明NOT NULL。
#### 全文索引
FULLTEXT，主要用来查找文本中的关键字，只能在 CHAR、VARCHAR 或 TEXT 类型的列上创建。在 MySQL 中只有 MyISAM 存储引擎支持全文索引。
### 实际使用类型
#### 单列索引
    CREATE INDEX index_addr ON tb_student(address(4));
#### 组合索引/多列索引
    CREATE INDEX index_na ON tb_student(name,address);
# RabbitMQ
安装后执行```sudo rabbitmq-plugins enable rabbitmq_management```才能打开 http://127.0.0.1:15672/ 管理页面。
# SpringBoot
>spring boot允许你通过命名约定按照一定的格式(application-{profile}.properties)来定义多个配置文件，然后通过在application.properties通过spring.profiles.active来具体激活一个或者多个配置文件

> 
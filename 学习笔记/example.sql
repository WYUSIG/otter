# 模拟双向同步数据库1
create database otter_demo_ds1;

create table if not exists otter_demo_ds1.tb_user
(
    id   bigint      not null primary key auto_increment comment '用户id',
    name varchar(30) null comment '用户姓名',
    age  int         null comment '年龄'
) engine = innodb
  default charset = utf8mb4;

create table if not exists otter_demo_ds1.tb_order
(
    id        bigint      not null primary key auto_increment comment '订单号',
    code      varchar(50) null comment '订单编号',
    good_name varchar(50) null comment '商品名称',
    price     decimal     null comment '价格'
) engine = innodb
  default charset = utf8mb4;

create table if not exists otter_demo_ds1.tb_lead
(
    id                   bigint       not null primary key auto_increment comment '例子id',
    user_id              bigint       not null comment '用户id',
    user_name            bigint       null comment '用户姓名',
    user_phone           varchar(20)  null comment '用户电话',
    cc_id                bigint       not null comment '销售id',
    cc_name              varchar(50)  null comment '销售姓名',
    user_intention       varchar(100) null comment '用户意向',
    last_attend_time     datetime     null comment '最近一次出席时间',
    cc_last_contact_time datetime     null comment '最近一次联系时间'
) engine = innodb
  default charset = utf8mb4;

# ============================================================== #

# 模拟双向同步数据库2
create database otter_demo_ds2;

create table if not exists otter_demo_ds2.tb_user
(
    id        bigint      not null primary key auto_increment comment '用户id',
    name      varchar(30) null comment '用户姓名',
    age       int         null comment '年龄',
    wx_openid varchar(50) comment '微信openid'
) engine = innodb
  default charset = utf8mb4;

create table if not exists otter_demo_ds2.tb_order
(
    id    bigint      not null primary key auto_increment comment '订单号',
    code  varchar(50) null comment '订单编号',
    price decimal     null comment '价格'
) engine = innodb
  default charset = utf8mb4;

create table if not exists otter_demo_ds2.tb_lead
(
    id         bigint      not null primary key auto_increment comment '例子id',
    user_id    bigint      not null comment '用户id',
    user_name  bigint      null comment '用户姓名',
    user_phone varchar(20) null comment '用户电话',
    cc_id      bigint      not null comment '销售id',
    cc_name    varchar(50) null comment '销售姓名'
) engine = innodb
  default charset = utf8mb4;

create table if not exists otter_demo_ds2.tb_lead_extend
(
    id                   bigint       not null primary key comment '例子id',
    user_intention       varchar(100) null comment '用户意向',
    last_attend_time     datetime     null comment '最近一次出席时间',
    cc_last_contact_time datetime     null comment '最近一次联系时间'
) engine = innodb
  default charset = utf8mb4;



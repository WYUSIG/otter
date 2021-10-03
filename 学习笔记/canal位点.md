mysql查看binlog position
```sql
show binary logs;

show binlog events in 'mysql-bin.000012';

mysqlbinlog --start-position=219 --stop-position=1001 ../data/mysql-bin.000012
```

journalName:binlog文件名

position:位置

timestamp:时间戳

canal获取最后的位置信息：

1、先从自身获取（内存、磁盘、zk等）

2、配置文件中获取

3、连接远程show master status获取

4、若没有配置journalName，则根据配置timestamp获取
   （连接找出所有binlog，根据时间来找到对应位置）

5、若配置journaName，优先匹配journalName + position
  没有position在匹配journamName + timestamp
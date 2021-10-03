### 添加zookeeper

页面：

addZookeeper.vm

接口：

com.alibaba.otter.manager.web.home.module.action.AutoKeeperClusterAction.doAdd

com.alibaba.otter.manager.biz.config.autokeeper.impl.AutoKeeperClusterServiceImpl

com.alibaba.otter.manager.biz.config.autokeeper.dal.ibatis.IbatisAutoKeeperClusterDAO

实体：

com.alibaba.otter.shared.common.model.autokeeper.AutoKeeperCluster

数据库：

autokeeper_cluster表

sqlmap/sqlmap-mapping-autokeeper.xml:44
```xml
<insert id="insertAutoKeeperCluster" parameterClass="autoKeeperCluster">
    insert into AUTOKEEPER_CLUSTER
    (CLUSTER_NAME,SERVER_LIST,DESCRIPTION,GMT_CREATE,GMT_MODIFIED)
    values (#clusterName#,#serverList#,#description#,now(),now())
</insert>
```

### 添加node

页面：

addNode.vm

接口：

com.alibaba.otter.manager.web.home.module.action.NodeAction.doAdd

com.alibaba.otter.manager.biz.config.node.impl.NodeServiceImpl

com.alibaba.otter.manager.biz.config.node.dal.ibatis.IbatisNodeDAO

实体：

com.alibaba.otter.shared.common.model.config.node.Node

com.alibaba.otter.shared.common.model.config.node.NodeParameter

数据库：

node表

sqlmap/sqlmap-mapping-node.xml:96
```xml
<insert id="insertNode" parameterClass="node">
    insert into NODE
    (NAME,IP, PORT, DESCRIPTION, PARAMETERS,GMT_CREATE,
    GMT_MODIFIED)
    SELECT
    #name#,#ip#,#port#,#description#,#parameters#,now(),now()
    from dual
    WHERE not exists (select * from NODE
    where NODE.NAME = #name# or
    ( NODE.IP = #ip# and
    NODE.PORT = #port# )
    );
    <selectKey keyProperty="id" resultClass="long">
        select
        last_insert_id()
    </selectKey>
</insert>
```

### 添加数据源

页面：

addDataSource.vm

接口：

com.alibaba.otter.manager.web.home.module.action.DataMediaSourceAction.doAdd

com.alibaba.otter.manager.biz.config.datamediasource.impl.DataMediaSourceServiceImpl

com.alibaba.otter.manager.biz.config.datamediasource.dal.ibatis.IbatisDataMediaSourceDAO

实体：

com.alibaba.otter.shared.common.model.config.data.DataMediaSource

com.alibaba.otter.shared.common.model.config.data.db.DbMediaSource

数据库：

data_media_source表

sqlmap/sqlmap-mapping-datamediasource.xml:91
```xml
<insert id="insertDataMediaSource" parameterClass="dataMediaSource">
    insert into DATA_MEDIA_SOURCE
    (NAME,TYPE,PROPERTIES,GMT_CREATE, GMT_MODIFIED)
    SELECT #name#,#type#,#properties#,now(),now()
    from dual
    WHERE not exists (select * from DATA_MEDIA_SOURCE
    where DATA_MEDIA_SOURCE.NAME = #name# ); 
    <selectKey keyProperty="id" resultClass="long">
        select
        last_insert_id()
    </selectKey>
</insert>
```

### 添加数据表

页面：

addDataMedia.vm

接口：

com.alibaba.otter.manager.web.home.module.action.DataMediaAction.doAdd

com.alibaba.otter.manager.biz.config.datamedia.impl.DataMediaServiceImpl

com.alibaba.otter.manager.biz.config.datamedia.dal.ibatis.IbatisDataMediaDAO

实体：

com.alibaba.otter.shared.common.model.config.data.DataMedia

数据库：

data_media表，主要信息保存在properties，为DataMedia json字符串

sqlmap/sqlmap-mapping-datamedia.xml:113
```xml
<insert id="insertDataMedia" parameterClass="dataMedia">
    insert into DATA_MEDIA
    (NAME,NAMESPACE,PROPERTIES,DATA_MEDIA_SOURCE_ID,GMT_CREATE, GMT_MODIFIED)
    SELECT #name#,#namespace#,#properties#,#dataMediaSourceId#,now(),now()
    from dual
    WHERE not exists (select * from DATA_MEDIA
    where DATA_MEDIA.NAME = #name# 
    and DATA_MEDIA.NAMESPACE = #namespace#
    and DATA_MEDIA.DATA_MEDIA_SOURCE_ID = #dataMediaSourceId#); 
    <selectKey keyProperty="id" resultClass="long">
        select
        last_insert_id()
    </selectKey>
</insert>
```

### 添加canal

页面：

addCanal.vm

接口：

com.alibaba.otter.manager.web.home.module.action.CanalAction.doAdd

com.alibaba.otter.manager.biz.config.canal.impl.CanalServiceImpl

com.alibaba.otter.manager.biz.config.canal.dal.ibatis.IbatisCanalDAO

实体：

com.alibaba.otter.canal.instance.manager.model.Canal

com.alibaba.otter.canal.instance.manager.model.CanalParameter

数据库：

canal表

sqlmap/sqlmap-mapping-canal.xml:40
```xml
<insert id="insertCanal" parameterClass="canal">
    insert into CANAL
    (NAME, DESCRIPTION, PARAMETERS,GMT_CREATE,GMT_MODIFIED)
    SELECT
    #name#,#description#,#parameters#,now(),now()
    from dual
    WHERE not exists (select * from CANAL
    where CANAL.NAME = #name# );
    <selectKey keyProperty="id" resultClass="long">
        select
        last_insert_id()
    </selectKey>
</insert>
```

### 添加channel

页面：

addChannel.vm

接口：

com.alibaba.otter.manager.web.home.module.action.CanalAction.doAdd

com.alibaba.otter.manager.biz.config.canal.impl.CanalServiceImpl

com.alibaba.otter.manager.biz.config.canal.dal.ibatis.IbatisCanalDAO

实体：

com.alibaba.otter.canal.instance.manager.model.Canal

com.alibaba.otter.canal.instance.manager.model.CanalParameter

数据库：

canal表

sqlmap/sqlmap-mapping-canal.xml:40
```xml
<insert id="insertCanal" parameterClass="canal">
    insert into CANAL
    (NAME, DESCRIPTION, PARAMETERS,GMT_CREATE,GMT_MODIFIED)
    SELECT
    #name#,#description#,#parameters#,now(),now()
    from dual
    WHERE not exists (select * from CANAL
    where CANAL.NAME = #name# );
    <selectKey keyProperty="id" resultClass="long">
        select
        last_insert_id()
    </selectKey>
</insert>
```

### 添加pipeline

页面：

addPipeline.vm

接口：

com.alibaba.otter.manager.web.home.module.action.PipelineAction.doAdd

com.alibaba.otter.manager.biz.config.pipeline.impl.PipelineServiceImpl

com.alibaba.otter.manager.biz.config.pipeline.dal.ibatis.IbatisPipelineDAO

实体：

com.alibaba.otter.shared.common.model.config.pipeline.Pipeline

com.alibaba.otter.shared.common.model.config.pipeline.PipelineParameter

数据库：

pipeline表

sqlmap/sqlmap-mapping-pipeline.xml:102
```xml
<insert id="insertPipeline" parameterClass="pipeline">
    insert into PIPELINE
    (NAME, PARAMETERS,DESCRIPTION,CHANNEL_ID,GMT_CREATE, GMT_MODIFIED)
    SELECT #name#,#parameters#,#description#,#channelId#,now(),now()
    from dual
    WHERE not exists (select * from PIPELINE
    where PIPELINE.CHANNEL_ID = #channelId# and PIPELINE.NAME = #name#); 
    <selectKey keyProperty="id" resultClass="long">
        select last_insert_id()
    </selectKey>
</insert>
```


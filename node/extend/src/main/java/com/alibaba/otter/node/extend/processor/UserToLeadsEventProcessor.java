package com.alibaba.otter.node.extend.processor;

import com.alibaba.otter.shared.etl.model.EventColumn;
import com.alibaba.otter.shared.etl.model.EventData;

import java.sql.Types;

public class UserToLeadsEventProcessor extends AbstractEventProcessor {

    public boolean process(EventData eventData) {
        //处理非default not null字段, 都给默认值
        //借用createTime -> channel_level_id, isUpdate=false, bigint
        //借用version -> active_time,  isUpdate=false, datetime
        //借用userName -> channel_code,  isUpdate=false, varchar
        String phone = null;
        for (EventColumn column : eventData.getColumns()) {
            if ("createTime".equals(column.getColumnName())) {
                column.setNull(false);
                column.setColumnType(Types.BIGINT);
                column.setUpdate(false);
                column.setColumnValue("0");
            } else if ("version".equals(column.getColumnName())) {
                column.setNull(false);
                column.setColumnType(Types.DATE);
                column.setUpdate(false);
                column.setColumnValue("2021-10-15 00:00:00");
            } else if ("userName".equals(column.getColumnName())) {
                column.setNull(false);
                column.setColumnType(Types.VARCHAR);
                column.setUpdate(false);
                column.setColumnValue("");
            } else if ("phone".equals(column.getColumnName())) {
                if (!column.isNull()) {
                    phone = column.getColumnValue();
                }
            }
        }
        if (phone == null) {
            return false;
        }
        for (EventColumn column : eventData.getKeys()) {
            column.setColumnType(Types.VARCHAR);
            column.setColumnValue(phone);
        }
        return true;
    }
}

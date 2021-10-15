package com.alibaba.otter.node.extend.processor;

import com.alibaba.otter.shared.etl.model.EventColumn;
import com.alibaba.otter.shared.etl.model.EventData;

import java.sql.Types;

public class MxcLeadsToLeadsEventProcessor extends AbstractEventProcessor {

    public boolean process(EventData eventData) {
        String phone = null;
        for (EventColumn column : eventData.getColumns()) {
            if ("user_id".equals(column.getColumnName())) {
                column.setColumnType(Types.TINYINT);
                column.setNull(false);
                column.setColumnValue("1");
            } else if ("phone".equals(column.getColumnName())) {
                if (!column.isNull()) {
                    phone = column.getColumnValue();
                }
            }
        }
        if (phone == null) {
            return false;
        }
        for(EventColumn column : eventData.getKeys()) {
            column.setColumnType(Types.VARCHAR);
            column.setColumnValue(phone);
        }
        return true;
    }
}

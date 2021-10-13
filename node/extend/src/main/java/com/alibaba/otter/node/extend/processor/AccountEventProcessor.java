package com.alibaba.otter.node.extend.processor;

import com.alibaba.otter.shared.etl.model.EventColumn;
import com.alibaba.otter.shared.etl.model.EventData;

import java.sql.Types;

public class AccountEventProcessor extends AbstractEventProcessor {

    /**
     * status：A-> 0; D -> is_del = 1; I -> 1
     */
    public boolean process(EventData eventData) {
        boolean isDel = false;
        for (EventColumn column : eventData.getColumns()) {
            if ("status".equals(column.getColumnName())) {
                String oldValue = column.getColumnValue();
                if ("A".equals(oldValue)) {
                    column.setColumnValue("0");
                } else if ("D".equals(oldValue)) {
                    column.setColumnValue("1");
                    isDel = true;
                } else if ("I".equals(oldValue)) {
                    column.setColumnValue("1");
                } else {
                    column.setColumnValue("0");
                }
            }
        }
        //借用dingUserId -> is_del
        for (EventColumn column : eventData.getColumns()) {
            if ("dingUserId".equals(column.getColumnName())) {
                column.setColumnType(Types.INTEGER);
                column.setNull(false);
                if (isDel) {
                    column.setColumnValue("1");
                } else {
                    column.setColumnValue("0");
                }
            }
        }
        return true;
    }
}

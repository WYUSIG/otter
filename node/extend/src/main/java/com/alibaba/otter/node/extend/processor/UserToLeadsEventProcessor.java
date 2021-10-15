package com.alibaba.otter.node.extend.processor;

import com.alibaba.otter.shared.etl.model.EventColumn;
import com.alibaba.otter.shared.etl.model.EventData;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class UserToLeadsEventProcessor extends AbstractEventProcessor {

    public boolean process(EventData eventData) {
        List<EventColumn> columns = new ArrayList<EventColumn>();
        String phone = null;
        for (EventColumn column : eventData.getColumns()) {
            if ("phone".equals(column.getColumnName())) {
                if (!column.isNull()) {
                    phone = column.getColumnValue();
                }
            }
        }
        if (phone == null) {
            return false;
        }
        List<EventColumn> oldKeys = eventData.getOldKeys();
        eventData.setOldKeys(oldKeys);
        List<EventColumn> newKeys = new ArrayList<EventColumn>();
        EventColumn userPhone = new EventColumn();
        userPhone.setColumnType(Types.VARCHAR);
        userPhone.setKey(true);
        userPhone.setNull(false);
        userPhone.setColumnName("phone");
        userPhone.setColumnValue(phone);
        newKeys.add(userPhone);
        eventData.setKeys(newKeys);
        eventData.setColumns(columns);
        return true;
    }
}

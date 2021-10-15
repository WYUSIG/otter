package com.alibaba.otter.node.extend.processor;

import com.alibaba.otter.shared.etl.model.EventColumn;
import com.alibaba.otter.shared.etl.model.EventData;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class LeadsExtraInfoToLeadsEventProcessor extends AbstractEventProcessor {

    public boolean process(EventData eventData) {
        List<EventColumn> columns = new ArrayList<EventColumn>();
        String leadsId = null;
        for (EventColumn column : eventData.getColumns()) {
            if ("activity_user_id".equals(column.getColumnName())) {
                if (!column.isNull()) {
                    leadsId = column.getColumnValue();
                }
            }
        }
        if (leadsId == null) {
            return false;
        }
        List<EventColumn> oldKeys = eventData.getOldKeys();
        if (oldKeys.size() > 0) {
            eventData.setOldKeys(oldKeys);
        }
        List<EventColumn> newKeys = new ArrayList<EventColumn>();
        EventColumn activityUserId = new EventColumn();
        activityUserId.setColumnType(Types.BIGINT);
        activityUserId.setKey(true);
        activityUserId.setNull(false);
        activityUserId.setColumnName("activity_user_id");
        activityUserId.setColumnValue(leadsId);
        newKeys.add(activityUserId);
        eventData.setKeys(newKeys);
        eventData.setColumns(columns);
        return true;
    }
}

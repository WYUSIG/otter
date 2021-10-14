package com.alibaba.otter.node.extend.processor;

import com.alibaba.otter.shared.etl.model.EventColumn;
import com.alibaba.otter.shared.etl.model.EventData;
import com.alibaba.otter.shared.etl.model.EventType;

import java.util.ArrayList;
import java.util.List;

public class LeadsExtraInfoToLeadsEventProcessor extends AbstractEventProcessor {

    public boolean process(EventData eventData) {
        EventColumn idKey = null;
        EventColumn province = null;
        EventColumn city = null;
        for (EventColumn column : eventData.getColumns()) {
            if ("activity_user_id".equals(column.getColumnName())) {
                idKey = column;
                idKey.setIndex(1);
                idKey.setKey(true);
            } else if ("province".equals(column.getColumnName())) {
                province = column;
                province.setKey(false);
                province.setUpdate(true);
            } else if ("city".equals(column.getColumnName())) {
                city = column;
                city.setKey(false);
                city.setUpdate(true);
            }
        }
        eventData.setEventType(EventType.UPDATE);
        List<EventColumn> keys = new ArrayList<EventColumn>();
        keys.add(idKey);
        eventData.setKeys(keys);
        List<EventColumn> cols = new ArrayList<EventColumn>();
        cols.add(province);
        cols.add(city);
        eventData.setColumns(cols);
        return true;
    }
}

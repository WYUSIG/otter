package com.alibaba.otter.node.extend.processor;

import com.alibaba.otter.shared.etl.model.EventColumn;
import com.alibaba.otter.shared.etl.model.EventData;
import com.alibaba.otter.shared.etl.model.EventType;

import java.sql.Types;

public class LeadsExtraInfoToLeadsEventProcessor extends AbstractEventProcessor {

    public boolean process(EventData eventData) {
        //处理非default not null字段, 都给默认值
        //借用invite_level -> phone, varchar
        //借用demo_course_teacher_scratch -> channel_level_id, bigint
        //借用demo_course_teacher_python -> active_time, datetime
        //借用demo_course_teacher_cpp -> channel_code, varchar
        String leadsId = null;
        boolean isUpdate = !eventData.getEventType().equals(EventType.UPDATE) ;
        for (EventColumn column : eventData.getColumns()) {
            if ("invite_level".equals(column.getColumnName())) {
                column.setNull(false);
                column.setColumnType(Types.VARCHAR);
                column.setUpdate(isUpdate);
                column.setColumnValue("");
            } else if ("demo_course_teacher_scratch".equals(column.getColumnName())) {
                column.setNull(false);
                column.setColumnType(Types.BIGINT);
                column.setUpdate(isUpdate);
                column.setColumnValue("0");
            } else if ("demo_course_teacher_python".equals(column.getColumnName())) {
                column.setNull(false);
                column.setColumnType(Types.DATE);
                column.setUpdate(isUpdate);
                column.setColumnValue("2021-10-15 00:00:00");
            } else if ("demo_course_teacher_cpp".equals(column.getColumnName())) {
                column.setNull(false);
                column.setColumnType(Types.VARCHAR);
                column.setUpdate(isUpdate);
                column.setColumnValue("");
            } else if ("activity_user_id".equals(column.getColumnName())) {
                if (!column.isNull()) {
                    leadsId = column.getColumnValue();
                }
            }
        }
        if (leadsId == null) {
            return false;
        }
        for (EventColumn column : eventData.getKeys()) {
            column.setColumnValue(leadsId);
        }
        return true;
    }
}

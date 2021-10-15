package com.alibaba.otter.node.extend.processor;

import com.alibaba.otter.shared.etl.model.EventColumn;
import com.alibaba.otter.shared.etl.model.EventData;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class LeadsEventProcessor extends AbstractEventProcessor {

    private static final Map<String, Integer> channelIndex = new HashMap<String, Integer>();

    private static final Map<String, String> gradeMap = new HashMap<String, String>();

    static {
        channelIndex.put("channel4", 1);
        channelIndex.put("channel2", 2);
        channelIndex.put("channel3", 3);
        gradeMap.put("小学三年级", "三年级");
        gradeMap.put("小学一年级", "一年级");
        gradeMap.put("小学二年级", "二年级");
        gradeMap.put("小学四年级", "四年级");
        gradeMap.put("小学五年级", "五年级");
        gradeMap.put("初中一年级", "初一");
        gradeMap.put("中班", "幼儿园中班");
        gradeMap.put("一年级", "一年级");
        gradeMap.put("初中二年级", "初二");
        gradeMap.put("三年级", "三年级");
        gradeMap.put("二年级", "二年级");
        gradeMap.put("大班", "幼儿园大班");
        gradeMap.put("PRIMARY_SCHOOLE_1", "一年级");
        gradeMap.put("PRIMARY_SCHOOLE_2", "二年级");
        gradeMap.put("PRIMARY_SCHOOLE_3", "三年级");
        gradeMap.put("四年级", "四年级");
        gradeMap.put("PRIMARY_SCHOOLE_4", "四年级");
        gradeMap.put("五年级", "五年级");
        gradeMap.put("小学六年级", "六年级");
        gradeMap.put("PRIMARY_SCHOOLE_5", "五年级");
        gradeMap.put("六年级", "六年级");
        gradeMap.put("KINDERGARTEN_LARGE", "幼儿园大班");
        gradeMap.put("初中三年级", "初三");
        gradeMap.put("MIDDLE_SCHOOLE_1", "初一");
        gradeMap.put("KINDERGARTEN_MIDDLE", "幼儿园中班");
        gradeMap.put("初一", "初一");
        gradeMap.put("MIDDLE_SCHOOLE_2", "初二");
        gradeMap.put("初二", "初二");
        gradeMap.put("初三", "初三");
        gradeMap.put("幼儿园小班", "幼儿园小班");
        gradeMap.put("幼儿园中班", "幼儿园中班");
        gradeMap.put("幼儿园大班", "幼儿园大班");
        gradeMap.put("MIDDLE_SCHOOLE_3", "初三");
        gradeMap.put("5", "五年级");
        gradeMap.put("3", "三年级");
        gradeMap.put("4", "四年级");
        gradeMap.put("5年级", "五年级");
        gradeMap.put("3年级", "三年级");
        gradeMap.put("4年级", "四年级");
        gradeMap.put("6年级", "六年级");
        gradeMap.put("2年级", "二年级");
        gradeMap.put("五", "五年级");
        gradeMap.put("四", "四年级");
        gradeMap.put("三", "三年级");
        gradeMap.put("小学二年级及以上", "二年级");
        gradeMap.put("1年级", "一年级");
        gradeMap.put("一", "一年级");
        gradeMap.put("二", "二年级");
    }

    public boolean process(EventData eventData) {
        //是否是白名单例子
        boolean inWhiteList = false;
        //是否是死海例子
        boolean inDeadSeaList = false;
        //是否是公海例子
        boolean inPublicSea = false;
        String channelLevelId = null;
        int maxChannelIndex = 0;
        String demoCourseStatus = "0";
        String channel = null;
        String createTime = null;
        String signStatus = "0";
        for (EventColumn column : eventData.getColumns()) {
            if ("whiteListFlag".equals(column.getColumnName())) {
                if (!column.isNull() && "1".equals(column.getColumnValue())) {
                    inWhiteList = true;
                }
            } else if ("deadSeaFlag".equals(column.getColumnName())) {
                if (!column.isNull() && "1".equals(column.getColumnValue())) {
                    inDeadSeaList = true;
                }
            } else if ("publicSeaFlag".equals(column.getColumnName())) {
                if (!column.isNull() && "1".equals(column.getColumnValue())) {
                    inPublicSea = true;
                }
            } else if ("channel3".equals(column.getColumnName())) {
                if (channelIndex.get(column.getColumnName()) > maxChannelIndex) {
                    if (!column.isNull()) {
                        channelLevelId = column.getColumnValue();
                    }
                }
            } else if ("demoCourseEndTime".equals(column.getColumnName())) {
                if (!column.isNull()) {
                    if (!"2".equals(demoCourseStatus)) {
                        demoCourseStatus = "1";
                    }
                }
            } else if ("childSex".equals(column.getColumnName())) {
                String childSex = processChildSex(column);
                column.setNull(false);
                column.setColumnType(Types.TINYINT);
                column.setColumnValue(childSex);
            } else if ("grade".equals(column.getColumnName())) {
                if (!column.isNull()) {
                    String formatGrade = gradeMap.get(column.getColumnValue());
                    if (formatGrade != null) {
                        column.setNull(false);
                        column.setColumnValue(formatGrade);
                    } else {
                        column.setNull(true);
                    }
                } else {
                    column.setNull(true);
                }
            } else if ("intention".equals(column.getColumnName())) {
                String intention = processIntention(column);
                column.setNull(false);
                column.setColumnType(Types.TINYINT);
                column.setColumnValue(intention);
            } else if ("allocatedStatus".equals(column.getColumnName())) {
                String allocatedStatus = processAllocatedStatus(column);
                column.setNull(false);
                column.setColumnType(Types.TINYINT);
                column.setColumnValue(allocatedStatus);
            } else if ("dataStatus".equals(column.getColumnName())) {
                if (!column.isNull() && "CANCEL".equals(column.getColumnValue())) {
                    demoCourseStatus = "2";
                } else if (!column.isNull() && "SIGN".equals(column.getColumnValue())) {
                    signStatus = "1";
                }
            } else if ("channel".equals(column.getColumnName())) {
                if (!column.isNull()) {
                    channel = column.getColumnValue();
                }
            } else if ("createTime".equals(column.getColumnName())) {
                if (!column.isNull()) {
                    createTime = column.getColumnValue();
                }
            } else if ("childAge".equals(column.getColumnName())) {
                if (!column.isNull()) {
                    int childAge = Integer.parseInt(column.getColumnValue());
                    if (childAge < 0 || childAge > 99) {
                        column.setColumnValue("0");
                    }
                }
            }
        }
        String leadsType = "0";
        if (inWhiteList) {
            leadsType = "1";
        } else if (inDeadSeaList) {
            leadsType = "3";
        } else if (inPublicSea) {
            leadsType = "2";
        }
        //借用publicSeaFlag -> leads_type
        //借用channel3 -> channel_level_id
        //借用demoCourseEndTime -> demo_course_status
        //借用dataStatus -> sign_status
        for (EventColumn column : eventData.getColumns()) {
            if ("publicSeaFlag".equals(column.getColumnName())) {
                column.setColumnType(Types.INTEGER);
                column.setNull(false);
                column.setColumnValue(leadsType);
            } else if ("channel3".equals(column.getColumnName())) {
                column.setColumnType(Types.BIGINT);
                if (channelLevelId == null) {
                    column.setNull(true);
                } else {
                    column.setNull(false);
                    try {
                        Integer.parseInt(channelLevelId);
                        column.setColumnValue(channelLevelId);
                    } catch (Exception e) {
                        column.setColumnValue("0");
                    }
                }
            } else if ("demoCourseEndTime".equals(column.getColumnName())) {
                column.setColumnType(Types.INTEGER);
                column.setNull(false);
                column.setColumnValue(demoCourseStatus);
            } else if ("dataStatus".equals(column.getColumnName())) {
                column.setColumnType(Types.TINYINT);
                column.setNull(false);
                column.setColumnValue(signStatus);
            }
            if ("attend_status".equals(column.getColumnName())) {
                fillDefaultIfNull(column, "-1");
            } else if ("activeChannel".equals(column.getColumnName())) {
                fillDefaultIfNull(column, channel);
            } else if ("activeTime".equals(column.getColumnName())) {
                fillDefaultIfNull(column, createTime);
            } else {
                fillDefaultIfNull(column, null);
            }
        }
        return true;
    }

    private void fillDefaultIfNull(EventColumn column, String value) {
        if (column.isNull()) {
            column.setNull(false);
            if (value != null) {
                column.setColumnValue(value);
                return;
            }
            switch (column.getColumnType()) {
                case Types.INTEGER:
                    column.setColumnValue("0");
                    break;
                case Types.TINYINT:
                    column.setColumnValue("0");
                    break;
                case Types.BIGINT:
                    column.setColumnValue("0");
                    break;
                case Types.VARCHAR:
                    column.setColumnValue("");
                    break;
                case Types.DATE:
                    column.setColumnValue("2021-10-14 00:00:00");
                    break;
            }
        }
    }

    private String processAllocatedStatus(EventColumn column) {
        if (column.isNull()) {
            return "1";
        } else {
            if ("未分配".equals(column.getColumnValue())) {
                return "0";
            } else {
                return "1";
            }
        }
    }

    private String processIntention(EventColumn column) {
        if (column.isNull()) {
            return "-1";
        } else {
            if ("HP".equals(column.getColumnValue())) {
                return "2";
            } else if ("P".equals(column.getColumnValue())) {
                return "1";
            } else if ("LP".equals(column.getColumnValue())) {
                return "0";
            } else if ("OKN".equals(column.getColumnValue())) {
                return "0";
            } else {
                return "-1";
            }
        }
    }

    private String processChildSex(EventColumn column) {
        if (column.isNull()) {
            return "-1";
        } else {
            if ("M".equals(column.getColumnValue()) || "男".equals(column.getColumnValue()) || "1".equals(column.getColumnValue())) {
                return "1";
            } else if ("F".equals(column.getColumnValue()) || "女".equals(column.getColumnValue()) || "0".equals(column.getColumnValue())) {
                return "0";
            } else {
                return "-1";
            }
        }
    }
}

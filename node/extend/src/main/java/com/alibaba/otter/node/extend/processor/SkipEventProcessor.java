package com.alibaba.otter.node.extend.processor;

import com.alibaba.otter.shared.etl.model.EventData;

public class SkipEventProcessor extends AbstractEventProcessor {

    public boolean process(EventData eventData) {
        return false;
    }
}

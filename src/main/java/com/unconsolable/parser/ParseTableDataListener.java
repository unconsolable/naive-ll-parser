package com.unconsolable.parser;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.List;
import java.util.Map;

public class ParseTableDataListener extends AnalysisEventListener<Map<Integer, String>> {
    private final List<Map<Integer, String>> list;
    private final Map<Integer, String> header;

    public ParseTableDataListener(List<Map<Integer, String>> list, Map<Integer, String> header) {
        this.list = list;
        this.header = header;
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        header.clear();
        header.putAll(headMap);
    }
}

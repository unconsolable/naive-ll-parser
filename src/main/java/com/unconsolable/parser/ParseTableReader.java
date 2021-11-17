package com.unconsolable.parser;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseTableReader {
    // 解析表条目
    private final List<Map<Integer, String>> list;

    // 终结符到下标
    private final Map<String, Integer> term2idx;

    // 非终结符到下标
    private final Map<String, Integer> nonterm2idx;

    public Map<String, Integer> getTerm2idx() {
        return term2idx;
    }

    public Map<String, Integer> getNonterm2idx() {
        return nonterm2idx;
    }

    public ParseTableReader() {
        list = new ArrayList<>();
        term2idx = new HashMap<>();
        nonterm2idx = new HashMap<>();
    }

    public List<Map<Integer, String>> getList() {
        return list;
    }

    public void read(String path) {
        Map<Integer, String> header = new HashMap<>();
        EasyExcel.read(path, new ParseTableDataListener(list, header)).sheet().doRead();
        // 终结符到下标计算
        for (Integer i : header.keySet()) {
            String v = header.get(i);
            if (v != null) {
                term2idx.put(v, i);
            }
        }
        // 非终结符到下标计算
        for (int i = 0; i < list.size(); i++) {
            nonterm2idx.put(list.get(i).get(0), i);
        }
    }
}
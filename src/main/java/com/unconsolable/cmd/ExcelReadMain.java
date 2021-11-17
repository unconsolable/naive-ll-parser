package com.unconsolable.cmd;

import com.unconsolable.parser.ParseTableReader;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ExcelReadMain {
    public static void main(String[] args) {
        // 检验 parse table 读入
        ParseTableReader t1 = new ParseTableReader();
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine();
        t1.read(path);
        List<Map<Integer, String>> list = t1.getList();
        Map<String, Integer> hd = t1.getTerm2idx();
        for (String key : hd.keySet()) {
            System.out.print(key + " " + hd.get(key).toString() + " ");
        }
        System.out.print('\n');
        hd = t1.getNonterm2idx();
        for (String key : hd.keySet()) {
            System.out.print(key + " " + hd.get(key).toString() + " ");
        }
        System.out.print('\n');
        for (Map<Integer, String> map : list) {
            for (Integer key : map.keySet()) {
                String s = map.get(key);
                System.out.print(key.toString() + " " + s + " ");
            }
            System.out.print('\n');
        }
    }
}

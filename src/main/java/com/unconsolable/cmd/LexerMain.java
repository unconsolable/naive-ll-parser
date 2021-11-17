package com.unconsolable.cmd;

import com.unconsolable.lexer.Lexer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class LexerMain {
    public static void main(String[] args) {
        String prog;
        Scanner sc = new Scanner(System.in);
        try {
            // 读入源程序路径
            String path;
            path = sc.nextLine();
            prog = Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Path error");
            return;
        }
        // 初始化 lexer, 扫描 token
        Lexer l = new Lexer(prog);
        while (l.scan()) ;
        // 报错或结果输出
        if (l.err != null) {
            System.out.println("Lexer error");
            System.out.println(l.err);
            return;
        }
        System.out.println(l.lexemes);
        System.out.println(l.getLine());
    }
}

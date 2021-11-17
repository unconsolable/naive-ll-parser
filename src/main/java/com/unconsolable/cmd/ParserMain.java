package com.unconsolable.cmd;

import com.unconsolable.lexer.Lexer;
import com.unconsolable.parser.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class ParserMain {
    public static void main(String[] args) {
        String prog, parseTablePath;
        Scanner sc = new Scanner(System.in);
        try {
            String path;
            path = sc.nextLine();
            parseTablePath = sc.nextLine();
            prog = Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Path error");
            return;
        }
        Lexer l = new Lexer(prog);
        while (l.scan()) ;
        if (l.err != null) {
            System.out.println("Lexer error");
            System.out.println(l.err);
            return;
        }
        // 初始化 ll parser 并解析
        Parser p = new Parser(parseTablePath);
        p.Parse(l.lexemes);
        if (p.err != null) {
            System.out.println("Parser error");
            System.out.println(p.err);
        }
    }
}

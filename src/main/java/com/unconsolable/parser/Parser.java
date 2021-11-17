package com.unconsolable.parser;

import com.unconsolable.lexer.*;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Parser {
    private final String parseTablePath;
    public String err = null;

    public Parser(String parseTablePath) {
        this.parseTablePath = parseTablePath;
    }

    public void Parse(List<Token> lexemes) {
        // 读入解析表
        ParseTableReader t1 = new ParseTableReader();
        t1.read(parseTablePath);
        Map<String, Integer> nonTerm2Idx = t1.getNonterm2idx();
        Map<String, Integer> term2Idx = t1.getTerm2idx();
        List<Map<Integer, String>> parseTable = t1.getList();
        // 状态栈存储
        Stack<String> stk = new Stack<>();
        // token 中添加结束符
        lexemes.add(new Token(Tag.FINAL));
        // 文法开始符号进栈
        stk.push("P");
        int idxLexemes = 0;
        while (!stk.empty()) {
            if (idxLexemes >= lexemes.size()) {
                err = "Lexemes too short";
                return;
            }
            String top = stk.peek();
            if (top.equals("empty")) {
                // 匹配空串
                stk.pop();
            } else if (term2Idx.containsKey(top)) {
                // 栈顶为终结符
                if (top.equals("ID")) {
                    Identifier id;
                    try {
                        id = (Identifier) lexemes.get(idxLexemes);
                    } catch (ClassCastException e) {
                        err = "Lexemes type mismatch.";
                        return;
                    }
                    if (id.tag != Tag.ID) {
                        err = "ID content mismatch";
                        return;
                    }
                    System.out.print("匹配终结符 ");
                    System.out.println(id);
                    ++idxLexemes;
                    stk.pop();
                } else if (top.equals("NUM")) {
                    Num n;
                    try {
                        n = (Num) lexemes.get(idxLexemes);
                    } catch (ClassCastException e) {
                        err = "Lexemes type mismatch.";
                        return;
                    }
                    System.out.print("匹配终结符 ");
                    System.out.println(n);
                    ++idxLexemes;
                    stk.pop();
                } else if (Keywords.keywords.contains(top)) {
                    Identifier id;
                    try {
                        id = (Identifier) lexemes.get(idxLexemes);
                    } catch (ClassCastException e) {
                        err = "Lexemes type mismatch.";
                        return;
                    }
                    if (id.tag != Tag.KEYWORD || !id.lexeme.equals(top)) {
                        err = "Keyword content mismatch";
                        return;
                    }
                    System.out.print("匹配终结符 ");
                    System.out.println(id);
                    ++idxLexemes;
                    stk.pop();
                } else if (Operators.operators.contains(top)) {
                    Operator op;
                    try {
                        op = (Operator) lexemes.get(idxLexemes);
                    } catch (ClassCastException e) {
                        err = "Lexemes type mismatch.";
                        return;
                    }
                    if (!op.oper.equals(top)) {
                        err = "Operator content mismatch";
                        return;
                    }
                    System.out.print("匹配终结符 ");
                    System.out.println(op);
                    ++idxLexemes;
                    stk.pop();
                } else {
                    err = "Invalid terminator";
                    return;
                }
            } else if (nonTerm2Idx.containsKey(top)) {
                // 栈顶为非终结符
                int nonTermIdx = nonTerm2Idx.get(top);
                int termIdx = 0;
                if (lexemes.get(idxLexemes).tag == Tag.ID) {
                    termIdx = term2Idx.get("ID");
                } else if (lexemes.get(idxLexemes).tag == Tag.NUM) {
                    termIdx = term2Idx.get("NUM");
                } else if (lexemes.get(idxLexemes).tag == Tag.KEYWORD) {
                    Identifier id = (Identifier) lexemes.get(idxLexemes);
                    if (!term2Idx.containsKey(id.lexeme)) {
                        err = "Invalid keyword";
                        return;
                    }
                    termIdx = term2Idx.get(id.lexeme);
                } else if (lexemes.get(idxLexemes).tag == Tag.OPERATOR) {
                    Operator op = (Operator) lexemes.get(idxLexemes);
                    if (!term2Idx.containsKey(op.oper)) {
                        err = "Invalid operator";
                        return;
                    }
                    termIdx = term2Idx.get(op.oper);
                } else if (lexemes.get(idxLexemes).tag == Tag.FINAL) {
                    termIdx = term2Idx.get("$");
                }
                // 根据下标确定产生式，反向压栈
                String production = parseTable.get(nonTermIdx).get(termIdx);
                if (production == null) {
                    err = "Invalid production";
                    return;
                }
                System.out.print("使用产生式 ");
                System.out.println(production);
                String[] terms = production.split(" ");
                stk.pop();
                // 0: LHS, 1: ->, start from 2
                for (int i = terms.length - 1; i >= 2; --i) {
                    stk.push(terms[i]);
                }
            } else {
                err = "Stack top invalid";
                return;
            }
        }
        if (idxLexemes < lexemes.size() && lexemes.get(idxLexemes).tag == Tag.FINAL) {
            return;
        }
        err = "Lexemes too long";
    }
}

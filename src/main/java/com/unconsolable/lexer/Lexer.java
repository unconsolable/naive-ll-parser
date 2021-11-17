package com.unconsolable.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Lexer {
    // 程序包含行数
    private int line = 1;
    // 等待读取的字符串下标, 预读功能通过下标移动实现
    private int idx = 0;
    // 待处理字符串
    private final String s;
    // 关键字集合
    private static final Set<String> keywords;
    // 部分运算符集合
    private static final Set<String> operators;
    // 所有运算符首字母
    private static final String operatorFirstCh;
    // 解析词素列表
    public List<Token> lexemes;
    // 存放错误信息
    public String err;

    static {
        keywords = Keywords.keywords;
        operators = Operators.operators;
        operatorFirstCh = operators.stream().map(s -> s.substring(0, 1)).collect(Collectors.joining());
    }

    public Lexer(String dest) {
        s = dest;
        lexemes = new ArrayList<>();
    }

    // If finished, return false; else return true;
    public boolean scan() {
        // Escape ' ', '\t', '\n'
        for (; idx < s.length(); idx++) {
            char ch = s.charAt(idx);
            if (ch != ' ' && ch != '\t') {
                if (ch == '\n') {
                    ++line;
                } else {
                    break;
                }
            }
        }

        if (idx >= s.length()) {
            return false;
        }

        // Read number
        if (Character.isDigit(s.charAt(idx))) {
            // 1 is int state, 2 is double state
            int state = 1, end = idx;
            for (; end < s.length(); ++end) {
                if (!('0' <= s.charAt(end) && s.charAt(end) <= '9')) {
                    if (s.charAt(end) == '.') {
                        state = 2;
                    } else {
                        break;
                    }
                }
            }

            try {
                switch (state) {
                    case 1:
                        lexemes.add(new Num(Integer.parseInt(s.substring(idx, end))));
                        break;
                    case 2:
                        lexemes.add(new Num(Double.parseDouble(s.substring(idx, end))));
                        break;
                }
            } catch (NumberFormatException e) {
                err = e.getMessage();
                return false;
            }

            idx = end;
            return true;
        }

        // Read keyword / identifier
        if (Character.isLetter(s.charAt(idx))) {
            int end = idx;
            for (; end < s.length(); ++end) {
                if (!Character.isLetterOrDigit(s.charAt(end))) {
                    break;
                }
            }

            String cur = s.substring(idx, end);
            if (keywords.contains(cur)) {
                lexemes.add(new Identifier(Tag.KEYWORD, cur));
            } else {
                lexemes.add(new Identifier(Tag.ID, cur));
            }

            idx = end;
            return true;
        }

        // Read char
        if (s.charAt(idx) == '\'') {
            if (idx == s.length() - 1 || s.charAt(idx + 1) == '\'') {
                err = "Empty char literal";
                return false;
            }
            if (idx == s.length() - 2 || s.charAt(idx + 2) != '\'') {
                err = "Invalid char";
                return false;
            }
            lexemes.add(new Char(s.charAt(idx + 1)));
            idx += 3;
            return true;
        }

        // Read string
        if (s.charAt(idx) == '\"') {
            int end = idx + 1;
            for (; end < s.length(); end++) {
                if (s.charAt(end) == '\"') {
                    end++;
                    break;
                }
            }

            if (end - idx < 2 || s.charAt(end - 1) != '\"') {
                err = "Invalid string";
                return false;
            }
            lexemes.add(new Str(s.substring(idx, end)));
            idx = end;
            return true;
        }

        // Read operator
        if (isOperatorFirstCh(s.substring(idx, idx + 1))) {
            if (idx + 2 <= s.length()) {
                String len2Op = s.substring(idx, idx + 2);
                if (isOperator(len2Op)) {
                    lexemes.add(new Operator(len2Op));
                    idx += 2;
                    return true;
                }
            }
            String len1Op = s.substring(idx, idx + 1);
            if (isOperator(len1Op)) {
                lexemes.add(new Operator(len1Op));
                idx++;
                return true;
            }
        }
        // Unrecognized
        err = "Unrecognized string";
        return false;
    }

    public int getLine() {
        return line;
    }

    private boolean isOperatorFirstCh(String op) {
        if (op.length() == 1) {
            return operatorFirstCh.contains(op);
        }
        return false;
    }

    private boolean isOperator(String op) {
        return operators.contains(op);
    }
}

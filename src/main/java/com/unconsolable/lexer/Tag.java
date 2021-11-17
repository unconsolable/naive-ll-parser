package com.unconsolable.lexer;

public class Tag {
    // number, identifier, keyword, char, string
    public final static int NUM;
    public final static int ID;
    public final static int KEYWORD;
    public final static int CHAR;
    public final static int STRING;
    public final static int OPERATOR;
    public final static int FINAL;

    static {
        NUM = 257;
        ID = 258;
        KEYWORD = 259;
        CHAR = 260;
        STRING = 261;
        OPERATOR = 262;
        FINAL = 263;
    }
}

package com.unconsolable.lexer;

public class Identifier extends Token {
    public final String lexeme;

    public Identifier(int t, String s) {
        super(t);
        lexeme = s;
    }

    @Override
    public String toString() {
        if (super.tag == Tag.ID) {
            return "Identifier{" + lexeme + '}';
        }
        if (super.tag == Tag.KEYWORD) {
            return "Keyword{" + lexeme + '}';
        }
        return "";
    }
}

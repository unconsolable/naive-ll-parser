package com.unconsolable.lexer;

public class Char extends Token {
    public final char c;

    public Char(char c) {
        super(Tag.CHAR);
        this.c = c;
    }

    @Override
    public String toString() {
        return "Char{" + c + '}';
    }
}

package com.unconsolable.lexer;

public class Str extends Token {
    public final String str;

    public Str(String s) {
        super(Tag.STRING);
        str = s;
    }

    @Override
    public String toString() {
        return "Str{" + str + '}';
    }
}

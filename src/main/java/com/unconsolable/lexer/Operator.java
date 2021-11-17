package com.unconsolable.lexer;

public class Operator extends Token {
    public final String oper;

    Operator(String oper) {
        super(Tag.OPERATOR);
        this.oper = oper;
    }

    @Override
    public String toString() {
        return "Operator{" + oper + '}';
    }
}

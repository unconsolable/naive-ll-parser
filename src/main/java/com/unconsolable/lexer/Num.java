package com.unconsolable.lexer;

public class Num extends Token {
    public final boolean isDouble;
    public final int value;
    public final double value1;

    public Num(int v) {
        super(Tag.NUM);
        isDouble = false;
        value = v;
        value1 = v;
    }

    public Num(double v) {
        super(Tag.NUM);
        isDouble = true;
        value = (int) v;
        value1 = v;
    }

    @Override
    public String toString() {
        if (isDouble) {
            return "Num{double, " + value1 + '}';
        }
        return "Num{int, " + value + '}';
    }
}

package com.unconsolable.lexer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Operators {
    public static final Set<String> operators;

    static {
        operators = new HashSet<>(Arrays.asList(
                "+", "-", "*", "/", "%", "++", "--",
                "&&", "||", "!",
                "<=", ">=", "<", ">", "==", "!=", "=",
                ".", ",", ";",
                "(", ")", "[", "]", "{", "}"
        ));
    }
}

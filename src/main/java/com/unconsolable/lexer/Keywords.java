package com.unconsolable.lexer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Keywords {
    public static final Set<String> keywords;

    static {
        keywords = new HashSet<>(Arrays.asList(
                "auto", "break", "case", "char",
                "const", "continue", "default", "do",
                "double", "else", "enum", "extern",
                "float", "for", "goto", "if",
                "int", "long", "register", "return",
                "short", "signed", "sizeof", "static",
                "struct", "switch", "typedef", "union",
                "unsigned", "void", "volatile", "while"
        ));
    }
}

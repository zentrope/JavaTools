package com.zentrope.tools.json.reader;

import java.util.ArrayList;
import java.util.List;

class Tokenizer {

    private final char[] buffer;
    private Integer ptr = 0;

    public Tokenizer(final String text) {
        this.buffer = text.toCharArray();
    }

    private Character next() {
        if ((ptr + 1) > buffer.length) {
            return null;
        }
        return buffer[ptr++];
    }

    private Boolean isWordEnder(final Character c) {
        return c == '[' || c == ']' || c == '{' || c == '}' || c == ',' || c == ':' || Character.isWhitespace(c);
    }

    private String readString() {
        var accum = "";
        var escaped = false;
        Character c;

        while ((c = next()) != null) {
            if (c == '\\') {
                escaped = true;
                continue;
            }

            if (escaped) {
                escaped = false;
                accum = accum + c.toString();
                continue;
            }

            if (c == '"') {
                break;
            }

            accum = accum + c.toString();
        }

        return accum;
    }

    public List<Token> tokenize() {
        final var tokens = new ArrayList<Token>();

        Character c;
        String numAccum = "";

        while ((c = next()) != null) {

            if (Character.isWhitespace(c)) {
                continue;
            }

            if (c == '"') {
                final var string = readString();
                final var token = new Token(string, TokenType.String);
                tokens.add(token);
                continue;
            }

            if (isWordEnder(c) && !numAccum.isEmpty()) {
                final var test = numAccum.toLowerCase();
                if (test.equals("true") || test.equals("false")) {
                    tokens.add(new Token(test, TokenType.Boolean));
                } else if (test.equals("null")) {
                    tokens.add(new Token(TokenType.Null));
                } else {
                    tokens.add(new Token(numAccum, TokenType.Number));
                }
                numAccum = "";
            }

            switch (c) {
            case ':':
                tokens.add(new Token(":", TokenType.Colon));
                break;
            case ',':
                tokens.add(new Token(",", TokenType.Comma));
                break;
            case '[':
                tokens.add(new Token("[", TokenType.OpenArray));
                break;
            case ']':
                tokens.add(new Token("]", TokenType.CloseArray));
                break;
            case '{':
                tokens.add(new Token("{", TokenType.OpenObject));
                break;
            case '}':
                tokens.add(new Token("}", TokenType.CloseObject));
                break;
            default:
                numAccum = numAccum + c.toString();
            }
        }
        return tokens;
    }

}

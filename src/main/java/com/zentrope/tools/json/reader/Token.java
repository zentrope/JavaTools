package com.zentrope.tools.json.reader;

/**
 * Represents a JSON token type and its value. This is intended to be
 * consumed by the parser.
 */

final class Token {

    final String value;
    final TokenType type;

    public Token(final String value, final TokenType type) {
        this.value = value;
        this.type = type;
    }

    public Token(final TokenType type) {
        this.type = type;
        this.value = type.value();
    }

    @Override
    public String toString() {
        return String.format("<token:%s=«%s»>", type, value);
    }

    @Override
    public boolean equals(final Object o) {
        final var a = this;
        final var b = (Token) o;
        return a.type == b.type && a.value.equals(b.value);
    }
}

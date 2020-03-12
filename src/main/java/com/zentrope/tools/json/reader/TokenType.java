package com.zentrope.tools.json.reader;

/**
 * Represents the type a JSON token can have.
 */

public enum TokenType {

    String(""),
    Number(""),
    Boolean(""),
    OpenObject("{"),
    CloseObject("}"),
    Colon(":"),
    Comma(","),
    OpenArray("["),
    CloseArray("]"),
    Null("null");

    final private String value;

    private TokenType(final String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}

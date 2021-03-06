package com.zentrope.tools.json.reader;

import java.util.List;
import java.util.Objects;

public final class Parser {

    final private List<Token> tokens;
    private Integer ptr = 0;

    public Parser(final String json) {
        this(new Tokenizer(json).tokenize());
    }

    public Parser(final List<Token> tokens) {
        this.tokens = tokens;
    }

    public JsonArray parseJsonArray() throws ParseException {

        var array = new JsonArray();
        var token = current();

        if (Objects.isNull(token)) {
            throw new ParseException("Expected '[' but got null.");
        }

        if (token.type != TokenType.OpenArray) {
            var msg = String.format("Expected '%s' but got '%s'.", "[", token);
            throw new ParseException(msg);
        }

        while (true) {
            token = next();

            if (Objects.isNull(token)) {
                var msg = String.format("Expected value or ']' but got '%s'.", token);
                throw new ParseException(msg);
            }

            if (token.type == TokenType.CloseArray) {
                break;
            }

            if (token.type == TokenType.Comma) {
                continue;
            }

            var element = parseValue();
            array.append(element);
        }
        return array;
    }

    public JsonObject parseJsonObject() throws ParseException {

        var object = new JsonObject();
        var token = current();

        if (token == null) {
            throw new ParseException("Expected '{', got null.");
        }

        if (token.type != TokenType.OpenObject) {
            throw new ParseException("Expected '{', but got '" + token.value + "'.");
        }

        while (true) {

            token = next();

            if (Objects.isNull(token)) {
                throw new ParseException("Expected object property or '}', got null.");
            }

            if (token.type == TokenType.CloseObject) {
                break;
            }

            if (token.type == TokenType.Comma) {
                continue;
            }

            if (token.type == TokenType.String) {
                var name = token.value;
                skipToken(TokenType.Colon);
                var value = parseValue();
                object.setValue(name, value);
            }
        }

        return object;
    }

    private Object parseValue() throws ParseException {
        var curr = current();
        if (curr == null) {
            var msg = String.format("Expected a value but got a null.");
            throw new ParseException(msg);
        }

        switch (curr.type) {

        case Boolean:
            return Boolean.valueOf(curr.value);

        case String:
            return curr.value;

        case Number:
            try {
                return Integer.valueOf(curr.value);
            }
            catch (NumberFormatException e) {
                var msg = String.format("Unable to parse '%s' as an integer.", curr.value);
                throw new ParseException(msg);
            }

        case Null:
            return null;

        case OpenObject:
            return parseJsonObject();

        case OpenArray:
            return parseJsonArray();

        default:
            var msg = String.format("Unexpected value type: '%s'.", curr);
            throw new ParseException(msg);
        }
    }

    private Token current() throws ParseException {
        var curr = this.tokens.get(this.ptr);
        if (Objects.isNull(curr)) {
            var msg = "Expected a token but got a null.";
            throw new ParseException(msg);
        }
        return curr;
    }

    private Token next() {
        if ((ptr + 1) > tokens.size()) {
            return null;
        }
        ptr += 1;
        return tokens.get(ptr);
    }

    private void skipToken(TokenType type) throws ParseException {
        var curr = next();
        if (Objects.isNull(curr)) {
            var msg = String.format("Expected a '%s' but got a null.", type);
            throw new ParseException(msg);
        }

        if (curr.type != type) {
            var msg = String.format("Expected a '%s', but got a '%s'.", type, curr);
            throw new ParseException(msg);
        }
        next();
    }
}

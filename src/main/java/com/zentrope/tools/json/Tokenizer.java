//
// Copyright (c) 2020-present Keith Irwin
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published
// by the Free Software Foundation, either version 3 of the License,
// or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see
// <http://www.gnu.org/licenses/>.
//

package com.zentrope.tools.json;

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

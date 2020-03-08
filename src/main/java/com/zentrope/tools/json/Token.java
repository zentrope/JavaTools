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

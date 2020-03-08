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
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see
// <http://www.gnu.org/licenses/>.
//

package com.zentrope.tools.json;

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

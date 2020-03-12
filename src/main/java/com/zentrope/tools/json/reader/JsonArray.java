package com.zentrope.tools.json.reader;

import java.util.ArrayList;
import java.util.List;

final class JsonArray {

    private final List<Object> elements;

    public JsonArray() {
        this.elements = new ArrayList<>();
    }

    void append(final Object value) {
        elements.add(value);
    }

    public JsonObject getJsonObject(final Integer i) {
        final var result = this.get(i);
        if (result instanceof JsonObject) {
            return (JsonObject) result;
        }
        return null;
    }

    public JsonArray getJsonArray(final Integer i) {
        final var result = this.get(i);
        if (result instanceof JsonArray) {
            return (JsonArray) result;
        }
        return null;
    }

    public Integer getInteger(final Integer i) {
        final var result = this.get(i);
        if (result instanceof Integer) {
            return (Integer) result;
        }
        return null;
    }

    public String getString(final Integer i) {
        final var result = this.get(i);
        if (result instanceof String) {
            return (String) result;
        }
        return null;
    }

    public Boolean getBoolean(final Integer i) {
        final var result = this.get(i);
        if (result instanceof Boolean) {
            return (Boolean) result;
        }
        return null;
    }

    public Integer size() {
        return this.elements.size();
    }

    private Object get(final Integer i) {
        try {
            return this.elements.get(i);
        }

        catch (final IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public int hashCode() {
        return elements.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        return hashCode() == o.hashCode();
    }

    @Override
    public String toString() {
        return String.format("<JsonArray:%s>", elements);
    }
}

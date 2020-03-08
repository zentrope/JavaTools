package com.zentrope.tools.json;

import java.util.ArrayList;
import java.util.List;

final class JsonArray {

    private List<Object> elements;

    public JsonArray() {
        this.elements = new ArrayList<>();
    }

    void append(Object value) {
        elements.add(value);
    }

    public JsonObject getJsonObject(Integer i) {
        var result = this.get(i);
        if (result instanceof JsonObject) {
            return (JsonObject) result;
        }
        return null;
    }

    public JsonArray getJsonArray(Integer i) {
        var result = this.get(i);
        if (result instanceof JsonArray) {
            return (JsonArray) result;
        }
        return null;
    }

    public Integer getInteger(Integer i) {
        var result = this.get(i);
        if (result instanceof Integer) {
            return (Integer) result;
        }
        return null;
    }

    public String getString(Integer i) {
        var result = this.get(i);
        if (result instanceof String) {
            return (String) result;
        }
        return null;
    }

    public Boolean getBoolean(Integer i) {
        final var result = this.get(i);
        if (result instanceof Boolean) {
            return (Boolean) result;
        }
        return null;
    }

    public Integer size() {
        return this.elements.size();
    }

    private Object get(Integer i) {
        try {
            return this.elements.get(i);
        }

        catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public int hashCode() {
        return elements.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return hashCode() == o.hashCode();
    }

    @Override
    public String toString() {
        return String.format("<JsonArray:%s>", elements);
    }
}

package com.zentrope.tools.json;

import java.util.HashMap;
import java.util.Map;

final class JsonObject {

    private final Map<String, Object> properties;

    public JsonObject() {
        this.properties = new HashMap<String, Object>();
    }

    void setValue(final String name, final Object value) {
        this.properties.put(name, value);
    }

    public JsonObject getJsonObject(final String name) {
        if (properties.containsKey(name)) {
            final var object = properties.get(name);
            if (object instanceof JsonObject) {
                return (JsonObject) object;
            }
        }
        return null;
    }

    public String getString(final String name) {
        if (!properties.containsKey(name)) {
            return null;
        }
        final var object = properties.get(name);
        if (object instanceof String) {
            return (String) object;
        }
        return null;
    }

    public Integer getInteger(final String name) {
        if (!properties.containsKey(name)) {
            return null;
        }
        final var object = properties.get(name);
        if (object instanceof Integer) {
            return (Integer) object;
        }
        return null;
    }

    public Boolean getBoolean(final String name) {
        if (!properties.containsKey(name)) {
            return null;
        }
        final var object = properties.get(name);
        if (object instanceof Boolean) {
            return (Boolean) object;
        }
        return null;
    }

    @Override
    public int hashCode() {
        return properties.hashCode();
    }

    @Override
    public String toString() {
        return properties.toString();
    }

    @Override
    public boolean equals(final Object o) {
        return o.hashCode() == this.hashCode();
    }
}

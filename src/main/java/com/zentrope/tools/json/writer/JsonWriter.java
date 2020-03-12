package com.zentrope.tools.json.writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final public class JsonWriter {

    private JsonWriter() {
    }

    public static JsonObject object() {
        return new JsonObject();
    }

    private static String stringify(Boolean value) {
        return value ? "true" : "false";
    }

    private static String stringify(String value) {
        return String.format("\"%s\"", value);
    }

    private static String stringify(Integer value) {
        return value.toString();
    }

    private static String stringify(Object value) {
        if (value == null) {
            return "null";
        }

        if (value instanceof String) {
            return stringify((String)value);
        }

        if (value instanceof Integer) {
            return stringify((Integer)value);
        }

        if (value instanceof Boolean) {
            return stringify((Boolean)value);
        }

        return value.toString();
    }

    public static class JsonArray {

        private List<Object> elements = new ArrayList<>();

        public JsonArray() {
        }

        public JsonArray append(Object value) {
            elements.add(value);
            return this;
        }

        @Override
        public String toString() {
            return "not-implemented";
        }
    }

    public static class JsonObject {

        private Map<String, Object> root = new HashMap<>();

        public JsonObject() {
        }

        public JsonObject property(String name, boolean value) {
            return property(name, Boolean.valueOf(value));
        }

        public JsonObject property(String name, int value) {
            return property(name, Integer.valueOf(value));
        }

        public JsonObject property(String name, Object value) {
            root.put(name, value);
            return this;
        }

        @Override
        public String toString() {
            var entries = new ArrayList<String>();
            for (String key: root.keySet()) {
                var jsonKey = stringify(key);
                var jsonVal = stringify(root.get(key));
                entries.add(jsonKey + " : " + jsonVal);
            }
            var entryString = String.join(", ", entries);
            var builder = new StringBuilder();
            builder.append("{ ");
            builder.append(entryString);
            builder.append(" }");
            return builder.toString();
        }
    }
}

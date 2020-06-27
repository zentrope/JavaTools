package com.zentrope.tools.json.writer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final public class JsonWriter {

    private JsonWriter() {
    }

    public static JsonObject object() {
        return new JsonObject();
    }

    public static JsonArray array() {
        return new JsonArray();
    }

    private static String stringify(Object value) {
        if (value == null)
            return "null";

        if (value instanceof String)
            return String.format("\"%s\"", (String) value);

        return value.toString();
    }

    public static class JsonArray {

        private List<Object> elements = new ArrayList<>();

        public JsonArray() {
        }

        public JsonArray add(boolean value) {
            return add(Boolean.valueOf(value));
        }

        public JsonArray add(int value) {
            return add(Integer.valueOf(value));
        }

        public JsonArray add(Object value) {
            elements.add(value);
            return this;
        }

        public JsonArray add(Object... values) {
            elements.addAll(Arrays.asList(values));
            return this;
        }

        @Override
        public String toString() {
            var entries = new ArrayList<String>();
            for (Object element: elements)
                entries.add(stringify(element));
            return "[ " + String.join(", ", entries) + " ]";
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
            for (String key: root.keySet())
                entries.add(stringify(key) + " : " + stringify(root.get(key)));
            return "{ " + String.join(", ", entries) + " }";
        }
    }
}

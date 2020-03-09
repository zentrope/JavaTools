package com.zentrope.tools.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;


public class ParserTest {

    // This set of tests isn't comprehensive. I use it to help develop
    // the library, and to fix bugs as they occur. It's entirely
    // possible the parser fails with valid JSON if I never encounter
    // that JSON in the wild.

    @Test
    public void simpleArray() {

        try {
            var doc = Helpers.jsonFrom("simple-array");
            var expected = new JsonArray();
            expected.append("string");
            expected.append(Integer.valueOf(42));
            expected.append(Boolean.TRUE);
            expected.append("other-string");

            var computed = new Parser(doc).parseJsonArray();
            assertEquals("string", computed.getString(0));
            assertEquals(Integer.valueOf(42), computed.getInteger(1));
            assertEquals(Boolean.TRUE, computed.getBoolean(2));
            assertEquals("other-string", computed.getString(3));
            assertEquals(null, computed.getString(4));
            assertEquals(null, computed.getBoolean(4));
            assertEquals(null, computed.getInteger(4));
            assertEquals(null, computed.getJsonObject(4));

            assertEquals(expected, computed);
        }

        catch (Exception e) {
            System.out.println("ERROR: " + e);
            fail("ERROR: " + e);
        }
    }

    @Test
    public void arrayOfObjects() {

        var doc = Helpers.jsonFrom("array-of-objects");
        try {
            var computed = new Parser(doc).parseJsonArray();

            var uc1 = computed.getJsonObject(0);
            var uc2 = computed.getJsonObject(1);

            assertEquals("anna", uc1.getString("name"));
            assertEquals("maya", uc2.getString("name"));
            assertEquals("director", uc1.getString("title"));
            assertEquals("manager", uc2.getString("title"));
        }

        catch (ParseException e) {
            System.out.println("ERROR: " + e);
            fail("ERROR: " + e);
        }
    }

    @Test
    public void arrayOfArrays() {
        var doc = Helpers.jsonFrom("array-with-array");
        var embedded = new JsonArray();
        embedded.append("c");
        embedded.append("d");

        var expected = new JsonArray();
        expected.append("a");
        expected.append(Integer.valueOf(42));
        expected.append(embedded);
        expected.append(Boolean.TRUE);

        try {
            var computed = new Parser(doc).parseJsonArray();
            assertEquals(expected, computed);

            assertEquals("a", computed.getString(0));
            assertEquals(Integer.valueOf(42), computed.getInteger(1));

            var sub = computed.getJsonArray(2);
            assertEquals(embedded, sub);
            assertEquals("c", sub.getString(0));
            assertEquals("d", sub.getString(1));

            assertEquals(Boolean.TRUE, computed.getBoolean(3));

        }

        catch (ParseException e) {
            System.out.println("ERROR: " + e);
            fail("ERROR: " + e);
        }
    }

    @Test
    public void simpleObject() {
        var doc = Helpers.jsonFrom("simple-object");
        var expected = new JsonObject();
        expected.setValue("foo", "bar");
        expected.setValue("isAnswer", Boolean.TRUE);
        expected.setValue("size", Integer.valueOf(42));

        try {
            var computed = new Parser(doc).parseJsonObject();
            assertEquals(computed, expected);
            assertEquals(computed.getBoolean("isAnswer"), true);
            assertEquals(computed.getString("foo"), "bar");
            assertEquals(computed.getInteger("size"), Integer.valueOf(42));
            assertEquals(computed.getString("absent"), null);
        }

        catch (ParseException e) {
            System.out.println("ERROR: " + e);
            fail("ERROR: " + e);
        }
    }

    @Test
    public void simpleObjectWithExplicitNull() {
        var doc = Helpers.jsonFrom("object-with-null");
        try {
            var expected = new JsonObject();
            expected.setValue("foo", "bar");
            expected.setValue("optional", null);

            var computed = new Parser(doc).parseJsonObject();
            assertEquals("bar", computed.getString("foo"));
            assertEquals(null, computed.getString("optional"));

            assertEquals(expected, computed);
        }

        catch (ParseException e) {
            System.out.println("ERROR: " + e);
            fail("ERROR: " + e);
        }
    }

    @Test
    public void objectWithObjectProperty() {
        var doc = Helpers.jsonFrom("object-with-object");
        try {
            var user = new JsonObject();
            user.setValue("name", "anna");
            user.setValue("title", "director");

            var expected = new JsonObject();
            expected.setValue("foo", "bar");
            expected.setValue("user", user);

            var computed = new Parser(doc).parseJsonObject();
            var computedUser = computed.getJsonObject("user");

            assertEquals(user, computedUser);
            assertEquals("bar", computed.getString("foo"));
            assertEquals("anna", computedUser.getString("name"));
            assertEquals("director", computedUser.getString("title"));

            assertEquals(expected, computed);
        }

        catch (ParseException e) {
            System.out.println("ERROR: " + e);
            fail("ERROR: " + e);
        }
    }
}

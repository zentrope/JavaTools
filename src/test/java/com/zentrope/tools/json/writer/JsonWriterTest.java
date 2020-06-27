package com.zentrope.tools.json.writer;

import static com.zentrope.tools.json.Helpers.jsonFrom;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JsonWriterTest {

    @Test
    public void SimpleObject() {
        var expected = jsonFrom("writer-simple-object");
        var computed = JsonWriter
            .object()
            .property("foo", "bar")
            .property("answer", 42)
            .property("isAnswer", true)
            .property("optional", null)
            .toString();

        assertEquals(expected.length(), computed.length());
        assertEquals(expected, computed);
    }
}

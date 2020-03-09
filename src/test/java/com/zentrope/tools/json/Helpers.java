package com.zentrope.tools.json;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

final class Helpers {

    static String jsonFrom(final String jsonFileName) {
        try {
            final var path = Paths.get("src/test/resources/" + jsonFileName + ".json");
            final var bytes = Files.readAllBytes(path);
            return new String(bytes);
        }

        catch (IOException e) {
            System.out.println("ERROR: " + e);
            fail(String.format("Error loading '%s.json'.", jsonFileName));
            return "";
        }
    }

}

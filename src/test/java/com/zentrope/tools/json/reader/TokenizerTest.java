package com.zentrope.tools.json.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TokenizerTest {

    // The Tokenizer returns tokens which may or may not represent good
    // JSON. No checking is done, in other words. What we want to verify
    // is that if we provide good JSON, we'll get good tokens back.

    @Test
    public void testComplicatedJSON() {
        var testJson = "{ \"pro\\\"perty\" : \"name\", \"foo\": \"bar\", \"secret\":42, \"junk\" : [\"gargle\", 33] }";

        var expectedTokens = makeDoc(new Token[] {
                new Token(TokenType.OpenObject),
                new Token("pro\"perty", TokenType.String),
                new Token(TokenType.Colon),
                new Token("name", TokenType.String),
                new Token(TokenType.Comma),
                new Token("foo", TokenType.String),
                new Token(TokenType.Colon),
                new Token("bar", TokenType.String),
                new Token(TokenType.Comma),
                new Token("secret", TokenType.String),
                new Token(TokenType.Colon),
                new Token("42", TokenType.Number),
                new Token(TokenType.Comma),
                new Token("junk", TokenType.String),
                new Token(TokenType.Colon),
                new Token(TokenType.OpenArray),
                new Token("gargle", TokenType.String),
                new Token(TokenType.Comma),
                new Token("33", TokenType.Number),
                new Token(TokenType.CloseArray),
                new Token(TokenType.CloseObject)
            });

        var computedTokens = new Tokenizer(testJson).tokenize();

        assertNotNull(computedTokens);
        assertEquals(computedTokens.size(), expectedTokens.size());
        assertTrue(computedTokens.equals(expectedTokens));
    }

    @Test
    public void testBasicTokenizer() {

        var tokens = new Tokenizer("{ \"answer\" : 42}").tokenize();

        var testTokens = makeDoc(new Token[] {
                new Token(TokenType.OpenObject),
                new Token("answer", TokenType.String),
                new Token(TokenType.Colon),
                new Token("42", TokenType.Number),
                new Token(TokenType.CloseObject)});

        assertNotNull(tokens);
        assertEquals(tokens.size(), testTokens.size());
        assertTrue(testTokens.equals(tokens));
    }

    @Test
    public void testBooleanToken() {
        var computed = new Tokenizer("{\"answer\" : true}").tokenize();
        var expected = makeDoc(new Token[] {
                new Token(TokenType.OpenObject),
                new Token("answer", TokenType.String),
                new Token(TokenType.Colon),
                new Token("true", TokenType.Boolean),
                new Token(TokenType.CloseObject)});

        assertNotNull(computed);
        assertEquals(computed.size(), expected.size());
        assertTrue(computed.equals(expected));

        computed = new Tokenizer("{\"answer\" : false}").tokenize();
        expected.set(3, new Token("false", TokenType.Boolean));

        assertNotNull(computed);
        assertEquals(computed.size(), expected.size());
        assertTrue(computed.equals(expected));
    }

    @Test
    public void jsonWithNullKeyword() {
        var computed = new Tokenizer("{\"optional\" : null}").tokenize();
        var expected = makeDoc(new Token[] {
                new Token(TokenType.OpenObject),
                new Token("optional", TokenType.String),
                new Token(TokenType.Colon),
                new Token(TokenType.Null),
                new Token(TokenType.CloseObject)});

        assertNotNull(computed);
        assertEquals(expected, computed);
    }

    /**
     * Convert an array of tokens into a list of tokens.
     */
    private List<Token> makeDoc(Token... tokens) {
        return new ArrayList<Token>(Arrays.asList(tokens));
    }
}

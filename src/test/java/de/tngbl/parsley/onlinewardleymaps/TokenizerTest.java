package de.tngbl.parsley.onlinewardleymaps;

import de.tngbl.parsley.TestUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TokenizerTest {

    private static final String COMPONENT_EXPRESSION = "component Cup of Tea [0.79, 0.61] label [19, -4]";

    @Test
    public void shouldIgnoreLabels() {

        Tokenizer tokenizer = new Tokenizer(Arrays.asList(COMPONENT_EXPRESSION));

        List<List<Token>> tokens = tokenizer.tokenize();

        assertEquals(1, tokens.size());
        assertEquals(3, tokens.get(0).size());

    }
    @Test
    public void shouldParseLinkExpression() {

        Tokenizer tokenizer = new Tokenizer(TestUtils.getLinksTestData());
        List<List<Token>> tokens = tokenizer.tokenize();
        assertEquals(3, tokens.size());

    }

    @Test
    public void shouldParseWholeFile() {

        Tokenizer tokenizer = new Tokenizer(TestUtils.getTeaShopTestData());
        List<List<Token>> tokens = tokenizer.tokenize();
        assertEquals(20, tokens.size());    // ignore Blank lines and ignored keywords

    }

    @Test
    public void shouldTokenizeWithNumberLiteral() throws IOException {

        String rawExpression = "evolve Kettle 0.62 label [16, 7]";
        Tokenizer tokenizer = new Tokenizer(List.of(rawExpression));

        List<Token> tokens = tokenizer.tokenizeExpression(rawExpression);

        assertTrue(tokens.get(0) instanceof Commands);
        assertTrue(tokens.get(1) instanceof StringLiteral);
        assertTrue(tokens.get(2) instanceof NumberLiteral);

    }

}

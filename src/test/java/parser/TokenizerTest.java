package parser;

import interpreter.Token;
import interpreter.Tokenizer;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TokenizerTest {

    private static final String COMPONENT_EXPRESSION = "component Cup of Tea [0.79, 0.61] label [19, -4]";

    @Test
    public void shouldParseComponentExpression() {

        Tokenizer tokenizer = new Tokenizer(Arrays.asList(COMPONENT_EXPRESSION));

        List<List<Token>> tokens = tokenizer.tokenize();

        assertEquals(1, tokens.size());
        assertEquals(4, tokens.get(0).size());

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
        assertEquals(25, tokens.size());

    }

}

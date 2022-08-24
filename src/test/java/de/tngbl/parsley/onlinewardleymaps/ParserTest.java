package de.tngbl.parsley.onlinewardleymaps;

import de.tngbl.parsley.TestUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {

    @Test
    public void shouldThrowMeaningfulException() throws IOException {

        Parser parser = new Parser(TestUtils.TEA_SHOP_FILE);
        List<Token> tokens = List.of(new StringLiteral("Foo"));

        ParserException e = assertThrows(ParserException.class, () -> parser.buildExpressions(tokens));
        System.out.println(e);
    }

    @Test
    public void shouldProcessFile() throws IOException {

        Tokenizer tokenizer = new Tokenizer(TestUtils.getTeaShopTestData());
        List<List<Token>> tokens = tokenizer.tokenize();
        Parser parser = new Parser(TestUtils.TEA_SHOP_FILE);

        tokens.forEach(t -> {
            System.out.println(parser.buildExpressions(t).toString());
        });

    }

}

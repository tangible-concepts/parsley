package de.tngbl.parsley.onlinewardleymaps;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NumberLiteralTest {

    @Test
    public void shouldPass() {

        String[] numbers = new String[]{"0.62", "0.999999", "0.0", "1.0"};

        Arrays.stream(numbers).forEach(s -> {
            assertTrue(NumberLiteral.isNumberLiteral(s));
        });

    }

    @Test
    public void shouldNotPass() {

        String[] numbers = new String[]{"foobar","This. is a sentence.", "0,54", "0.1.1"};

        Arrays.stream(numbers).forEach(s -> {
            assertFalse(NumberLiteral.isNumberLiteral(s), s);
        });

    }

}

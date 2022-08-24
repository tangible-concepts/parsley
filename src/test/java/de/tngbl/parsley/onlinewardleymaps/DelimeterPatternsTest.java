package de.tngbl.parsley.onlinewardleymaps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DelimeterPatternsTest {

    @Test
    public void shouldMatch() {

        assertTrue(StringLiteral.delimitsStringLiteral("label"));
        assertFalse(StringLiteral.delimitsStringLiteral("labelling"));
        assertTrue(StringLiteral.delimitsStringLiteral("[0.39,"));
        assertTrue(StringLiteral.delimitsStringLiteral("0.26]"));

    }




}

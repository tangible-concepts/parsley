package de.tngbl.parsley.onlinewardleymaps;

import java.util.Locale;

public class LabelLiteral extends PositionLiteral {

    public static final String LABEL_LITERAL = "label";

    public LabelLiteral(float x, float y) {
        super(x, y);
    }

    public static boolean isLabelLiteral(String s) {
        return s.trim().toLowerCase(Locale.ROOT).equals(LABEL_LITERAL);
    }
}

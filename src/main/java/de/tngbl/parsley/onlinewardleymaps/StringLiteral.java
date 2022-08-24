package de.tngbl.parsley.onlinewardleymaps;

public class StringLiteral implements Token {

    public final static String DELIMITER_LABEL = "label";
    public final static String DELIMITER_OPENING_BRACKET = "[";
    public final static String DELIMITER_CLOSING_BRACKET = "]";

    private final String literal;

    public StringLiteral(String literal) {
        this.literal = literal;
    }

    public static boolean isStringLiteral(String s) {

        if (
                LinkOperator.isLink(s) ||
                FlowOperator.isFlow(s) ||
                NumberLiteral.isNumberLiteral(s) ||
                delimitsStringLiteral(s)
        ) {

            return false;
        }

        return true;
    }

    public String getLiteral() {
        return literal;
    }

    @Override
    public String toString() {
        return "StringLiteral{" +
                "literal='" + literal + '\'' +
                '}';
    }

    public static boolean delimitsStringLiteral(String s) {

        return s.equals(DELIMITER_LABEL) ||
                s.startsWith(DELIMITER_OPENING_BRACKET) ||
                s.endsWith(DELIMITER_CLOSING_BRACKET);
    }
}

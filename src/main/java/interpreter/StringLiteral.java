package interpreter;

public class StringLiteral implements Token {

    private final String literal;

    public StringLiteral(String literal) {
        this.literal = literal;
    }

    public static boolean isStringLiteral(String s) {

        if (Keywords.isKeyword(s)) {
            return false;
        }

        if (LinkLiteral.isLink(s)) {
            return false;
        }

        for (String d : ParserUtils.delimiters) {
            if (s.startsWith(d)) {
                return false;
            }
        }

        return true;
    }

    public String getLiteral() {
        return literal;
    }

    @Deprecated
    public static Token getFromExpression(String rawExpression) {
        String literal = rawExpression.substring(0,rawExpression.indexOf("["));
        return new StringLiteral(literal);
    }
}

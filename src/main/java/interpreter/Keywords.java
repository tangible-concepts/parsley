package interpreter;

import java.util.Locale;

public enum Keywords implements Token {

    COMPONENT, UNDEFINED;

    public static Keywords getFromExpression(String rawExpression) {

        if (null == rawExpression || rawExpression.isEmpty()) return Keywords.UNDEFINED;

        String token = rawExpression.split(" ")[0].toLowerCase(Locale.ROOT);

        return switch (token) {
            case "component" -> COMPONENT;
            default -> UNDEFINED;
        };

    }

    public static boolean isKeyword(String s) {

        return Keywords.getFromExpression(s) != UNDEFINED;

    }
}

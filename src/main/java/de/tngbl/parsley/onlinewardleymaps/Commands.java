package de.tngbl.parsley.onlinewardleymaps;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Commands identify the type of expressions, they are always the first token of an expression.
 * Mot every expression has to contain a command, e.g. relationships.
 */
public enum Commands implements Token {

    TITLE,
    ANCHOR,
    COMPONENT,
    EVOLVE,
    ANNOTATION,
    ANNOTATIONS,
    NOTE,
    STYLE,
    PIPELINE,
    EVOLUTION,
    INERTIA,
    MARKET,
    UNDEFINED;

    public static List<Commands> ignoredKeywords = Arrays.asList(new Commands[]{ANNOTATION, ANNOTATIONS, NOTE, STYLE, EVOLUTION});

    public static Commands getFromExpression(String rawExpression) {

        if (null == rawExpression || rawExpression.isEmpty()) return Commands.UNDEFINED;

        String token = rawExpression.split(" ")[0].toLowerCase(Locale.ROOT);

        switch (token) {
            case "title":
                return TITLE;
            case "anchor":
                return ANCHOR;
            case "component":
                return COMPONENT;
            case "evolve":
                return EVOLVE;
            case "annotation":
                return ANNOTATION;
            case "annotations":
                return ANNOTATIONS;
            case "note":
                return NOTE;
            case "style":
                return STYLE;
            case "pipeline":
                return PIPELINE;
            case "evolution":
                return EVOLUTION;
            case "inertia":
                return INERTIA;
            case "market":
                return  MARKET;
            default:
                return UNDEFINED;
        }

    }

    public static boolean isCommand(String s) {

        return Commands.getFromExpression(s) != UNDEFINED;

    }
}

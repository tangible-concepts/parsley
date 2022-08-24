package de.tngbl.parsley.onlinewardleymaps;

import io.quarkus.logging.Log;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PositionLiteral implements Token {

    private final float x;
    private final float y;

    public PositionLiteral(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static boolean isPointLiteral(String s) {
        return (s.startsWith("["));
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public static PositionLiteral getFromExpression(String rawExpression) {

        List<String> floats = Arrays.stream(rawExpression.split(","))
                .collect(Collectors.toList())
                .stream()
                .map(e -> e.replace("[", ""))
                .map(e -> e.replace("]", ""))
                .collect(Collectors.toList());

        try {
            return new PositionLiteral(Float.parseFloat(floats.get(1)),
                    Float.parseFloat(floats.get(0)));
        } catch (NumberFormatException | IndexOutOfBoundsException e ) {
            Log.warn(String.format("Error parsing Position - Resetting to [0.0, 0.0], expression: '%s',  %s", rawExpression, e.getMessage()));
            return new PositionLiteral(0f, 0f);
        }
    }

}

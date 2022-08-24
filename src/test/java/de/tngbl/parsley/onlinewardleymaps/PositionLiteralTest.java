package de.tngbl.parsley.onlinewardleymaps;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionLiteralTest {

    @ParameterizedTest
    @MethodSource("provideAcceptablePositions")
    public void shouldAccept(String expression, float expectedY,  float expectedX) {

        PositionLiteral literal = PositionLiteral.getFromExpression(expression);

        assertEquals(expectedX, literal.getX());
        assertEquals(expectedY, literal.getY());

    }

    private static Stream<Arguments> provideAcceptablePositions() {
        return Stream.of(
                Arguments.of("[0.95, 0.63]", 0.95f, 0.63f),
                Arguments.of("[19, -4]", 19f, -4f),
                Arguments.of("[-57, 4]", -57f, 4f),
                Arguments.of("[0.95,0.63]", 0.95f, 0.63f),
                Arguments.of("[19,-4]", 19f, -4f),
                Arguments.of("[-57,4]", -57f, 4f),
                Arguments.of("[0.95,0.63 ]", 0.95f, 0.63f),
                Arguments.of("[19,-4 ]", 19f, -4f),
                Arguments.of("[-57,4 ]", -57f, 4f),
                Arguments.of("[ 0.95,0.63 ]", 0.95f, 0.63f),
                Arguments.of("[ 19,-4 ]", 19f, -4f),
                Arguments.of("[ -57,4 ]", -57f, 4f),
                Arguments.of("[ 0.95, 0.63 ]", 0.95f, 0.63f),
                Arguments.of("[ 19, -4 ]", 19f, -4f),
                Arguments.of("[ -57, 4 ]", -57f, 4f)
        );
    }

}

package de.tngbl.parsley.onlinewardleymaps;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlowOperatorTest {

    static Stream<String> validFlowDefinitions() {
        return Stream.of(
                "diversity+<>monoculture",
                "Testing+>AI-Vision-Inspection",
                "traceability+<food standards",
                "diversity +<>monoculture",
                "Testing +>AI-Vision-Inspection",
                "traceability +<food standards",
                "diversity +<> monoculture",
                "Testing +> AI-Vision-Inspection",
                "traceability +< food standards",
                "diversity+<> monoculture",
                "Testing+> AI-Vision-Inspection",
                "traceability+< food standards",
//                " +<> monoculture",
//                " +> AI-Vision-Inspection",
//                " +< food standards",
                "+<> monoculture",
                "+> AI-Vision-Inspection",
                "+< food standards",
                "+<>monoculture",
                "+>AI-Vision-Inspection",
                "+<food standards",
                "Educational Institutions+<>Agenda");
    }

    @ParameterizedTest
    @MethodSource("validFlowDefinitions")
    public void patternShouldMatch(String s) {

        Pattern p = Pattern.compile(FlowOperator.PATTERN_STRING);

        Matcher matcher = p.matcher(s);
        assertTrue(matcher.matches(), s);
    }

    @ParameterizedTest
    @MethodSource("validFlowDefinitions")
    public void patternShoulFindOperatorAndOperands(String s) {

        Pattern p = Pattern.compile(FlowOperator.PATTERN_STRING);
        Matcher matcher = p.matcher(s);
        matcher.matches();
        int count = matcher.groupCount();
        List<String> validOperators = List.of("+<>", "+<", "+>");

         if (count == 3) {
            String firstOperand = matcher.group(1);
            String operator = matcher.group(2);
            String secondOperand = matcher.group(3);

            if (firstOperand != null) {
                assertFalse(firstOperand.isBlank(), s);
            }
            assertTrue(validOperators.contains(operator), s);
            assertFalse(secondOperand.isBlank(), s);

         } else {
            Assertions.fail(s);
        }

    }

    @ParameterizedTest
    @MethodSource("validFlowDefinitions")
    public void patternShoulFindOperatorUsingNames(String s) {

        Pattern p = Pattern.compile(FlowOperator.PATTERN_STRING);
        Matcher matcher = p.matcher(s);
        matcher.matches();
        List<String> validOperators = List.of("+<>", "+<", "+>");

        assertTrue(validOperators.contains(matcher.group(FlowOperator.PATTERN_GROUP_OPERATOR)));

    }


}

package de.tngbl.parsley.onlinewardleymaps;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FlowOperator implements Token {

    static final String PATTERN_STRING = "(?<left>.+)?\\s?(?<operator>\\+[<>]{1,2})\\s?(?<right>.*)";
    static final String PATTERN_GROUP_LEFT = "left";
    static final String PATTERN_GROUP_OPERATOR = "operator";
    static final String PATTERN_GROUP_RIGHT = "right";
    static final Pattern PATTERN = Pattern.compile(PATTERN_STRING);

    static final String FLOW_SYMBOL = "+<>";
    static final String FLOW_PAST_SYMBOL = "+<";
    static final String FLOW_FUTURE_SYMBOL = "+>";

    private final String operator;
    private final Optional<String> leftOperand;
    private final Optional<String> rightOperand;

    public FlowOperator(String rawToken) {

        Matcher matcher = PATTERN.matcher(rawToken);

        if (matcher.matches()) {

            String extractedOperator =  matcher.group(PATTERN_GROUP_OPERATOR);

            switch (extractedOperator) {
                case FLOW_SYMBOL:
                    this.operator = FLOW_SYMBOL;
                    break;
                case FLOW_PAST_SYMBOL:
                    this.operator = FLOW_PAST_SYMBOL;
                    break;
                case FLOW_FUTURE_SYMBOL:
                    this.operator = FLOW_FUTURE_SYMBOL;
                    break;
                default:
                    throw new ParserException(String.format("Unrecognized Flow Operator %s", extractedOperator));
            }

            this.leftOperand = Optional.ofNullable(matcher.group(PATTERN_GROUP_LEFT));
            this.rightOperand = Optional.ofNullable(matcher.group(PATTERN_GROUP_RIGHT));

        } else {
            throw new ParserException(String.format("Cannot create FlowOperator from %s", rawToken));
        }

    }

    public String getOperator() {
        return operator;
    }

    public Optional<String> getLeftOperand() {
        return leftOperand;
    }

    public Optional<String> getRightOperand() {
        return rightOperand;
    }

    public static boolean isFlow(String s) {
        return Pattern.compile(PATTERN_STRING).matcher(s).matches();
    }
}

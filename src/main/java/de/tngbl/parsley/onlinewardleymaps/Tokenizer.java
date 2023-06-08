package de.tngbl.parsley.onlinewardleymaps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Lexer for Wardley Map grammar, as used in https://www.onlinewardleymaps.com/
 * There seems to be no formal description of the grammar, so this is a rather pragmatic approach.
 */
public class Tokenizer {

    private final static Logger LOG = LoggerFactory.getLogger(Tokenizer.class);
    public static final String INLINE_COMMENT = "//";

    private final List<String> expressions;

    public Tokenizer(List<String> expressions) {
        this.expressions = expressions;
    }

    public List<List<Token>> tokenize() {

        List<List<Token>> tokenizedExpressions = new LinkedList<>();

        for (String expression : expressions) {

            if (isComment(expression)) {
                continue;   // skip comments
            }

            List<Token> tokens = tokenizeExpression(expression);

            if (!tokens.isEmpty()) {
                tokenizedExpressions.add(tokens);
            }
        }

        return tokenizedExpressions;
    }

    public List<Token> tokenizeExpression(String expression) {

        List<String> rawTokens = new LinkedList<>(prepcrocessExpressions(expression));

        List<Token> tokens = new LinkedList<>();

        // Consume one raw token after the other, enrich the list of created tokens
        while (!rawTokens.isEmpty()) {

            if (Commands.isCommand(rawTokens.get(0))
                    && tokens.isEmpty()                 // Commands are allowed only as first token
                    && ! isOperatorExpression(expression)) {
                tokenizeCommand(rawTokens, tokens);
            } else if (LinkOperator.isLink(rawTokens.get(0))) {
                tokenizeLinkOperator(rawTokens, tokens);
            } else if (FlowOperator.isFlow(rawTokens.get(0))) {
                tokenizeFlowOperator(rawTokens, tokens);
            } else if (NumberLiteral.isNumberLiteral(rawTokens.get(0))) {
                tokenizeNumberLiteral(rawTokens, tokens);
            } else if (StringLiteral.isStringLiteral(rawTokens.get(0))) {
                tokenizeStringLiteral(rawTokens, tokens);
            } else if (PositionLiteral.isPointLiteral(rawTokens.get(0))) {
                tokenizePositionLiteral(rawTokens, tokens);
            } else if (LabelLiteral.isLabelLiteral(rawTokens.get(0))) {
                tokenizeLabelLiteral(rawTokens);
            } else {
                LOG.warn("Unknown Token: {}", rawTokens.get(0));
                break;  // ignore unimplemented Token-types
            }
        }
        return tokens;
    }

    private boolean isOperatorExpression(String expression) {

        return expression.contains(LinkOperator.SYMBOL) ||
                expression.contains(FlowOperator.FLOW_SYMBOL) ||
                expression.contains(FlowOperator.FLOW_PAST_SYMBOL) ||
                expression.contains(FlowOperator.FLOW_FUTURE_SYMBOL);
    }

    /**
     * Preprocessing is perfromed to ensure a certain level of well-formdness on the input, allowing later steps during the parsing and interpretation process to rely on assumptions, eg. operators are always isolated tokens.
     * @param rawExpression the input as defined in the source
     * @return splitted expression
     */
    private List<String> prepcrocessExpressions(String rawExpression) {

        rawExpression = rawExpression
                .replace(LinkOperator.SYMBOL, " " + LinkOperator.SYMBOL + " ")
                .replace(FlowOperator.FLOW_SYMBOL, " " + FlowOperator.FLOW_SYMBOL + "")
                .replace(FlowOperator.FLOW_PAST_SYMBOL, " " + FlowOperator.FLOW_PAST_SYMBOL + "")
                .replace(FlowOperator.FLOW_FUTURE_SYMBOL, " " + FlowOperator.FLOW_FUTURE_SYMBOL + "");
        return Arrays.stream(rawExpression.split(" "))
                .filter(s -> !s.isBlank())
                .collect(Collectors.toList());
    }

    private boolean isComment(String rawToken) {

        return rawToken.trim().startsWith(INLINE_COMMENT);

    }

    private void tokenizeCommand(List<String> rawTokens, List<Token> tokens) {

        String rawKeyword = rawTokens.remove(0);
        Commands command = Commands.getFromExpression(rawKeyword);

        // ignore those
        if (Commands.ignoredKeywords.contains(command)) {
            rawTokens.clear();
            return;
        }

        tokens.add(command);

        if (command == Commands.ANCHOR ||
                command == Commands.COMPONENT ||
                command == Commands.EVOLVE ||
                command == Commands.PIPELINE ||
                command == Commands.MARKET) {

            tokenizeIdentifier(rawTokens, tokens);
        }

    }

    private void tokenizeIdentifier(List<String> rawTokens, List<Token> tokens) {
        tokenizeStringLiteral(rawTokens, tokens);

        // Replace extracted StringLiteral with Identifier
        try {

            Token removed = tokens.remove(tokens.size() - 1);
            Identifier identifier = new Identifier((StringLiteral) removed);
            tokens.add(identifier);

        } catch (ClassCastException e) {
            System.out.println(e);
        }
    }

    private void tokenizeLinkOperator(List<String> rawTokens, List<Token> tokens) {

        rawTokens.remove(0);
        tokens.add(new LinkOperator());
    }

    private void tokenizeFlowOperator(List<String> rawTokens, List<Token> tokens) {

    FlowOperator flowOperator = new FlowOperator(rawTokens.remove(0));

        if (flowOperator.getLeftOperand().isPresent()) {
            tokens.add(new Identifier(new StringLiteral(flowOperator.getLeftOperand().get())));
        }   // else left operand is already extracted and last element of tokens

        tokens.add(flowOperator);

        if (flowOperator.getRightOperand().isPresent()) {
            if (flowOperator.getRightOperand().get().isEmpty()) {
                tokens.add(new Identifier(new StringLiteral(rawTokens.remove(0))));
            } else {
                tokens.add(new Identifier(new StringLiteral(flowOperator.getRightOperand().get())));
            }
        } // else right operand is a seperate raw token

    }

    private void tokenizeNumberLiteral(List<String> rawTokens, List<Token> tokens) {

        String rawNumber = rawTokens.remove(0);

        try {
            float number = Float.parseFloat(rawNumber);
            tokens.add(new NumberLiteral(number));
        } catch (NumberFormatException nfe) {
            throw new ParserException(String.format("Cannot create NumberLiteral from %s", rawNumber));
        }
    }

    private void tokenizeStringLiteral(List<String> rawTokens, List<Token> tokens) {

        if (!rawTokens.isEmpty()) {

            StringBuilder buffer = new StringBuilder(rawTokens.remove(0));

            while (!rawTokens.isEmpty()) {
                if (StringLiteral.isStringLiteral(rawTokens.get(0))) {
                    buffer.append(" ").append(rawTokens.remove(0));
                } else {
                    break;
                }
            }
            StringLiteral stringLiteral = new StringLiteral(buffer.toString());
            tokens.add(stringLiteral);
        }
    }

    private void tokenizePositionLiteral(List<String> rawTokens, List<Token> tokens) {

        StringBuilder buffer = new StringBuilder(rawTokens.remove(0));
        String originalString = rawTokens.toString();

        while (!rawTokens.isEmpty()) {
            if (!rawTokens.get(0).endsWith("]")) {
                buffer.append(rawTokens.remove(0));
            } else {
                buffer.append(rawTokens.remove(0));
                Token pointLiteral = PositionLiteral.getFromExpression(buffer.toString());
                tokens.add(pointLiteral);
                break;
            }
        }

    }

    private void tokenizeLabelLiteral(List<String> rawTokens) {

        // remove 3 Tokens
        for (int i = 1; i <=3; i++) {
            rawTokens.remove(0);
        }
    }

}

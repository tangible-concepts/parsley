package interpreter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Tokenizer {

    private final List<String> expressions;

    public Tokenizer(List<String> expressions) {
        this.expressions = expressions;
    }

    public List<List<Token>> tokenize() {

        List<List<Token>> tokenizedExpressions = new LinkedList<>();

        for (String expression : expressions) {
            tokenizedExpressions.add(tokenizeExpression(expression));
        }

        return tokenizedExpressions;

    }

    public List<Token> tokenizeExpression(String expression) {

        List<String> rawTokens = new LinkedList<>(prepareExpression(expression));

        List<Token> tokens = new LinkedList<>();

        while (!rawTokens.isEmpty()) {
            if (Keywords.isKeyword(rawTokens.get(0))) {
                tokenizeKeyword(rawTokens, tokens);
            } else if (LinkLiteral.isLink(rawTokens.get(0))) {
                tokenizeLinkLiteral(rawTokens, tokens);
            } else if (StringLiteral.isStringLiteral(rawTokens.get(0))) {
                tokenizeStringLiteral(rawTokens, tokens);
            } else if (PointLiteral.isPointLiteral(rawTokens.get(0))) {
                tokenizePointLiteral(rawTokens, tokens);
            } else {
                break;  // ignore unimplemented Token-types
            }
        }
        return tokens;
    }

    private void tokenizeLinkLiteral(List<String> rawTokens, List<Token> tokens) {
        rawTokens.remove(0);
        tokens.add(new LinkLiteral());
    }

    private List<String> prepareExpression(String expression) {
        expression = expression.replace("->", " -> ");
        return Arrays.stream(expression.split(" "))
                .filter(s->!s.isBlank() )
                .toList();
    }

    private void tokenizePointLiteral(List<String> rawTokens, List<Token> tokens) {
        StringBuilder buffer = new StringBuilder(rawTokens.remove(0));

        while (!rawTokens.isEmpty()) {
            if (!rawTokens.get(0).endsWith("]")) {
                buffer.append(rawTokens.remove(0));
            } else {
                buffer.append(rawTokens.remove(0));
                Token pointLiteral = PointLiteral.getFromExpression(buffer.toString());
                tokens.add(pointLiteral);
                break;
            }
        }
    }

    private void tokenizeKeyword(List<String> rawTokens, List<Token> tokens) {
        String rawKeyword = rawTokens.remove(0);
        Keywords keyword = Keywords.getFromExpression(rawKeyword);
        tokens.add(keyword);
    }

    private void tokenizeStringLiteral(List<String> rawTokens, List<Token> tokens) {
        StringBuilder buffer = new StringBuilder(rawTokens.remove(0));

        while (!rawTokens.isEmpty()) {
            if (StringLiteral.isStringLiteral(rawTokens.get(0))) {
                buffer.append(" ").append(rawTokens.remove(0));
            } else {
                break;
            }
        }

        Token stringLiteral = new StringLiteral(buffer.toString());
        tokens.add(stringLiteral);
    }

}

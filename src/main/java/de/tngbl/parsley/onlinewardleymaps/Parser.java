package de.tngbl.parsley.onlinewardleymaps;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {

    private final Tokenizer tokenizer;

    public Parser(File file) throws IOException {

        List<String> expressions = Files.lines(file.toPath()).collect(Collectors.toList());
        this.tokenizer = new Tokenizer(expressions);

        parse();

    }

    public List<Expression> parse() {

        List<Expression> expressions = new LinkedList<>();

        List<List<Token>> tokenizedExpressions = tokenizer.tokenize();
        tokenizedExpressions.forEach(t -> expressions.add(buildExpressions(t)));

        return expressions;
    }

    public Expression buildExpressions(List<Token> tokens) {

        guard(tokens);

        if (tokens.get(0) instanceof Commands && tokens.get(0) != Commands.UNDEFINED) {
            return buildCommandExpression(tokens);
        } else if (tokens.get(1) instanceof LinkOperator) {
            return new LinkExpression(tokens);
        } else if (tokens.get(1) instanceof FlowOperator) {
            return new FlowExpression(tokens);
        } else {
            throw new ParserException(String.format("Cannot evaluate expression from set of tokens: %s", tokens.stream().map(Object::toString).collect(Collectors.joining(","))));
        }

    }

    private Expression buildCommandExpression(List<Token> tokens) {

        Commands command = (Commands) tokens.get(0);

        switch (command) {
            case TITLE:
                return new TitleExpression(tokens);
            case ANCHOR:
                return new AnchorExpression(tokens);
            case COMPONENT:
                return new ComponentExpression(tokens);
            case EVOLVE:
                return new EvolveExpression(tokens);
            case PIPELINE:
                return new PipelineExpression(tokens);
            case MARKET:
                return new MarketExpression(tokens);
            default:
                throw new ParserException("Unexpected Keyword: " + command);
        }

    }

    private void guard(List<Token> tokens) {

        if (tokens.size() < 2) {
            throw new ParserException(String.format("Incomplete Expression, too less tokens in: %s", tokens.stream().map(Object::toString).collect(Collectors.joining(","))));
        }

    }
}

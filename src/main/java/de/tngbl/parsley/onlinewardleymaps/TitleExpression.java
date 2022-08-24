package de.tngbl.parsley.onlinewardleymaps;

import java.util.List;

public class TitleExpression extends Expression {

    private final String title;

    public TitleExpression(List<Token> tokens) {
        super(tokens);
        this.title = ((StringLiteral) tokens.get(1)).getLiteral();
    }

    @Override
    public boolean isApplicable(List<Token> tokens) {

        return tokens.size()>=2 &&
                tokens.get(1) instanceof StringLiteral;
    }

    @Override
    public InterpreterContext evaluate(InterpreterContext context) {
        context.getMap().setMapTitle(this.title);
        return context;
    }

    @Override
    public String toString() {
        return "TitleExpression{" +
                "title='" + title + '\'' +
                '}';
    }
}

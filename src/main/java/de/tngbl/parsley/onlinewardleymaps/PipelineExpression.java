package de.tngbl.parsley.onlinewardleymaps;

import io.quarkus.logging.Log;

import java.util.List;

// TODO: Pipelines not yet fully implementd
public class PipelineExpression extends ComponentExpression {

    private final String name;

    public PipelineExpression(List<Token> tokens) {
        super(tokens);
        this.name = ((Identifier) tokens.get(1)).getName();
    }

    @Override
    public boolean isApplicable(List<Token> tokens) {

        if (tokens.size() == 2) {
            // Missing PositionLiteral FIXME
            tokens.add(new PositionLiteral(0.5f, 0.5f));
        }

        return tokens.size() >= 3 &&
                tokens.get(0) == Commands.PIPELINE &&
                tokens.get(1) instanceof Identifier &&
                tokens.get(2) instanceof PositionLiteral;
    }

    @Override
    public InterpreterContext evaluate(InterpreterContext context) {

        Log.error(String.format("PipelineExpressions are not supported yet! Expressing pipeline '%s' as component!", name));
        return super.evaluate(context);
    }
}

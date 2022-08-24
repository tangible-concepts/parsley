package de.tngbl.parsley.onlinewardleymaps;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Expression {

    public static final String UNDECLARED_IDENTIFIER_MESSAGE = "Undeclared identifier: '%s'";

    /**
     * Subclasses should delegate to this constructor to trigger validation via {@link #isApplicable(List)} method.
     * The subclass constructor should take care of value extraction from the given List of tokens.
     * Extracted values should be kept in final fields, so that {@link #evaluate(InterpreterContext)} may operate on them.
     *
     * @param tokens Tokens that form the expression
     */
    public Expression(List<Token> tokens) {

        if (!isApplicable(tokens)) {
            throw new ParserException(String.format("Set of tokens not applicable for Expression: %s", tokens.stream().map(Object::toString).collect(Collectors.joining(", "))));
        }

    }

    /**
     * Decide if this expression can be formed of the given set of tokens.
     * @param tokens Tokens that should be used to form the expression
     * @return
     */
    abstract public boolean isApplicable(List<Token> tokens);

    /**
     * Evaluates this expression and change the state of the given {@link InterpreterContext}
     * @param context
     * @return
     */
    public abstract InterpreterContext evaluate(InterpreterContext context);

}

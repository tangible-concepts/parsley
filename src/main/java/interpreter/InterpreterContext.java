package interpreter;

import java.util.List;

public class InterpreterContext {

    private final List<Token> tokens;

    public InterpreterContext(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Token> getTokens() {
        return tokens;
    }
}

package de.tngbl.parsley.onlinewardleymaps;

import de.tngbl.parsley.wardleymapping.WardleyMap;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Interpreter {

    private final List<Expression> expressions;
    private final Set<String> knownIdentifier = new HashSet<>();

    public Interpreter(List<Expression> expressions) {
        this.expressions = expressions;

    }

    public WardleyMap interpret() {

        WardleyMap map = new WardleyMap();

        for (Expression expression : expressions) {

            InterpreterContext context = new InterpreterContext(map, knownIdentifier);
            InterpreterContext returnedContext = expression.evaluate(context);
            knownIdentifier.addAll(returnedContext.getKnownIdentifier());

        }

        return map;

    }

}

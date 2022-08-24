package de.tngbl.parsley.onlinewardleymaps;

import de.tngbl.parsley.wardleymapping.WardleyMap;

import java.util.Collections;
import java.util.Set;

public class InterpreterContext {

    private final WardleyMap map;
    private final Set<String> knownIdentifier;

    public InterpreterContext(WardleyMap map, Set<String> knownIdentifier) {
        this.map = map;
        this.knownIdentifier = knownIdentifier;
    }

    public Set<String> getKnownIdentifier() {
        return Collections.unmodifiableSet(knownIdentifier);
    }

    public WardleyMap getMap() {
        return map;
    }

    public void addIdentifier(String name) {

        if (! knownIdentifier.contains(name)) {
            knownIdentifier.add(name);
        } else {
            throw new ParserException(String.format("Multiple declaration of Identifier %s!", name));
        }

    }
}

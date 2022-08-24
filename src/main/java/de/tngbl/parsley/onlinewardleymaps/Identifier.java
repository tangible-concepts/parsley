package de.tngbl.parsley.onlinewardleymaps;

/**
 * Identifiers are StringLiterals used to identify objects in the parsed context
 */
public class Identifier extends StringLiteral {

    public Identifier(StringLiteral name) {
        super(name.getLiteral());
    }

    public String getName() {
        return super.getLiteral();
    }

    @Override
    public String toString() {
        return "Identifier{" +
                "name='" + super.getLiteral() + '\'' +
        "}";
    }
}

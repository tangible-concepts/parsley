package de.tngbl.parsley.wardleymapping;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.Objects;

@ValueObject
public final class Name {

    private final String value;

    private Name(String value) {
        this.value = value;
    }

    public Name(Name name) {
        this.value = name.value();
    }

    public static Name of(String name) {
        return new Name(name);
    }

    public String value() {

        if (value == null || value.isBlank()) {
            throw new MapSemanticsViolated("Names may not be blank nor null!");
        }

        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Name) obj;
        return Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }

}

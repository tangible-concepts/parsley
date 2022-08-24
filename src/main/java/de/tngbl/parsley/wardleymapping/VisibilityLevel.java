package de.tngbl.parsley.wardleymapping;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.Objects;

@ValueObject
public final class VisibilityLevel {
    private final float value;

    VisibilityLevel(float value) {

        if (value < 0 || value > 1) {
            throw new MapSemanticsViolated(String.format("Visibility must not be less than zero or larger than 1, but was '%s'!", Float.toString(value)));
        }

        this.value = value;
    }

    public static VisibilityLevel of(String visibility) {
        return new VisibilityLevel(Float.parseFloat(visibility));
    }

    public float value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (VisibilityLevel) obj;
        return Float.floatToIntBits(this.value) == Float.floatToIntBits(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}

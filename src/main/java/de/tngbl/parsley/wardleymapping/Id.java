package de.tngbl.parsley.wardleymapping;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.Objects;
import java.util.UUID;

/**
 * Unique Identifier for element on map. Instances need to be distinguishable because the name may be ambiguous.
 * E.g. when evaluating multiple versions of a map, several Map instances represent the different states of the map so each instance needs to be identifiable, same for components.
 */
@ValueObject
public final class Id {
    private final UUID uuid;

    Id(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID uuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Id) obj;
        return Objects.equals(this.uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "MapUid[" +
                "uuid=" + uuid + ']';
    }

    public Id() {
        this(UUID.randomUUID());
    }

    public String value() {
        return uuid.toString();
    }

}

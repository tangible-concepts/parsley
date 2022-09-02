package de.tngbl.parsley.wardleymapping;

/**
 * Base Class for every element that is part of a map.
 */
public abstract class MapElement implements Identifiable{

    private final Id id;

    public MapElement() {
        this.id = new Id();
    }

    public MapElement(Id id) {
        this.id = id;
    }

    @Override
    public Id getId() {
        return id;
    }
}

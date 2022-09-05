package de.tngbl.parsley.neo4jadapter;

public enum RelationshipTypes {

    NEEDS("NEEDS"),
    EVOLVES_TO("EVOLVES_TO"),
    CONTAINS("CONTAINS"),
    INERTIA("INERTIA");

    private final String type;

    RelationshipTypes(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }

}

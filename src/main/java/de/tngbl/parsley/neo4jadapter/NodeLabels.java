package de.tngbl.parsley.neo4jadapter;

public enum NodeLabels {

    MAP("MAP"),
    COMPONENT("COMPONENT"),
    ANCHOR("ANCHOR"),
    EVOLVED_COMPONENT("EVOLVED_COMPONENT");

private final String label;

    NodeLabels(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}

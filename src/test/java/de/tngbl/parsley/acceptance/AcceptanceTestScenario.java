package de.tngbl.parsley.acceptance;

import de.tngbl.parsley.wardleymapping.*;

import java.util.List;

public class AcceptanceTestScenario {

    private final String inputFile;
    private final WardleyMap map;
    private final List<Anchor> anchors;
    private final List<Component> components;
    private final List<Link> relationships;
    private final List<EvolvedComponent> evolvedComponents;
    private final boolean importOnly;

    AcceptanceTestScenario(String inputFile, WardleyMap map, List<Anchor> anchors, List<Component> components, List<Link> relationships, List<EvolvedComponent> evolvedComponents) {
        this.inputFile = inputFile;
        this.map = map;
        this.anchors = anchors;
        this.components = components;
        this.relationships = relationships;
        this.evolvedComponents = evolvedComponents;
        this.importOnly = false;
    }

    AcceptanceTestScenario(String inputFile) {
        this.inputFile = inputFile;
        this.map = null;
        this.anchors = null;
        this.components = null;
        this.relationships = null;
        this.evolvedComponents = null;
        this.importOnly = true;
    }

    public String getInputFile() {
        return inputFile;
    }

    public List<Anchor> getAnchors() {
        return anchors;
    }

    public List<Component> getComponents() {
        return components;
    }

    public List<Link> getRelationships() {
        return relationships;
    }

    public List<EvolvedComponent> getEvolvedComponents() {
        return evolvedComponents;
    }

    public WardleyMap getMap() {
        return map;
    }

    public boolean isImportOnly() {
        return importOnly;
    }

    @Override
    public String toString() {
        return inputFile;
    }
}

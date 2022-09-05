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
    private final List<InertiaTemplate> inertias;
    private final List<PipelineTemplate> pipelines;
    private final List<FlowTemplate> flows;
    private final boolean importOnly;

    AcceptanceTestScenario(String inputFile,
                           WardleyMap map,
                           List<Anchor> anchors,
                           List<Component> components,
                           List<Link> relationships,
                           List<EvolvedComponent> evolvedComponents,
                           List<InertiaTemplate> inertias,
                           List<PipelineTemplate> pipelines,
                           List<FlowTemplate> flows) {
        this.inputFile = inputFile;
        this.map = map;
        this.anchors = anchors;
        this.components = components;
        this.relationships = relationships;
        this.evolvedComponents = evolvedComponents;
        this.inertias = inertias;
        this.pipelines = pipelines;
        this.flows = flows;
        this.importOnly = false;
    }

    AcceptanceTestScenario(String inputFile) {
        this.inputFile = inputFile;
        this.map = null;
        this.anchors = null;
        this.components = null;
        this.relationships = null;
        this.evolvedComponents = null;
        this.inertias = null;
        this.pipelines = null;
        this.flows = null;
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

    public List<InertiaTemplate> getInertias() {
        return inertias;
    }

    public List<PipelineTemplate> getPipelines() {
        return pipelines;
    }

    public List<FlowTemplate> getFlows() {
        return flows;
    }

    @Override
    public String toString() {
        return inputFile;
    }

    public static class InertiaTemplate {

        private final Component component;

        public InertiaTemplate(Component component) {
            this.component = component;
        }

        public Component getComponent() {
            return component;
        }
    }

    public static class PipelineTemplate {

        private final String componentName;
        private final String min;
        private final String max;

        public PipelineTemplate(String componentName, String min, String max) {
            this.componentName = componentName;
            this.min = min;
            this.max = max;
        }

        public PipelineTemplate(String componentName) {
            this(componentName, "0", "1");
        }

        public String getComponentName() {
            return componentName;
        }

        public String getMin() {
            return min;
        }

        public String getMax() {
            return max;
        }
    }

    public static class FlowTemplate {

        private final String from;
        private final String to;

        public FlowTemplate(String from, String to) {
            this.from = from;
            this.to = to;
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }
    }
}

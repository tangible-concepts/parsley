package de.tngbl.parsley.acceptance;

import de.tngbl.parsley.wardleymapping.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static de.tngbl.parsley.acceptance.AcceptanceTestScenario.*;

public class AcceptanceTestScenarioBuilder {

    private final String inputFile;
    private final WardleyMap map = new WardleyMap();
    private final List<Anchor> anchors = new LinkedList<>();
    private final List<Component> components = new LinkedList<>();
    private final List<Link> relationships = new LinkedList<>();
    private final List<EvolvedComponent> evolvedComponents = new LinkedList<>();
    private final List<InertiaTemplate> inertias = new LinkedList<>();
    private final List<PipelineTemplate> pipelines = new LinkedList<>();
    private final List<FlowTemplate> flows = new LinkedList<>();

    public AcceptanceTestScenarioBuilder(String inputFile) {
        this.inputFile = inputFile;
    }

    public AcceptanceTestScenarioBuilder withMapTitle(String name) {
       map.setMapTitle(name);
       return this;
    }

    public AcceptanceTestScenarioBuilder withAnchor(String name, String visibility, String evolution) {
       anchors.add(new Anchor(Name.of(name), VisibilityLevel.of(visibility), EvolutionLevel.of(evolution)));
       return this;
    }

    public AcceptanceTestScenarioBuilder withComponent(String name, String visibility, String evolution) {
        return withComponent(name, visibility, evolution, false);
    }

    public AcceptanceTestScenarioBuilder withComponent(String name, String visibility, String evolution, boolean hasInertia) {
        Component component = new Component(Name.of(name), VisibilityLevel.of(visibility), EvolutionLevel.of(evolution));
        components.add(component);

        if (hasInertia) {
            inertias.add(new InertiaTemplate(component));
        }

        return this;
    }

    public AcceptanceTestScenarioBuilder withRelationship(String from, String to) {

        Optional<? extends ComponentBase> fromComponent = components.stream().filter(c -> c.getName().value().equals(from)).findFirst();

        if (fromComponent.isEmpty()) {
            fromComponent = anchors.stream().filter(a -> a.getName().value().equals(from)).findFirst();
        }

        if (fromComponent.isEmpty()) {
            fromComponent = evolvedComponents.stream().filter(e -> e.getName().value().equals(from)).findFirst();
        }

        if (fromComponent.isEmpty()) {
            throw new IllegalArgumentException(String.format("Undeclared Component: %s", from));
        }

        Component toComponent = components.stream().filter(c -> c.getName().value().equals(to)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.format("Undeclared Component: %s", to)));
        relationships.add(new Link(fromComponent.get(), toComponent));
        return this;
    }

    public AcceptanceTestScenarioBuilder withEvolution(String component, String evolution) {
        Component origin = components.stream().filter(c -> c.getName().value().equals(component)).findFirst().orElseThrow( () -> new IllegalArgumentException(String.format("Undeclared Component: %s", component)));
        EvolutionLevel evolutionLevel = EvolutionLevel.of(evolution);
        evolvedComponents.add(new EvolvedComponent(origin,evolutionLevel));
        return this;
    }

    public AcceptanceTestScenarioBuilder withPipeline(String componentName, String min, String max) {
        pipelines.add(new PipelineTemplate(componentName, min, max));
        return this;
    }

    public AcceptanceTestScenarioBuilder withPipeline(String componentName) {
        pipelines.add(new PipelineTemplate(componentName));
        return this;
    }

    public AcceptanceTestScenarioBuilder withFlow(String from, String to) {
        flows.add(new FlowTemplate(from, to));
        return this;
    }

    public AcceptanceTestScenario build() {
        return components.isEmpty() ? new AcceptanceTestScenario(inputFile) : new AcceptanceTestScenario(inputFile, map, anchors, components, relationships, evolvedComponents, inertias, pipelines, flows);
    }
}

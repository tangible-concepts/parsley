package de.tngbl.parsley.acceptance;

import de.tngbl.parsley.wardleymapping.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class AcceptanceTestScenarioBuilder {

    private final String inputFile;
    private final WardleyMap map = new WardleyMap();
    private final List<Anchor> anchors = new LinkedList<>();
    private final List<Component> components = new LinkedList<>();
    private final List<Link> relationships = new LinkedList<>();
    private final List<EvolvedComponent> evolvedComponents = new LinkedList<>();

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
        components.add(new Component(Name.of(name), VisibilityLevel.of(visibility), EvolutionLevel.of(evolution)));
        return this;
    }

    public AcceptanceTestScenarioBuilder withRelationship(String from, String to) {

        Optional<? extends Component> fromComponent = components.stream().filter(c -> c.getName().value().equals(from)).findFirst();

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

    public AcceptanceTestScenario build() {
        return components.isEmpty() ? new AcceptanceTestScenario(inputFile) : new AcceptanceTestScenario(inputFile, map, anchors, components, relationships, evolvedComponents);
    }

}

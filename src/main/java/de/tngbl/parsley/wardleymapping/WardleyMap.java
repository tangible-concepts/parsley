package de.tngbl.parsley.wardleymapping;

import org.jmolecules.ddd.annotation.AggregateRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Optional;


/**
 * TODO:
 * - Method to retrieve Set of components by type, e.g. getAnchors()
 */
@AggregateRoot
public class WardleyMap implements Named, Identifiable {

    private final static Logger LOG = LoggerFactory.getLogger(WardleyMap.class);
    public static final String UNTITLED_MAP = "Untitled Map";

    private Name mapTitle;
    private final Id uid = new Id();
    private final java.util.Map<Name, MapElement> mapElements = new LinkedHashMap<>();

    public void setMapTitle(String title) {

        if (null != this.mapTitle) {
            LOG.info(String.format("Map title already set! Replacing '%s' with '%s'", mapTitle.value(), title));
        }

        this.mapTitle = Name.of(title);
    }


    public void addAnchor(String name, float visibility, float evolution) {

        Name anchorName = Name.of(name);
        VisibilityLevel anchorVisibility = new VisibilityLevel(visibility);
        EvolutionLevel anchorEvolution = EvolutionLevel.of(evolution);

        Anchor anchor = new Anchor(anchorName, anchorVisibility, anchorEvolution);
        mapElements.put(anchorName, anchor);

    }

    public Component addComponent(String name, float visibility, float evolution) {

        Name componentName = Name.of(name);
        VisibilityLevel componentVisibility = new VisibilityLevel(visibility);
        EvolutionLevel componentEvolution = EvolutionLevel.of(evolution);

        Component component = new Component(componentName, componentVisibility, componentEvolution);
        mapElements.put(componentName, component);
        return component;
    }

    public void addLink(String f, String t) {

        Name from = Name.of(f);
        Name to = Name.of(t);

        ComponentBase linkFrom = getComponent(from).orElseThrow((() -> new MapSemanticsViolated(String.format("Cannot create link for undeclared Component '%s'", f))));
        ComponentBase linkTo = getComponent(to).orElseThrow((() -> new MapSemanticsViolated(String.format("Cannot create link for undeclared Component '%s'", t))));

        Link link = new Link(linkFrom, linkTo);

        mapElements.put(link.deriveName(), link);

    }

    // TODO: Delegate responsibility to Component
    public void evolveComponent(String componentName, float evolutionLevel) {

        ComponentBase componentBase = getComponent(componentName).orElseThrow(() -> new MapSemanticsViolated(String.format("Cannot evolve undeclared Component%s", componentName)));
        if (! (componentBase instanceof Component)) {
            throw new MapSemanticsViolated(String.format("Cannot evolve Component: %s", componentBase));
        }

        Component origin = (Component) componentBase;
        EvolutionLevel evoLevel = EvolutionLevel.of(evolutionLevel);
        EvolvedComponent evolvedComponent = new EvolvedComponent(origin, evoLevel);

        Movement movement = new Movement(origin, evolvedComponent);
        origin.addMovement(movement);
        evolvedComponent.addMovement(movement);

        mapElements.put(evolvedComponent.deriveName(), evolvedComponent);

    }

    public Optional<ComponentBase> getComponent(String name) {
        return getComponent(Name.of(name));
    }


    public Optional<ComponentBase> getComponent(Name name) {

        MapElement element = mapElements.get(name);

        if (element instanceof ComponentBase) {
            return Optional.of((ComponentBase) element);
        } else {
            return Optional.empty();
        }

    }

    @Override
    public Name getName() {

        return this.mapTitle == null ? Name.of(UNTITLED_MAP) : mapTitle;
    }

    public java.util.Map<Name, MapElement> getMapElements() {
        return Collections.unmodifiableMap(mapElements);
    }

    @Override
    public Id getId() {
        return uid;
    }

}

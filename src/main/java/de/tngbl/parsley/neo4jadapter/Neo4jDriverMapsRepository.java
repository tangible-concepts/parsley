package de.tngbl.parsley.neo4jadapter;

import de.tngbl.parsley.wardleymapping.*;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Query;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
@Default
public class Neo4jDriverMapsRepository implements Maps {

    public static final String UID_PROPERTY = "uid";
    public final static String NAME_PROPERTY = "name";
    public final static String EVOLUTION_PROPERTY = "evolution";
    public final static String VISIBILITY_PROPERTY = "visibility";
    public final static String EVOLVED_FROM_PROPERTY = "evolved_from";
    public static final String HAS_INERTIA_PROPERTY = "hasInertia";

    private final Driver driver;

    public Neo4jDriverMapsRepository(Driver driver) {
        this.driver = driver;
    }

    @Override
    public void persist(WardleyMap map) {

        try (Session session = driver.session()) {

            // TODO: There should be methods on the domain model aggregate to retrieve these collections of MapElements
            List<Anchor> anchors = filterForElements(map.getMapElements(), Anchor.class);
            List<EvolvedComponent> evolvedComponents = filterForElements(map.getMapElements(), EvolvedComponent.class);
            List<Component> components = filterForElements(map.getMapElements(), Component.class);
            components.removeAll(anchors);
            components.removeAll(evolvedComponents);

            Transaction tx = session.beginTransaction();

            Node mapNode = persistMap(tx, map);
            persistAnchors(tx, anchors, map.getId());
            persistComponents(tx, components, map.getId());
            persistEvolvedComponents(tx, evolvedComponents, map.getId());
            persistLinks(tx, map, mapNode);

            tx.commit();
            session.close();
        }
    }


    private void persistAnchors(Transaction tx, List<Anchor> anchors, Id mapId) {

        Query query = new Query("CREATE (n:ANCHOR:COMPONENT {uid:$uid, name:$name, visibility: $visibility, evolution: $evolution}) RETURN n");
        persistAndLinkComponentsWithMap(tx, anchors, mapId, query);

    }

    private void persistComponents(Transaction tx, List<Component> components, Id mapId) {

        Query query = new Query("CREATE (n:COMPONENT {uid: $uid,  name:$name, visibility: $visibility, evolution: $evolution}) RETURN n");
        persistAndLinkComponentsWithMap(tx, components, mapId, query);

    }

    private void persistEvolvedComponents(Transaction tx, List<EvolvedComponent> evolvedComponents, Id mapId) {

        Query query = new Query("CREATE (n:COMPONENT:EVOLVED_COMPONENT {uid: $uid, name:$name, visibility: $visibility, evolution: $evolution}) RETURN n");
        persistAndLinkComponentsWithMap(tx, evolvedComponents, mapId, query);

        evolvedComponents.forEach(evolved -> {

            Map<String, Object> movementProperties = new HashMap<>();
            movementProperties.put("evolvedUid", evolved.getId().value());
            movementProperties.put("componentUid", evolved.getMovement().getFrom().getId().value());

            Query createMovementQuery = new Query("MATCH (e:COMPONENT:EVOLVED_COMPONENT), (c:COMPONENT) WHERE e.uid = $evolvedUid AND c.uid = $componentUid CREATE (c) -[r:EVOLVES_TO]->(e) RETURN r", movementProperties);
            tx.run(createMovementQuery);
        });

    }

    private void persistAndLinkComponentsWithMap(Transaction tx, List<? extends Component> components, Id mapId, Query query) {

        components.forEach(component -> {
            Map<String, Object> properties = getComponentProperties(component);
            tx.run(query.withParameters(properties));
            createContainsRelationship(tx, mapId, component.getId());
        });
    }

    private void persistLinks(Transaction tx, WardleyMap map, Node mapNode) {

        filterForElements(map.getMapElements(), Link.class).forEach( link -> {

            HashMap<String, Object> linkProperties = new HashMap<String, Object>();
            linkProperties.put("fromUid", link.getFrom().getId().value());
            linkProperties.put("toUid", link.getTo().getId().value());

            Query query = new Query("MATCH (from), (to) WHERE from.uid = $fromUid AND to.uid = $toUid CREATE (from) -[r:NEEDS]->(to) RETURN r", linkProperties);
            tx.run(query);
        });

    }

    private Relationship createContainsRelationship(Transaction tx, Id mapId, Id componentId) {

        HashMap containsProperties = new HashMap();
        containsProperties.put("mapUid", mapId.value());
        containsProperties.put("componentUid", componentId.value());

        Query createContainsQuery = new Query("MATCH (map:MAP), (component:COMPONENT) WHERE map.uid = $mapUid AND component.uid = $componentUid CREATE (map)-[r:CONTAINS]->(component) RETURN r", containsProperties);
        return tx.run(createContainsQuery).single().get(0).asRelationship();
    }

    private <T> List<T> filterForElements(Map<Name, MapElement> elements, Class<T> type) {
        return elements
                .entrySet()
                .stream()
                .filter(e -> type.isInstance(e.getValue()))
                .map(entry -> entry.getValue())
                .map(e -> type.cast(e))
                .collect(Collectors.toList());
    }


    private Node persistMap(Transaction tx, WardleyMap map) {

        Map<String, Object> baseProperties = getBaseProperties(map);
        Query createQuery = new Query("CREATE (n:MAP {name:$name, uid:$uid}) RETURN n", baseProperties);
        return tx.run(createQuery).single().get(0).asNode();
    }


    public <T extends Identifiable & Named> Map<String, Object> getBaseProperties(T element) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(NAME_PROPERTY, element.getName().value());
        properties.put(UID_PROPERTY, element.getId().value());

        return properties;
    }

    public Map<String, Object> getComponentProperties(Component element) {

        Map<String, Object> properties = getBaseProperties(element);
        properties.put(VISIBILITY_PROPERTY, Float.toString(element.getVisibility().value()));
        properties.put(EVOLUTION_PROPERTY, Float.toString(element.getEvolution().value()));
        return properties;
    }


}




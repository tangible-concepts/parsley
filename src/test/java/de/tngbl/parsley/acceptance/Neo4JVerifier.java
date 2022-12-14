package de.tngbl.parsley.acceptance;

import de.tngbl.parsley.neo4jadapter.Neo4jDriverMapsRepository;
import de.tngbl.parsley.neo4jadapter.NodeLabels;
import de.tngbl.parsley.neo4jadapter.RelationshipTypes;
import de.tngbl.parsley.wardleymapping.*;
import org.neo4j.driver.AccessMode;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Query;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.tngbl.parsley.acceptance.AcceptanceTestScenario.*;
import static de.tngbl.parsley.neo4jadapter.Neo4jDriverMapsRepository.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.neo4j.driver.SessionConfig.builder;

public class Neo4JVerifier {

    final Driver driver;
    final WardleyMap map;
    final Node wardleyMapNode;

    public Neo4JVerifier(Driver drive, WardleyMap map) {
        this.driver = drive;
        this.map = map;
        this.wardleyMapNode = getNodeForMap(map);
    }

    public void verifyContainsRelationshiopOnAllMapElements(WardleyMap map) {

        String mapUid = getUidFrom(wardleyMapNode);

        map.getMapElements().entrySet().stream().forEach(e -> {

            Map<String, Object> properties = new HashMap<>();
            properties.put("mapUid", mapUid);
            properties.put("componentName", e.getValue().getId().value());

            Query query = new Query("MATCH (m:MAP) -[r:CONTAINS]-> (c) WHERE m.uid = $mapUid AND c.name = $componentName RETURN r", properties);
            runQueryAndExpectResults(query, 1);

        });
    }

    public void verifyAnchors(List<Anchor> anchors) {

        anchors.stream().forEach(a -> {
            verifyComponentProperties(NodeLabels.ANCHOR, a);
        });
    }

    public void verifyComponents(List<Component> components) {

        components.stream().forEach(c -> {
            verifyComponentProperties(NodeLabels.COMPONENT, c);
        });
    }

    public void verifyMovement(List<EvolvedComponent> evolvedComponents) {

        evolvedComponents.stream().forEach(e -> {
            verifyComponentProperties(NodeLabels.COMPONENT, e);
            e.getMovements().forEach(m -> verifyRelationship(m));
        });

    }

    public void verifyLinks(List<Link> relationships) {

        relationships.stream().forEach(r -> {
            verifyRelationship(r);
        });
    }

    private void verifyRelationship(Link r) {

        try (Transaction tx = driver.session(builder().withDefaultAccessMode(AccessMode.READ).build()).beginTransaction()) {

            String relationType;
            if (r instanceof Movement) {
                relationType = RelationshipTypes.EVOLVES_TO.type();
            } else {
                relationType = RelationshipTypes.NEEDS.type();
            }

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("fromName", r.getFrom().getName().value());
            parameters.put("toName", r.getTo().getName().value());

            // Parameter replacement in Neo4j Driver not supported for IN Clauses, thus replacement beforehand
            String queryText = "MATCH (f:COMPONENT)-[r]->(t:COMPONENT) WHERE f.name = $fromName AND t.name = $toName AND type(r) IN ['$type'] RETURN r" .replace("$type", relationType);
            Query query = new Query(queryText, parameters);

            List<Relationship> matches = tx.run(query).stream().map(l -> l.get(0).asRelationship()).collect(Collectors.toList());

            assertTrue(matches.size() >= 1, String.format("Expected to find at least one realtionship: %s, Query: %s", r.toString(), query.toString()));
        }

    }

    public void verifyComponentProperties(NodeLabels type, ComponentBase component) {

        String label = type.label();
        String rawQuery = "MATCH (a:$label) WHERE a.name = $name AND a.visibility = $visibility AND a.evolution = $evolution RETURN a" .replace("$label", label);

        Query query = new Query(rawQuery, getComponentProperties(component));
        runQueryAndExpectResults(query, 1);

    }


    private Map<String, Object> getComponentProperties(ComponentBase component) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(NAME_PROPERTY, component.getName().value());
        properties.put(VISIBILITY_PROPERTY, Float.toString(component.getVisibility().value()));
        properties.put(EVOLUTION_PROPERTY, Float.toString(component.getEvolution().value()));
        return properties;
    }

    private void runQueryAndExpectResults(Query query, int minResults) {

        try (Transaction tx = driver.session(builder().withDefaultAccessMode(AccessMode.READ).build()).beginTransaction()) {
            int size = tx.run(query).list().size();
            assertTrue(size >= minResults, String.format("Expected query to return %d result(s) but was %d: %s", minResults, size, query.toString()));
        }

    }

    private Node getNodeForMap(WardleyMap map) {
        try (Transaction tx = driver.session(builder().withDefaultAccessMode(AccessMode.READ).build()).beginTransaction()) {
            Map<String, Object> mapProperties = new HashMap<>();
            mapProperties.put("mapName", map.getName().value());
            Query mapQuery = new Query("MATCH (m:MAP) WHERE m.name =$mapName RETURN m", mapProperties);
            return tx.run(mapQuery).stream().map(s -> s.get(0).asNode()).findFirst().orElseThrow();
        }
    }

    private String getUidFrom(Node node) {
        return node.get(Neo4jDriverMapsRepository.UID_PROPERTY).asString();
    }

    public void verifyInertias(List<InertiaTemplate> inertias) {
        inertias.forEach(i -> verifyInertia(i));
    }

    private void verifyInertia(InertiaTemplate i) {
        try (Transaction tx = driver.session(builder().withDefaultAccessMode(AccessMode.READ).build()).beginTransaction()) {

            Map<String, Object> parameter = new HashMap<>();
            parameter.put("componentName", i.getComponent().getName().value());
            Query query = new Query("MATCH (c:COMPONENT)-[m:EVOLVES_TO]->(:EVOLVED_COMPONENT) WHERE c.name = $componentName AND EXISTS(m.hasInertia) RETURN m", parameter);
            assertTrue(tx.run(query).list().size() >= 1, String.format("Expected at least one Inertia for Component: %s", i.getComponent().getName().value()));

        }
    }

    public void verifyPipelines(List<PipelineTemplate> pipelines) {
        // not yet supported
    }

    public void verifyFlows(List<FlowTemplate> flows) {
        //  not yet supported
    }
}

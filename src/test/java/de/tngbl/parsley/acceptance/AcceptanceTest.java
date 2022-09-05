package de.tngbl.parsley.acceptance;

import de.tngbl.parsley.launcher.Launcher;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.neo4j.driver.AccessMode;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Query;
import org.neo4j.driver.Transaction;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.neo4j.driver.SessionConfig.builder;

@QuarkusTest
public class AcceptanceTest {

    @Inject
    Launcher launcher;

    @Inject
    Driver driver;

    private Neo4JVerifier verifier;

    @ParameterizedTest
    @ArgumentsSource(AcceptanceTestScenarioProvider.class)
    public void acceptanceTest(AcceptanceTestScenario scenario) throws Exception {
        
        wipeDB();

        assumeTrue(launcher != null);

        int result = launcher.run("-f", scenario.getInputFile());
        assertEquals(0, result);

        if (!scenario.isImportOnly()) {
            verifyGraphIntegrity(scenario);
        }
    }

    private void wipeDB() {

        Query query1 = new Query("match (a) -[r] -> () delete a, r");
        Query query2 = new Query("match (a) delete a");

        try (Transaction tx = driver.session(builder().withDefaultAccessMode(AccessMode.WRITE).build()).beginTransaction()){
            tx.run(query1);
            tx.run(query2);
            tx.commit();
        }
    }

    // TODO: Ensure, that each Component is connected to the Map via CONTAINS Relationship
    // TODO: Support Inertia
    // TODO: Support Pipelines
    // TODO: Support market
    private void verifyGraphIntegrity(AcceptanceTestScenario scenario) {

        Neo4JVerifier verifier = new Neo4JVerifier(driver, scenario.getMap());

        verifier.verifyContainsRelationshiopOnAllMapElements(scenario.getMap());
        verifier.verifyAnchors(scenario.getAnchors());
        verifier.verifyComponents(scenario.getComponents());
        verifier.verifyMovement(scenario.getEvolvedComponents());
        verifier.verifyLinks(scenario.getRelationships());
        verifier.verifyInertias(scenario.getInertias());
        verifier.verifyPipelines(scenario.getPipelines());
        verifier.verifyFlows(scenario.getFlows());

    }

    @Test
    public void testWithFixture() throws Exception {

        AcceptanceTestScenario scenario = new AcceptanceTestScenarioBuilder("src/test/resources/personal/ESG - Sustainability").build();

        int result = launcher.run("-f", scenario.getInputFile());
        assertEquals(0, result);

        if (!scenario.isImportOnly()) {
            verifyGraphIntegrity(scenario);
        }
    }

}
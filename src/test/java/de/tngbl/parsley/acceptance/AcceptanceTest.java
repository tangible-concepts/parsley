package de.tngbl.parsley.acceptance;

import de.tngbl.parsley.launcher.Launcher;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.neo4j.driver.Driver;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

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

        assumeTrue(launcher != null);

        int result = launcher.run("-f", scenario.getInputFile());
        assertEquals(0, result);

        if (!scenario.isImportOnly()) {
            verifyGraphIntegrity(scenario);
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
        //verifier.verifyMovement(scenario.getEvolvedComponents());
        verifier.verifyLinks(scenario.getRelationships());

    }

    @Test
    public void testWithFixture() throws Exception {

//        AcceptanceTestScenario scenario = new AcceptanceTestScenarioBuilder("src/test/resources/tmp").build();
        AcceptanceTestScenario scenario = new AcceptanceTestScenarioBuilder("src/test/resources/tea-shop").build();

        int result = launcher.run("-f", scenario.getInputFile());
        assertEquals(0, result);

        if (!scenario.isImportOnly()) {
            verifyGraphIntegrity(scenario);
        }
    }

}
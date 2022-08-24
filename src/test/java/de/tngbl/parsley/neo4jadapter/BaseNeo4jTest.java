package de.tngbl.parsley.neo4jadapter;

import org.junit.jupiter.api.BeforeEach;
import org.neo4j.driver.AccessMode;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Transaction;

import javax.inject.Inject;

import static org.neo4j.driver.SessionConfig.builder;

public abstract class BaseNeo4jTest {

    @Inject
    Driver driver;

    @BeforeEach
    public void truncateDB() {

        try (Transaction tx = driver.session(builder().withDefaultAccessMode(AccessMode.WRITE).build()).beginTransaction()) {
            tx.run("MATCH (n) DETACH DELETE n");
            tx.commit();
        }

    }
}

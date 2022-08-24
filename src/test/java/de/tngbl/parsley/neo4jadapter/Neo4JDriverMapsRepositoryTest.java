package de.tngbl.parsley.neo4jadapter;

import de.tngbl.parsley.TestUtils;
import de.tngbl.parsley.onlinewardleymaps.Expression;
import de.tngbl.parsley.onlinewardleymaps.Interpreter;
import de.tngbl.parsley.onlinewardleymaps.Parser;
import de.tngbl.parsley.wardleymapping.WardleyMap;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.Driver;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@QuarkusTest
public class Neo4JDriverMapsRepositoryTest {

    @Inject
    Driver driver;

    @Test
    public void shouldPersist() throws IOException {

        Parser parser = new Parser(TestUtils.TEA_SHOP_FILE);
        List<Expression> expressions = parser.parse();
        Interpreter interpreter = new Interpreter(expressions);

        WardleyMap map = interpreter.interpret();

        Neo4jDriverMapsRepository repository = new Neo4jDriverMapsRepository(driver);
        repository.persist(map);

    }
}

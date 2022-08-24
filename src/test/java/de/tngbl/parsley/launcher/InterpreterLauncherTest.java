package de.tngbl.parsley.launcher;

import de.tngbl.parsley.TestUtils;
import de.tngbl.parsley.neo4jadapter.BaseNeo4jTest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.io.File;
import java.util.List;

@QuarkusTest
public class InterpreterLauncherTest extends BaseNeo4jTest {

    @Inject
    InterpreterLauncher launcher;

    @Test
    public void shouldLaunchAndRun() {

        File testFile = TestUtils.TEA_SHOP_FILE;
        launcher.run(List.of(testFile));

    }
}

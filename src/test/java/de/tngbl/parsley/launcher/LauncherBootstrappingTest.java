package de.tngbl.parsley.launcher;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class LauncherBootstrappingTest {

    @Inject
    Launcher launcher;

    @Test
    public void shouldBootstrap() {

        assertNotNull(launcher);
    }

}

package de.tngbl.parsley;

import de.tngbl.parsley.launcher.Launcher;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class ParsleyApplication {

    public static void main(String... args) {
        Quarkus.run(Launcher.class, args);
        Quarkus.waitForExit();
    }

}

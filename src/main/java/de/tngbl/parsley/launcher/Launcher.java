package de.tngbl.parsley.launcher;

import io.quarkus.logging.Log;
import io.quarkus.runtime.QuarkusApplication;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Launcher implements QuarkusApplication {

    @ConfigProperty(name="neo4j.url")
    String neo4jUrl;

    @ConfigProperty(name="neo4j.user")
    String neo4User;

    @ConfigProperty(name="neo4j.password")
    String neo4jPassword;

    @Inject
    InterpreterLauncher interpreterLauncher;

    @Produces
    @ApplicationScoped
    public Driver driver() {
        return GraphDatabase.driver(neo4jUrl, AuthTokens.basic(neo4User, neo4jPassword));
    }

    @Override
    public int run(String... args) throws Exception {
        Log.info("Runs with args: " + Arrays.stream(args).map(s->s.toString()).collect(Collectors.joining()));
        parseArgsAndBootstrap(args);
        return 0;
    }

    private void parseArgsAndBootstrap(String[] args) {

        List<File> sources = new LinkedList<>();

        // TODO replace with Picoli
        Iterator<String> argsIter = Arrays.stream(args).iterator();
        while (argsIter.hasNext()) {

            String arg = argsIter.next();
            if (arg.equals("-f")) {
                String files = argsIter.next();
                sources.add(new File(files));
            }
        }

        if (!sources.isEmpty()) {
            interpreterLauncher.run(sources);
        } else {
            throw new LaunchTerminated("No input file or directory was specified! Use the '-f' switch to define an input file location.");
        }

    }

}

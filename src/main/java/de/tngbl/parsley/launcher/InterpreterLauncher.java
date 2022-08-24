package de.tngbl.parsley.launcher;

import de.tngbl.parsley.onlinewardleymaps.Expression;
import de.tngbl.parsley.onlinewardleymaps.Interpreter;
import de.tngbl.parsley.onlinewardleymaps.Parser;
import de.tngbl.parsley.wardleymapping.Maps;
import de.tngbl.parsley.wardleymapping.WardleyMap;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

@ApplicationScoped
public class InterpreterLauncher {

    @Inject
    Maps mapsRepository;

    public void run(List<File> sources) {

        List<File> inputSources = collectInputSources(sources);

        for (File file : inputSources)  {

            if (file.isDirectory()) {
                Log.error(String.format("Ignoring directory: %s", file.getAbsolutePath()));
                return;
            }

            try {
                Parser parser = new Parser(file);
                List<Expression> expressions = parser.parse();
                Interpreter interpreter = new Interpreter(expressions);
                WardleyMap map = interpreter.interpret();
                mapsRepository.persist(map);
            } catch (IOException e) {
                throw new LaunchTerminated("-f requires an existing file or directory", e);
            }
        }
    }

    private List<File> collectInputSources(List<File> sources) {

        List<File> inputSources = new LinkedList<>();

        for (File f : sources) {
            if (! f.exists()) {
                continue;
            } else if (f.isFile()) {
                inputSources.add(f);
            } else if(f.isDirectory()) {

                try (Stream<Path> paths = Files.walk(f.toPath())) {
                    paths.filter(Files::isRegularFile).forEach(d -> inputSources.add(d.toFile()));
                } catch (IOException e) {
                    Log.error(String.format("Cannot read file: %s", f.getAbsolutePath()));
                }

            }
        }

        return inputSources;

    }

    public Maps getMapsRepository() {
        return mapsRepository;
    }
}

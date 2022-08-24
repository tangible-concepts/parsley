package de.tngbl.parsley.launcher;

import java.io.IOException;

public class LaunchTerminated extends RuntimeException {

    public LaunchTerminated(String s) {
        super(s);
    }

    public LaunchTerminated(String s, IOException e) {
        super(s,e);
    }
}

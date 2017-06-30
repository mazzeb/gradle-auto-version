package com.github.mazzeb.gradle.autoversion;

import java.io.Console;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class InteractiveVersionReader {

    private final Console console;

    private InteractiveVersionReader(final Console console) {
        this.console = console;
    }

    public static InteractiveVersionReader interactiveVersionReader() {
        return new InteractiveVersionReader(System.console());
    }

    public static InteractiveVersionReader interactiveVersionReader(final Console console) {
        return new InteractiveVersionReader(console);
    }

    public Version readVersion(String prompt, Version oldVersion) {
        String line = console.readLine(String.format("%s (%s): ", prompt, oldVersion.toString()));
        return parseVersion(line);
    }

    public Version parseVersion(String versionString) {
        String[] split = versionString.split("\\.", 3);
        Version.Builder builder = Version.versionBuilder();
        if (split.length > 0) {
            builder.withMajor(Long.valueOf(split[0]));
        }
        if (split.length > 1) {
            builder.withMinor(Long.valueOf(split[1]));
        }
        if (split.length > 2) {
            String s = split[2];
            String[] splitLabel = s.split("-", 2);
            if (splitLabel.length > 0) {
                builder.withPatch(Long.valueOf(splitLabel[0]));
            }
            if (splitLabel.length > 1) {
                builder.withLabel(splitLabel[1]);
            }
        }
        return builder.build();
    }
}
